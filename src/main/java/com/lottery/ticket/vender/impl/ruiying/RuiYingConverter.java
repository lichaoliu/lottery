package com.lottery.ticket.vender.impl.ruiying;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;
import com.lottery.ticket.vender.AbstractVenderConverter;

@Service
public class RuiYingConverter extends AbstractVenderConverter {

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(RuiYingLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= RuiYingLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		return RuiYingLotteryDef.lotteryTypeMap;
	}

	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return RuiYingLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		StringBuffer stringBuffer=new StringBuffer();
		if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_SPF_WRQ.value||tickt.getLotteryType().intValue()==LotteryType.JCZQ_BQC.value
				||tickt.getLotteryType().intValue()==LotteryType.JCZQ_JQS.value
				||tickt.getLotteryType().intValue()==LotteryType.JCZQ_RQ_SPF.value){
					String conStr=venderSp.replace("(","_").replace(":", "").replace(")","");
					String []cons=conStr.split("\\|")[1].split("\\,");
					int i=0;
					for(String con:cons){
						stringBuffer.append("20").append(con.replace("=","(").replace("/",",")).append(")");
						if(i!=cons.length-1){
							stringBuffer.append("|");
						}
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_BF.value){
					String []cons=venderSp.split("\\|")[1].split("\\,");
					int i=0;
					for(String con:cons){
						stringBuffer.append("20").append(con.split("\\=")[0]).append("(");
						String []cs=con.split("\\=")[1].split("\\/");
						int j=0;
						for(String c:cs){
							stringBuffer.append(c.replace("(","_").replace(")",""));
							if(j!=cs.length-1){
								stringBuffer.append(",");
							}
							j++;
						}
						stringBuffer.append(")");
						if(i!=cons.length-1){
							stringBuffer.append("|");
						}
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_HHGG.value){
					String conStr=venderSp.replace("(","_").replace(":", "").replace(")","");
					String []cons=conStr.split("\\|")[1].split("\\,");
					int i=0;
					String []contents=tickt.getContent().split("\\-")[1].split("\\|");
					for(String con:cons){
						stringBuffer.append(contents[i].split("\\(")[0]).append("(");
						stringBuffer.append(con.split("\\=")[1].replace("/", ",")).append(")");
						if(i!=cons.length-1){
							stringBuffer.append("|");
						}
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_DXF.value){
					String conStr=venderSp;
					String []cons=conStr.split("\\|")[1].split("\\,");
					int i=0;
					for(String con:cons){
						String []cs=con.split("\\=")[1].split("\\/");
						stringBuffer.append("20").append(con.split("\\=")[0].replace(")",":"));
						int f=0;
						for(String c:cs){
							stringBuffer.append(c.split("\\(")[0]).append("_").append(
									c.split("\\(")[1].replace(")",""));
							if(f!=cs.length-1){
								stringBuffer.append(",");
							}
							f++;
						}
						    stringBuffer.append(")");
							if(i!=cons.length-1){
							    stringBuffer.append("|");
						     }
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_RFSF.value){
					String []cons=venderSp.split("\\|")[1].split("\\,");
					int i=0;
					for(String con:cons){
						String []cs=con.split("\\=")[1].split("\\/");
						stringBuffer.append("20").append(con.split("\\=")[0].replace(")",":"));
						int f=0;
						for(String c:cs){
							stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(
									c.split("\\(")[1].replace(")",""));
							if(f!=cs.length-1){
								stringBuffer.append(",");
							}
							f++;
						}
						    stringBuffer.append(")");
							if(i!=cons.length-1){
							    stringBuffer.append("|");
						     }
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_SFC.value){
					String conStr=venderSp;
					String []cons=conStr.split("\\|")[1].split("\\,");
					int i=0;
					for(String con:cons){
						String []cs=con.split("\\=")[1].split("\\/");
						stringBuffer.append("20").append(con.split("\\=")[0]).append("(");
						int f=0;
						for(String c:cs){
							stringBuffer.append(c.replace("(","_").replace(")",""));
							if(f!=cs.length-1){
								stringBuffer.append(",");
							}
							f++;
						}
						stringBuffer.append(")");
						if(i!=cons.length-1){
							stringBuffer.append("|");
						}
						
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_SF.value){
					String []cons=venderSp.split("\\|")[1].split("\\,");
					int i=0;
					for(String con:cons){
						String []cs=con.split("\\=")[1].split("\\/");
						stringBuffer.append("20").append(con.split("\\=")[0]).append("(");
						int f=0;
						for(String c:cs){
							stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(
									c.split("\\(")[1].replace(")",""));
							if(f!=cs.length-1){
								stringBuffer.append(",");
							}
							f++;
						}
						    stringBuffer.append(")");
							if(i!=cons.length-1){
							    stringBuffer.append("|");
						     }
						i++;
					}
				}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_HHGG.value){
					String conStr=venderSp;
					String []cons=conStr.split("\\|")[1].split("\\,");
					int i=0;
					String []contents=tickt.getContent().split("\\-")[1].split("\\|");
					for(String con:cons){
						String cs[]=con.split("\\/");
						String playType=contents[i].split("\\*")[1].split("\\(")[0];
						stringBuffer.append(contents[i].split("\\(")[0]).append("(");
						if("3003".equals(playType)){
							int j=0;
							for(String c:cs){
								stringBuffer.append(c.contains("=")?c.split("\\(")[0].split("\\=")[1]:c.split("\\(")[0]).append("_").append(c.contains("=")?c.split("\\=")[1].split("\\(")[1].replace(")", ""):c.split("\\(")[1].replace(")", ""));
								if(j!=cs.length-1){
									stringBuffer.append(",");
								}
								j++;
							}
							stringBuffer.append(")");
						}else if("3004".equals(playType)){
							stringBuffer.append(con.split("\\=")[0].split("\\(")[1].replace(")",":"));
							int j=0;
							for(String c:cs){
								if(c.contains("=")){
									c=c.split("\\=")[1];
								}
								stringBuffer.append(c.split("\\(")[0]).append("_").append(c.split("\\(")[1].replace(")", ""));
								if(j!=cs.length-1){
									stringBuffer.append(",");
								}
								j++;
							}
							stringBuffer.append(")");
						}else if("3002".equals(playType)){
							stringBuffer.append(con.split("\\=")[0].split("\\(")[1].replace(")",":"));
							int j=0;
							for(String c:cs){
								if(c.contains("=")){
									c=c.split("\\=")[1];
								}
								stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(c.split("\\(")[1].replace(")",""));
								if(j!=cs.length-1){
									stringBuffer.append(",");
								}
								j++;
							}
							stringBuffer.append(")");
							
						}else{
							int j=0;
							for(String c:cs){
								if(c.contains("=")){
									c=c.split("\\=")[1];
								}
								stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(c.split("\\(")[1].replace(")", ""));
								if(j!=cs.length-1){
									stringBuffer.append(",");
								}
								j++;
							}
							stringBuffer.append(")");
						}
						
						if(i!=cons.length-1){
							stringBuffer.append("|");
						}
						i++;
					}
				}
		return stringBuffer.toString();
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_RUIYING, this);
	}

	
	public static void main(String[] args) {
		String venderSp="SFC|160619302=2(2.650)/1(1.310),160619303=1(2.050),160619304=2(1.060)|3*1:1";
		String content="300113001-20160619302(0,3)|20160619303(3)|20160619304(0)^";
		StringBuffer stringBuffer=new StringBuffer();
		int lotteryType=3001;
		if(lotteryType==LotteryType.JCZQ_SPF_WRQ.value||lotteryType==LotteryType.JCZQ_BQC.value
		||lotteryType==LotteryType.JCZQ_JQS.value||lotteryType==LotteryType.JCZQ_BQC.value
		||lotteryType==LotteryType.JCZQ_RQ_SPF.value){
			String conStr=venderSp.replace("7+","7").replace("(","_").replace(":", "").replace(")","").replace("+",",");
			String []cons=conStr.split("\\|")[1].split("\\,");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.replace("=","(").replace("/",",")).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCZQ_BF.value){
			String []cons=venderSp.split("\\|")[1].split("\\,");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(");
				String []cs=con.split("\\=")[1].split("\\/");
				int j=0;
				for(String c:cs){
					stringBuffer.append(c.replace("(","_").replace(")",""));
					if(j!=cs.length-1){
						stringBuffer.append(",");
					}
					j++;
				}
				stringBuffer.append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCZQ_HHGG.value){
			String conStr=venderSp.replace("7+","7").replace("(","_").replace(":", "").replace(")","").replace("+",",");
			String []cons=conStr.split("\\|")[1].split("\\,");
			int i=0;
			String []contents=content.split("\\-")[1].split("\\|");
			for(String con:cons){
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				stringBuffer.append(con.split("\\=")[1].replace("/", ",")).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_DXF.value){
			String conStr=venderSp;
			String []cons=conStr.split("\\|")[1].split("\\,");
			int i=0;
			for(String con:cons){
				String []cs=con.split("\\=")[1].split("\\/");
				stringBuffer.append("20").append(con.split("\\=")[0].replace(")",":"));
				int f=0;
				for(String c:cs){
					stringBuffer.append(c.split("\\(")[0]).append("_").append(
							c.split("\\(")[1].replace(")",""));
					if(f!=cs.length-1){
						stringBuffer.append(",");
					}
					f++;
				}
				    stringBuffer.append(")");
					if(i!=cons.length-1){
					    stringBuffer.append("|");
				     }
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_RFSF.value){
			String []cons=venderSp.split("\\|")[1].split("\\,");
			int i=0;
			for(String con:cons){
				String []cs=con.split("\\=")[1].split("\\/");
				stringBuffer.append("20").append(con.split("\\=")[0].replace(")",":"));
				int f=0;
				for(String c:cs){
					stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(
							c.split("\\(")[1].replace(")",""));
					if(f!=cs.length-1){
						stringBuffer.append(",");
					}
					f++;
				}
				    stringBuffer.append(")");
					if(i!=cons.length-1){
					    stringBuffer.append("|");
				     }
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_SF.value){
			String []cons=venderSp.split("\\|")[1].split("\\,");
			int i=0;
			for(String con:cons){
				String []cs=con.split("\\=")[1].split("\\/");
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(");
				int f=0;
				for(String c:cs){
					stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(
							c.split("\\(")[1].replace(")",""));
					if(f!=cs.length-1){
						stringBuffer.append(",");
					}
					f++;
				}
				    stringBuffer.append(")");
					if(i!=cons.length-1){
					    stringBuffer.append("|");
				     }
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_SFC.value){
			String conStr=venderSp;
			String []cons=conStr.split("\\|")[1].split("\\,");
			int i=0;
			for(String con:cons){
				String []cs=con.split("\\=")[1].split("\\/");
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(");
				int f=0;
				for(String c:cs){
					stringBuffer.append(c.replace("(","_").replace(")",""));
					if(f!=cs.length-1){
						stringBuffer.append(",");
					}
					f++;
				}
				stringBuffer.append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_HHGG.value){
			String conStr=venderSp;
			String []cons=conStr.split("\\|")[1].split("\\,");
			int i=0;
			String []contents=content.split("\\-")[1].split("\\|");
			for(String con:cons){
				String cs[]=con.split("\\/");
				String playType=contents[i].split("\\*")[1].split("\\(")[0];
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				if("3003".equals(playType)){
					int j=0;
					for(String c:cs){
						stringBuffer.append(c.contains("=")?c.split("\\(")[0].split("\\=")[1]:c.split("\\(")[0]).append("_").append(c.contains("=")?c.split("\\=")[1].split("\\(")[1].replace(")", ""):c.split("\\(")[1].replace(")", ""));
						if(j!=cs.length-1){
							stringBuffer.append(",");
						}
						j++;
					}
					stringBuffer.append(")");
				}else if("3004".equals(playType)){
					stringBuffer.append(con.split("\\=")[0].split("\\(")[1].replace(")",":"));
					int j=0;
					for(String c:cs){
						if(c.contains("=")){
							c=c.split("\\=")[1];
						}
						stringBuffer.append(c.split("\\(")[0]).append("_").append(c.split("\\(")[1].replace(")", ""));
						if(j!=cs.length-1){
							stringBuffer.append(",");
						}
						j++;
					}
					stringBuffer.append(")");
				}else if("3002".equals(playType)){
					stringBuffer.append(con.split("\\=")[0].split("\\(")[1].replace(")",":"));
					int j=0;
					for(String c:cs){
						if(c.contains("=")){
							c=c.split("\\=")[1];
						}
						stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(c.split("\\(")[1].replace(")",""));
						if(j!=cs.length-1){
							stringBuffer.append(",");
						}
						j++;
					}
					stringBuffer.append(")");
					
				}else{
					int j=0;
					for(String c:cs){
						if(c.contains("=")){
							c=c.split("\\=")[1];
						}
						stringBuffer.append(c.split("\\(")[0].replace("1", "3").replace("2", "0")).append("_").append(c.split("\\(")[1].replace(")", ""));
						if(j!=cs.length-1){
							stringBuffer.append(",");
						}
						j++;
					}
					stringBuffer.append(")");
				}
				
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}
		System.out.println(stringBuffer.toString());
		
		
	}
	
}
