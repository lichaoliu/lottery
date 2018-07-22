package com.lottery.draw.impl;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketResultStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;

import com.lottery.core.domain.ticket.Ticket;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IPrizeNumConverter;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huay.HuayConverter;
import com.lottery.ticket.vender.impl.huay.HuayService;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HuayPhaseDrawWork extends AbstractVenderPhaseDrawWorker {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    protected static Map<Integer, IPrizeNumConverter> prizeNumConverterMap = new HashMap<Integer, IPrizeNumConverter>();
    @Autowired
    IdGeneratorService igenGeneratorService;
    private String openPrizeNum = "13009";
    private String openPrizeSale = "13007";
    private String queryCode = "13011";
    @Autowired
    private HuayConverter huayConverter;

    @Override
    protected LotteryDraw get(IVenderConfig config,
                              Integer lotteryType, String phase) {
        if (config == null) {
            return null;
        }
        String lotno = huayConverter.convertLotteryType(LotteryType.getLotteryType(lotteryType));
        String convertPhase = huayConverter.convertPhase(LotteryType.getPhaseType(lotteryType), phase);
        if (StringUtils.isBlank(lotno) || StringUtils.isBlank(convertPhase)) {
            return null;
        }
        String message = getElement(lotno, convertPhase, config);
        String messagestr = getElementSaleScale(lotno, convertPhase, config);
        String returnStr = "";
        try {
            returnStr = HTTPUtil.post(config.getRequestUrl(), message ,CharsetConstant.CHARSET_UTF8);
        } catch (Exception e) {
            logger.error("开奖结果接口返回异常", e);
        }

        String saleResult = "";

        try {
            saleResult = HTTPUtil.post(config.getRequestUrl(), messagestr ,CharsetConstant.CHARSET_UTF8);
        } catch (Exception e1) {
            logger.error("请求url出错，错误原因是{}", e1.toString());
        }
        ;

        //处理结果
        try {
            if (StringUtils.isNotBlank(returnStr)) {
                LotteryDraw lotteryDraw = dealResult(returnStr, phase, lotteryType);
                if (lotteryDraw != null) {
                    logger.error("华阳开奖信息查询,彩种:{},期号{},发送的是：{}返回结果是：{}", new Object[]{lotteryType, phase, message, messagestr});
                    LotteryDraw lotteryD = dealResultSale(saleResult, phase, lotteryDraw);
                    return lotteryD;
                }
            }
        } catch (Exception e) {
            logger.error("处理开奖结果异常", e);
        }
        return null;
    }

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_HY;
    }

    /**
     * 查奖结果处理
     *
     * @param desContent
     * @param count
     * @return
     */
    private LotteryDraw dealResult(String desContent, String phase, int lotteryType) {
        LotteryDraw lotteryDraw = new LotteryDraw();
        lotteryDraw.setResponsMessage(desContent);
        try {
            Map<String, String> map = XmlParse.getElementText("body/", "oelement", desContent);
            if ("0".equals(map.get("errorcode"))) {

                StringBuilder stringBuilder = new StringBuilder();
                String prizecode = map.get("bonuscode");
                List<HashMap<String, String>> mapParams = XmlParse.getElementTextList("body/elements/", "element", desContent);
                if (mapParams != null) {
                    int i = 0;
                    String z6Num="";
                    for (HashMap<String, String> mapParam : mapParams) {
                        //大乐透追加
                        if (LotteryType.CJDLT.getValue() == lotteryType && prizeNum.containsKey(mapParam.get("bonuslevel"))) {
                            stringBuilder.append(prizeNum.get(mapParam.get("bonuslevel"))).append("_");
                        } else {
                            stringBuilder.append(mapParam.get("bonuslevel")).append("_");
                        }
                        if(i==5){
                        	z6Num="z6_"+mapParam.get("bonuscount")+"_"+new BigDecimal(mapParam.get("bonusvalue")).divide(new BigDecimal(100));
                        }
                        stringBuilder.append(mapParam.get("bonuscount")).append("_");
                        stringBuilder.append(new BigDecimal(mapParam.get("bonusvalue")).divide(new BigDecimal(100)));
                        if (i != mapParams.size() - 1) {
                            stringBuilder.append(LotteryDraw.LEVEV_SPILTSTRI);
                        }
                        i++;
                    }
                    if(!"".equals(z6Num)){
                    	 stringBuilder.append(",").append(z6Num);
                    }
                }
                if (StringUtils.isNotBlank(prizecode) && !"-".equals(prizecode)) {

                    lotteryDraw.setPhase(phase);
                    lotteryDraw.setLotteryType(lotteryType);
                    IPrizeNumConverter prConverter = prizeNumConverterMap.get(lotteryType);
                    lotteryDraw.setWindCode(prConverter.convert(prizecode));
                    if (stringBuilder.length() > 0) {
                        lotteryDraw.setResultDetail(stringBuilder.toString());
                    }
                    return lotteryDraw;
                }
                return null;
            }

        } catch (DocumentException e) {
            logger.error("处理开奖结果异常", e);
        }
        return null;
    }


    /**
     * 查奖结果销量、奖池信息处理
     *
     * @param desContent

     * @return
     */
    private LotteryDraw dealResultSale(String desContent, String issue, LotteryDraw lotteryDraw) {
        try {
            Map<String, String> map = XmlParse.getElementText("body/", "oelement", desContent);
            if ("0".equals(map.get("errorcode"))) {
                HashMap<String, String> mapParams = XmlParse.getElementText("body/elements/", "element", desContent);
                if (mapParams != null) {
                    String status = mapParams.get("status");
                    String totalSaleNum = new BigDecimal(mapParams.get("salemoney")).divide(new BigDecimal("100")) + "";
                    String totalprizeNum = new BigDecimal(mapParams.get("bonusmoney")).divide(new BigDecimal("100")) + "";
                    if (StringUtils.isNotBlank(totalprizeNum)) {
                        lotteryDraw.setJackpot(totalprizeNum);
                    }
                    if (StringUtils.isNotBlank(totalSaleNum) && Double.parseDouble(totalSaleNum) > 9) {
                        totalSaleNum = ConvertNum(totalSaleNum.trim());
                    }
                    if (status != null && Integer.parseInt(status) >= 4) {
                        lotteryDraw.setStatus(PhaseStatus.prize_open.getValue());
                    } else if ("2".equals(status)) {
                        lotteryDraw.setStatus(PhaseStatus.close.getValue());
                    } else if ("0".equals(status)) {
                        lotteryDraw.setStatus(PhaseStatus.open.getValue());
                    }
                    lotteryDraw.setVolumeOfSales(totalSaleNum);
                    return lotteryDraw;
                }
            }
            return null;
        } catch (DocumentException e) {
            logger.error("处理开奖结果异常", e);
        }
        return null;
    }

    private String getElement(String lotteryNo, String phase, IVenderConfig huayConfig) {

        try {
            String messageid = igenGeneratorService.getMessageId();
            String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
            XmlParse   xmlParse = HuayService.addHuayHead(huayConfig.getAgentCode(), openPrizeNum, messageid, timestamp);
            HashMap<String, String> bodyAttr = new HashMap<String, String>();
            Element bodyeElement = xmlParse.getBodyElement();
            Element elements = bodyeElement.addElement("elements");
            bodyAttr.put("lotteryid", lotteryNo.trim());
            bodyAttr.put("issue", phase.trim());
            Element element2 = elements.addElement("element");
            xmlParse.addBodyElement(bodyAttr, element2);
            String md = MD5Util.MD5(timestamp + huayConfig.getKey() + xmlParse.getBodyElement().asXML());
            xmlParse.addHeaderElement("digest", md);
            return xmlParse.asXML();
        } catch (Exception e) {
            logger.error("开奖号码查询异常", e);
        }
        return null;
    }

    private String getElementSaleScale(String lotteryNo, String phase, IVenderConfig huayConfig) {
        String md = "";
        XmlParse xmlParse = null;
        try {
            String messageid = igenGeneratorService.getMessageId();
            String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
            xmlParse = HuayService.addHuayHead(huayConfig.getAgentCode(), openPrizeSale, messageid, timestamp);
            HashMap<String, String> bodyAttr = new HashMap<String, String>();
            Element bodyeElement = xmlParse.getBodyElement();
            Element elements = bodyeElement.addElement("elements");
            bodyAttr.put("lotteryid", lotteryNo.trim());
            bodyAttr.put("issue", phase.trim());
            Element element2 = elements.addElement("element");
            xmlParse.addBodyElement(bodyAttr, element2);
            try {
                md = MD5Util.MD5(timestamp + huayConfig.getKey() + xmlParse.getBodyElement().asXML());
            } catch (Exception e) {
                logger.error("加密异常" + e.getMessage());
            }
            xmlParse.addHeaderElement("digest", md);
            return xmlParse.asXML();
        } catch (Exception e) {
            logger.error("开奖号码查询异常", e);
        }
        return null;
    }


    protected static Map<String, String> prizeNum = new HashMap<String, String>();

    static {

        prizeNum.put("11", "z1");
        prizeNum.put("12", "z2");
        prizeNum.put("13", "z3");
        prizeNum.put("14", "z4");
        prizeNum.put("15", "z5");
        prizeNum.put("16", "z6");
        prizeNum.put("17", "z7");
        prizeNum.put("18", "z8");

        //开奖号码转换
        //双色球
        IPrizeNumConverter prConverter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {
                content = content.substring(0, 17) + "|" + content.substring(18, 20);
                return content;
            }
        };
        //3D
        IPrizeNumConverter sandConverter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {
                String[] ss = content.split(",");
                StringBuilder srBuilder = new StringBuilder();
                int i = 0;
                for (String s : ss) {
                    srBuilder.append(s);
                    if (i < ss.length - 1) {
                        srBuilder.append(",");
                    }
                    i++;
                }
                return srBuilder.toString();
            }
        };

        //七乐彩
        IPrizeNumConverter qlcConverter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {
                content = content.substring(0, 20) + "|" + content.substring(21, 23);
                return content;
            }
        };
        //大乐彩
        IPrizeNumConverter cjdltConverter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {
                content = content.substring(0, 14) + "|" + content.substring(15, 20);
                return content;
            }
        };

        //足彩胜平负
        IPrizeNumConverter zcConverter = new IPrizeNumConverter() {
            @Override
            public String convert(String content) {
                return content;
            }
        };
        prizeNumConverterMap.put(LotteryType.SSQ.getValue(), prConverter);
        prizeNumConverterMap.put(LotteryType.F3D.getValue(), sandConverter);
        prizeNumConverterMap.put(LotteryType.QLC.getValue(), qlcConverter);
        prizeNumConverterMap.put(LotteryType.CJDLT.getValue(), cjdltConverter);
        prizeNumConverterMap.put(LotteryType.PL3.getValue(), sandConverter);
        prizeNumConverterMap.put(LotteryType.PL5.getValue(), sandConverter);
        prizeNumConverterMap.put(LotteryType.QXC.getValue(), sandConverter);
        prizeNumConverterMap.put(LotteryType.ZC_SFC.getValue(), zcConverter);
        prizeNumConverterMap.put(LotteryType.ZC_RJC.getValue(), zcConverter);
        prizeNumConverterMap.put(LotteryType.ZC_JQC.getValue(), zcConverter);
        prizeNumConverterMap.put(LotteryType.ZC_BQC.getValue(), zcConverter);

        prizeNumConverterMap.put(LotteryType.CQSSC.getValue(), sandConverter);
        prizeNumConverterMap.put(LotteryType.GXK3.getValue(), sandConverter);
        prizeNumConverterMap.put(LotteryType.SD_11X5.getValue(), sandConverter);
    }

    protected boolean ticketPrize(Ticket ticket, IVenderConfig config) {
        String messageStr = getElement(ticket, config);
        if (StringUtils.isEmpty(messageStr)) {
            return false;
        }
        String returnStr = "";
        try {
            returnStr = HTTPUtil.post(config.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
        } catch (Exception e) {
            logger.error("华阳查奖接口返回异常{}", e);
        }
        if (StringUtils.isBlank(returnStr)) {
            logger.error("华阳查奖接口返回空");
            return false;
        }
        // 查奖处理结果
        return dealResult(returnStr, ticket);
    }

    private boolean dealResult(String desContent, Ticket ticket) {
        try {
            HashMap<String, String> map = XmlParse.getElementText("body/elements", "element", desContent);
            HashMap<String, String> mapCode = XmlParse.getElementText("body/", "oelement", desContent);
            String code = mapCode.get("errorcode");
            if (ErrorCode.Success.getValue().equals(code)) {//成功
                String status = map.get("status");
                String prebonusvalue = map.get("prebonusvalue");//税前
                String bonusvalue = map.get("bonusvalue");//中奖金额
                if ("2".equals(status)) {//中奖
                    ticket.setPretaxPrize(new BigDecimal(prebonusvalue));
                    ticket.setPosttaxPrize(new BigDecimal(bonusvalue));
                    ticket.setTicketResultStatus(TicketResultStatus.win_little.getValue());
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("华阳查奖处理结果异常", e);
        }
        return false;
    }

    private String getElement(Ticket ticket, IVenderConfig huayConfig) {
        String md = "";
        XmlParse xmlParse = null;
        try {
            //头部
            String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
            String messageid = igenGeneratorService.getMessageId();
            xmlParse = HuayService.addHuayHead(huayConfig.getAgentCode(), queryCode, messageid, timestamp);
            Element bodyeElement = xmlParse.getBodyElement();
            Element elements = bodyeElement.addElement("elements");
            HashMap<String, String> bodyAttr = null;
            bodyAttr = new HashMap<String, String>();
            bodyAttr.put("id", ticket.getId());
            Element element2 = elements.addElement("element");
            xmlParse.addBodyElement(bodyAttr, element2);
            try {
                md = MD5Util.MD5(timestamp + huayConfig.getKey() + xmlParse.getBodyElement().asXML());
            } catch (Exception e) {
                logger.error("加密异常", e);
            }
            xmlParse.addHeaderElement("digest", md);
            return xmlParse.asXML();
        } catch (Exception e) {
            logger.error("华阳查中奖结果拼串异常", e);
        }
        return null;
    }

   


}
