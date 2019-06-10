package com.lottery.lottype.jc;

import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.LotteryType;

public class JcResultUtil {

	
	public static String getLotteryInnerResult(LotteryType type,String result) {
		String calresult = "";
		if(LotteryType.JCZQ_RQ_SPF==type) {
			if("3".equals(result)) {
				calresult = LotteryConstant.JCZQ_SPF_S_VALUE;
			}else if("1".equals(result)) {
				calresult = LotteryConstant.JCZQ_SPF_P_VALUE;
			}else if("0".equals(result)) {
				calresult = LotteryConstant.JCZQ_SPF_F_VALUE;
			}
		}else if(LotteryType.JCZQ_SPF_WRQ==type) {
			if("3".equals(result)) {
				calresult = LotteryConstant.JCZQ_SPF_WRQ_S_VALUE;
			}else if("1".equals(result)) {
				calresult = LotteryConstant.JCZQ_SPF_WRQ_P_VALUE;
			}else if("0".equals(result)) {
				calresult = LotteryConstant.JCZQ_SPF_WRQ_F_VALUE;
			}
		}else if(LotteryType.JCZQ_BF==type) {
			if("10".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_1_0_VALUE;
			}else if("20".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_2_0_VALUE;
			}else if("21".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_2_1_VALUE;
			}else if("30".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_3_0_VALUE;
			}else if("31".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_3_1_VALUE;
			}else if("32".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_3_2_VALUE;
			}else if("40".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_4_0_VALUE;
			}else if("41".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_4_1_VALUE;
			}else if("42".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_4_2_VALUE;
			}else if("50".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_5_0_VALUE;
			}else if("51".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_5_1_VALUE;
			}else if("52".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_5_2_VALUE;
			}else if("01".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_0_1_VALUE;
			}else if("02".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_0_2_VALUE;
			}else if("12".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_1_2_VALUE;
			}else if("03".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_0_3_VALUE;
			}else if("13".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_1_3_VALUE;
			}else if("23".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_2_3_VALUE;
			}else if("04".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_0_4_VALUE;
			}else if("14".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_1_4_VALUE;
			}else if("24".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_2_4_VALUE;
			}else if("05".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_0_5_VALUE;
			}else if("15".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_1_5_VALUE;
			}else if("25".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_2_5_VALUE;
			}else if("00".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZP_0_0_VALUE;
			}else if("11".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZP_1_1_VALUE;
			}else if("22".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZP_2_2_VALUE;
			}else if("33".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZP_3_3_VALUE;
			}else if("99".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZP_QT_VALUE;
			}else if("09".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZF_QT_VALUE;
			}else if("90".equals(result)) {
				calresult = LotteryConstant.JCZQ_BF_ZS_QT_VALUE;
			}
		}else if(LotteryType.JCZQ_BQC==type) {
			if("33".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_SS_VALUE;
			}else if("31".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_SP_VALUE;
			}else if("30".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_SF_VALUE;
			}else if("13".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_PS_VALUE;
			}else if("11".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_PP_VALUE;
			}else if("10".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_PF_VALUE;
			}else if("03".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_FS_VALUE;
			}else if("01".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_FP_VALUE;
			}else if("00".equals(result)) {
				calresult = LotteryConstant.JCZQ_BQC_FF_VALUE;
			}
		}else if(LotteryType.JCZQ_JQS==type) {
			if("0".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_0_VALUE;
			}else if("1".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_1_VALUE;
			}else if("2".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_2_VALUE;
			}else if("3".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_3_VALUE;
			}else if("4".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_4_VALUE;
			}else if("5".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_5_VALUE;
			}else if("6".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_6_VALUE;
			}else if("7".equals(result)) {
				calresult = LotteryConstant.JCZQ_JQS_7_VALUE;
			}
		}else if(LotteryType.JCLQ_SF==type) {
			if("3".equals(result)) {
				calresult = LotteryConstant.JCLQ_SF_S_VALUE;
			}else if("0".equals(result)) {
				calresult = LotteryConstant.JCLQ_SF_F_VALUE;
			}
		}else if(LotteryType.JCLQ_RFSF==type) {
			if("3".equals(result)) {
				calresult = LotteryConstant.JCLQ_RFSF_S_VALUE;
			}else if("0".equals(result)) {
				calresult = LotteryConstant.JCLQ_RFSF_F_VALUE;
			}
		}else if(LotteryType.JCLQ_SFC==type) {
			if("01".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_H_1_5_VALUE;
			}else if("02".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_H_6_10_VALUE;
			}else if("03".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_H_11_15_VALUE;
			}else if("04".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_H_16_20_VALUE;
			}else if("05".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_H_21_25_VALUE;
			}else if("06".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_H_26_PLUS_VALUE;
			}else if("11".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_A_1_5_VALUE;
			}else if("12".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_A_6_10_VALUE;
			}else if("13".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_A_11_15_VALUE;
			}else if("14".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_A_16_20_VALUE;
			}else if("15".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_A_21_25_VALUE;
			}else if("16".equals(result)) {
				calresult = LotteryConstant.JCLQ_SFC_A_26_PLUS_VALUE;
			}
		}else if(LotteryType.JCLQ_DXF==type) {
			if("1".equals(result)) {
				calresult = LotteryConstant.JCLQ_DXF_LARGE;
			}else if("2".equals(result)) {
				calresult = LotteryConstant.JCLQ_DXF_SMALL;
			}
		}
		
		return calresult;
	}
}
