/**
 * 
 */
package com.lottery.lottype.sp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.IntegerBeanLabelItem;
import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.LotteryType;

/**
 * @author qatang
 * 竞彩足球即时SP值
 */
public class JczqSPType extends IntegerBeanLabelItem {


	private static final long serialVersionUID = 2465840431256917709L;

	private static final Logger logger = LoggerFactory.getLogger(JczqSPType.class.getName());
	
	private static List<JczqSPType> items = new ArrayList<JczqSPType>();
	
	private static Map<LotteryType, List<JczqSPType>> lotteryItemsMap = new HashMap<LotteryType, List<JczqSPType>>();
	
	protected JczqSPType(String name, int value) {
		this(name, value, null);
	}
	
	protected JczqSPType(String name, int value, LotteryType lotteryType) {
		super(JczqSPType.class.getName(), name, value);
		items.add(this);
		
		if (lotteryType != null) {
			synchronized(this) {
				List<JczqSPType> lotteryItems = lotteryItemsMap.get(lotteryType);
				if (lotteryItems == null) {
					lotteryItems = new ArrayList<JczqSPType>();
					lotteryItemsMap.put(lotteryType, lotteryItems);
				}
				lotteryItems.add(this);
			}
		}
	}
	
	public static JczqSPType getItem(int value){
		try {
			return (JczqSPType)JczqSPType.getResult(JczqSPType.class.getName(), value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static List<JczqSPType> getItems() {
		return items;
	}
	
	public static List<JczqSPType> getItems(LotteryType lotteryType) {
		return lotteryItemsMap.get(lotteryType);
	}
	
	private static Map<JczqSPType, String> lotteryConstantMap = new HashMap<JczqSPType, String>();
	
	public static String getLotteryConstant(JczqSPType spType) {
		return lotteryConstantMap.get(spType);
	}
	
	
	//让球胜平负
	public static final JczqSPType SPF_S = new JczqSPType("让球胜平负-主队胜", 1, LotteryType.JCZQ_RQ_SPF);
	public static final JczqSPType SPF_P = new JczqSPType("让球胜平负-主队平", 2, LotteryType.JCZQ_RQ_SPF);
	public static final JczqSPType SPF_F = new JczqSPType("让球胜平负-主队负", 3, LotteryType.JCZQ_RQ_SPF);
	//public static final JczqSPType SPF_RQ = new JczqSPType("让球胜平负-主队让球数", 4, LotteryType.JCZQ_SPF);
	
	//全场比分
	public static final JczqSPType BF_ZS_1_0 = new JczqSPType("全场比分-主胜-1:0", 5, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_2_0 = new JczqSPType("全场比分-主胜-2:0", 6, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_2_1 = new JczqSPType("全场比分-主胜-2:1", 7, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_3_0 = new JczqSPType("全场比分-主胜-3:0", 8, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_3_1 = new JczqSPType("全场比分-主胜-3:1", 9, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_3_2 = new JczqSPType("全场比分-主胜-3:2", 10, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_4_0 = new JczqSPType("全场比分-主胜-4:0", 11, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_4_1 = new JczqSPType("全场比分-主胜-4:1", 12, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_4_2 = new JczqSPType("全场比分-主胜-4:2", 13, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_5_0 = new JczqSPType("全场比分-主胜-5:0", 14, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_5_1 = new JczqSPType("全场比分-主胜-5:1", 15, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_5_2 = new JczqSPType("全场比分-主胜-5:2", 16, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZS_QT = new JczqSPType("全场比分-主胜-胜其他", 17, LotteryType.JCZQ_BF);
	
	public static final JczqSPType BF_ZP_0_0 = new JczqSPType("全场比分-主平-0:0", 18, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZP_1_1 = new JczqSPType("全场比分-主平-1:1", 19, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZP_2_2 = new JczqSPType("全场比分-主平-2:2", 20, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZP_3_3 = new JczqSPType("全场比分-主平-3:3", 21, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZP_QT = new JczqSPType("全场比分-主平-平其他", 22, LotteryType.JCZQ_BF);
	
	public static final JczqSPType BF_ZF_0_1 = new JczqSPType("全场比分-主负-0:1", 23, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_0_2 = new JczqSPType("全场比分-主负-0:2", 24, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_1_2 = new JczqSPType("全场比分-主负-1:2", 25, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_0_3 = new JczqSPType("全场比分-主负-0:3", 26, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_1_3 = new JczqSPType("全场比分-主负-1:3", 27, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_2_3 = new JczqSPType("全场比分-主负-2:3", 28, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_0_4 = new JczqSPType("全场比分-主负-0:4", 29, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_1_4 = new JczqSPType("全场比分-主负-1:4", 30, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_2_4 = new JczqSPType("全场比分-主负-2:4", 31, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_0_5 = new JczqSPType("全场比分-主负-0:5", 32, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_1_5 = new JczqSPType("全场比分-主负-1:5", 33, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_2_5 = new JczqSPType("全场比分-主负-2:5", 34, LotteryType.JCZQ_BF);
	public static final JczqSPType BF_ZF_QT = new JczqSPType("全场比分-主负-负其他", 35, LotteryType.JCZQ_BF);
	
	//总进球数
	public static final JczqSPType JQS_0 = new JczqSPType("总进球数-0", 36, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_1 = new JczqSPType("总进球数-1", 37, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_2 = new JczqSPType("总进球数-2", 38, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_3 = new JczqSPType("总进球数-3", 39, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_4 = new JczqSPType("总进球数-4", 40, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_5 = new JczqSPType("总进球数-5", 41, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_6 = new JczqSPType("总进球数-6", 42, LotteryType.JCZQ_JQS);
	public static final JczqSPType JQS_7 = new JczqSPType("总进球数-7+", 43, LotteryType.JCZQ_JQS);
	
	//半场胜平负
	public static final JczqSPType BQC_SS = new JczqSPType("半全场胜平负-胜胜", 44, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_SP = new JczqSPType("半全场胜平负-胜平", 45, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_SF = new JczqSPType("半全场胜平负-胜负", 46, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_PS = new JczqSPType("半全场胜平负-平胜", 47, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_PP = new JczqSPType("半全场胜平负-平平", 48, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_PF = new JczqSPType("半全场胜平负-平负", 49, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_FS = new JczqSPType("半全场胜平负-负胜", 50, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_FP = new JczqSPType("半全场胜平负-负平", 51, LotteryType.JCZQ_BQC);
	public static final JczqSPType BQC_FF = new JczqSPType("半全场胜平负-负负", 52, LotteryType.JCZQ_BQC);
	

	
	//胜平负
	public static final JczqSPType SPF_WRQ_S = new JczqSPType("胜平负-主队胜", 55, LotteryType.JCZQ_SPF_WRQ);
	public static final JczqSPType SPF_WRQ_P = new JczqSPType("胜平负-主队平", 56, LotteryType.JCZQ_SPF_WRQ);
	public static final JczqSPType SPF_WRQ_F = new JczqSPType("胜平负-主队负", 57, LotteryType.JCZQ_SPF_WRQ);
	
	@Override
	public String toString() {
		return this.name;
	}

	static {
		lotteryConstantMap.put(JczqSPType.SPF_S, LotteryConstant.JCZQ_SPF_S_VALUE);
		lotteryConstantMap.put(JczqSPType.SPF_P, LotteryConstant.JCZQ_SPF_P_VALUE);
		lotteryConstantMap.put(JczqSPType.SPF_F, LotteryConstant.JCZQ_SPF_F_VALUE);
		//lotteryConstantMap.put(JczqSPType.SPF_RQ, LotteryConstant.JCZQ_SPF_RQ_VALUE);
		
		lotteryConstantMap.put(JczqSPType.BF_ZS_1_0, LotteryConstant.JCZQ_BF_ZS_1_0_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_2_0, LotteryConstant.JCZQ_BF_ZS_2_0_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_2_1, LotteryConstant.JCZQ_BF_ZS_2_1_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZS_3_0, LotteryConstant.JCZQ_BF_ZS_3_0_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_3_1, LotteryConstant.JCZQ_BF_ZS_3_1_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_3_2, LotteryConstant.JCZQ_BF_ZS_3_2_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZS_4_0, LotteryConstant.JCZQ_BF_ZS_4_0_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_4_1, LotteryConstant.JCZQ_BF_ZS_4_1_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_4_2, LotteryConstant.JCZQ_BF_ZS_4_2_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZS_5_0, LotteryConstant.JCZQ_BF_ZS_5_0_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_5_1, LotteryConstant.JCZQ_BF_ZS_5_1_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZS_5_2, LotteryConstant.JCZQ_BF_ZS_5_2_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZS_QT, LotteryConstant.JCZQ_BF_ZS_QT_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZP_0_0, LotteryConstant.JCZQ_BF_ZP_0_0_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZP_1_1, LotteryConstant.JCZQ_BF_ZP_1_1_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZP_2_2, LotteryConstant.JCZQ_BF_ZP_2_2_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZP_3_3, LotteryConstant.JCZQ_BF_ZP_3_3_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZP_QT, LotteryConstant.JCZQ_BF_ZP_QT_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_0_1, LotteryConstant.JCZQ_BF_ZF_0_1_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZF_0_2, LotteryConstant.JCZQ_BF_ZF_0_2_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_1_2, LotteryConstant.JCZQ_BF_ZF_1_2_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_0_3, LotteryConstant.JCZQ_BF_ZF_0_3_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZF_1_3, LotteryConstant.JCZQ_BF_ZF_1_3_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_2_3, LotteryConstant.JCZQ_BF_ZF_2_3_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_0_4, LotteryConstant.JCZQ_BF_ZF_0_4_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZF_1_4, LotteryConstant.JCZQ_BF_ZF_1_4_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_2_4, LotteryConstant.JCZQ_BF_ZF_2_4_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_0_5, LotteryConstant.JCZQ_BF_ZF_0_5_VALUE);
        lotteryConstantMap.put(JczqSPType.BF_ZF_1_5, LotteryConstant.JCZQ_BF_ZF_1_5_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_2_5, LotteryConstant.JCZQ_BF_ZF_2_5_VALUE);
		lotteryConstantMap.put(JczqSPType.BF_ZF_QT, LotteryConstant.JCZQ_BF_ZF_QT_VALUE);
		
		lotteryConstantMap.put(JczqSPType.JQS_0, LotteryConstant.JCZQ_JQS_0_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_1, LotteryConstant.JCZQ_JQS_1_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_2, LotteryConstant.JCZQ_JQS_2_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_3, LotteryConstant.JCZQ_JQS_3_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_4, LotteryConstant.JCZQ_JQS_4_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_5, LotteryConstant.JCZQ_JQS_5_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_6, LotteryConstant.JCZQ_JQS_6_VALUE);
		lotteryConstantMap.put(JczqSPType.JQS_7, LotteryConstant.JCZQ_JQS_7_VALUE);
		
		lotteryConstantMap.put(JczqSPType.BQC_SS, LotteryConstant.JCZQ_BQC_SS_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_SP, LotteryConstant.JCZQ_BQC_SP_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_SF, LotteryConstant.JCZQ_BQC_SF_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_PS, LotteryConstant.JCZQ_BQC_PS_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_PP, LotteryConstant.JCZQ_BQC_PP_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_PF, LotteryConstant.JCZQ_BQC_PF_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_FS, LotteryConstant.JCZQ_BQC_FS_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_FP, LotteryConstant.JCZQ_BQC_FP_VALUE);
		lotteryConstantMap.put(JczqSPType.BQC_FF, LotteryConstant.JCZQ_BQC_FF_VALUE);
		
		lotteryConstantMap.put(JczqSPType.SPF_WRQ_S, LotteryConstant.JCZQ_SPF_WRQ_S_VALUE);
		lotteryConstantMap.put(JczqSPType.SPF_WRQ_P, LotteryConstant.JCZQ_SPF_WRQ_P_VALUE);
		lotteryConstantMap.put(JczqSPType.SPF_WRQ_F, LotteryConstant.JCZQ_SPF_WRQ_F_VALUE);
		
	
	}
	
}
