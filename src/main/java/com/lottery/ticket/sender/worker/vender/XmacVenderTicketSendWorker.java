package com.lottery.ticket.sender.worker.vender;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lottery.common.Constants;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;

/**
 * Created by fengqinyun on 15/9/1.
 */

public class XmacVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "101";
	
	@Override
    protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType, IVenderConfig venderConfig, IVenderConverter venderConverter) throws Exception {
    	List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();

		for (Ticket ticket : ticketList) {
			TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
			ticketSendResultList.add(ticketSendResult);
			String messageStr = "";
			String returnStr = "";
			try {
				String lotno = venderConverter.convertLotteryType(lotteryType);
				String phase = venderConverter.convertPhase(lotteryType, ticketBatch.getPhase());
				messageStr = getElement(ticket, lotno, phase, lotteryType,venderConfig,venderConverter);
				ticketSendResult.setSendMessage(messageStr);

				ticketSendResult.setSendTime(new Date());
				returnStr = HTTPUtil.post(venderConfig.getRequestUrl(),messageStr,CharsetConstant.CHARSET_GBK);
				if(StringUtils.isEmpty(returnStr)){
					//logger.error("{}请求返回结果为空",messageStr);
					ticketSendResult.setStatus(TicketSendResultStatus.unkown);
					ticketSendResult.setMessage(returnStr);
					ticketSendResult.setSendMessage(messageStr);
					ticketSendResult.setResponseMessage(returnStr);
					return ticketSendResultList;
				}
				ticketSendResult.setResponseMessage(returnStr);
				ticketSendResult.setStatusCode(returnStr);
				returnStr="<param>"+returnStr.split("\\?>")[1]+"</param>";
				
				HashMap<String, String> mapResult = XmlParse.getElementText("/", "ActionResult", returnStr);
				String code=mapResult.get("xCode");
				String xMessage=mapResult.get("xMessage");
				if ("0".equals(code)) {
					ticketSendResult.setStatus(TicketSendResultStatus.success);
				} else if ("1008".equals(code)) {// 票号重复
					ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
					ticketSendResult.setMessage("网站平台交易流水号重复");
				} else if (Constants.macSendFailedError.containsKey(returnStr)) {
					ticketSendResult.setStatus(TicketSendResultStatus.failed);
				} else if (Constants.macSendError.containsKey(returnStr)) {
					ticketSendResult.setStatus(TicketSendResultStatus.failed);
				} else {
					ticketSendResult.setStatus(TicketSendResultStatus.unkown);
				}
				ticketSendResult.setMessage(xMessage);
				ticketSendResult.setSendTime(new Date());
			} catch (Exception e) {
				ticketSendResult.setStatus(TicketSendResultStatus.unkown);
				ticketSendResult.setStatus(convertResultStatusFromException(e));
				ticketSendResult.setMessage(e.getMessage());
				ticketSendResult.setSendMessage(messageStr);
				ticketSendResult.setResponseMessage(returnStr);
				logger.error("送票处理错误", e);
			}

		}
		return ticketSendResultList;
	}

	/**
	 * 送票前拼串
	 * 
	 * @param ticket
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception
	 */

	public String getElement(Ticket ticket, String lotteryNo, String phase,LotteryType lotteryType, IVenderConfig venderConfig,  IVenderConverter xmacConverter) {
	try{
		   
		   String wAgent=venderConfig.getAgentCode();
	       String wMsgID=idGeneratorService.getMessageId();
           Double amount = ticket.getAmount().doubleValue() / 100;
           String content=getContent(ticket,xmacConverter);
           StringBuilder wParam=new StringBuilder();
           wParam.append("OrderID="+ticket.getId())
                  .append("_").append("LotID=").append(lotteryNo)
                  .append("_").append("LotIssue=").append(phase)
		          .append("_").append("LotMoney=").append(amount.intValue())
		          .append("_").append("LotCode=").append(content)
		          .append("_").append("LotMulti=").append(ticket.getMultiple())
		          .append("_").append("Attach=").append("000")
                  .append("_").append("OneMoney=2");
//           logger.error("送票内容为{}",wParam);
           String wSign = MD5Util.getMD5((wAgent+betCode+wMsgID+wParam.toString()+venderConfig.getKey()).getBytes(CharsetConstant.CHARSET_GBK));
           String playParam = "wAgent=" + wAgent +
					"&wAction=" + betCode +
					"&wMsgID=" + wMsgID +
					"&wSign=" + wSign +
					"&wParam=" + URLEncoder.encode(wParam.toString(),CharsetConstant.CHARSET_GBK);
			return playParam;
		} catch (Exception e) {
			logger.error("送票拼串异常", e);
		}
		return null;
	}


    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_XMAC;
    }
    
    
    
    
    /**
     * 
     * @param ticket
     * @param xmacConverter
     * @return
     */
    private static String getContent(Ticket ticket,IVenderConverter xmacConverter){
    	StringBuilder strBuilder=new StringBuilder("1#");
    	 if(ticket.getPlayType()==PlayType.ssq_fs.getValue()){
    		 strBuilder.append(getPlayType(ticket.getContent())).append("#");
         }else if(ticket.getPlayType()==PlayType.jxssc_other_2z.getValue()){
        	 String str=ticket.getContent().split("-")[1].replace("^","").replace(",","");
        	 if(str.length()==2){
        		 strBuilder.append("0751").append("#");
        	 }else{
        		 strBuilder.append("0771").append("#");
        	 }
         }else{
        	 strBuilder.append(xmacConverter.convertPlayType(ticket.getPlayType())).append("#");
         }
    	 strBuilder.append(xmacConverter.convertContent(ticket)).append("#");
    	 strBuilder.append(ticket.getMultiple());
    	 return strBuilder.toString();
    }
    
    
    
    /**
     * 双色球玩法
     * @param content
     * @return
     */
    private static String getPlayType(String content){
    	String []contents=content.split("\\-")[1].replace("^","").replace(",","").split("\\|");
    	int redLen=contents[0].length()/2;
    	int blueLen=contents[1].length()/2;
    	if(redLen>6&&blueLen==1){
    		return "013";
    	}else if(redLen>6&&blueLen>1){
    		return "016";
    	}else if(redLen==6&&blueLen>1){
    		return "014";
    	}
    	return  null;
    	
    }
  
  
}
