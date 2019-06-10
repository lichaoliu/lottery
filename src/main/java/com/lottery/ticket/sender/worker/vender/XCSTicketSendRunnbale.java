package com.lottery.ticket.sender.worker.vender;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.domain.ticket.LotteryLogicMachine;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.PrizeService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class XCSTicketSendRunnbale implements Runnable{
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	private TicketSendResult ticketSendResult;
	private CountDownLatch latch;
	
	private Ticket ticket;
	
	protected LotteryLogicMachine logicmachine;
	
	
	protected PrizeService prizeService;
	private IVenderConfig venerConfig;
	private IVenderConverter xcsConverter;
	public XCSTicketSendRunnbale(IVenderConfig venerConfig,LotteryLogicMachine logicmachine,Ticket ticket,TicketSendResult ticketSendResult,CountDownLatch latch,IVenderConverter xcsConverter,PrizeService prizeService){
		this.latch=latch;
		this.ticket=ticket;
		this.ticketSendResult=ticketSendResult;
		this.logicmachine=logicmachine;
		this.venerConfig=venerConfig;
		this.xcsConverter=xcsConverter;
		this.prizeService=prizeService;
		
	}
	
     public XCSTicketSendRunnbale(){
		
	}
	
	@Override
	public void run() {
		try{
			
			int playType=ticket.getPlayType();
             
            
			if(playType==PlayType.jczq_bf_4_1.value||playType==PlayType.jczq_bf_4_4.value||playType==PlayType.jczq_bf_4_5.value||playType==PlayType.jczq_bf_4_6.value||playType==PlayType.jczq_bf_4_11.value
			||playType==PlayType.jczq_bqc_4_1.value||playType==PlayType.jczq_bqc_4_4.value||playType==PlayType.jczq_bqc_4_5.value||playType==PlayType.jczq_bqc_4_6.value
			||playType==PlayType.jczq_bqc_4_11.value||playType==PlayType.jczq_bqc_1_1.value||playType==PlayType.jczq_bf_1_1.value||playType==PlayType.jczq_jqs_1_1.value
                    ||playType==PlayType.jczq_spf_1_1.value||playType==PlayType.jczq_spfwrq_1_1.value
					){
                logger.error("票:{},的玩法是:{}自动过滤",ticket.getId(),playType);
				ticketSendResult.setStatus(TicketSendResultStatus.failed);
				ticketSendResult.setStatusCode("100");
				ticketSendResult.setResponseMessage("10");
			}else{
                boolean flag=true;

                if(playType==PlayType.jczq_mix_4_4.value||playType==PlayType.jczq_mix_4_5.value||playType==PlayType.jczq_mix_4_6.value||playType==PlayType.jczq_mix_4_11.value||

                        playType==PlayType.jczq_mix_4_1.value){
                      String content=ticket.getContent();
                     if(content.contains("*3007")||content.contains("*3008")||content.contains("*3009")){
                         logger.error("混合过关票:{},的玩法是:{}自动过滤",ticket.getId(),playType);
                         ticketSendResult.setStatus(TicketSendResultStatus.success);
                         ticketSendResult.setStatusCode("1019");
                         ticketSendResult.setResponseMessage("10");
                         flag=false;
                     }


                }
                if(flag){
                    String requestStr=getElement(ticket, venerConfig, xcsConverter);
                    logger.error("小财神ip={},port={},ticketid={},发送:{}",logicmachine.getIp(), logicmachine.getPort(),ticket.getId(),requestStr);
                    String str=HTTPUtil.sendSocket(logicmachine.getIp(), logicmachine.getPort(),requestStr, CharsetConstant.CHARSET_UTF8,60000);
                    logger.error("订单:{},投注返回:{}",ticket.getId(),str);
                    if(!"success".equals(str)){
                        ticketSendResult.setStatus(TicketSendResultStatus.failed);
                        ticketSendResult.setStatusCode(str);
                        ticketSendResult.setResponseMessage(requestStr);

                    }else{
                        ticketSendResult.setSendMessage("");
                        ticketSendResult.setSendTime(new Date());
                        ticketSendResult.setPeiLv(peilv(ticket));
                        ticketSendResult.setResponseMessage(str);
                        ticketSendResult.setStatusCode(str);
                        ticketSendResult.setMessage("");
                        ticketSendResult.setStatus(TicketSendResultStatus.printed);
                        ticketSendResult.setPrintTime(new Date());
                    }
                }else{

                }


			}
		
		}catch(Exception e){
			ticketSendResult.setStatus(TicketSendResultStatus.unkown);
			logger.error("送票出错",e);
		}
		latch.countDown();
		
	}

	
	protected String getElement(Ticket ticket,IVenderConfig xcsConfig, IVenderConverter xcsConverter) {
		StringBuilder stringBuilder=new StringBuilder();

		int lotteryType=ticket.getLotteryType();
		stringBuilder.append("caizhong=").append(xcsConverter.convertLotteryType(LotteryType.get(ticket.getLotteryType())));
		stringBuilder.append("&beishu=").append(ticket.getMultiple());
		stringBuilder.append("&jine=").append(ticket.getAmount().divide(new BigDecimal(100)).intValue());
		String[] zhushu=StringUtils.split(ticket.getContent(), "^");
		
		stringBuilder.append("&zhushu=").append(zhushu.length);
		stringBuilder.append("&neirong=").append(xcsConverter.convertContent(ticket));
		if(ticket.getPlayType().toString().endsWith("01")){
			stringBuilder.append("&moshi=").append("1");
		}else{
			stringBuilder.append("&moshi=").append("2");
		}
		
		if(LotteryType.getJclqValue().contains(lotteryType)||LotteryType.getJczqValue().contains(lotteryType)){
			String playType=ticket.getPlayType()+"";
			playType=playType.substring(playType.length()-4);
			String g=playType.substring(0, 1);
			String gu=playType.substring(1);
			if (gu.substring(0,1).equals("0")&&gu.substring(1,2).equals("0")){
				gu=gu.substring(2);
			}
			if (gu.substring(0,1).equals("0")&&!gu.substring(1,2).equals("0")){
				gu=gu.substring(1);
			}
			String guoguan = g + "x" + gu;
			if(guoguan.equals("1x1")){
				guoguan="01";
			}
			stringBuilder.append("&guoguan=").append(guoguan);
			String []changshu=ticket.getContent().split("\\-")[1].split("\\|");
			
			stringBuilder.append("&changci=").append(changshu.length);
		}else{
			stringBuilder.append("&guoguan=1&changci=1");
		}
		
		stringBuilder.append("&ticketId=").append(ticket.getId());
		return stringBuilder.toString();
	 }
	
	protected String peilv(Ticket ticket){
		LotteryType lotteryType=LotteryType.get(ticket.getLotteryType());
		String odds="";
		if(LotteryType.getJclq().contains(lotteryType)||LotteryType.getJczq().contains(lotteryType)){
			lotteryType=LotteryType.getLotteryType(ticket.getLotteryType());
			odds=prizeService.simulateOdd(ticket.getContent(),lotteryType);
            logger.error("票:{},模拟赔率是:{}",ticket.getId(),odds);
		}

		return odds;
	}
	

}
