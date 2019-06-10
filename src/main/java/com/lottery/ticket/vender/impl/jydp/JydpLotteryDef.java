package com.lottery.ticket.vender.impl.jydp;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wyliuxiaoyan
 *
 */
public class JydpLotteryDef {
    /** 彩种转换 */
    protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
    /** 彩种转换 */
    protected static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
    /** 彩期转换*/
    protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
    /** 玩法转换*/
    public static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
    public static Map<String, String> WEEKSTR = new HashMap<String, String>();
    /** 票内容转换 */
    protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
    static {
    		WEEKSTR.put("1", "周一");
    		WEEKSTR.put("2", "周二");
    		WEEKSTR.put("3", "周三");
    		WEEKSTR.put("4", "周四");
    		WEEKSTR.put("5", "周五");
    		WEEKSTR.put("6", "周六");
    		WEEKSTR.put("7", "周日");	
    	
    	/**
		 * 彩期转换
		 * */
		//默认的期号转换
		IPhaseConverter defaultPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		
		
		phaseConverterMap.put(LotteryType.ZC_SFC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SD_11X5, defaultPhaseConverter);
		
		//彩种转换
		lotteryTypeMap.put(LotteryType.ZC_SFC, "102");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "103");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "106");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "107");
		lotteryTypeMap.put(LotteryType.CJDLT, "110");
		lotteryTypeMap.put(LotteryType.PL5, "109");
		lotteryTypeMap.put(LotteryType.PL3, "105");
		lotteryTypeMap.put(LotteryType.QXC, "108");
		lotteryTypeMap.put(LotteryType.SD_11X5, "409");
		
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"511");  
		lotteryTypeMap.put(LotteryType.JCZQ_BF,"502"); 
		lotteryTypeMap.put(LotteryType.JCZQ_JQS,"503"); 
		lotteryTypeMap.put(LotteryType.JCZQ_BQC,"504"); 
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"501"); 
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG,"509"); 
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF,"505");  
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"506");  
		lotteryTypeMap.put(LotteryType.JCLQ_SFC,"507");  
		lotteryTypeMap.put(LotteryType.JCLQ_DXF,"508");  
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG,"510");  
		
		//玩法转换
		playTypeMap.put(PlayType.dlt_dan.getValue(), "1");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "2");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "3");
		playTypeMap.put(PlayType.p5_dan.getValue(), "1");
		playTypeMap.put(PlayType.p5_fu.getValue(), "2");
		playTypeMap.put(PlayType.qxc_dan.getValue(), "1");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "2");
		
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "11");
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "13");
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "13");
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "21");
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "23");
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "26");
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "31");
		
		//sd11x5
		playTypeMap.put(PlayType.sd11x5_sr2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_sr3.getValue(), "3");
		playTypeMap.put(PlayType.sd11x5_sr4.getValue(), "4");
		playTypeMap.put(PlayType.sd11x5_sr5.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_sr6.getValue(), "6");
		playTypeMap.put(PlayType.sd11x5_sr7.getValue(), "7");
		playTypeMap.put(PlayType.sd11x5_sr8.getValue(), "8");
		playTypeMap.put(PlayType.sd11x5_sq1.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sq2.getValue(), "9");//前二直选
		playTypeMap.put(PlayType.sd11x5_sq3.getValue(), "11");//前三直选
		playTypeMap.put(PlayType.sd11x5_sz2.getValue(), "10");
		playTypeMap.put(PlayType.sd11x5_sz3.getValue(), "12");
		
		playTypeMap.put(PlayType.sd11x5_mr2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr3.getValue(), "3");
		playTypeMap.put(PlayType.sd11x5_mr4.getValue(), "4");
		playTypeMap.put(PlayType.sd11x5_mr5.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_mr6.getValue(), "6");
		playTypeMap.put(PlayType.sd11x5_mr7.getValue(), "7");
		playTypeMap.put(PlayType.sd11x5_mr8.getValue(), "8");
		playTypeMap.put(PlayType.sd11x5_mq1.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mq2.getValue(), "9");//前二直选
		playTypeMap.put(PlayType.sd11x5_mq3.getValue(), "11");//前三直选
		playTypeMap.put(PlayType.sd11x5_mz2.getValue(), "10");
		playTypeMap.put(PlayType.sd11x5_mz3.getValue(), "12");
		
		playTypeMap.put(PlayType.sd11x5_dr2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dr3.getValue(), "3");
		playTypeMap.put(PlayType.sd11x5_dr4.getValue(), "4");
		playTypeMap.put(PlayType.sd11x5_dr5.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr6.getValue(), "6");
		playTypeMap.put(PlayType.sd11x5_dr7.getValue(), "7");
		playTypeMap.put(PlayType.sd11x5_dr8.getValue(), "8");
		playTypeMap.put(PlayType.sd11x5_dz2.getValue(), "10");
		playTypeMap.put(PlayType.sd11x5_dz3.getValue(), "12");
		
		
		
		//足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "2");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "2");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "2");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "2");
		
		
		//竞彩串法
	   playTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "1");
	   playTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "2");
	   playTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "3");
	   playTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "4");
       playTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "5");
       playTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "6");
       playTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "7");
       playTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "8");
       playTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "9");
       playTypeMap.put(PlayType.jczq_spf_4_11.getValue(), "10");
       playTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "11");
       playTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "12");
       playTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "13");
       playTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "14");
       playTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "15");
       playTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "16");
       playTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "17");
       playTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "18");
       playTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "19");
       playTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "20");
   	   playTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "21");
   	   playTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "22");
   	   playTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "23");
   	   playTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "24");
   	   playTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "25");
   	   playTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "26");
   	   playTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "27"); 
       playTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "28");
       playTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "29");
       playTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "30");
       playTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "31");
       playTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "32");
       playTypeMap.put(PlayType.jczq_spf_7_120.getValue(), "33");
       playTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "35");
       playTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "36");
       playTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "37");
       playTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "38");
       playTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "39");
       playTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "40");
       playTypeMap.put(PlayType.jczq_spf_8_247.getValue(), "41");
       
       
   	 
   	 playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(), "1");
   	 playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(), "2");
   	 playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(), "3");
   	 playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(), "4");
  	 playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(), "5");
   	 playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(), "6");
   	 playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(), "7");
   	 playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(), "8");
   	 playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(), "9");
   	 playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(), "10");
   	 playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(), "11");
   	 playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(), "12");
  	 playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(), "13");
  	 playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(), "14");
  	 playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(), "15");
  	 playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(), "16");
  	 playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(), "17");
   	 playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(), "18");
     playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(), "19");
  	 playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(), "20");
  	 playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(), "21");
  	 playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(), "22");
  	 playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(), "23");
  	 playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(), "24");
  	 playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(), "25");
  	 playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(), "26");
  	 playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(), "27");
   	 playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(), "28");
   	 playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(), "35");
   	 
   	
   	 playTypeMap.put(PlayType.jczq_bf_1_1.getValue(), "1");
   	 playTypeMap.put(PlayType.jczq_bf_2_1.getValue(), "2");
   	 playTypeMap.put(PlayType.jczq_bf_3_1.getValue(), "3");
   	 playTypeMap.put(PlayType.jczq_bf_3_3.getValue(), "4");
   	 playTypeMap.put(PlayType.jczq_bf_3_4.getValue(), "5");
   	 playTypeMap.put(PlayType.jczq_bf_4_1.getValue(), "6");
     playTypeMap.put(PlayType.jczq_bf_4_4.getValue(), "7");
  	 playTypeMap.put(PlayType.jczq_bf_4_5.getValue(), "8");
  	 playTypeMap.put(PlayType.jczq_bf_4_6.getValue(), "9");
  	 playTypeMap.put(PlayType.jczq_bf_4_11.getValue(), "10");
   	 playTypeMap.put(PlayType.jczq_bf_5_1.getValue(), "11");
   	 playTypeMap.put(PlayType.jczq_bf_6_1.getValue(), "18");
   	 playTypeMap.put(PlayType.jczq_bf_7_1.getValue(), "28");
   	 playTypeMap.put(PlayType.jczq_bf_8_1.getValue(), "35");
   	
   	 
   	 playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "1");
   	 playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "2");
   	 playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "3");
   	 playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "4");
  	 playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "5");
   	 playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "6");
     playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "7");
  	 playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "8");
  	 playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "9");
  	 playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "10");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "11");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "12");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "13");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "14");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "15");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "16");
   	 playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "17");
   	 playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "18");
     playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "19");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "20");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "21");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "22");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "23");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "24");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "25");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "26");
  	 playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "27");
   	 playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "28");
   	 playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "29");
   	 playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "30");
   	 playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "31");
   	 playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "32");
   	 playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "33");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "35");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "36");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "37");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "38");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "39");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "40");
   	 playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "41");
   	 
   	 playTypeMap.put(PlayType.jczq_mix_2_1.getValue(), "2");
   	 playTypeMap.put(PlayType.jczq_mix_3_1.getValue(), "3");
     playTypeMap.put(PlayType.jczq_mix_3_3.getValue(), "4");
  	 playTypeMap.put(PlayType.jczq_mix_3_4.getValue(), "5");
   	 playTypeMap.put(PlayType.jczq_mix_4_1.getValue(), "6");
   	 playTypeMap.put(PlayType.jczq_mix_4_4.getValue(), "7");
  	 playTypeMap.put(PlayType.jczq_mix_4_5.getValue(), "8");
  	 playTypeMap.put(PlayType.jczq_mix_4_6.getValue(), "9");
  	 playTypeMap.put(PlayType.jczq_mix_4_11.getValue(), "10");
   	 playTypeMap.put(PlayType.jczq_mix_5_1.getValue(), "11");
   	 playTypeMap.put(PlayType.jczq_mix_5_5.getValue(), "12");
   	 playTypeMap.put(PlayType.jczq_mix_5_6.getValue(), "13");
   	 playTypeMap.put(PlayType.jczq_mix_5_10.getValue(), "14");
   	 playTypeMap.put(PlayType.jczq_mix_5_16.getValue(), "15");
   	 playTypeMap.put(PlayType.jczq_mix_5_20.getValue(), "16");
   	 playTypeMap.put(PlayType.jczq_mix_5_26.getValue(), "17");
   	 playTypeMap.put(PlayType.jczq_mix_6_1.getValue(), "18");
   	 playTypeMap.put(PlayType.jczq_mix_6_6.getValue(), "19");
   	 playTypeMap.put(PlayType.jczq_mix_6_7.getValue(), "20");
   	 playTypeMap.put(PlayType.jczq_mix_6_15.getValue(), "21");
   	 playTypeMap.put(PlayType.jczq_mix_6_20.getValue(), "22");
   	 playTypeMap.put(PlayType.jczq_mix_6_22.getValue(), "23");
   	 playTypeMap.put(PlayType.jczq_mix_6_35.getValue(), "24");
   	 playTypeMap.put(PlayType.jczq_mix_6_42.getValue(), "25");
   	 playTypeMap.put(PlayType.jczq_mix_6_50.getValue(), "26");
   	 playTypeMap.put(PlayType.jczq_mix_6_57.getValue(), "27");
   	 playTypeMap.put(PlayType.jczq_mix_7_1.getValue(), "28");
   	 playTypeMap.put(PlayType.jczq_mix_7_7.getValue(), "29");
  	 playTypeMap.put(PlayType.jczq_mix_7_8.getValue(), "30");
  	 playTypeMap.put(PlayType.jczq_mix_7_21.getValue(), "31");
  	 playTypeMap.put(PlayType.jczq_mix_7_35.getValue(), "32");
  	 playTypeMap.put(PlayType.jczq_mix_7_120.getValue(), "33");
   	 playTypeMap.put(PlayType.jczq_mix_8_1.getValue(), "35");
   	 playTypeMap.put(PlayType.jczq_mix_8_8.getValue(), "36");
   	 playTypeMap.put(PlayType.jczq_mix_8_9.getValue(), "37");
   	 playTypeMap.put(PlayType.jczq_mix_8_28.getValue(), "38");
   	 playTypeMap.put(PlayType.jczq_mix_8_56.getValue(), "39");
   	 playTypeMap.put(PlayType.jczq_mix_8_70.getValue(), "40");
   	 playTypeMap.put(PlayType.jczq_mix_8_247.getValue(), "41");
   	 
   	 
   	 playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(), "1");
   	 playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(), "2");
   	 playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(), "3");
   	 playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(), "4");
  	 playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(), "5");
   	 playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(), "6");
   	 playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(), "7");
  	 playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(), "8");
  	 playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(), "9");
  	 playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(), "10");
   	 playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(), "11");
   	 playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(), "18");
   	 playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(), "28");
   	 playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(), "35");
   	 
   	 
		
		
		
   	 
     	playTypeMap.put(PlayType.jclq_sf_1_1.getValue(),"1");
      	playTypeMap.put(PlayType.jclq_sf_2_1.getValue(),"2");
     	playTypeMap.put(PlayType.jclq_sf_3_1.getValue(),"3");
     	playTypeMap.put(PlayType.jclq_sf_3_3.getValue(),"4");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(),"5");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(),"6");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(),"7");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(),"8");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(),"9");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(),"10");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(),"11");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(),"12");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(),"13");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(),"14");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(),"15");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(),"16");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(),"17");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(),"18");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(),"19");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(),"20");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(),"21");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(),"22");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(),"23");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(),"24");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(),"25");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(),"26");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(),"27");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(),"28");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(),"29");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(),"30");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(),"31");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(),"32");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(),"33");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(),"35");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(),"36");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(),"37");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(),"38");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(),"39");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(),"40");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(),"41");
		
		
		
		
		
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"1");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"2");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"3");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"4");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"5");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"6");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"7");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"8");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"9");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"10");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"11");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"12");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"13");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"14");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"15");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"16");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"17");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"18");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"19");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"20");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"21");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"22");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"23");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"24");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"25");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"26");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"27");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"28");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"29");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"30");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"31");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"32");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"33");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"35");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"36");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"37");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"38");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"39");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"40");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"41");
		
		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"1");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"2");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"3");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"4");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"5");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"6");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"7");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"8");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"9");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"10");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"11");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"18");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"28");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"35");
		
		
		
		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"1");
		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"2");
		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"3");
		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"4");
		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"5");
		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"6");
		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"7");
		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"8");
		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"9");
		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"10");
		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"11");
		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"12");
		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"13");
		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"14");
		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"15");
		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"16");
		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"17");
		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"18");
		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"19");
		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"20");
		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"21");
		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"22");
		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"23");
		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"24");
		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"25");
		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"26");
		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"27");
		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"28");
		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"29");
		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"30");
		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"31");
		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"32");
		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"33");
		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"35");
		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"36");
		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"37");
		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"38");
		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"39");
		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"40");
		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"41");
		
		
		
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"2");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"3");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"4");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"5");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"6");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"7");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"8");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"9");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"10");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"11");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"12");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"13");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"14");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"15");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"16");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"17");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"18");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"19");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"20");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"21");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"22");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"23");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"24");
		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"25");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"26");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"27");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"28");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"29");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"30");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"31");
		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"32");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"33");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"35");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"36");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"37");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"38");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"39");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"40");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"41");
		
		
		
		 //dlt
		IVenderTicketConverter dlt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.split("\\^");
				int i=0;
				for(String s:strs){
					String content=s.replace(",", "/").replace("|", "//");
					strBuilder.append(content);
					if(i!=strs.length-1){
						strBuilder.append(",");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		
		IVenderTicketConverter dltdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String strs=contentStr.replace("^","");
				String content=strs.replace(",", "/").replace("|", ",").replace("#", "//");;
				strBuilder.append(content);
	        return strBuilder.toString();
	   }};
	   
	   IVenderTicketConverter sdx5ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.split("\\^");
				int i=0;
				for(String s:strs){
					String []cons=s.split("\\,");
					int j=0;
					for(String con:cons){
						strBuilder.append(con);
						if(j!=cons.length-1){
							strBuilder.append("/");
						}
						j++;
					}
					if(i!=strs.length-1){
						strBuilder.append(",");
					}
					i++;
				}
	          return strBuilder.toString();
	   }};
	   
	   IVenderTicketConverter sdx5fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String []strs=contentStr.split("\\^");
				StringBuilder strBuilder=new StringBuilder();
				int i=0;
				for(String s:strs){
					String content=s.replace(",", "//");
					strBuilder.append(content);
					if(i!=strs.length-1){
						strBuilder.append(",");
					}
					i++;
				}
	          return strBuilder.toString();
	   }};
	   
	   IVenderTicketConverter sdx5zxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String strs=contentStr.replace("^","");
				StringBuilder strBuilder=new StringBuilder();
				String content=strs.replace(",", "/").replace(";", "//");
				strBuilder.append(content);
	            return strBuilder.toString();
	   }};
	   
	   IVenderTicketConverter sdx5dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String strs=contentStr.replace("^","");
				StringBuilder strBuilder=new StringBuilder();
				String content=strs.replace(",", "/").replace("#", "$");
				strBuilder.append(content);
	          return strBuilder.toString();
	   }};
	   IVenderTicketConverter p5ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.split("\\^");
				int i=0;
				for(String s:strs){
					String []cons=s.split("\\,");
					int j=0;
					for(String con:cons){
						strBuilder.append(Integer.parseInt(con));
						if(j!=cons.length-1){
							strBuilder.append("/");
						}
						j++;
					}
					if(i!=strs.length-1){
						strBuilder.append(",");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		
		
		 IVenderTicketConverter p3zxfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
					String []strs=contentStr.replace("^","").split("\\|");
					int i=0;
					for(String s:strs){
						String []cons=s.split("\\,");
						int j=0;
						for(String con:cons){
							strBuilder.append(Integer.parseInt(con));
							if(j!=cons.length-1){
								strBuilder.append("/");
							}
							j++;
						}
						if(i!=strs.length-1){
							strBuilder.append("//");
						}
						i++;
					}
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter p3hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.replace("^","").split("\\,");
				int i=0;
				for(String s:strs){
					strBuilder.append(s);
					if(i!=strs.length-1){
						strBuilder.append("/");
					}
					i++;	
				}
	        return strBuilder.toString();
	}};
		
		
		 IVenderTicketConverter p5fs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
					String strs=contentStr.replace("^","");
					String []contents=strs.split("\\|");
					int j=0;
					for(String content:contents){
						String []cons=content.split("\\,");
						int i=0;
						for(String con:cons){
							strBuilder.append(Integer.parseInt(con));
							if(i!=cons.length-1){
								strBuilder.append("/");
							}
							i++;
						}
						if(j!=contents.length-1){
							strBuilder.append("//");
						}
						j++;
					}
		             return strBuilder.toString();
				}};
				
		 //足彩单式
		IVenderTicketConverter zuds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.replace("~","9").split("\\^");
				int i=0;
				for(String s:strs){
					String content=s.replace(",", "/");
					strBuilder.append(content);
					if(i!=strs.length-1){
						strBuilder.append(",");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter zufs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String []strs=contentStr.replace("^","").replace("~","9").split("\\,");
				StringBuilder strBuilder=new StringBuilder();
				int j=0;
				for(String str:strs){
					for(int i=0;i<str.length();i++){
						strBuilder.append(str.substring(i,i+1));
						if(i!=str.length()-1){
							strBuilder.append("/");
						}
					}
					if(j!=strs.length-1){
						strBuilder.append("//");
					}
					j++;
				}
		        return strBuilder.toString();
		}};
		
		 //jc
		IVenderTicketConverter jcspf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr =ticket.getContent().split("-")[1].replace("^", "");
				StringBuilder strBuilder=new StringBuilder();
				String []cons=contentStr.split("\\|");
				int i=0;
				for(String con:cons){
					String c=con.substring(0, 8);
					String week=DateUtil.getWeekOfDate(c);
					String content=con.substring(8).split("\\(")[1].split("\\)")[0].replace(",", "/");
					strBuilder.append(week).append(con.substring(8).split("\\(")[0]).append(",").append(content);
					if(i!=cons.length-1){
						strBuilder.append("//");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		 //jc
		IVenderTicketConverter jczqbf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				int i=0;
				for(String con:cons){
					String c=con.substring(0, 8);
					String week=DateUtil.getWeekOfDate(c);
					String content=con.substring(8).split("\\(")[1].split("\\)")[0].replace("99", "44").replace("09", "34").replace("90", "43");
					String []contents=content.split("\\,");
					strBuilder.append(week+con.substring(8).split("\\(")[0]).append(",");
					int j=0;
					for(String ctent:contents){
						strBuilder.append(ctent.substring(0,1)).append(":").append(ctent.substring(1));
						if(j!=contents.length-1){
							strBuilder.append("/");
						}
						j++;
					}
					if(i!=cons.length-1){
						strBuilder.append("//");
					}
					i++;
				}
		        return strBuilder.toString();
			}};
			
			IVenderTicketConverter jczqbqc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []cons=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
					int i=0;
					for(String con:cons){
						String c=con.substring(0, 8);
						String week=DateUtil.getWeekOfDate(c);
						String content=con.substring(8).split("\\(")[1].split("\\)")[0];
						String []contents=content.split("\\,");
						strBuilder.append(week+con.substring(8).split("\\(")[0]).append(",");
						int j=0;
						for(String ctent:contents){
							strBuilder.append(ctent.substring(0,1)).append("_").append(ctent.substring(1));
							if(j!=contents.length-1){
								strBuilder.append("/");
							}
							j++;
						}
						if(i!=cons.length-1){
							strBuilder.append("//");
						}
						i++;
					}
			         return strBuilder.toString();
				}};
			
			
				IVenderTicketConverter jcmix = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer ticketContent = new StringBuffer();
						String contentStr = ticket.getContent().split("-")[1].replace("^","");
						String[] oneCodes = contentStr.split("\\|");
						int j=0;
						for (String oneCode:oneCodes) {
							String c=oneCode.substring(0, 8);
							String week=DateUtil.getWeekOfDate(c);
							String code=oneCode.split("\\*")[1].split("\\(")[0];
							ticketContent.append(week).append(oneCode.substring(8,11)).append(",");
							if("3010".equals(code)){
								ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace(",", "/"));
							}else if("3006".equals(code)){
								ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("1","1R").replace("3","3R").replace("0","0R").replace(",", "/"));
							}else if("3008".equals(code)){//jqs
								ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("0","0B").replace("1","1B").replace("2","2B").replace("3","3B")
							   .replace("4","4B").replace("5","5B").replace("6","6B").replace("7","7B").replace(",", "/"));
							}else if("3007".equals(code)){//bf
								String []cons=oneCode.split("\\(")[1].split("\\)")[0].replace("99", "44").replace("09", "34").replace("90", "43").split("\\,");
								int i=0;
								for(String con:cons){
									ticketContent.append(con.substring(0,1)).append(":").append(con.substring(1));
									if(i!=cons.length-1){
										ticketContent.append("/");
									}
									i++;
								}
							}else if("3009".equals(code)){//bqc
								String []cons=oneCode.split("\\(")[1].split("\\)")[0].split("\\,");
								int i=0;
								for(String con:cons){
									ticketContent.append(con.substring(0,1)).append("_").append(con.substring(1));
									if(i!=cons.length-1){
										ticketContent.append("/");
									}
									i++;
								}
							}
							if(j!=oneCodes.length-1){
								ticketContent.append("//");
							}
							j++;
						}
						 return ticketContent.toString();
				}};
				
				IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
				    	String contentStr =ticket.getContent().split("-")[1].replace("^", "");
						String []cons=contentStr.split("\\|");
						int i=0;
						for(String con:cons){
							String c=con.substring(0, 8);
							String week=DateUtil.getWeekOfDate(c);
							String content=con.substring(8).split("\\(")[1].split("\\)")[0].replace(",", "/");
							strBuilder.append(week).append(con.substring(8).split("\\(")[0]).append(",").append(content.replace("3","1"));
							if(i!=cons.length-1){
								strBuilder.append("//");
							}
							i++;
						}
				        return strBuilder.toString();
				}};
		
				IVenderTicketConverter jclqsfc = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						String []cons=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
						int i=0;
						for(String con:cons){
							String c=con.substring(0, 8);
							String week=DateUtil.getWeekOfDate(c);
							String content=con.substring(8).split("\\(")[1].split("\\)")[0];
							String []contents=content.split("\\,");
							strBuilder.append(week+con.substring(8).split("\\(")[0]).append(",");
							int j=0;
							for(String ctent:contents){
								strBuilder.append(ctent.replace("01","51").replace("02","52").replace("03","53").replace("04","54").replace("05","55").replace("06","56")
								.replace("11","01").replace("12","02").replace("13","03").replace("14","04").replace("15","05").replace("16","06"));
								if(j!=contents.length-1){
									strBuilder.append("/");
								}
								j++;
							}
							if(i!=cons.length-1){
								strBuilder.append("//");
							}
							i++;
				
					}
			        return strBuilder.toString();
			}};
			
			
			IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer ticketContent = new StringBuffer();
					String contentStr = ticket.getContent().split("-")[1].replace("^","");
					String[] oneCodes = contentStr.split("\\|");
					int j=0;
					for (String oneCode:oneCodes) {
						String c=oneCode.substring(0, 8);
						String week=DateUtil.getWeekOfDate(c);
						String code=oneCode.split("\\*")[1].split("\\(")[0];
						ticketContent.append(week).append(oneCode.substring(8,11)).append(",");
						if("3001".equals(code)){//sf
							ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("3","1").replace(",", "/"));
						}else if("3002".equals(code)){//rfsf
							ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("3","1D").replace("0","0D").replace(",", "/"));
						}else if("3004".equals(code)){//dxf
							ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("1","1B").replace("2","2B").replace(",", "/"));
						}else if("3003".equals(code)){//sfc
							ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("01","51").replace("02","52").replace("03","53").replace("04","54").replace("05","55").replace("06","56")
									.replace("11","01").replace("12","02").replace("13","03").replace("14","04").replace("15","05").replace("16","06").replace(",", "/"));
						}
						if(j!=oneCodes.length-1){
							ticketContent.append("//");
						}
						j++;
					}
					 return ticketContent.toString();
			}};
		
			
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, dlt);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, dlt);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dltdt);
		
		playTypeToAdvanceConverterMap.put(PlayType.p5_dan, p5ds);
		playTypeToAdvanceConverterMap.put(PlayType.p5_fu, p5fs);
		
		
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, p5ds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, p5ds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, p5ds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, p3zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, p3zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, p3zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, p3hz);
		
		playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, p5ds);
		playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, p5fs);
		
		//足彩
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zuds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zufs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zuds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zufs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zuds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zufs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zuds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zufs);
		
		//11x5
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq2, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq3, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz2, sdx5ds);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz3, sdx5ds);
		
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr2, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr3, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr4, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr5, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr6, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr7, sdx5fs);
//		playTypeToAdvanceConverterMap.put(PlayType.sd11c5_mr8, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq1, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq2, sdx5zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq3, sdx5zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz2, sdx5fs);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz3, sdx5fs);
		
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr2, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr3, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr4, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr5, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr6, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr7, sdx5dt);
//		playTypeToAdvanceConverterMap.put(PlayType.sd11c5_dr8, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz2, sdx5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz3, sdx5dt);
		
		 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jcspf);
     	 
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jcspf);
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jczqbf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11,jczqbf);
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jcspf);
  	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jczqbqc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jczqbqc);

  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,jcmix);
  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,jcmix);
  		
  		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jclqsf);
			    		
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jclqsf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jclqsf);
 			    		
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,jclqsfc);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,jclqsfc);
			    		
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jcspf);
 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jcspf);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jcspf);
		 
 		 
 		 
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,jclqmix);
		
		
    }
    
  
}
