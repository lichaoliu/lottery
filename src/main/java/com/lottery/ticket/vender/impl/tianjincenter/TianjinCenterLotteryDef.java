package com.lottery.ticket.vender.impl.tianjincenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class TianjinCenterLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<String, LotteryType> toLotteryTypeMap = new HashMap<String, LotteryType>();
	/** 彩期转换 */
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换 */
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	/** 玩法编码转换 */
	public static Map<Integer, String> playCodeMap = new HashMap<Integer, String>();
	/** 票内容转换 */
	protected static Map<PlayType, ITicketContentConverter> ticketContentConverterMap = new HashMap<PlayType, ITicketContentConverter>();
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
	/**
	 * 出票商期号转换为平台期号
	 * */
	static protected Map<LotteryType, IPhaseConverter> phaseReverseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	static {
		/**
		 * 彩期转换
		 * */
		// 默认的期号转换
		IPhaseConverter defaultPhaseConverter = new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase.substring(4);
			}
		};
		IPhaseConverter phaseConverter = new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};

		phaseConverterMap.put(LotteryType.SSQ, phaseConverter);
		phaseConverterMap.put(LotteryType.F3D, phaseConverter);
		phaseConverterMap.put(LotteryType.QLC, phaseConverter);
		phaseConverterMap.put(LotteryType.TJKL10, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.TJSSC, defaultPhaseConverter);
		// 彩种转换
		lotteryTypeMap.put(LotteryType.SSQ, "B001");
		lotteryTypeMap.put(LotteryType.F3D, "D3");
		lotteryTypeMap.put(LotteryType.QLC, "QL730");
		lotteryTypeMap.put(LotteryType.TJKL10, "K520");
		lotteryTypeMap.put(LotteryType.TJSSC, "DT5");

		toLotteryTypeMap.put("B001", LotteryType.SSQ);
		toLotteryTypeMap.put("D3", LotteryType.F3D);
		toLotteryTypeMap.put("QL730", LotteryType.QLC);
		toLotteryTypeMap.put("K520", LotteryType.TJKL10);
		toLotteryTypeMap.put("DT5", LotteryType.TJSSC);

		// 玩法转换
		// 双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "01");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "02");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "03");
		// 3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "04");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "04");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "04");

		playTypeMap.put(PlayType.d3_cdx_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_cjo_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_c3t_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_tlj_dan.getValue(), "01");

		playTypeMap.put(PlayType.d3_1d_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_2d_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_3dtx_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_c1d_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_c2d_ertong_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_c3d_erbutong_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_1d_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_2d_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_3dtx_fx.getValue(), "02");
		playTypeMap.put(PlayType.d3_c1d_fx.getValue(), "02");
		playTypeMap.put(PlayType.d3_c2d_ertong_fx.getValue(), "02");
		playTypeMap.put(PlayType.d3_c3d_erbutong_fx.getValue(), "02");
		playTypeMap.put(PlayType.d3_new_hs.getValue(), "04");
		playTypeMap.put(PlayType.d3_new_hzx.getValue(), "04");

		playCodeMap.put(PlayType.tjkl10_ms1.getValue(), "01");
		playCodeMap.put(PlayType.tjkl10_mr2.getValue(), "02");
		playCodeMap.put(PlayType.tjkl10_mr3.getValue(), "03");
		playCodeMap.put(PlayType.tjkl10_mr4.getValue(), "04");
		playCodeMap.put(PlayType.tjkl10_mr5.getValue(), "05");
		playCodeMap.put(PlayType.tjkl10_mz2.getValue(), "02");
		playCodeMap.put(PlayType.tjkl10_mz3.getValue(), "03");
		playCodeMap.put(PlayType.tjkl10_dr2.getValue(), "02");
		playCodeMap.put(PlayType.tjkl10_dr3.getValue(), "03");
		playCodeMap.put(PlayType.tjkl10_dr4.getValue(), "04");
		playCodeMap.put(PlayType.tjkl10_dr5.getValue(), "05");
		playCodeMap.put(PlayType.tjkl10_dq2.getValue(), "02");
		playCodeMap.put(PlayType.tjkl10_dq3.getValue(), "03");
		playCodeMap.put(PlayType.tjkl10_dz2.getValue(), "02");
		playCodeMap.put(PlayType.tjkl10_dz3.getValue(), "03");
		playCodeMap.put(PlayType.tjkl10_pq2.getValue(), "02");
		playCodeMap.put(PlayType.tjkl10_pq3.getValue(), "03");
		// 七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "01");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "02");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "03");

		// 七乐彩 单式
		IVenderTicketConverter qlc_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder contentStr = new StringBuilder();
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				String[] contents = ticket.getContent().split("-")[1].replace(",", "").split("\\^");
				for (String content : contents) {
					contentStr.append("00").append(beishu).append(content).append("^");
				}
				return contentStr.toString();
			}
		};
		// 七乐彩 复式
		IVenderTicketConverter qlc_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = "";
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				String content = ticket.getContent().split("-")[1].replace(",", "");
				contentStr = "10" + beishu + "*" + content;
				return contentStr;
			}
		};

		// 七乐彩 胆拖
		IVenderTicketConverter qlc_dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = "";
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				String content = ticket.getContent().split("-")[1].replace("#", "*").replace(",", "");
				contentStr = "20" + beishu + content;
				return contentStr;
			}
		};

		// 双色球 单式
		IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder contentStr = new StringBuilder();
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				String[] contents = ticket.getContent().split("-")[1].replace(",", "").replace("|", "~").split("\\^");
				for (String content : contents) {
					contentStr.append("00").append(beishu).append(content).append("^");
				}
				return contentStr.toString();
			}
		};
		// 双色球 复式
		IVenderTicketConverter ssq_fd = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = "";
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				String[] content = ticket.getContent().split("-")[1].replace(",", "").replace("^", "").split("\\|");
				if (content[0].length() > 12 && content[1].length() < 3) {
					contentStr = "10" + beishu + "*" + content[0] + "~" + content[1];
				} else if (content[0].length() <= 12 && content[1].length() > 2) {
					contentStr = "20" + beishu + "*" + content[0] + "~" + content[1];
				} else if (content[0].length() > 12 && content[1].length() > 2) {
					contentStr = "30" + beishu + "*" + content[0] + "~" + content[1];
				}
				return contentStr + "^";
			}
		};

		// 双色球 胆拖
		IVenderTicketConverter ssq_dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				String contentStr = "";
				String[] cont = ticket.getContent().split("-")[1].replace("#", "*").replace(",", "").replace("^", "").split("\\*");
				String[] contents = cont[1].split("\\|");
				if (contents[1].length() < 3) {
					contentStr = "40" + beishu + cont[0] + "*" + cont[1].replace("|", "~");
				} else if (contents[1].length() > 2) {
					contentStr = "50" + beishu + cont[0] + "*" + cont[1].replace("|", "~");
				}
				return contentStr + "^";
			}
		};
		// 3D直选、组三、组六单式
		IVenderTicketConverter d3_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				int playType = Integer.parseInt(ticket.getContent().split("-")[0]);
				String playTypeStr = "";
				String[] contents = ticket.getContent().split("-")[1].replace(",", "").split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				if (playType == PlayType.d3_zhi_dan.getValue()) {
					playTypeStr = "0";
				} else if (playType == PlayType.d3_z3_dan.getValue()) {
					playTypeStr = "1";
				} else if (playType == PlayType.d3_z6_dan.getValue()) {
					playTypeStr = "2";
				}
				for (String content : contents) {
					sBuilder.append("0").append(playTypeStr).append(beishu).append(content).append("^");
				}

				return sBuilder.toString();
			}
		};
		// 3D直选复式
		IVenderTicketConverter d3_zxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].replace(",", "").replace("^", "").split("\\|");

				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("20").append(beishu);
				for (String str : contents) {
					int count = str.length() / 2;
					String countStr = "";
					if (count <= 9) {
						countStr = "0" + count;
					} else {
						countStr = count + "";
					}
					sBuilder.append(countStr).append(str).append("^");
				}
				return sBuilder.toString();
			}
		};
		// 3D组三组六复式
		IVenderTicketConverter d3_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				int playType = Integer.parseInt(ticket.getContent().split("-")[0]);
				String playTypeStr = "";
				String contents = ticket.getContent().split("-")[1].replace(",", "");

				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				int count = contents.split("\\^")[0].length() / 2;
				String countStr = "";
				if (count <= 9) {
					countStr = "0" + count;
				} else {
					countStr = count + "";
				}

				if (playType == PlayType.d3_z3_fs.getValue()) {
					playTypeStr = "1";
				} else if (playType == PlayType.d3_z6_fs.getValue()) {
					playTypeStr = "2";
				}

				sBuilder.append("3").append(playTypeStr).append(beishu).append(countStr).append(contents);
				return sBuilder.toString();
			}
		};

		// 3d和值
		IVenderTicketConverter d3_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				int playType = Integer.parseInt(ticket.getContent().split("-")[0]);
				String playTypeStr = "";
				String contents = ticket.getContent().split("-")[1].replace(",", "");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				if (playType == PlayType.d3_z3_hz.getValue()) {
					playTypeStr = "1";
				} else if (playType == PlayType.d3_z6_hz.getValue()) {
					playTypeStr = "2";
				}
				sBuilder.append("1").append(playTypeStr).append(beishu).append(contents);
				return sBuilder.toString();

			}
		};

		// 3D 1d
		IVenderTicketConverter d3_1d = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				for (String content : contents) {
					if (!"~".equals(content.split("\\,")[0])) {
						sBuilder.append("03").append(beishu).append(content.split("\\,")[0]).append("^");
					} else if (!"~".equals(content.split("\\,")[1])) {
						sBuilder.append("04").append(beishu).append(content.split("\\,")[1]).append("^");
					} else if (!"~".equals(content.split("\\,")[2])) {
						sBuilder.append("05").append(beishu).append(content.split("\\,")[2]).append("^");
					}
					continue;
				}
				return sBuilder.toString();
			}
		};

		// 3D 2d
		IVenderTicketConverter d3_2d = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				for (String content : contents) {
					if ("~".equals(content.split("\\,")[0])) {
						sBuilder.append("06").append(beishu).append(content.replace("~","").replace(",", "")).append("^");
					} else if ("~".equals(content.split("\\,")[1])) {
						sBuilder.append("07").append(beishu).append(content.replace("~","").replace(",", "")).append("^");
					} else if ("~".equals(content.split("\\,")[2])) {
						sBuilder.append("08").append(beishu).append(content.replace("~","").replace(",", "")).append("^");
					}
					continue;
				}
				return sBuilder.toString();
			}
		};

		// 3D tx
		IVenderTicketConverter d3_tx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				for (String content : contents) {
					sBuilder.append("09").append(beishu);
					String[] cons = content.split("\\,");
					for (String con : cons) {
						sBuilder.append(con);
					}
					sBuilder.append("^");
				}
				return sBuilder.toString();
			}
		};

		// 3D 猜1D
		IVenderTicketConverter d3_c1d = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				for (String content : contents) {
					sBuilder.append("0a").append(beishu);
					String[] cons = content.split("\\,");
					for (String con : cons) {
						sBuilder.append(con);
					}
					sBuilder.append("^");
				}
				return sBuilder.toString();
			}
		};

		// 3D 猜1D复式
		IVenderTicketConverter d3_c1df = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String contents = ticket.getContent().split("-")[1];
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("38").append(beishu).append(contents.replace(",", ""));
				return sBuilder.toString();
			}
		};

		// 3D 猜2D同
		IVenderTicketConverter d3_c2d = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				for (String content : contents) {
					sBuilder.append("0b").append(beishu);
					String[] cons = content.split("\\,");
					for (String con : cons) {
						sBuilder.append(con).append(con);
					}
					sBuilder.append("^");
				}
				return sBuilder.toString();
			}
		};

		// 3D 猜2D同复试
		IVenderTicketConverter d3_c2dfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String contents = ticket.getContent().split("-")[1];
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("39").append(beishu).append(contents.replace(",", ""));
				return sBuilder.toString();
			}
		};
		// 3D 猜2D不同
		IVenderTicketConverter d3_c2db = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String[] contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				for (String content : contents) {
					sBuilder.append("0c").append(beishu);
					String[] cons = content.split("\\,");
					for (String con : cons) {
						sBuilder.append(con);
					}
					sBuilder.append("^");
				}
				return sBuilder.toString();
			}
		};

		// 3D 猜2D不同复试
		IVenderTicketConverter d3_c2dbfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String contents = ticket.getContent().split("-")[1];
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("3a").append(beishu).append(contents.replace(",", ""));
				return sBuilder.toString();
			}
		};
		// 3D 和数
		IVenderTicketConverter d3_hs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String contents = ticket.getContent().split("-")[1];
				String beishu =null;
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				int len=contents.replace("^", "").replace(",", "").length()/2;
				String lenstr="";
				if (len< 10) {
					lenstr = "0" + len;
				} else {
					lenstr = String.valueOf(len);
				}
				sBuilder.append("a0").append(beishu).append(lenstr).append(contents.replace(",", ""));
				return sBuilder.toString();
			}
		};

		// 3D猜大小
		IVenderTicketConverter d3_cdx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String []contents = ticket.getContent().split("-")[1].split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				
				for(String str:contents){
					sBuilder.append("94").append(beishu).append(str.replace("1", "0").replace("2", "1")).append("^");
				}
				
				return sBuilder.toString();
			}
		};

		// 3D猜 奇偶
		IVenderTicketConverter d3_cjo = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String contents = ticket.getContent().split("-")[1];
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("84").append(beishu).append(contents.replace("4", "0").replace("5", "1"));
				return sBuilder.toString();
			}
		};
		// 3D猜 三同单式
		IVenderTicketConverter d3_cst = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("b0").append(beishu).append("^");;
				return sBuilder.toString();
			}
		};

		// 3D猜 拖拉机单式
		IVenderTicketConverter d3_tlj = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("b1").append(beishu).append("^");
				return sBuilder.toString();
			}
		};
		// 3d直选和值
		IVenderTicketConverter d3zx_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				String contents = ticket.getContent().split("-")[1].replace(",", "");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "0" + ticket.getMultiple();
				} else {
					beishu = ticket.getMultiple() + "";
				}
				sBuilder.append("1").append("0").append(beishu).append(contents);
				return sBuilder.toString();

			}
		};

		// 快乐十分单式
		IVenderTicketConverter klsf_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				int playType = Integer.parseInt(ticket.getContent().split("-")[0]);
				String playTypeStr = "";
				String[] contents = ticket.getContent().split("-")[1].replace(",", "").split("\\^");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "000" + ticket.getMultiple();
				} else if (ticket.getMultiple() < 100) {
					beishu = "00" + ticket.getMultiple();
				} else if (ticket.getMultiple() < 1000) {
					beishu = "0" + ticket.getMultiple();
				} else if (ticket.getMultiple() >= 1000) {
					beishu = ticket.getMultiple() + "";
				}
				if (playType >= PlayType.tjkl10_sr2.getValue() && playType <= PlayType.tjkl10_sr5.getValue()) {
					playTypeStr = "0";
				} else if (playType == PlayType.tjkl10_sz3.getValue() || playType == PlayType.tjkl10_sz2.getValue()) {
					playTypeStr = "1";
				} else if (playType == PlayType.tjkl10_sh0.getValue() || playType == PlayType.tjkl10_ss1.getValue() || playType == PlayType.tjkl10_sq3.getValue() || playType == PlayType.tjkl10_sq2.getValue()) {
					playTypeStr = "2";
				}
				int i = 0;
				int count = contents[i].length() / 2;
				String countStr = "";
				if (count < 10) {
					countStr = "0" + count;
				} else {
					countStr = count + "";
				}

				sBuilder.append("0").append(playTypeStr).append(beishu).append(countStr).append(contents[i] + "").append("^");
				i++;

				return sBuilder.toString();

			}
		};

		// 快乐十分复式
		IVenderTicketConverter klsf_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				int playType = Integer.parseInt(ticket.getContent().split("-")[0]);
				String playTypeStr = "";
				String content = ticket.getContent().split("-")[1].replace(",", "");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "000" + ticket.getMultiple();
				} else if (ticket.getMultiple() < 100) {
					beishu = "00" + ticket.getMultiple();
				} else if (ticket.getMultiple() < 1000) {
					beishu = "0" + ticket.getMultiple();
				} else if (ticket.getMultiple() >= 1000) {
					beishu = ticket.getMultiple() + "";
				}
				if (playType >= PlayType.tjkl10_mr2.getValue() && playType <= PlayType.tjkl10_mr5.getValue()) {
					playTypeStr = "0";
				} else if (playType == PlayType.tjkl10_mz2.getValue() || playType == PlayType.tjkl10_mz3.getValue()) {
					playTypeStr = "1";
				} else if (playType == PlayType.tjkl10_ms1.getValue()) {
					playTypeStr = "2";
				}
				int count = content.split("\\^")[0].length() / 2;
				String countStr = "";
				if (count < 10) {
					countStr = "0" + count;
				} else {
					countStr = count + "";
				}
				String playtypeCode = playCodeMap.get(playType);
				sBuilder.append("1").append(playTypeStr).append(beishu).append(playtypeCode).append(countStr).append("*").append(content);
				return sBuilder.toString();

			}
		};

		// 快乐十分胆拖
		IVenderTicketConverter klsf_dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder sBuilder = new StringBuilder();
				int playType = Integer.parseInt(ticket.getContent().split("-")[0]);
				String playTypeStr = "";
				String content = ticket.getContent().split("-")[1].replace(",", "");
				String beishu = "";
				if (ticket.getMultiple() < 10) {
					beishu = "000" + ticket.getMultiple();
				} else if (ticket.getMultiple() < 100) {
					beishu = "00" + ticket.getMultiple();
				} else if (ticket.getMultiple() < 1000) {
					beishu = "0" + ticket.getMultiple();
				} else if (ticket.getMultiple() >= 1000) {
					beishu = ticket.getMultiple() + "";
				}
				if (playType >= PlayType.tjkl10_dr2.getValue() && playType <= PlayType.tjkl10_dr5.getValue()) {
					playTypeStr = "0";
				} else if (playType == PlayType.tjkl10_dz2.getValue() || playType == PlayType.tjkl10_dz3.getValue()) {
					playTypeStr = "1";
				} else if (playType == PlayType.tjkl10_dq2.getValue() || playType == PlayType.tjkl10_dq3.getValue()) {
					playTypeStr = "2";
				}
				String zhucontent = content.split("\\^")[0].split("\\#")[1];
				int count = zhucontent.length() / 2;
				String countStr = "";
				if (count < 10) {
					countStr = "0" + count;
				} else {
					countStr = count + "";
				}
				String dancontent = content.split("\\^")[0].split("\\#")[0];
				int danlen = dancontent.length() / 2;
				String danlenStr = "";
				if (danlen < 10) {
					danlenStr = "0" + danlen;
				} else {
					danlenStr = danlen + "";
				}
				String playtypeCode = playCodeMap.get(playType);
				sBuilder.append("2").append(playTypeStr).append(beishu).append(playtypeCode).append(danlenStr).append(dancontent).append("*").append(countStr).append(zhucontent).append("^");
				return sBuilder.toString();

			}
		};

		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq_fd);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssq_dt);

		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, d3_zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, d3_fs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, d3_fs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, d3zx_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, d3_hz);

		playTypeToAdvanceConverterMap.put(PlayType.d3_1d_dan, d3_1d);
		playTypeToAdvanceConverterMap.put(PlayType.d3_2d_dan, d3_2d);
		playTypeToAdvanceConverterMap.put(PlayType.d3_3dtx_dan, d3_tx);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c1d_dan, d3_c1d);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c2d_ertong_dan, d3_c2d);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c3d_erbutong_dan, d3_c2db);
		// playTypeToAdvanceConverterMap.put(PlayType.d3_1d_fs, d3_1d);
		// playTypeToAdvanceConverterMap.put(PlayType.d3_2d_fs, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_3dtx_fx, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c1d_fx, d3_c1df);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c2d_ertong_fx, d3_c2dfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c3d_erbutong_fx, d3_c2dbfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_new_hs, d3_hs);
		// playTypeToAdvanceConverterMap.put(PlayType.d3_new_hzx, d3_hz);

		playTypeToAdvanceConverterMap.put(PlayType.d3_cdx_dan, d3_cdx);
		playTypeToAdvanceConverterMap.put(PlayType.d3_cjo_dan, d3_cjo);
		playTypeToAdvanceConverterMap.put(PlayType.d3_c3t_dan, d3_cst);
		playTypeToAdvanceConverterMap.put(PlayType.d3_tlj_dan, d3_tlj);

		playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, qlc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, qlc_fs);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, qlc_dt);

		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sh0, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_ss1, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr2, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr3, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr4, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr5, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sq2, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sq3, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sz2, klsf_ds);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sz3, klsf_ds);

		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_ms1, klsf_fs);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr2, klsf_fs);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr3, klsf_fs);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr4, klsf_fs);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr5, klsf_fs);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mz2, klsf_fs);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mz3, klsf_fs);

		// playTypeToAdvanceConverterMap.put(PlayType.tjkl10_pq2, klsf_dingwei);
		// playTypeToAdvanceConverterMap.put(PlayType.tjkl10_pq3, klsf_dingwei);

		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr2, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr3, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr4, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr5, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dq2, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dq3, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dz2, klsf_dt);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dz3, klsf_dt);

		IPhaseConverter defaultphaseReverseConverter = new IPhaseConverter() {

			@Override
			public String convert(String phase) {

				return phase;
			}
		};
		IPhaseConverter tjfcphaseReverseConverter = new IPhaseConverter() {

			@Override
			public String convert(String phase) {

				return CoreDateUtils.formatDate(new Date(), CoreDateUtils.DATE_YEAR) + phase;
			}
		};

		phaseReverseConverterMap.put(LotteryType.SSQ, defaultphaseReverseConverter);
		phaseReverseConverterMap.put(LotteryType.F3D, defaultphaseReverseConverter);
		phaseReverseConverterMap.put(LotteryType.QLC, defaultphaseReverseConverter);
		phaseReverseConverterMap.put(LotteryType.TJKL10, tjfcphaseReverseConverter);
		phaseReverseConverterMap.put(LotteryType.TJSSC, tjfcphaseReverseConverter);
	}

}
