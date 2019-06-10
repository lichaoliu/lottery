package com.lottery.core.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.Constants;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryOrderLineContains;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.RaceSaleStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.dao.JcGuanyajunRaceDao;
import com.lottery.core.dao.JclqRaceDAO;
import com.lottery.core.dao.JczqRaceDao;
import com.lottery.core.domain.JcGuanYaJunRace;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;

@Service
public class JingcaiMatchVerifyService {
	
	private final Logger logger = LoggerFactory.getLogger(JingcaiMatchVerifyService.class);
	
	private String danguanType = Constants.JINGCAI_DANGUAN_TYPE;

	@Autowired
	private JczqRaceDao jczqRaceDao;
	
	@Autowired
	private JclqRaceDAO jclqRaceDao;
	
	@Autowired
	private JcGuanyajunRaceDao guanyajunRaceDao;
	
	
	
	/**
	 * 校验足球场次
	 * 是否存在
	 * 是否过期
	 * 是否支持该玩法
	 * @param betcode
	 * @param lotterytype
	 */
	public void validateIsJcMatchOutTimeAndTypeNotSupport(String betcode,LotteryType lotterytype,int prizeOptimize) {
		Set<String> matches = getAllMatches(betcode,lotterytype,prizeOptimize);
		for(String matchitem:matches) {
			LotteryType type = LotteryType.getLotteryType(Integer.parseInt(matchitem.split("\\_")[1]));
			if(LotteryType.getJczq().contains(type)) {
				validateZqMatch(Boolean.parseBoolean(matchitem.split("\\_")[2]), type, jczqRaceDao.find(matchitem.split("\\_")[0]),matchitem.split("\\_")[0]);
			}else if(LotteryType.getJclq().contains(type)) {
				validateLqMatch(Boolean.parseBoolean(matchitem.split("\\_")[2]), type, jclqRaceDao.find(matchitem.split("\\_")[0]),matchitem.split("\\_")[0]);
			}
			
		}
	}
	
	
	public void validateIsJcGuanyajunMatchOutTimeAndTypeNotSupport(String betcode,LotteryType lotterytype,String phase) {
		Set<String> matches = new HashSet<String>(Arrays.asList(betcode.split("\\-")[1].replace("^", "").split(",")));
		for(String matchitem:matches) {
			JcGuanYaJunRace race = guanyajunRaceDao.getRace(lotterytype.value, phase, matchitem);
			if(race==null) {
				throw new LotteryException(ErrorCode.match_not_exist, matchitem+"不存在");
			}else if(race.getStatus()!=RaceStatus.OPEN.value) {
				throw new LotteryException(ErrorCode.match_not_open, lotterytype.value+" "+race.getId().getMatchNum()+"未开售");
			}
		}
	}
	
	
	

	private void validateZqMatch(boolean isDanguan, LotteryType type,
			JczqRace race,String matchnum) {
		if(race==null) {
			logger.error(matchnum+"不存在");
			throw new LotteryException(ErrorCode.match_not_exist, matchnum+"不存在");
		}else if(race.getStatus()!=RaceStatus.OPEN.value) {
			throw new LotteryException(ErrorCode.match_not_open, race.getMatchNum()+"未开售");
		}else if(new Date().after(race.getEndSaleTime())) {
			throw new LotteryException(ErrorCode.match_out_time, race.getMatchNum()+"已过期");
		}
		String matchNum=race.getMatchNum();
		if(type==LotteryType.JCZQ_BF) {
			if(isDanguan) {
				if(race.getDgStaticSaleBfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleBfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCZQ_BQC) {
			if(isDanguan) {
				if(race.getDgStaticSaleBqcStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleBqcStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCZQ_JQS) {
			if(isDanguan) {
				if(race.getDgStaticSaleJqsStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleJqsStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCZQ_RQ_SPF) {
			if(isDanguan) {
				if(race.getDgStaticSaleSpfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleSpfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCZQ_SPF_WRQ) {
			if(isDanguan) {
				if(race.getDgStaticSaleSpfWrqStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleSpfWrqStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}
	}
	
	
	private void validateLqMatch(boolean isDanguan, LotteryType type,
			JclqRace race,String matchnum) {
		if(race==null) {
			logger.error(matchnum+"不存在");
			throw new LotteryException(ErrorCode.match_not_exist, matchnum+"不存在");
		}else if(race.getStatus()!=RaceStatus.OPEN.value) {
			throw new LotteryException(ErrorCode.match_not_open, race.getMatchNum()+",未开售");
		}else if(new Date().after(race.getEndSaleTime())) {
			throw new LotteryException(ErrorCode.match_out_time, race.getMatchNum()+",已期结");
		}
		String matchNum=race.getMatchNum();
		if(type==LotteryType.JCLQ_DXF) {
			if(isDanguan) {
				if(race.getDgStaticSaleDxfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleDxfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCLQ_RFSF) {
			if(isDanguan) {
				if(race.getDgStaticSaleRfsfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleRfsfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCLQ_SF) {
			if(isDanguan) {
				if(race.getDgStaticSaleSfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleSfStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}else if(type==LotteryType.JCLQ_SFC) {
			if(isDanguan) {
				if(race.getDgStaticSaleSfcStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport,"场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}else {
				if(race.getStaticSaleSfcStatus()==RaceSaleStatus.SALE_UNOPEN.value) {
					throw new LotteryException(ErrorCode.match_playtype_unsupport, "场次:"+matchNum+",玩法:"+type.getName()+"未开售");
				}
			}
		}
	}
	
	
	
	//20140101008_3001_false
	private Set<String> getAllMatches(String betcode,LotteryType lotterytype,int prizeOptimize) {
		betcode = betcode.replace("#", "|");
		
		Set<String> matches = new HashSet<String>();
		
		for(String bet:betcode.split("!")) {
			boolean isDanguan = bet.substring(5, 9).equals(danguanType);
			String code = bet.split("\\-")[1].replace("^", "");
			if(prizeOptimize==YesNoStatus.yes.value) {
				code = code.split(LotteryOrderLineContains.PRIZE_OPTIMIZE_SPLITLINE)[0];
			}
			
			for(String onebet:code.split("\\|")) {
				if(LotteryType.JCLQ_HHGG==lotterytype||LotteryType.JCZQ_HHGG==lotterytype) {
					matches.add(onebet.substring(0, 11)+"_"+onebet.split("\\*")[1].substring(0, 4)+"_"+isDanguan);
				}else {
					matches.add(onebet.substring(0, 11)+"_"+bet.substring(0, 4)+"_"+isDanguan);
				}
				
			}
		}
		
		return matches;
	}
	
	public static void main(String[] args) {
		Set<String> allMatches = new JingcaiMatchVerifyService().getAllMatches("301114001-20160201005*3006(3)|20160201009*3010(3)|20160202003*3006(3)|20160202005*3010(3)^", LotteryType.get(3011), 0);
		
		for(String match:allMatches) {
			System.out.println(match);
		}
	}
}
