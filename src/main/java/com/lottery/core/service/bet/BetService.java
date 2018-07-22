package com.lottery.core.service.bet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lottery.core.handler.OrderProcessHandler;
import com.lottery.core.handler.SystemExceptionMessageHandler;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.AgencyType;
import com.lottery.common.contains.ChaseStatus;
import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.contains.lottery.ChaseBuyStatus;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.LottypeConfigStatus;
import com.lottery.common.contains.lottery.NoticeStatus;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.coupon.UserCouponLineContains;
import com.lottery.common.contains.lottery.coupon.UserCouponStatus;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryChaseBuyDAO;
import com.lottery.core.dao.LotteryChaseDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.dao.coupon.UserCouponDao;
import com.lottery.core.dao.merchant.MerchantOrderDAO;
import com.lottery.core.domain.LotteryChase;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.coupon.UserCoupon;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.service.BeidanService;
import com.lottery.core.service.JingcaiGuanjunService;
import com.lottery.core.service.JingcaiMatchVerifyService;
import com.lottery.core.service.JingcaiService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.lottype.LotManager;

@Service
public class BetService {
	private final Logger logger = LoggerFactory.getLogger(BetService.class);
	@Autowired
	private UserInfoDao userinfoDao;

	@Autowired
	private LotteryOrderDAO lotteryOrderDao;
	@Autowired
	private LotManager lotManager;
	@Autowired
	private LotteryPhaseHandler phaseHandler;


	@Autowired
	private JingcaiMatchVerifyService jingcaiMatchVerifyService;

	@Autowired
	private JingcaiService jingcaiService;

	@Autowired
	private BeidanService beidanService;
	@Autowired
	private QueueMessageSendService queueMessageSendService;

	@Autowired
	private MerchantOrderDAO merchantOrderDAO;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
    @Autowired
	private UserAccountDAO userAccountDAO;
	@Autowired
    private UserAccountDetailDAO userAccountDetailDAO;
	@Autowired
	private LotteryChaseBuyDAO lotteryChaseBuyDAO;
	@Autowired
	private LotteryChaseDao lotteryChaseDao;
	@Autowired
	private LotteryPhaseService lotteryPhaseService;
	
	@Autowired
	private JingcaiGuanjunService jingcaiGuanjunService;

	@Autowired
	private OrderProcessHandler orderProcessHandler;
	@Autowired
	private SystemExceptionMessageHandler systemExceptionMessageHandler;
	// 投注接口
	@Transactional
	public LotteryOrder toBet(String userno, String betcode, String orderId, int lotteryType, String phase, int multiple, BigDecimal amount,int oneAmout, BetType bettype,String buyagencyno,int prizeOptimize,int codeFilter) {

		LotteryOrder order = createLotteryOrder(userno, betcode, orderId, lotteryType, phase, multiple, amount, oneAmout, bettype,buyagencyno,orderId,prizeOptimize,codeFilter);
		return order;
	}

