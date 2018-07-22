package com.lottery.ticket.vender.impl.tianjinfc;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;

public class TianJinFXLotteryDef {
	/** 彩种转换 */
	public static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	protected static Map<Integer, String> playType = new HashMap<Integer, String>();
	
	protected static Map<PlayType, String> typeMap = new HashMap<PlayType, String>();

	/** 票内容转换 */
	protected static Map<PlayType, ITicketContentConverter> ticketContentConverterMap = new HashMap<PlayType, ITicketContentConverter>();
	protected static int ssqFuShi=100102;
	protected static int ssqDanTuo=100103;
	protected static int KlsfDingWei=100439;
	protected static int qlcFuShi=100302;
	
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
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.TJKL10, defaultPhaseConverter);
		
		//彩种转换
		lotteryTypeMap.put(LotteryType.SSQ, "01");
		lotteryTypeMap.put(LotteryType.F3D, "02");
		lotteryTypeMap.put(LotteryType.QLC, "03");
		lotteryTypeMap.put(LotteryType.TJKL10, "06");
		
		//玩法转换
		playTypeMap.put(PlayType.ssq_dan.getValue(), "");
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "00");
		
		
		//黑龙江快乐十分单式
		playTypeMap.put(PlayType.hljkl10_ss1.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sh0.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sr2.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sr3.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sr4.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sr5.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sq2.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sq3.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sz2.getValue(), "00");
		playTypeMap.put(PlayType.hljkl10_sz3.getValue(), "00");
		//复式
		playTypeMap.put(PlayType.hljkl10_ms1.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_mr2.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_mr3.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_mr4.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_mr5.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_mz2.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_mz3.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_pq2.getValue(), "10");
		playTypeMap.put(PlayType.hljkl10_pq3.getValue(), "10");
	    //胆拖
		playTypeMap.put(PlayType.hljkl10_dr2.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dr3.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dr4.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dr5.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dq2.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dq3.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dz2.getValue(), "20");
		playTypeMap.put(PlayType.hljkl10_dz3.getValue(), "20");
		
		//3d
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "00");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "02");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "30");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "31");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "32");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(),"10");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "11");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "12");
		
		//qlc
		playTypeMap.put(PlayType.qlc_dan.getValue(), "00");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "10");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "20");
	
		//七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "00");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "10");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "20");
	
		
		typeMap.put(PlayType.tjkl10_ms1, "01");
		typeMap.put(PlayType.tjkl10_mr2, "02");
		typeMap.put(PlayType.tjkl10_mr3, "03");
		typeMap.put(PlayType.tjkl10_mr4, "04");
		typeMap.put(PlayType.tjkl10_mr5, "05");
		typeMap.put(PlayType.tjkl10_mz2, "02");
		typeMap.put(PlayType.tjkl10_mz3, "03");
		typeMap.put(PlayType.tjkl10_pq2, "02");
		typeMap.put(PlayType.tjkl10_pq3, "03");
		
		typeMap.put(PlayType.tjkl10_dr2, "02");
		typeMap.put(PlayType.tjkl10_dr3, "03");
		typeMap.put(PlayType.tjkl10_dr4, "04");
		typeMap.put(PlayType.tjkl10_dr5, "05");
		typeMap.put(PlayType.tjkl10_dq2, "02");
		typeMap.put(PlayType.tjkl10_dq3, "03");
		typeMap.put(PlayType.tjkl10_dz2, "02");
		typeMap.put(PlayType.tjkl10_dz3, "03");
		
		//qlsf单式
		playType.put(PlayType.tjkl10_ss1.getValue(), "02");
		playType.put(PlayType.tjkl10_sh0.getValue(), "02");
		playType.put(PlayType.tjkl10_sr2.getValue(), "00");
		playType.put(PlayType.tjkl10_sr3.getValue(), "00");
		playType.put(PlayType.tjkl10_sr4.getValue(), "00");
		playType.put(PlayType.tjkl10_sr5.getValue(), "00");
		playType.put(PlayType.tjkl10_sq2.getValue(), "02");
		playType.put(PlayType.tjkl10_sq3.getValue(), "02");
		playType.put(PlayType.tjkl10_sz2.getValue(), "01");
		playType.put(PlayType.tjkl10_sz3.getValue(), "01");
		//复式
		playType.put(PlayType.tjkl10_ms1.getValue(), "12");
		playType.put(PlayType.tjkl10_mr2.getValue(), "10");
		playType.put(PlayType.tjkl10_mr3.getValue(), "10");
		playType.put(PlayType.tjkl10_mr4.getValue(), "10");
		playType.put(PlayType.tjkl10_mr5.getValue(), "10");
		playType.put(PlayType.tjkl10_mz2.getValue(), "11");
		playType.put(PlayType.tjkl10_mz3.getValue(), "11");
		playType.put(PlayType.tjkl10_pq2.getValue(), "12");
		playType.put(PlayType.tjkl10_pq3.getValue(), "12");
		//胆拖
		playType.put(PlayType.tjkl10_dr3.getValue(), "20");
		playType.put(PlayType.tjkl10_dr4.getValue(), "20");
		playType.put(PlayType.tjkl10_dr5.getValue(), "20");
		playType.put(PlayType.tjkl10_dq2.getValue(), "20");
		playType.put(PlayType.tjkl10_dq3.getValue(), "22");
		playType.put(PlayType.tjkl10_dz2.getValue(), "21");
		playType.put(PlayType.tjkl10_dz3.getValue(), "21");
	}
}
