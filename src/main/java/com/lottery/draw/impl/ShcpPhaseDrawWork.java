package com.lottery.draw.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IPrizeNumConverter;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.shcp.SHCPConfig;
import com.lottery.ticket.vender.impl.shcp.SHCPConverter;
/**
 * 开奖号码 
 * @author wyliuxiaoyan
 *
 */
@Service
public class ShcpPhaseDrawWork extends AbstractVenderPhaseDrawWorker {
	protected final Logger logger = LoggerFactory.getLogger(ShcpPhaseDrawWork.class);
	protected static Map<Integer, IPrizeNumConverter> prizeNumConverterMap = new HashMap<Integer, IPrizeNumConverter>();
	
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String queryCode="20002";
	@Override
	 protected LotteryDraw get(IVenderConfig config,
             Integer lotteryType, String phase) {
        if (config == null) {
           return null;
        }
        IVenderConverter shConverter = getVenderConverter();
        String lotno = shConverter.convertLotteryType(LotteryType.getLotteryType(lotteryType));
        String convertPhase = shConverter.convertPhase(LotteryType.getPhaseType(lotteryType), phase);
        if (StringUtils.isBlank(lotno) || StringUtils.isBlank(convertPhase)) {
          return null;
        }
		String message=getElement(config,lotno,convertPhase);
		String returnStr="";
		try {
			 returnStr= HTTPUtil.sendPostMsg(config.getRequestUrl(), message);
		} catch (Exception e) {
			logger.error("上海查询开奖号码接口返回异常",e);
		}
		logger.error("上海开奖号码请求数据为{},返回数据为{}",message,returnStr);
		try {
			 if (StringUtils.isNotBlank(returnStr)) {
	             LotteryDraw lotteryDraw = dealResult(returnStr, lotteryType);
	             return lotteryDraw;
	         }
		} catch (Exception e) {
			logger.error("上海查询开奖号码解析异常",e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_SHCP;
	}

	
	/**
	 * 查询开奖号码结果处理
	 * @param desContent
	 * @param
	 * @return
	 * @throws DocumentException 
	 */
	private LotteryDraw dealResult(String desContent,int lotteryType){
		LotteryDraw lotteryDraw=new LotteryDraw();
		lotteryDraw.setResponsMessage(desContent);
		try {
			    HashMap<String, String> mapResult = XmlParse.getElementAttribute("body/rows/", "row", desContent);
			    String prizecode=mapResult.get("awardcode");
			    String issue=mapResult.get("pid");
			    if (StringUtils.isNotBlank(prizecode) && !"-".equals(prizecode)) {
                    lotteryDraw.setPhase(issue);
                    lotteryDraw.setLotteryType(lotteryType);
                    IPrizeNumConverter prConverter = prizeNumConverterMap.get(lotteryType);
                    lotteryDraw.setWindCode(prConverter.convert(prizecode));
                    if (prizecode!=null&&!"".equals(prizecode)) {
                        lotteryDraw.setStatus(PhaseStatus.prize_open.getValue());
                    }
                    return lotteryDraw;
                }
		} catch (Exception e) {
			logger.error("查询开奖号码结果异常",e);
		}
	    return lotteryDraw;
	}
	/**
	 * 拼串
	 * @param
	 * @return
	 */
	private String getElement(IVenderConfig shcpConfig,String lotteryNo,String phase) {
		try {
			String messageId=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
			XmlParse xmlParse =  SHCPConfig.addShcpHead(queryCode, shcpConfig.getAgentCode(),messageId);
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
	        bodyAttr.put("gid", lotteryNo);
	        bodyAttr.put("pid", phase);
	        xmlParse.addBodyElementAndAttribute("query","", bodyAttr);
			logger.info(xmlParse.asXML());
			String des = MD5Util.toMd5(xmlParse.asXML()+shcpConfig.getKey());
			return "xml="+xmlParse.asXML()+"&sign="+des;
		} catch (Exception e) {
			logger.error("开奖号码查询异常",e);
		}
		return null;
	}

	protected static Map<Integer, String> prizeNum = new HashMap<Integer, String>();

    static {

        // 开奖号码转换
        // 快彩
        IPrizeNumConverter defaultConverter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {
                return content;
            }
        };

        prizeNumConverterMap.put(LotteryType.JX_11X5.getValue(), defaultConverter);
		prizeNumConverterMap.put(LotteryType.HUBEI_11X5.getValue(), defaultConverter);
		prizeNumConverterMap.put(LotteryType.SD_11X5.getValue(), defaultConverter);
		prizeNumConverterMap.put(LotteryType.GD_11X5.getValue(), defaultConverter);
    }


}