	public void sendJms(String orderId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			queueMessageSendService.sendMessage(QueueName.betSplitQueue, map);
		} catch (Exception e) {
			logger.error("发送拆票jms消息出错orderId={}", orderId, e);
			orderProcessHandler.split(orderId);
			String errormessage=String.format("发送jms拆票消息失败,errormessage=%s",e.getMessage());
			systemExceptionMessageHandler.saveMessage(errormessage);
		}
	}

	public  void sendFreezeJms(String orderId,int lotteryType){
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			if (HighFrequencyLottery.contains(LotteryType.get(lotteryType))){
				queueMessageSendService.sendMessage(QueueName.betgaopinFreeze, map);
			}else{
				queueMessageSendService.sendMessage(QueueName.betFreeze, map);
			}


		} catch (Exception e) {
			logger.error("发送扣款jms消息出错orderId={}", orderId, e);

			orderProcessHandler.freezeAndSplit(orderId);
			String errormessage=String.format("发送jms拆票冻结消息失败,errormessage=%s",e.getMessage());
			systemExceptionMessageHandler.saveMessage(errormessage);
		}
	}

	public void sendJms(LotteryOrder order) {
		String orderId=null;
		try {
			orderId=order.getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			String queueName=QueueName.betSplitQueue.value;
			queueMessageSendService.sendMessage(queueName, map);
		} catch (Exception e) {
			logger.error("发送jms消息出错orderId={}", orderId, e);
		}
	}
	/**
	 * 彩种投注判断
	 * @param lotteryTypeNo 彩种
	 * @param betType 投注方式
	 * */
    @Transactional
	public void lotteryTypeValidate(int lotteryTypeNo,int betType){
		LotteryType lotteryType = LotteryType.getLotteryType(lotteryTypeNo);
		if (lotteryType == null) {
			throw new LotteryException(ErrorCode.lotterytype_not_exits, "该彩种不存在:" + lotteryTypeNo);
		}

		LottypeConfig lottypeConfig = null;
		try {
			lottypeConfig = phaseHandler.filterConfig(lotteryTypeNo);
		} catch (Exception e1) {

		}

		if (lottypeConfig != null ) {
			if (lottypeConfig.getState() != null&&lottypeConfig.getState() != LottypeConfigStatus.nomarl.value) {
				throw new LotteryException(ErrorCode.paused_sale, "该彩种暂停:lotteryType=" + lotteryTypeNo);
			}
			if (betType==BetType.chase.value){
				if(lottypeConfig.getChaseEndSale()!=null&&lottypeConfig.getChaseEndSale()!=YesNoStatus.no.value){
					throw new LotteryException(ErrorCode.chase_not_support, "该彩种不允许追号:lotteryType=" + lotteryTypeNo);
				}
			}
			if (betType==BetType.hemai.value){
				if(lottypeConfig.getHemaiEndSale()!=null&&lottypeConfig.getHemaiEndSale()!=YesNoStatus.no.value){
					throw new LotteryException(ErrorCode.casetlot_not_support, "该彩种不允许合买:lotteryType=" + lotteryTypeNo);
				}
			}
		}



	}
	/**
	 * 注码判断
	 * */
	@Transactional
	public void codeValidate(int lotteryTypeNo,String phase,String betcode,int multiple, int oneAmout, BigDecimal amount,int prizeOptimize){
		LotteryType lotteryType = LotteryType.getLotteryType(lotteryTypeNo);
		if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)){
			// 竞彩判断赛程
			jingcaiMatchVerifyService.validateIsJcMatchOutTimeAndTypeNotSupport(betcode, lotteryType,prizeOptimize);
		}
		if (LotteryType.getDc().contains(lotteryType)){
			beidanService.validateIsDcMatchOutTime(lotteryType, phase, betcode, prizeOptimize);
		}
		if(LotteryType.getGuanyajun().contains(lotteryType)) {
			jingcaiMatchVerifyService.validateIsJcGuanyajunMatchOutTimeAndTypeNotSupport(betcode, lotteryType, phase);
		}

		boolean validate = false;
		try {
			//奖金优化单独验证
			if(prizeOptimize==YesNoStatus.yes.value) {
				validate = lotManager.getLot(String.valueOf(lotteryTypeNo)).validatePrizeOptimize(betcode, amount, new BigDecimal(multiple), oneAmout);
			}else {
				validate = lotManager.getLot(String.valueOf(lotteryTypeNo)).validateWithLimited(betcode, amount, new BigDecimal(multiple), oneAmout);
			}
		} catch (LotteryException e) {
			logger.error("校验出现异常:betcode={} 倍数multiple={},amount={},oneAmout={} memo={}", new Object[] { betcode, multiple, amount, oneAmout,e.getErrorCode().getMemo() });
			throw e;
		} catch (Exception e) {
			logger.error("注码金额校验错误:betcode={} 倍数multiple={},amount={},oneAmout={}", new Object[] { betcode, multiple, amount, oneAmout });
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}

		if (!validate) {
			logger.error("注码金额校验错误:betcode={} 倍数multiple={},amount={},oneAmout={}", new Object[]{betcode, multiple, amount, oneAmout});
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}

	}


    @Transactional
	public LotteryOrder createLotteryOrder(String userno, String betcode, String orderId, int lotteryTypeNo, String phaseNo, int multiple, BigDecimal amount, int oneAmout, BetType bettype,String buyagencno,String agentId,int prizeOptimize,int codeFilter) {
		if (StringUtils.isBlank(orderId)) {
			throw new LotteryException(ErrorCode.paramter_error, "主键不能为空");
		}
		if(multiple<0){
			throw new LotteryException(ErrorCode.paramter_error,"倍数不能为负");
		}
		LotteryType lotteryType = LotteryType.getLotteryType(lotteryTypeNo);
		int amt=amount.intValue()/multiple;

		Date dealDate = null;
		String minteamid = "0";
		String maxteamid = "0";
		try {
			minteamid= CoreDateUtils.formatDate(new Date(),"yyMMddHHmm");
			maxteamid= CoreDateUtils.formatDate(new Date(),"yyMMddHHmm");
		}catch (Exception e){

		}

		if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
			minteamid = jingcaiService.getMinTeamid(betcode, lotteryType,prizeOptimize);
			maxteamid = jingcaiService.getMaxTeamid(betcode, lotteryType,prizeOptimize);
			dealDate = jingcaiService.getDealTeime(minteamid, lotteryType);
			phaseNo = maxteamid.substring(0, 8);
			LotteryPhase phase = null;
			try {
				int phaseType = LotteryType.getPhaseType(lotteryTypeNo).getValue();
				phase = lotteryPhaseService.getByTypeAndPhase(phaseType,phaseNo);
			} catch (Exception e) {

			}
			if (phase == null) {
				throw new LotteryException(ErrorCode.no_phase, "该彩期不存在,lotteryType="+lotteryTypeNo+"phaseNo=" + phaseNo);
			}
		} else if (LotteryType.getDc().contains(lotteryType)) {

			minteamid = beidanService.getMinTeamid(betcode, lotteryType, phaseNo,prizeOptimize);
			maxteamid = beidanService.getMaxTeamid(betcode, lotteryType, phaseNo,prizeOptimize);
			dealDate = beidanService.getDealTeime(minteamid, lotteryType, phaseNo);
		} else if(LotteryType.getGuanyajun().contains(lotteryType)) {
			minteamid = jingcaiGuanjunService.getMinTeamid(betcode);
			maxteamid = jingcaiGuanjunService.getMaxTeamid(betcode);
			dealDate = lotteryPhaseService.getByTypeAndPhase(lotteryTypeNo, phaseNo).getEndSaleTime();
		} else {
			LotteryPhase phase = null;
			try {
				phase = lotteryPhaseService.getByTypeAndPhase(lotteryTypeNo, phaseNo);
				dealDate = phase.getEndSaleTime();
			} catch (Exception e) {

			}
			if (phase == null) {
				throw new LotteryException(ErrorCode.no_phase, "彩种:"+lotteryType.getName()+",彩期不存在,phaseNo=" + phaseNo);
			}
			if (phase.getEndSaleTime() == null) {
				throw new LotteryException(ErrorCode.no_phase, "彩种:"+lotteryType.getName()+",结束时间不存在,phaseNo=" + phaseNo);
			}
			
			if (phase.getEndSaleTime().before(new Date())) {
				throw new LotteryException(ErrorCode.phase_outof, "彩种:"+lotteryType.getName()+"彩期已过期:" + phaseNo+",betcode:"+betcode);
			}
			if (phase.getForSale() != YesNoStatus.yes.getValue()) {
				throw new LotteryException(ErrorCode.not_sale_phase,"彩种:"+lotteryType.getName()+ "彩期不在销售状态:" + phaseNo+",betcode:"+betcode);
			}
		}
		UserInfo userInfo = userinfoDao.find(userno);
		if (userInfo == null) {
			throw new LotteryException(ErrorCode.no_user, "用户:" + userno + "不存在");
		}
		/** 生成订单 */
		LotteryOrder order = new LotteryOrder();
		order.setId(orderId);
		order.setBuyAgencyno(buyagencno);
		order.setUserno(userno);
		order.setBuyUserno(userno);
		order.setAmount(amount);
		order.setContent(betcode);
		order.setDeadline(dealDate);
		order.setFirstMatchNum(Long.parseLong(minteamid));
		order.setLastMatchNum(Long.parseLong(maxteamid));
		order.setReceiveTime(new Date());
		order.setLotteryType(lotteryTypeNo);
		order.setPhase(phaseNo);
		order.setIsExchanged(YesNoStatus.no.value);
		order.setUserName(userInfo.getUsername());
		order.setRealName(userInfo.getRealName());
        order.setTicketCount(0);
        order.setFailCount(0);
		order.setTerminalId("0");
		order.setAddPrize(BigDecimal.ZERO);
		order.setSmallPosttaxPrize(BigDecimal.ZERO);
		order.setTotalPrize(BigDecimal.ZERO);
		order.setPretaxPrize(BigDecimal.ZERO);
		order.setBigPosttaxPrize(BigDecimal.ZERO);
		order.setTerminalTypeId(userInfo.getTerminalType());
		order.setPreferentialAmount(BigDecimal.ZERO);
		if (oneAmout == 200) {
			order.setAddition(YesNoStatus.no.value);
		} else {
			order.setAddition(YesNoStatus.yes.value);
		}
		order.setMultiple(multiple);
		order.setAmt(new BigDecimal(amt));
		order.setAgencyNo(userInfo.getAgencyNo());
		order.setOrderResultStatus(OrderResultStatus.not_open.value);
		order.setOrderStatus(OrderStatus.PRINT_WAITING.value);
		order.setBetType(bettype.getValue());
		order.setPayStatus(PayStatus.NOT_PAY.value);
		order.setAgentId(agentId);
		order.setAccountType(AccountType.bet.value);
		String matchNums=lotManager.getLot(String.valueOf(lotteryTypeNo)).getAllMatches(betcode,prizeOptimize);
		if(StringUtils.isNotBlank(matchNums)){
			order.setMatchNums(matchNums);
		}else{
			order.setMatchNums(phaseNo);
		}
		order.setPlayTypeStr(lotManager.getLot(String.valueOf(lotteryTypeNo)).getAllPlayType(betcode));
		order.setPrizeOptimize(prizeOptimize);
		order.setCodeFilter(codeFilter);
		lotteryOrderDao.insert(order);
		return order;
	}

	/**
	 * 大客户投注
	 * 
	 * @param merchantCode
	 *            接入商编号
	 * @param merchantNo
	 *            接入商订单号
	 * @param betcode
	 *            投注注码
	 * @param orderId
	 *            平台订单号
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param playType
	 *            玩法
	 * @param multiple
	 *            倍数
	 * @param addition
	 *            追加,0 否,1是
	 * @param endtime 结束时间
	 * @param terminalId 出票终端
	 * */
	@Transactional
	public MerchantOrder merchantBet(String merchantCode, String merchantNo, String betcode, String orderId, int lotteryType, String phase, int playType, int multiple, int amount, int addition,String endtime,String terminalId) {
		MerchantOrder merchantOrder = new MerchantOrder();
		String orderBet = playType + "-" + betcode;
		int oneAmout = 200;
		if (addition == YesNoStatus.yes.value) {
			oneAmout = 300;
		}
		LotteryOrder lotteryOrder=createLotteryOrder(merchantCode, orderBet, orderId, lotteryType, phase, multiple, new BigDecimal(amount), oneAmout, BetType.bet_merchant,AgencyType.merchant.value,merchantNo,YesNoStatus.no.value,YesNoStatus.no.value);
		lotteryOrder.setPayStatus(PayStatus.NOT_PAY.value);
		lotteryOrder.setTerminalId(terminalId);
		if(StringUtils.isNotBlank(endtime)&& (LotteryType.getJcValue().contains(lotteryType)||LotteryType.getDcValue().contains(lotteryType))){
			Date date= CoreDateUtils.parseDateTime(endtime);
			lotteryOrder.setDeadline(date);
		}
		lotteryOrder.setMemo("接入商投注");
		merchantOrder.setOrderid(orderId);
		merchantOrder.setMerchantCode(merchantCode);
		merchantOrder.setMerchantNo(merchantNo);
		merchantOrder.setLotteryType(lotteryType);
		merchantOrder.setPhase(phase);
		merchantOrder.setPlayType(playType);
		merchantOrder.setAmount(new BigDecimal(amount));
		merchantOrder.setCreateTime(new Date());
		merchantOrder.setMultiple(multiple);
		merchantOrder.setBetCode(betcode);
		merchantOrder.setIsExchanged(YesNoStatus.no.value);
		merchantOrder.setOrderResultStatus(OrderResultStatus.not_open.getValue());
		merchantOrder.setOrderStatus(OrderStatus.PRINTING.getValue());
		merchantOrder.setAddition(addition);
		merchantOrder.setTotalPrize(BigDecimal.ZERO);
		merchantOrder.setOrderStatusNotice(NoticeStatus.no_notice.value);
		merchantOrder.setPrizeStatusNotice(NoticeStatus.no_notice.value);
		merchantOrder.setEndTime(lotteryOrder.getDeadline());
		merchantOrderDAO.insert(merchantOrder);
		return merchantOrder;
	}
	
	
	/**
	 * 追号投注
	 * @param orderId 订单号
     *@param  chaseBuyId 执行追号id
     *
	 * */
	@Transactional
	public LotteryOrder chaseBet(String orderId,String chaseBuyId){

		/** 生成订单 */

		LotteryChaseBuy lotteryChaseBuy=lotteryChaseBuyDAO.findWithLock(chaseBuyId,true);

		return this.toChase(orderId,lotteryChaseBuy);
	}

	@Transactional
	public   LotteryOrder toChase(String orderId,LotteryChaseBuy lotteryChaseBuy){
		if(StringUtils.isBlank(orderId)){
			throw new LotteryException("订单号不能为空");
		}
		if (lotteryChaseBuy==null){
			throw  new LotteryException("追号不存在");
		}
		String chaseBuyId=lotteryChaseBuy.getId();
		if (lotteryChaseBuy.getStatus()!=ChaseBuyStatus.chasebuy_no.value){
			throw new LotteryException("追号状态"+chaseBuyId+"不是未追号");
		}
		String chaseId=lotteryChaseBuy.getChaseId();
		LotteryChase lotteryChase=lotteryChaseDao.findWithLock(chaseId, true);
		if(lotteryChase==null){
			throw new LotteryException("追号:"+chaseId+"不存在");
		}
		if(lotteryChase.getState()!= ChaseStatus.NORMAL.value){
			throw  new LotteryException("追号："+chaseId+"的状态不是正常态,为:"+lotteryChase.getState());
		}
		if(lotteryChase.getRemainNum()==0){
			throw  new LotteryException("追号："+chaseId+"的剩余期为0");
		}


		int lotteryType=lotteryChaseBuy.getLotteryType();
		String phase=lotteryChaseBuy.getPhase();
		if(phase.equals(lotteryChase.getLastPhase())){
			throw  new LotteryException("追号："+chaseId+"的上一期与本次追号相同:"+phase);
		}
		String userno=lotteryChaseBuy.getUserno();
		BigDecimal amount=lotteryChaseBuy.getAmount();
		String betcode=lotteryChase.getBetCode();
		int addition=lotteryChaseBuy.getAddition();
		int multiple=lotteryChaseBuy.getMultiple();
		LotteryOrder order = createLotteryOrder(userno, betcode, orderId, lotteryType, phase, multiple, amount, 200, BetType.chase, AgencyType.web.value, chaseBuyId, YesNoStatus.no.value,YesNoStatus.no.value);
		order.setChaseId(chaseId);
		order.setAccountType(AccountType.Chase.value);
		order.setAddition(addition);
		order.setPayStatus(PayStatus.ALREADY_PAY.getValue());
		order.setOrderStatus(OrderStatus.NOT_SPLIT.value);
		order.setMemo("追号投注");
		UserAccount userAccount=userAccountDAO.findWithLock(userno, true);
		if(userAccount==null){
			throw new LotteryException("用户:"+userno+"不存在");
		}
		String id = idGeneratorDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, userno,order.getUserName(),orderId, new Date(), BigDecimal.ZERO, AccountType.Chase, CommonStatus.success, userAccount.getBalance(), AccountDetailType.freeze,
				userAccount.getDrawbalance(), userAccount.getFreeze(), "执行追号冻结流程",SignStatus.flat);
		uad.setDrawAmount(BigDecimal.ZERO);
		uad.setNotDrawAmount(BigDecimal.ZERO);
		uad.setGiveAmount(BigDecimal.ZERO);
		uad.setOtherid(chaseId);
		userAccountDetailDAO.insert(uad);
		lotteryChaseBuy.setOrderId(orderId);
		lotteryChase.setBetNum(lotteryChase.getBetNum() + 1);
		lotteryChaseBuy.setStatus(ChaseBuyStatus.chasebuy_yes.value);
		lotteryChaseBuy.setFinishTime(new Date());
		lotteryChaseBuy.setOrderStatus(OrderStatus.PRINTING.value);
		lotteryChaseBuy.setOrderResultStatus(OrderResultStatus.not_open.value);
		lotteryChaseBuy.setRemainAmount(lotteryChase.getRemainAmount().subtract(amount));
		lotteryChaseBuyDAO.merge(lotteryChaseBuy);
		lotteryChase.setRemainAmount(lotteryChase.getRemainAmount().subtract(amount));
		lotteryChase.setRemainNum(lotteryChase.getRemainNum() - 1);
		lotteryChase.setLastPhase(phase);
		if(lotteryChase.getRemainNum()==0){
			lotteryChase.setState(ChaseStatus.END.value);
		}

		lotteryChase.setChangeTime(new Date());
		lotteryChaseDao.merge(lotteryChase);
		return order;
	}
	
	@Transactional
	public LotteryOrder fileUploadBet(String userno, String betcode,String orderId,int lotteryType,String phase, int multiple, int amount,int oneAmount,String buyAgencyno,int prizeOptimize,int codeFilter,String couponId){
		LotteryOrder order=couponBet(userno, betcode, orderId, lotteryType, phase, multiple, new BigDecimal(amount), oneAmount, BetType.upload, buyAgencyno,prizeOptimize,codeFilter,couponId);
		order.setContent("单式上传");
		order.setMemo("单式上传");
		return order;
	}

	@Autowired
	protected UserCouponDao userCouponDao;
	@Transactional
	public   LotteryOrder couponBet(String userno, String betcode, String orderId, int lotteryType, String phase, int multiple, BigDecimal amount,int oneAmout, BetType bettype,String buyagencyno,int prizeOptimize,int codeFilter,String couponId){
		UserCoupon userCoupon=null;
		if(StringUtils.isNotBlank(couponId)){
			 userCoupon=userCouponDao.findWithLock(couponId,true);
			if(userCoupon!=null){
				if(userCoupon.getStatus() != UserCouponStatus.unuse.getValue()){
					throw new LotteryException(ErrorCode.coupon_notunuse,"优惠券不是未使用");
				}
				if(!userCoupon.getUserno().equals(userno)){
					throw new LotteryException(ErrorCode.coupon_notowner, "优惠券不是本人");
				}
				//判断优惠券是否过期
				if(userCoupon.getEndDate().getTime() + 24*60*60*1000 < System.currentTimeMillis()){
					throw new LotteryException(ErrorCode.coupon_timeout, "过期");
				}
				JSONObject couponTypeObj = JSONObject.fromObject(userCoupon.getCouponTypeDesc());
				BigDecimal orderAmount = new BigDecimal(couponTypeObj.getString(UserCouponLineContains.ORDERAMOUNT));
				if(amount.compareTo(orderAmount) < 0){
					throw new LotteryException(ErrorCode.coupon_notmatch_amount, "优惠券不满足订单条件金额");
				}

				String lotteryTypes = couponTypeObj.getString(UserCouponLineContains.LOTTERYTYPES);
				if(!"0".equals(lotteryTypes)){
					if(!lotteryTypes.contains(String.valueOf(lotteryType))){
						throw new LotteryException(ErrorCode.coupon_notmatch_lotterytype, "优惠券不满足彩种限制");
					}
				}
				//BigDecimal discountAmount = new BigDecimal(couponTypeObj.getString(UserCouponLineContains.DISCOUNTAMOUNT));
				userCoupon.setOrderId(orderId);
				userCoupon.setOrderTime(new Date());
				userCoupon.setStatus(UserCouponStatus.used.getValue());
				userCouponDao.update(userCoupon);
			}
		}

		LotteryOrder lotteryOrder=toBet(userno, betcode, orderId, lotteryType, phase, multiple, amount, oneAmout, bettype, buyagencyno, prizeOptimize, codeFilter);
		lotteryOrder.setChaseId(couponId);
		if (userCoupon!=null){
			lotteryOrder.setPreferentialAmount(userCoupon.getPreferentialAmount());
			lotteryOrder.setMemo("优惠券购彩,优惠"+userCoupon.getPreferentialAmount().divide(new BigDecimal(100)).doubleValue());
		}else{
			lotteryOrder.setMemo("投注");
		}
		return lotteryOrder;
	}
	
	/**
	 * 充值完成后发送检查jms消息
	 * */
    public void transactionBet(String userno){
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userno", userno);
			queueMessageSendService.sendMessage(QueueName.tranasctionbet, map);
		}catch (Exception e){
			logger.error("发送检查jms出错",e);
		}
	}


}
