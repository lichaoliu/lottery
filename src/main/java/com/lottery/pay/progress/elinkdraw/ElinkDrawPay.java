package com.lottery.pay.progress.elinkdraw;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.pay.RechargeRequestData;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.progress.elinkdraw.util.DigestMD5;
import com.lottery.pay.progress.elinkdraw.util.SecureUtil;
import com.lottery.pay.progress.elinknew.util.HttpClient;
import com.lottery.pay.progress.elinkpc.ElinkpcUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 银联draw
 * @author wyliuxiaoyan
 *
 */
public class ElinkDrawPay {
	
	protected static Logger logger = LoggerFactory.getLogger(ElinkDrawPay.class);
	
	

   
	public static Map<String, String> getReturnUrl(String batchNo,List<LotteryDrawAmount> lotteryDrawList,IPayConfig payConfig,String getClassPath) throws Exception {
		String nowTime=DateUtil.format("yyyyMMdd", new Date());
	    String fileName=getClassPath+payConfig.getPartner()+"_"+nowTime+"_"+batchNo+".txt";  
        StringBuilder stringBuilder = new StringBuilder();//内容更新  
       
        	BigDecimal totalMoney= getTotalMoney(lotteryDrawList);
        	BigDecimal totalLotteryDrawAmount=BigDecimal.ZERO;
            stringBuilder.append(payConfig.getPartner()).append("|").append(batchNo).append("|").append(lotteryDrawList.size()).append("|").append(totalMoney.doubleValue()).append("<br>\n");  
            for(LotteryDrawAmount lotteryDrawAmount:lotteryDrawList){
            	stringBuilder.append(nowTime).append("|");
            	stringBuilder.append(lotteryDrawAmount.getId()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getBankId()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getUserName()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getBankName()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getProvince()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getCity()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getBankAddress()).append("|");
            	stringBuilder.append(lotteryDrawAmount.getDrawAmount()).append("|");
            	stringBuilder.append("提现").append("|").append("01").append("<br>\n");  
            	totalLotteryDrawAmount=totalLotteryDrawAmount.add(lotteryDrawAmount.getDrawAmount());
            }
            writeFile(nowTime,batchNo,stringBuilder.toString(),payConfig,fileName);
            String path=getClassPath+payConfig.getEncryptCerPath();
            //txt签名密文
            String chkValue1 = SecureUtil.digitalSign(payConfig.getPartner(), stringBuilder.toString(), path);
           
    		String plain = stringBuilder.toString() + chkValue1;
    		byte[] temSen=getFlatContent(fileName);
			String flatContent = new String(temSen, "GBK");
			// 对需要上传的字段签名
			String signValue = DigestMD5.MD5Sign(payConfig.getPartner(), fileName, plain.getBytes("GBK"), path);
			logger.error("文件上传签名内容:" + signValue);
    	    Map<String,String>resultMap=new HashMap<String, String>();
    	    resultMap.put("merId", payConfig.getPartner());
    	    resultMap.put("fileName", fileName);
    	    resultMap.put("fileContent", flatContent);//压缩后内容
    	    resultMap.put("chkValue", signValue);//签名内容
    	    return resultMap;
	}


	

	/**
	 * 订单查询
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> queryOrderInfo(String orderId,String transTime,String partner,String signCertPath,String signCertPwd) throws Exception{
		//把请求参数打包成数组
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", "5.0.0");// 版本号
		req.put("encoding", CharsetConstant.CHARSET_UTF8);// 字符编码
		req.put("orderId", orderId);// 订单号
		req.put("signMethod", "01");
		req.put("txnType", "00");// 交易类型
		req.put("txnSubType", "00");
		req.put("bizType", "000000");//产品类型
		req.put("accessType", "0");//接入类型
		req.put("merId", partner);// 商户代码
		req.put("txnTime",transTime);
		Map<String, String> resData= ElinkpcUtil.signData(req,signCertPath,signCertPwd);
		return  resData;
	}
	
	/**
	 * 写入文件
	 * @param nowTime
	 * @param batchNo
	 * @param stringBuilder
	 * @param payConfig
	 * @return
	 */
	private static String writeFile(String nowTime,String batchNo,String content,IPayConfig payConfig,String fileName){
		File f = new File(fileName);  
        if (!f.exists()) { 
            try {
				f.createNewFile();// 不存在则创建  
				BufferedWriter output = new BufferedWriter(new FileWriter(f));  
		        output.write(content);  
		        output.close(); 
			} catch (IOException e) {
				logger.error("写入txt异常");
			}
        }  
        return fileName;
	}
	
	/**
	 *  压缩文件
	 * @param fileName
	 * @return
	 */
	private static byte[] getFlatContent(String fileName){
		// 文件上传准备
		File file = new File(fileName);
		byte[] temSen = null;
		try {
			temSen = SecureUtil.getBytes(file);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return temSen;
	}
	
	/**
     * 金额相加
     * @param lotteryDrawAmounts
     * @return
     */
    private static BigDecimal getTotalMoney(List<LotteryDrawAmount> lotteryDrawAmounts){
    	BigDecimal totalLotteryDrawAmount=BigDecimal.ZERO;
    	for(LotteryDrawAmount lotteryDrawAmount:lotteryDrawAmounts){
    		totalLotteryDrawAmount=totalLotteryDrawAmount.add(lotteryDrawAmount.getDrawAmount());
    	}
    	return totalLotteryDrawAmount;
    }

}
