package com.lottery.draw.impl;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IPrizeNumConverter;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huancai.HuancaiConfig;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
public class HuanCaiPhaseDrawWorker extends AbstractVenderPhaseDrawWorker {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    protected static Map<Integer, IPrizeNumConverter> prizeNumConverterMap = new HashMap<Integer, IPrizeNumConverter>();
    @Autowired
    IdGeneratorService igenGeneratorService;
    private String openPrizeNum = "1001";


    @Override
    protected LotteryDraw get(IVenderConfig config, Integer lotteryType, String phase) {
        if (config == null) {
            return null;
        }
        IVenderConverter huancaiConverter = getVenderConverter();
        String lotno = huancaiConverter.convertLotteryType(LotteryType.getLotteryType(lotteryType));
        String convertPhase = huancaiConverter.convertPhase(LotteryType.getPhaseType(lotteryType), phase);
        if (StringUtils.isBlank(lotno) || StringUtils.isBlank(convertPhase)) {
            return null;
        }
        String message = getElement(lotno, convertPhase, config);
        String returnStr = "";

        // 处理结果
        try {
            returnStr = HTTPUtil.sendPostMsg(config.getRequestUrl(), message);
            if (StringUtils.isNotBlank(returnStr)) {
                LotteryDraw lotteryDraw = dealResult(returnStr, phase, lotteryType, huancaiConverter);
                return lotteryDraw;
            }
        } catch (Exception e) {
            logger.error("处理开奖结果异常",e);
            logger.error("returnstr={}",returnStr);
        }
        return null;
    }

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_HUANCAI;
    }

    /**
     * 查奖结果处理
     *
     * @param desContent
     * @param
     * @return
     */
    private LotteryDraw dealResult(String desContent, String issue, int lotteryType, IVenderConverter zchConverter) {
        LotteryDraw lotteryDraw = new LotteryDraw();
        try {
            Map<String, String> map = XmlParse.getElementAttribute("/", "response", desContent);
            if ("0000".equals(map.get("code"))) {
                Map<String, String> mapParam = XmlParse.getElementAttribute("response/", "issue", desContent);
                if (mapParam != null) {
                    String prizecode = mapParam.get("bonusCode");
                    if (StringUtils.isNotBlank(prizecode) && !"-".equals(prizecode)) {
                        lotteryDraw.setPhase(issue);
                        lotteryDraw.setResponsMessage(desContent);
                        lotteryDraw.setLotteryType(lotteryType);
                        IPrizeNumConverter prConverter = prizeNumConverterMap.get(lotteryType);
                        lotteryDraw.setWindCode(prConverter.convert(prizecode));
                        if (prizecode!=null&&!"".equals(prizecode)) {
                            lotteryDraw.setStatus(PhaseStatus.prize_open.getValue());
                        }
                        return lotteryDraw;
                    }
                    return null;
                }
            }

        } catch (Exception e) {
            logger.error("处理开奖结果异常", e);
        }
        return null;
    }
   
    private String getElement(String lotteryNo, String phase, IVenderConfig huancaiConfig) {

        try {
        	String messageId=DateUtil.format("yyyyMMddHHmmss", new Date());;
            XmlParse xmlParse = HuancaiConfig.addGxHead(openPrizeNum,huancaiConfig.getAgentCode(),messageId);
            HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
            bodyAttr.put("lottid", lotteryNo);
            bodyAttr.put("name", phase);
            xmlParse.addBodyElementAndAttribute("issue","", bodyAttr);
            String md = MD5Util.toMd5(huancaiConfig.getAgentCode()+messageId + huancaiConfig.getKey() + xmlParse.getBodyElement().asXML());
    		xmlParse.addHeaderElement("digest", md);
    		return  "cmd="+openPrizeNum+"&msg="+xmlParse.asXML();
        } catch (Exception e) {
            logger.error("开奖号码查询异常" ,e);
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

        IPrizeNumConverter jsk3Converter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {

                String[] condeString=content.split("\\,");
                Arrays.sort(condeString);  //进行排序

                StringBuffer sb=new StringBuffer();
                for(int i=0;i<condeString.length;i++){
                    sb.append(condeString[i]);
                    if(i<condeString.length-1){
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
        };




        prizeNumConverterMap.put(LotteryType.JSK3.getValue(), jsk3Converter);
        prizeNumConverterMap.put(LotteryType.AHK3.getValue(), jsk3Converter);
        prizeNumConverterMap.put(LotteryType.GXK3.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.XJ_11X5.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.SD_11X5.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.GD_11X5.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.CQSSC.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.XJSSC.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.CQKL10.getValue(), defaultConverter);
        prizeNumConverterMap.put(LotteryType.GDKL10.getValue(), defaultConverter);
    }





}
