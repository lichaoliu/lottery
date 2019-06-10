package com.lottery.ticket.vender.notice.impl.Gzcp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.ticket.IVenderConverter;

public class GzcpUtil {

	protected static Map<LotteryType, String> spLotteryType = new HashMap<LotteryType, String>();
	protected static Map<LotteryType, String> otherSpItem = new HashMap<LotteryType, String>();
	protected static Map<LotteryType, Map<String,String>> spItems = new HashMap<LotteryType, Map<String,String>>();
	
	public String convertPeilu(String info,LotteryType lotteryType,String content,IVenderConverter venderConverter) throws Exception {
		Map<String, Map<String, String>> peilus = getPeilu(info, lotteryType,venderConverter);
		
		StringBuilder odds = new StringBuilder();
		
		for(String id:peilus.keySet()) {
			odds.append(id.split(":")[0]);
			String lotterytype = id.split(":")[1];
			if(lotteryType==LotteryType.JCZQ_HHGG||lotteryType==LotteryType.JCLQ_HHGG) {
				odds.append("*").append(lotterytype);
			}
			odds.append("(");
			if(otherSpItem.containsKey(LotteryType.get(Integer.parseInt(lotterytype)))) {
				odds.append(peilus.get(id).get(otherSpItem.get(LotteryType.get(Integer.parseInt(lotterytype))))).append(":");
				peilus.get(id).remove(otherSpItem.get(LotteryType.get(Integer.parseInt(lotterytype))));
			}
			for(String key:peilus.get(id).keySet()) {
				odds.append(key).append("_").append(peilus.get(id).get(key)).append(",");
			}
			deleteBuilder(odds,",");
			odds.append(")").append("|");
		}
		deleteBuilder(odds,"|");
		return odds.toString();
	}
	
	private void deleteBuilder(StringBuilder odds,String delete) {
		if(odds.toString().endsWith(delete)) {
			odds = odds.deleteCharAt(odds.length()-1);
		}
	}
	
	
	private Map<String,Map<String,String>> getPeilu(String info,LotteryType lotteryType,IVenderConverter venderConverter) throws Exception {
		Map<String,Map<String,String>> peilu = new LinkedHashMap<String,Map<String,String>>();
		List<Element> items = getItems(info);
		
		for(Element item : items) {
			String id = item.elementTextTrim("id");
			if(lotteryType==LotteryType.JCZQ_HHGG||lotteryType==LotteryType.JCLQ_HHGG) {
				getPeiluByLotteryType(id,venderConverter.findLotteryType(id.split("_")[3]), peilu, item);
			}else {
				getPeiluByLotteryType(id,lotteryType, peilu, item);
			}
			
			
		}
		return peilu;
	}

