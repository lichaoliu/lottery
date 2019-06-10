package com.lottery.pay.impl.elinkdraw;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.util.DateUtil;

public class ElinkDrawUtil {

	   
		   
		public Map<String,String> getData(String totalCount,String totalMoney,String batchNo){
		   Map<String, String> contentData = new HashMap<String, String>();
		   // 固定填写
			contentData.put("version", "1.1.0");// M
			contentData.put("encoding", "UTF-8");// M
			// 01RSA02 MD5 (暂不支持)
			contentData.put("signMethod", "01");// M
			contentData.put("txnType", "21");// M	// 取值：21 批量交易
			contentData.put("txnSubType", "03");// M// 填写：01：退货02：代收03：代付
			contentData.put("bizType", "000000");// M
			contentData.put("channelType", "07");// M
			// 0：普通商户直连接入1：收单机构接入2：平台类商户接入
			contentData.put("accessType", "0");// M
			// 接入类型为商户接入时需上送
			contentData.put("merId", "802290049000180");// C//
			//商户类型为平台类商户接入时必须上送
			contentData.put("subMerId", "");//C//
			//商户类型为平台类商户接入时必须上送
			contentData.put("subMerName", "");//C
			contentData.put("subMerAbbr", "");//C//商户类型为平台类商户接入时必须上送
			// 批量交易时填写，当天唯一,0001-9999，商户号+批次号+上交易时间确定一笔交易
			contentData.put("batchNo", batchNo);// M
			
			contentData.put("txnTime", DateUtil.format("yyyyMMddHHmmss", new Date()));// M// 前8位需与文件中的委托日期保持一致
			contentData.put("totalQty",totalCount);// M// 批量交易时填写，填写批量中，总的交易比数
			contentData.put("totalAmt",totalMoney);// M总的交易金额
			contentData.put("fileContent", "fileContent文件流");// M// 使用DEFLATE压缩算法压缩后，Base64编码的方式传输经压缩编码的文件内容

			//商户自定义保留域，交易应答时会原样返回
			contentData.put("reqReserved", "");//O
			//交易包含多个子域，所有子域需用“{}”包含，子域间以“&”符号链接。格式如下：{子域名1=值&子域名2=值&子域名3=值}。具体子域的名称、取值根据商户不同而定。
			contentData.put("reserved", "");//O
			return contentData;
		}


}
