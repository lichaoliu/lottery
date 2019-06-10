package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.*;

import com.lottery.common.contains.lottery.*;
import com.lottery.common.util.CoreDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.AgencyExchanged;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.TerminalConfigDAO;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.dao.TicketBatchDAO;
import com.lottery.core.dao.TicketDAO;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketAllotLog;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.lottype.LotManager;
import com.lottery.lottype.SplitedLot;

@Service
public class OrderSplitService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private LotteryOrderDAO lotteryorderdao;
	
	@Autowired
	private LotManager lotManager;

	
	@Autowired
	private TicketDAO ticketdao;
	@Autowired
	private TicketBatchDAO ticketBatchDao;

	@Autowired
	private JingcaiGuanjunService jingcaiGuanjunService;
	
	@Autowired
	private IdGeneratorDao idDao;
	@Autowired
	protected TerminalDAO terminalDAO;
	@Autowired
	protected TerminalConfigDAO terminalConfigDAO;
	@Autowired
	protected ITerminalSelector terminalSelector;
	@Autowired
	private TicketAllotLogService ticketAllotLogService;
	
	@Autowired
	private JingcaiService jingcaiService;

	@Autowired
	private BeidanService beidanService;

	@Transactional
	public List<Ticket> ticketSplit(LotteryOrder lotteryOrder, List<SplitedLot> splitLotList, Long id) {

		String orderid = lotteryOrder.getId();
		String minteamid = "0";
		String maxteamid = "0";
		try {
			minteamid= CoreDateUtils.formatDate(new Date(),"yyMMddHHmm");
			maxteamid= CoreDateUtils.formatDate(new Date(),"yyMMddHHmm");
		}catch (Exception e){

		}

		List<Ticket> ticketList=new ArrayList<Ticket>();
		try {

			Map<String, Object> contidion = new HashMap<String, Object>();
			contidion.put("orderid", orderid);
			long ticketnum = ticketdao.getCountByCondition(contidion);
			if (ticketnum > 0) {
				logger.error("订单{}已经拆票,票的个数为{},更新订单状态为拆票,本次拆票退出", new Object[] { orderid, ticketnum });
				lotteryorderdao.updateOrderStatus(OrderStatus.PRINTING.value, orderid);
				return null;
			}

			// 注码验证
			// 注码格式 注码_倍数_单注金额_单倍金额，多注用！分割,倍数目前传1
			Integer terminalTypeId = lotteryOrder.getTerminalTypeId();
            int ticketCount=splitLotList.size();
			Long batchId=0l;
			if (terminalTypeId != null && terminalTypeId == -1){
				batchId=idDao.getBatchTicketId(ticketCount);
			}

			LotteryType lotteryType = LotteryType.getLotteryType(lotteryOrder.getLotteryType());
			for (SplitedLot lot : splitLotList) {
				Ticket ticket = new Ticket();
				ticket.setOrderAmount(lotteryOrder.getAmount());
				ticket.setTicketResultStatus(TicketResultStatus.not_draw.getValue());
				ticket.setAddition(lotteryOrder.getAddition());
				ticket.setAmount(new BigDecimal(lot.getAmt()));
				ticket.setContent(lot.getBetcode());
				ticket.setCreateTime(new Date());
				String ticketId = idDao.getTicketId(id);
				ticket.setId(ticketId);
				id++;
				if (LotteryType.JCLQ_HHGG == lotteryType || LotteryType.JCZQ_HHGG == lotteryType) {
					ticket.setLotteryType(lot.getLotterytype());
				} else {
					ticket.setLotteryType(lotteryOrder.getLotteryType());
				}
				ticket.setAgentId(lotteryOrder.getAgentId());
				ticket.setUserName(lotteryOrder.getUserName());
				ticket.setRealName(lotteryOrder.getRealName());
				ticket.setMultiple(lot.getLotMulti());
				ticket.setOrderId(orderid);
				ticket.setAgencyExchanged(YesNoStatus.no.value);
				ticket.setAddPrize(BigDecimal.ZERO);
				ticket.setUserno(lotteryOrder.getUserno());
				ticket.setPhase(lotteryOrder.getPhase());
				ticket.setPlayType(Integer.parseInt(lot.getBetcode().split("\\-")[0]));
				ticket.setDeadline(lotteryOrder.getDeadline());
				ticket.setTerminateTime(lotteryOrder.getDeadline());
				ticket.setIsExchanged(YesNoStatus.no.value);
				ticket.setAgencyExchanged(AgencyExchanged.exchange_no.value);
				ticket.setTicketEndTime(getTicketEndTime(ticket,lotteryOrder));
				ticket.setBatchId("0");
				ticket.setBatchIndex(0l);
				ticket.setMatchNums(lotManager.getLot(String.valueOf(ticket.getLotteryType())).getAllMatches(lot.getBetcode(),YesNoStatus.no.value));

				if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
					minteamid = jingcaiService.getMinTeamid(lot.getBetcode(), lotteryType,YesNoStatus.no.value);
					maxteamid = jingcaiService.getMaxTeamid(lot.getBetcode(), lotteryType,YesNoStatus.no.value);
				} else if (LotteryType.getDc().contains(lotteryType)) {
					minteamid = beidanService.getMinTeamid(lot.getBetcode(), lotteryType, lotteryOrder.getPhase(),YesNoStatus.no.value);
					maxteamid = beidanService.getMaxTeamid(lot.getBetcode(), lotteryType, lotteryOrder.getPhase(),YesNoStatus.no.value);
				} else if(LotteryType.getGuanyajun().contains(lotteryType)) {
					minteamid = jingcaiGuanjunService.getMinTeamid(lot.getBetcode());
					maxteamid = jingcaiGuanjunService.getMaxTeamid(lot.getBetcode());
				}
				ticket.setFirstMatchnum(Long.parseLong(minteamid));
				ticket.setLastMatchnum(Long.parseLong(maxteamid));
				ticket.setStatus(TicketStatus.UNALLOTTED.getValue());


				ticketList.add(ticket);
				ticketdao.merge(ticket);
			}
            lotteryorderdao.updateOrderStatusAndTicketCount(orderid,OrderStatus.PRINTING.value,ticketCount);


			Long batchIndex = 0l;
			if (terminalTypeId != null && terminalTypeId != TerminalType.all.value) {//指定出票类型
				TicketBatch tb = ticketBatch(lotteryOrder, terminalTypeId,batchId);
				if (tb != null) {
					saveTicketBatch(ticketList,tb,batchIndex);
				}
			}else if(StringUtils.isNoneBlank(lotteryOrder.getTerminalId())&&!lotteryOrder.getTerminalId().equals("0")){//指定出票终端
				TicketBatch ticketBatch = new TicketBatch();
				ticketBatch.setCreateTime(new Date());
				ticketBatch.setPhase(lotteryOrder.getPhase());
				ticketBatch.setTerminateTime(lotteryOrder.getDeadline());
				ticketBatch.setLotteryType(lotteryOrder.getLotteryType());
				Long terminalId=Long.valueOf(lotteryOrder.getTerminalId());
				ticketBatch.setTerminalId(terminalId);
				ticketBatch.setId(idDao.getId(batchId));
				ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value);
				Terminal terminal = terminalSelector.getTerminal(terminalId);
				if (terminal != null)
					ticketBatch.setTerminalTypeId(terminal.getTerminalType());
				ticketBatchDao.insert(ticketBatch);
				saveTicketBatch(ticketList,ticketBatch,batchIndex);
			}



		} catch (Exception e) {
			logger.error("订单orderId={}拆票失败", orderid, e);
			throw new LotteryException(orderid + "拆票失败");
		}
		return ticketList;
	}


	private void  saveTicketBatch(List<Ticket> ticketList,TicketBatch ticketBatch,Long batchIndex){
		for(Ticket ticket:ticketList){
			ticket.setStatus(TicketStatus.UNSENT.getValue());
			ticket.setBatchId(ticketBatch.getId());
			ticket.setBatchIndex(batchIndex++);
			TicketAllotLog allotLog = new TicketAllotLog();
			allotLog.setBatchId(ticketBatch.getId());
			allotLog.setCreateTime(new Date());
			allotLog.setTerminalId(ticketBatch.getTerminalId());
			allotLog.setTicketId(ticket.getId());
			ticketAllotLogService.save(allotLog);
		}
	}

	protected TicketBatch ticketBatch(LotteryOrder lotteryOrder, int terminalType,Long batchId) {
		TerminalConfig terminalConfig = null;
		try {
			Long[] ids = terminalDAO.getTerminalIdByType(terminalType);
			if (ids != null && ids.length > 0) {
				Long id = ids[0];
				try {
					terminalConfig = terminalConfigDAO.get(lotteryOrder.getLotteryType(), id, PlayType.mix.getValue());
				} catch (Exception e) {
					logger.error("查找终端配置出错", e);
				}

			}

			if (terminalConfig != null) {
				TicketBatch ticketBatch = new TicketBatch();
				ticketBatch.setCreateTime(new Date());
				ticketBatch.setPhase(lotteryOrder.getPhase());
				ticketBatch.setTerminateTime(lotteryOrder.getDeadline());
				ticketBatch.setLotteryType(lotteryOrder.getLotteryType());
				ticketBatch.setTerminalId(terminalConfig.getTerminalId());
				ticketBatch.setId(idDao.getId(batchId));
				ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value);
				Terminal terminal = terminalSelector.getTerminal(terminalConfig.getTerminalId());
				if (terminal != null)
					ticketBatch.setTerminalTypeId(terminal.getTerminalType());
				ticketBatchDao.insert(ticketBatch);
				return ticketBatch;
			}
		} catch (Exception e) {
			logger.error("订单{}指定出票地出错", lotteryOrder.getId(), e);
		}

		return null;

	}
    @Transactional
	public void ticketMerge(List<Ticket> ticketList){
		BigDecimal amount=BigDecimal.ZERO;
		int playType=0;
		int lotteryType=0;
		String content=unionContent(ticketList);
		String phase=null;
		Date date=new Date();
		String ids="";
		int multiple=0;
		String ticketId ="HB"+ idDao.getMessageId();
		for (Ticket ticket:ticketList){
			ids+=ticket.getId()+",";
			playType=ticket.getPlayType();
            date=ticket.getDeadline();
			lotteryType=ticket.getLotteryType();
			multiple=ticket.getMultiple();
			phase=ticket.getPhase();

			amount=amount.add(ticket.getAmount());
			ticket.setStatus(TicketStatus.UNINIT.value);
			ticket.setBatchId(ticketId);
			ticketdao.merge(ticket);

		}

		Ticket ticket = new Ticket();
		ticket.setOrderAmount(amount);
		ticket.setTicketResultStatus(TicketResultStatus.not_draw.getValue());
		ticket.setAddition(0);
		ticket.setAmount(amount);
		ticket.setContent(content);
		ticket.setCreateTime(new Date());
		ticket.setStatus(TicketStatus.UNALLOTTED.value);
		ticket.setId(ticketId);
		ticket.setLotteryType(lotteryType);
		ticket.setAgentId(ticketId);
		ticket.setUserName("票合并");
		ticket.setRealName("票合并");
		ticket.setMultiple(multiple);
		ticket.setOrderId(ticketId);
		ticket.setAgencyExchanged(YesNoStatus.no.value);
		ticket.setAddPrize(BigDecimal.ZERO);
		ticket.setUserno("123456");
		ticket.setPhase(phase);
		ticket.setPlayType(playType);
		ticket.setDeadline(date);
		ticket.setTerminateTime(date);
		ticket.setIsExchanged(YesNoStatus.no.value);
		ticket.setTicketEndTime(date);
		ticket.setBatchId("0");
		ticket.setPeilv(ids);
		ticket.setBatchIndex(0l);
		ticketdao.insert(ticket);

	}
	
	private Date getTicketEndTime(Ticket ticket,LotteryOrder lotteryOrder) {
		Date dealDate = lotteryOrder.getDeadline();
		try {
			LotteryType lotteryType = LotteryType.getLotteryType(ticket.getLotteryType());
			if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
				String minteamid = jingcaiService.getMinTeamid(ticket.getContent(), lotteryType,YesNoStatus.no.value);
				dealDate = jingcaiService.getDealTeime(minteamid, lotteryType);
			} else if (LotteryType.getDc().contains(lotteryType)) {
				String minteamid = beidanService.getMinTeamid(ticket.getContent(), lotteryType, lotteryOrder.getPhase(),YesNoStatus.no.value);
				dealDate = beidanService.getDealTeime(minteamid, lotteryType, lotteryOrder.getPhase());
			} else {
				dealDate = lotteryOrder.getDeadline();
			}
		}catch(Exception e) {
			logger.error("获取票截止时间出错{}",ticket.getId(),e);
		}
		return dealDate;
	}
	
	
	private String unionContent(List<Ticket> tickets) {
		String type = tickets.get(0).getContent().split("\\-")[0];
		StringBuilder content = new StringBuilder(type+"-");
		
		for(Ticket ticket:tickets) {
			content.append(ticket.getContent().split("\\-")[1]);
		}
		return content.toString();
	}

}
