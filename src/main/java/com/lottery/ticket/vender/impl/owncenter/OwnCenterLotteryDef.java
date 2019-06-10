package com.lottery.ticket.vender.impl.owncenter;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengqinyun on 15/5/20.
 */
public class OwnCenterLotteryDef {
	/** 彩种转换 */
	protected static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	public static Map<Integer, String> playTypeMapJc = new HashMap<Integer, String>();
	/** 票内容转换 */
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();



	static{
		
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
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_SFC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, defaultPhaseConverter);
		
		
		//竞彩串法
		 playTypeMapJc.put(PlayType.jczq_spf_1_1.getValue(), "1001");
		 playTypeMapJc.put(PlayType.jczq_spf_2_1.getValue(), "2001");
		 playTypeMapJc.put(PlayType.jczq_spf_3_1.getValue(), "3001");
        playTypeMapJc.put(PlayType.jczq_spf_4_1.getValue(), "4001");
        playTypeMapJc.put(PlayType.jczq_spf_5_1.getValue(), "5001");
        playTypeMapJc.put(PlayType.jczq_spf_6_1.getValue(), "6001");
        playTypeMapJc.put(PlayType.jczq_spf_7_1.getValue(), "7001");
        playTypeMapJc.put(PlayType.jczq_spf_8_1.getValue(), "8001");
        playTypeMapJc.put(PlayType.jczq_spf_3_3.getValue(), "3003");
        playTypeMapJc.put(PlayType.jczq_spf_3_4.getValue(), "3004");
        playTypeMapJc.put(PlayType.jczq_spf_4_4.getValue(), "4004");
        playTypeMapJc.put(PlayType.jczq_spf_4_5.getValue(), "4005");
        playTypeMapJc.put(PlayType.jczq_spf_4_6.getValue(), "4006");
        playTypeMapJc.put(PlayType.jczq_spf_4_11.getValue(), "4011");
        playTypeMapJc.put(PlayType.jczq_spf_5_5.getValue(), "5005");
        playTypeMapJc.put(PlayType.jczq_spf_5_6.getValue(), "5006");
    	 playTypeMapJc.put(PlayType.jczq_spf_5_10.getValue(), "5010");
    	 playTypeMapJc.put(PlayType.jczq_spf_5_16.getValue(), "5016");
    	 playTypeMapJc.put(PlayType.jczq_spf_5_20.getValue(), "5020");
    	 playTypeMapJc.put(PlayType.jczq_spf_5_26.getValue(), "5026");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_6.getValue(), "6006");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_7.getValue(), "6007");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_15.getValue(), "6015");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_20.getValue(), "6020");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_22.getValue(), "6022");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_35.getValue(), "6035");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_42.getValue(), "6042");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_50.getValue(), "6050");
    	 playTypeMapJc.put(PlayType.jczq_spf_6_57.getValue(), "6057");
    	 playTypeMapJc.put(PlayType.jczq_spf_7_7.getValue(), "7007");
    	 playTypeMapJc.put(PlayType.jczq_spf_7_8.getValue(), "7008");
    	 playTypeMapJc.put(PlayType.jczq_spf_7_21.getValue(), "7021");
    	 playTypeMapJc.put(PlayType.jczq_spf_7_35.getValue(), "7035");
    	 playTypeMapJc.put(PlayType.jczq_spf_7_120.getValue(), "7120");
    	 playTypeMapJc.put(PlayType.jczq_spf_8_8.getValue(), "8008");
    	 playTypeMapJc.put(PlayType.jczq_spf_8_9.getValue(), "8009");
    	 playTypeMapJc.put(PlayType.jczq_spf_8_28.getValue(), "8028");
    	 playTypeMapJc.put(PlayType.jczq_spf_8_56.getValue(), "8056");
    	 playTypeMapJc.put(PlayType.jczq_spf_8_70.getValue(), "8070");
    	 playTypeMapJc.put(PlayType.jczq_spf_8_247.getValue(), "8247");
    	 
    	 playTypeMapJc.put(PlayType.jczq_jqs_1_1.getValue(), "1001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_2_1.getValue(), "2001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_3_1.getValue(), "3001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_4_1.getValue(), "4001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_1.getValue(), "5001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_1.getValue(), "6001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_7_1.getValue(), "7001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_8_1.getValue(), "8001");
    	 playTypeMapJc.put(PlayType.jczq_jqs_3_3.getValue(), "3003");
    	 playTypeMapJc.put(PlayType.jczq_jqs_3_4.getValue(), "3004");
    	 playTypeMapJc.put(PlayType.jczq_jqs_4_4.getValue(), "4004");
    	 playTypeMapJc.put(PlayType.jczq_jqs_4_5.getValue(), "4005");
    	 playTypeMapJc.put(PlayType.jczq_jqs_4_6.getValue(), "4006");
    	 playTypeMapJc.put(PlayType.jczq_jqs_4_11.getValue(), "4011");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_5.getValue(), "5005");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_6.getValue(), "5006");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_10.getValue(), "5010");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_16.getValue(), "5016");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_20.getValue(), "5020");
    	 playTypeMapJc.put(PlayType.jczq_jqs_5_26.getValue(), "5026");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_6.getValue(), "6006");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_7.getValue(), "6007");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_15.getValue(), "60015");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_20.getValue(), "6020");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_22.getValue(), "6022");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_35.getValue(), "6035");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_42.getValue(), "6042");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_50.getValue(), "6050");
   	 playTypeMapJc.put(PlayType.jczq_jqs_6_57.getValue(), "6057");
   	 
   	 playTypeMapJc.put(PlayType.jczq_bf_1_1.getValue(), "1001");
   	 playTypeMapJc.put(PlayType.jczq_bf_2_1.getValue(), "2001");
   	 playTypeMapJc.put(PlayType.jczq_bf_3_1.getValue(), "3001");
   	 playTypeMapJc.put(PlayType.jczq_bf_4_1.getValue(), "4001");
   	 playTypeMapJc.put(PlayType.jczq_bf_5_1.getValue(), "5001");
   	 playTypeMapJc.put(PlayType.jczq_bf_6_1.getValue(), "6001");
   	 playTypeMapJc.put(PlayType.jczq_bf_7_1.getValue(), "7001");
   	 playTypeMapJc.put(PlayType.jczq_bf_8_1.getValue(), "8001");
   	 playTypeMapJc.put(PlayType.jczq_bf_3_3.getValue(), "3003");
   	 playTypeMapJc.put(PlayType.jczq_bf_3_4.getValue(), "3004");
   	 playTypeMapJc.put(PlayType.jczq_bf_4_4.getValue(), "4004");
   	 playTypeMapJc.put(PlayType.jczq_bf_4_5.getValue(), "4005");
   	 playTypeMapJc.put(PlayType.jczq_bf_4_6.getValue(), "4006");
   	 playTypeMapJc.put(PlayType.jczq_bf_4_11.getValue(), "4011");
   	 
   	 
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_1_1.getValue(), "1001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_2_1.getValue(), "2001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_1.getValue(), "3001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_1.getValue(), "4001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_1.getValue(), "5001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_1.getValue(), "6001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_1.getValue(), "7001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_1.getValue(), "8001");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_3.getValue(), "3003");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_4.getValue(), "3004");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_4.getValue(), "4004");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_5.getValue(), "4005");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_6.getValue(), "4006");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_11.getValue(), "4011");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_5.getValue(), "5005");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_6.getValue(), "5006");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_10.getValue(), "5010");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_16.getValue(), "5016");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_20.getValue(), "5020");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_26.getValue(), "5026");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_6.getValue(), "6006");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_7.getValue(), "6007");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_15.getValue(), "6015");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_20.getValue(), "6020");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_22.getValue(), "6022");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_35.getValue(), "6035");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_42.getValue(), "6042");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_50.getValue(), "6050");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_57.getValue(), "6057");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_7.getValue(), "7007");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_8.getValue(), "7008");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_21.getValue(), "7021");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_35.getValue(), "7035");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_120.getValue(), "7120");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_8.getValue(), "8008");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_9.getValue(), "8009");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_28.getValue(), "8028");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_56.getValue(), "8056");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_70.getValue(), "8070");
   	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_247.getValue(), "8247");
   	 
   	 playTypeMapJc.put(PlayType.jczq_mix_2_1.getValue(), "2001");
   	 playTypeMapJc.put(PlayType.jczq_mix_3_1.getValue(), "3001");
   	 playTypeMapJc.put(PlayType.jczq_mix_4_1.getValue(), "4001");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_1.getValue(), "5001");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_1.getValue(), "6001");
   	 playTypeMapJc.put(PlayType.jczq_mix_7_1.getValue(), "7001");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_1.getValue(), "8001");
   	 playTypeMapJc.put(PlayType.jczq_mix_3_3.getValue(), "3003");
   	 playTypeMapJc.put(PlayType.jczq_mix_3_4.getValue(), "3004");
   	 playTypeMapJc.put(PlayType.jczq_mix_4_4.getValue(), "4004");
   	 playTypeMapJc.put(PlayType.jczq_mix_4_5.getValue(), "4005");
   	 playTypeMapJc.put(PlayType.jczq_mix_4_6.getValue(), "4006");
   	 playTypeMapJc.put(PlayType.jczq_mix_4_11.getValue(), "4011");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_5.getValue(), "5005");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_6.getValue(), "5006");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_10.getValue(), "5010");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_16.getValue(), "5016");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_20.getValue(), "5020");
   	 playTypeMapJc.put(PlayType.jczq_mix_5_26.getValue(), "5026");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_6.getValue(), "6006");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_7.getValue(), "6007");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_15.getValue(), "6015");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_20.getValue(), "6020");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_22.getValue(), "6022");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_35.getValue(), "6035");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_42.getValue(), "6042");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_50.getValue(), "6050");
   	 playTypeMapJc.put(PlayType.jczq_mix_6_57.getValue(), "6057");
   	 playTypeMapJc.put(PlayType.jczq_mix_7_7.getValue(), "7007");
   	 playTypeMapJc.put(PlayType.jczq_mix_7_8.getValue(), "7008");
   	 playTypeMapJc.put(PlayType.jczq_mix_7_21.getValue(), "7021");
   	 playTypeMapJc.put(PlayType.jczq_mix_7_35.getValue(), "7035");
   	 playTypeMapJc.put(PlayType.jczq_mix_7_120.getValue(), "7120");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_8.getValue(), "8008");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_9.getValue(), "8009");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_28.getValue(), "8028");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_56.getValue(), "8056");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_70.getValue(), "8070");
   	 playTypeMapJc.put(PlayType.jczq_mix_8_247.getValue(), "8247");
   	 
   	 
   	 playTypeMapJc.put(PlayType.jczq_bqc_1_1.getValue(), "1001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_2_1.getValue(), "2001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_3_1.getValue(), "3001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_4_1.getValue(), "4001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_5_1.getValue(), "5001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_6_1.getValue(), "6001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_7_1.getValue(), "7001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_8_1.getValue(), "8001");
   	 playTypeMapJc.put(PlayType.jczq_bqc_3_3.getValue(), "3003");
   	 playTypeMapJc.put(PlayType.jczq_bqc_3_4.getValue(), "3004");
   	 playTypeMapJc.put(PlayType.jczq_bqc_4_4.getValue(), "4004");
   	 playTypeMapJc.put(PlayType.jczq_bqc_4_5.getValue(), "4005");
   	 playTypeMapJc.put(PlayType.jczq_bqc_4_6.getValue(), "4006");
   	 playTypeMapJc.put(PlayType.jczq_bqc_4_11.getValue(), "4011");
		
		
		
   	 
   	playTypeMapJc.put(PlayType.jclq_sf_1_1.getValue(),"1001");
   	playTypeMapJc.put(PlayType.jclq_sf_2_1.getValue(),"2001");
   	playTypeMapJc.put(PlayType.jclq_sf_3_1.getValue(),"3001");
		playTypeMapJc.put(PlayType.jclq_sf_4_1.getValue(),"4001");
		playTypeMapJc.put(PlayType.jclq_sf_5_1.getValue(),"5001");
		playTypeMapJc.put(PlayType.jclq_sf_6_1.getValue(),"6001");
		playTypeMapJc.put(PlayType.jclq_sf_7_1.getValue(),"7001");
		playTypeMapJc.put(PlayType.jclq_sf_8_1.getValue(),"8001");
		playTypeMapJc.put(PlayType.jclq_sf_3_3.getValue(),"3003");
		playTypeMapJc.put(PlayType.jclq_sf_3_4.getValue(),"3004");
		playTypeMapJc.put(PlayType.jclq_sf_4_4.getValue(),"4004");
		playTypeMapJc.put(PlayType.jclq_sf_4_5.getValue(),"4005");
		playTypeMapJc.put(PlayType.jclq_sf_4_6.getValue(),"4006");
		playTypeMapJc.put(PlayType.jclq_sf_4_11.getValue(),"4011");
		playTypeMapJc.put(PlayType.jclq_sf_5_5.getValue(),"5005");
		playTypeMapJc.put(PlayType.jclq_sf_5_6.getValue(),"5006");
		playTypeMapJc.put(PlayType.jclq_sf_5_10.getValue(),"5010");
		playTypeMapJc.put(PlayType.jclq_sf_5_16.getValue(),"5016");
		playTypeMapJc.put(PlayType.jclq_sf_5_20.getValue(),"5020");
		playTypeMapJc.put(PlayType.jclq_sf_5_26.getValue(),"5026");
		playTypeMapJc.put(PlayType.jclq_sf_6_6.getValue(),"6006");
		playTypeMapJc.put(PlayType.jclq_sf_6_7.getValue(),"6007");
		playTypeMapJc.put(PlayType.jclq_sf_6_15.getValue(),"6015");
		playTypeMapJc.put(PlayType.jclq_sf_6_20.getValue(),"6020");
		playTypeMapJc.put(PlayType.jclq_sf_6_22.getValue(),"6022");
		playTypeMapJc.put(PlayType.jclq_sf_6_35.getValue(),"6035");
		playTypeMapJc.put(PlayType.jclq_sf_6_42.getValue(),"6042");
		playTypeMapJc.put(PlayType.jclq_sf_6_50.getValue(),"6050");
		playTypeMapJc.put(PlayType.jclq_sf_6_57.getValue(),"6057");
		playTypeMapJc.put(PlayType.jclq_sf_7_7.getValue(),"7007");
		playTypeMapJc.put(PlayType.jclq_sf_7_8.getValue(),"7008");
		playTypeMapJc.put(PlayType.jclq_sf_7_21.getValue(),"7021");
		playTypeMapJc.put(PlayType.jclq_sf_7_35.getValue(),"7035");
		playTypeMapJc.put(PlayType.jclq_sf_7_120.getValue(),"7120");
		playTypeMapJc.put(PlayType.jclq_sf_8_8.getValue(),"8008");
		playTypeMapJc.put(PlayType.jclq_sf_8_9.getValue(),"8009");
		playTypeMapJc.put(PlayType.jclq_sf_8_28.getValue(),"8028");
		playTypeMapJc.put(PlayType.jclq_sf_8_56.getValue(),"8056");
		playTypeMapJc.put(PlayType.jclq_sf_8_70.getValue(),"8070");
		playTypeMapJc.put(PlayType.jclq_sf_8_247.getValue(),"8247");
		
		
		
		playTypeMapJc.put(PlayType.jclq_rfsf_1_1.getValue(),"1001");
		playTypeMapJc.put(PlayType.jclq_rfsf_2_1.getValue(),"2001");
		playTypeMapJc.put(PlayType.jclq_rfsf_3_1.getValue(),"3001");
		playTypeMapJc.put(PlayType.jclq_rfsf_4_1.getValue(),"4001");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_1.getValue(),"5001");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_1.getValue(),"6001");
		playTypeMapJc.put(PlayType.jclq_rfsf_7_1.getValue(),"7001");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_1.getValue(),"8001");
		playTypeMapJc.put(PlayType.jclq_rfsf_3_3.getValue(),"3003");
		playTypeMapJc.put(PlayType.jclq_rfsf_3_4.getValue(),"3004");
		playTypeMapJc.put(PlayType.jclq_rfsf_4_4.getValue(),"4004");
		playTypeMapJc.put(PlayType.jclq_rfsf_4_5.getValue(),"4005");
		playTypeMapJc.put(PlayType.jclq_rfsf_4_6.getValue(),"4006");
		playTypeMapJc.put(PlayType.jclq_rfsf_4_11.getValue(),"4011");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_5.getValue(),"5005");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_6.getValue(),"5006");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_10.getValue(),"5010");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_16.getValue(),"5016");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_20.getValue(),"5020");
		playTypeMapJc.put(PlayType.jclq_rfsf_5_26.getValue(),"5026");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_6.getValue(),"6006");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_7.getValue(),"6007");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_15.getValue(),"6015");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_20.getValue(),"6020");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_22.getValue(),"6022");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_35.getValue(),"6035");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_42.getValue(),"6042");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_50.getValue(),"6050");
		playTypeMapJc.put(PlayType.jclq_rfsf_6_57.getValue(),"6057");
		playTypeMapJc.put(PlayType.jclq_rfsf_7_7.getValue(),"7007");
		playTypeMapJc.put(PlayType.jclq_rfsf_7_8.getValue(),"7008");
		playTypeMapJc.put(PlayType.jclq_rfsf_7_21.getValue(),"7021");
		playTypeMapJc.put(PlayType.jclq_rfsf_7_35.getValue(),"7035");
		playTypeMapJc.put(PlayType.jclq_rfsf_7_120.getValue(),"7120");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_8.getValue(),"8008");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_9.getValue(),"8009");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_28.getValue(),"8028");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_56.getValue(),"8056");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_70.getValue(),"8070");
		playTypeMapJc.put(PlayType.jclq_rfsf_8_247.getValue(),"8247");
		
		playTypeMapJc.put(PlayType.jclq_sfc_1_1.getValue(),"1001");
		playTypeMapJc.put(PlayType.jclq_sfc_2_1.getValue(),"2001");
		playTypeMapJc.put(PlayType.jclq_sfc_3_1.getValue(),"3001");
		playTypeMapJc.put(PlayType.jclq_sfc_4_1.getValue(),"4001");
		playTypeMapJc.put(PlayType.jclq_sfc_5_1.getValue(),"5001");
		playTypeMapJc.put(PlayType.jclq_sfc_6_1.getValue(),"6001");
		playTypeMapJc.put(PlayType.jclq_sfc_7_1.getValue(),"7001");
		playTypeMapJc.put(PlayType.jclq_sfc_8_1.getValue(),"8001");
		playTypeMapJc.put(PlayType.jclq_sfc_3_3.getValue(),"3X3");
		playTypeMapJc.put(PlayType.jclq_sfc_3_4.getValue(),"3004");
		playTypeMapJc.put(PlayType.jclq_sfc_4_4.getValue(),"4004");
		playTypeMapJc.put(PlayType.jclq_sfc_4_5.getValue(),"4005");
		playTypeMapJc.put(PlayType.jclq_sfc_4_6.getValue(),"4006");
		playTypeMapJc.put(PlayType.jclq_sfc_4_11.getValue(),"4011");
		
		playTypeMapJc.put(PlayType.jclq_dxf_1_1.getValue(),"1001");
		playTypeMapJc.put(PlayType.jclq_dxf_2_1.getValue(),"2001");
		playTypeMapJc.put(PlayType.jclq_dxf_3_1.getValue(),"3001");
		playTypeMapJc.put(PlayType.jclq_dxf_4_1.getValue(),"4001");
		playTypeMapJc.put(PlayType.jclq_dxf_5_1.getValue(),"5001");
		playTypeMapJc.put(PlayType.jclq_dxf_6_1.getValue(),"6001");
		playTypeMapJc.put(PlayType.jclq_dxf_7_1.getValue(),"7001");
		playTypeMapJc.put(PlayType.jclq_dxf_8_1.getValue(),"8001");
		playTypeMapJc.put(PlayType.jclq_dxf_3_3.getValue(),"3003");
		playTypeMapJc.put(PlayType.jclq_dxf_3_4.getValue(),"3004");
		playTypeMapJc.put(PlayType.jclq_dxf_4_4.getValue(),"4004");
		playTypeMapJc.put(PlayType.jclq_dxf_4_5.getValue(),"4005");
		playTypeMapJc.put(PlayType.jclq_dxf_4_6.getValue(),"4006");
		playTypeMapJc.put(PlayType.jclq_dxf_4_11.getValue(),"4011");
		playTypeMapJc.put(PlayType.jclq_dxf_5_5.getValue(),"5005");
		playTypeMapJc.put(PlayType.jclq_dxf_5_6.getValue(),"5006");
		playTypeMapJc.put(PlayType.jclq_dxf_5_10.getValue(),"5010");
		playTypeMapJc.put(PlayType.jclq_dxf_5_16.getValue(),"5016");
		playTypeMapJc.put(PlayType.jclq_dxf_5_20.getValue(),"5020");
		playTypeMapJc.put(PlayType.jclq_dxf_5_26.getValue(),"5026");
		playTypeMapJc.put(PlayType.jclq_dxf_6_6.getValue(),"6006");
		playTypeMapJc.put(PlayType.jclq_dxf_6_7.getValue(),"6007");
		playTypeMapJc.put(PlayType.jclq_dxf_6_15.getValue(),"6015");
		playTypeMapJc.put(PlayType.jclq_dxf_6_20.getValue(),"6020");
		playTypeMapJc.put(PlayType.jclq_dxf_6_22.getValue(),"6022");
		playTypeMapJc.put(PlayType.jclq_dxf_6_35.getValue(),"6035");
		playTypeMapJc.put(PlayType.jclq_dxf_6_42.getValue(),"6042");
		playTypeMapJc.put(PlayType.jclq_dxf_6_50.getValue(),"6050");
		playTypeMapJc.put(PlayType.jclq_dxf_6_57.getValue(),"6057");
		playTypeMapJc.put(PlayType.jclq_dxf_7_7.getValue(),"7007");
		playTypeMapJc.put(PlayType.jclq_dxf_7_8.getValue(),"7008");
		playTypeMapJc.put(PlayType.jclq_dxf_7_21.getValue(),"7021");
		playTypeMapJc.put(PlayType.jclq_dxf_7_35.getValue(),"7035");
		playTypeMapJc.put(PlayType.jclq_dxf_7_120.getValue(),"7120");
		playTypeMapJc.put(PlayType.jclq_dxf_8_8.getValue(),"8008");
		playTypeMapJc.put(PlayType.jclq_dxf_8_9.getValue(),"8009");
		playTypeMapJc.put(PlayType.jclq_dxf_8_28.getValue(),"8028");
		playTypeMapJc.put(PlayType.jclq_dxf_8_56.getValue(),"8056");
		playTypeMapJc.put(PlayType.jclq_dxf_8_70.getValue(),"8070");
		playTypeMapJc.put(PlayType.jclq_dxf_8_247.getValue(),"8247");
		
		playTypeMapJc.put(PlayType.jclq_mix_2_1.getValue(),"2001");
		playTypeMapJc.put(PlayType.jclq_mix_3_1.getValue(),"3001");
		playTypeMapJc.put(PlayType.jclq_mix_4_1.getValue(),"4001");
		playTypeMapJc.put(PlayType.jclq_mix_5_1.getValue(),"5001");
		playTypeMapJc.put(PlayType.jclq_mix_6_1.getValue(),"6001");
		playTypeMapJc.put(PlayType.jclq_mix_7_1.getValue(),"7001");
		playTypeMapJc.put(PlayType.jclq_mix_8_1.getValue(),"8001");
		playTypeMapJc.put(PlayType.jclq_mix_3_3.getValue(),"3003");
		playTypeMapJc.put(PlayType.jclq_mix_3_4.getValue(),"3004");
		playTypeMapJc.put(PlayType.jclq_mix_4_4.getValue(),"4004");
		playTypeMapJc.put(PlayType.jclq_mix_4_5.getValue(),"4005");
		playTypeMapJc.put(PlayType.jclq_mix_4_6.getValue(),"4006");
		playTypeMapJc.put(PlayType.jclq_mix_4_11.getValue(),"4011");
		playTypeMapJc.put(PlayType.jclq_mix_5_5.getValue(),"5005");
		playTypeMapJc.put(PlayType.jclq_mix_5_6.getValue(),"5006");
		playTypeMapJc.put(PlayType.jclq_mix_5_10.getValue(),"5010");
		playTypeMapJc.put(PlayType.jclq_mix_5_16.getValue(),"5016");
		playTypeMapJc.put(PlayType.jclq_mix_5_20.getValue(),"5020");
		playTypeMapJc.put(PlayType.jclq_mix_5_26.getValue(),"5026");
		playTypeMapJc.put(PlayType.jclq_mix_6_6.getValue(),"6006");
		playTypeMapJc.put(PlayType.jclq_mix_6_7.getValue(),"6007");
		playTypeMapJc.put(PlayType.jclq_mix_6_15.getValue(),"6015");
		playTypeMapJc.put(PlayType.jclq_mix_6_20.getValue(),"6020");
		playTypeMapJc.put(PlayType.jclq_mix_6_22.getValue(),"6022");
		playTypeMapJc.put(PlayType.jclq_mix_6_35.getValue(),"6035");
		playTypeMapJc.put(PlayType.jclq_mix_6_42.getValue(),"6042");
		playTypeMapJc.put(PlayType.jclq_mix_6_50.getValue(),"6050");
		playTypeMapJc.put(PlayType.jclq_mix_6_57.getValue(),"6057");
		playTypeMapJc.put(PlayType.jclq_mix_7_7.getValue(),"7007");
		playTypeMapJc.put(PlayType.jclq_mix_7_8.getValue(),"7008");
		playTypeMapJc.put(PlayType.jclq_mix_7_21.getValue(),"7021");
		playTypeMapJc.put(PlayType.jclq_mix_7_35.getValue(),"7035");
		playTypeMapJc.put(PlayType.jclq_mix_7_120.getValue(),"7120");
		playTypeMapJc.put(PlayType.jclq_mix_8_8.getValue(),"8008");
		playTypeMapJc.put(PlayType.jclq_mix_8_9.getValue(),"8009");
		playTypeMapJc.put(PlayType.jclq_mix_8_28.getValue(),"8028");
		playTypeMapJc.put(PlayType.jclq_mix_8_56.getValue(),"8056");
		playTypeMapJc.put(PlayType.jclq_mix_8_70.getValue(),"8070");
		playTypeMapJc.put(PlayType.jclq_mix_8_247.getValue(),"8247");
 		
		
			IVenderTicketConverter cp = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
			        return contentStr.toString();
			     }};
			
					playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, cp);

					playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, cp);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, cp);

					playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, cp);

					playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, cp);
					playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, cp);

					playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, cp);

					playTypeToAdvanceConverterMap.put(PlayType.p5_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.p5_fu, cp);

					playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, cp);
					playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, cp);			
				
			//足彩
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, cp);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, cp);
			
			
			 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,cp);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,cp);
	     	 
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,cp);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,cp);
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11, cp);
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,cp);
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,cp);
	    	 

	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,cp);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,cp);
	  		
	  		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,cp);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,cp);
	 			    		
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,cp);
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,cp);
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,cp);
	 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,cp);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,cp);
	 		 
	 		 
	 		 
	 		 
	 		 
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,cp);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,cp);
	 		

			
	}
	                
	
}