	private void getPeiluByLotteryType(String id,LotteryType lotteryType,
			Map<String, Map<String, String>> peilu, Element item) {
		Map<String, String> itemmap = new LinkedHashMap<String, String>();
		Element element = item.element(spLotteryType.get(lotteryType));
		
		if(otherSpItem.containsKey(lotteryType)) {
			itemmap.put(otherSpItem.get(lotteryType), element.element(otherSpItem.get(lotteryType)).getTextTrim());
		}
		for(String name : spItems.get(lotteryType).keySet()) {
			Element v = element.element(name);
			if(null != v) {
				itemmap.put(spItems.get(lotteryType).get(name), v.getTextTrim());
			}
		}
		peilu.put(StringUtil.join(":", id.split("_")[0]+id.split("_")[2],String.valueOf(lotteryType.value)), itemmap);
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<Element> getItems(String info) throws Exception {
		Document document = DocumentHelper.parseText(info);
		return document.getRootElement().elements("item");
	}
	


	static {
	
		
		otherSpItem.put(LotteryType.JCLQ_RFSF, "letPoint");
		otherSpItem.put(LotteryType.JCLQ_DXF, "basePoint");
		
		spLotteryType.put(LotteryType.JCZQ_RQ_SPF, "letVs");
		spLotteryType.put(LotteryType.JCZQ_SPF_WRQ, "vs");
		spLotteryType.put(LotteryType.JCZQ_BF, "score");
		spLotteryType.put(LotteryType.JCZQ_JQS, "goal");
		spLotteryType.put(LotteryType.JCZQ_BQC, "half");
		spLotteryType.put(LotteryType.JCLQ_SF, "vs");
		spLotteryType.put(LotteryType.JCLQ_RFSF, "letVs");
		spLotteryType.put(LotteryType.JCLQ_SFC, "diff");
		spLotteryType.put(LotteryType.JCLQ_DXF, "bs");
		
		Map<String,String> spf = new HashMap<String,String>();
		spf.put("v3", "3");
		spf.put("v1", "1");
		spf.put("v0", "0");
		spItems.put(LotteryType.JCZQ_RQ_SPF, spf);
		spItems.put(LotteryType.JCZQ_SPF_WRQ, spf);
		spItems.put(LotteryType.JCLQ_SF, spf);
		spItems.put(LotteryType.JCLQ_RFSF, spf);
		
		
		Map<String,String> bs = new HashMap<String,String>();
		bs.put("g", "1");
		bs.put("l", "2");
		spItems.put(LotteryType.JCLQ_DXF, bs);
		
		Map<String,String> diff = new HashMap<String,String>();
		diff.put("v01", "01");
		diff.put("v02", "02");
		diff.put("v03", "03");
		diff.put("v04", "04");
		diff.put("v05", "05");
		diff.put("v06", "06");
		diff.put("v11", "11");
		diff.put("v12", "12");
		diff.put("v13", "13");
		diff.put("v14", "14");
		diff.put("v15", "15");
		diff.put("v16", "16");
		spItems.put(LotteryType.JCLQ_SFC, diff);
		
		Map<String,String> bf = new HashMap<String,String>();
		bf.put("v10", "10");
		bf.put("v20", "20");
		bf.put("v21", "21");
		bf.put("v30", "30");
		bf.put("v31", "31");
		bf.put("v32", "32");
		bf.put("v40", "40");
		bf.put("v41", "41");
		bf.put("v42", "42");
		bf.put("v50", "50");
		bf.put("v51", "51");
		bf.put("v52", "52");
		bf.put("v01", "01");
		bf.put("v02", "02");
		bf.put("v12", "12");
		bf.put("v03", "03");
		bf.put("v13", "13");
		bf.put("v23", "23");
		bf.put("v04", "04");
		bf.put("v14", "14");
		bf.put("v24", "24");
		bf.put("v05", "05");
		bf.put("v15", "15");
		bf.put("v25", "25");
		bf.put("v00", "00");
		bf.put("v11", "11");
		bf.put("v22", "22");
		bf.put("v33", "33");
		bf.put("v99", "99");
		bf.put("v09", "09");
		bf.put("v90", "90");
		spItems.put(LotteryType.JCZQ_BF, bf);
		
		Map<String,String> jqs = new HashMap<String,String>();
		jqs.put("v0", "0");
		jqs.put("v1", "1");
		jqs.put("v2", "2");
		jqs.put("v3", "3");
		jqs.put("v4", "4");
		jqs.put("v5", "5");
		jqs.put("v6", "6");
		jqs.put("v7", "7");
		spItems.put(LotteryType.JCZQ_JQS, jqs);
		
		Map<String,String> bqc = new HashMap<String,String>();
		bqc.put("v00", "00");
		bqc.put("v01", "01");
		bqc.put("v03", "03");
		bqc.put("v10", "10");
		bqc.put("v11", "11");
		bqc.put("v13", "13");
		bqc.put("v30", "30");
		bqc.put("v31", "31");
		bqc.put("v33", "33");
		spItems.put(LotteryType.JCZQ_BQC, bqc);
	}
}
