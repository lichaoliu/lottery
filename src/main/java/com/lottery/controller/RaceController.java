package com.lottery.controller;

import com.lottery.common.ListSerializable;
import com.lottery.common.ResponseData;
import com.lottery.common.cache.CacheService;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.DcRace;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.domain.ZcRace;
import com.lottery.core.service.DcRaceService;
import com.lottery.core.service.JclqRaceService;
import com.lottery.core.service.JczqRaceService;
import com.lottery.core.service.ZcRaceService;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.JclqMatchSpService;
import com.lottery.scheduler.fetcher.sp.impl.JczqMatchSpService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RequestMapping("/race")
@Controller
public class RaceController {
	private static final Logger logger = LoggerFactory.getLogger(RaceController.class);
	@Autowired
	protected JczqRaceService jczqRaceService;
	@Autowired
	private JclqRaceService jclqRaceService;
	@Autowired
	private ZcRaceService zcRaceService;
	@Autowired
	private DcRaceService dcRaceService;
	
	@Resource(name = "xmemcachedService")
	private CacheService cacheService;
	
    @Autowired
	private JclqMatchSpService jclqMatchSpService;
    
    @Autowired
	private JczqMatchSpService jczqMatchSpService;
    
	/**
	 * @param lotteryType
	 *            彩种
	 * @param type
	 *            类型 01单关，02过关
	 * */
	@RequestMapping(value = "/jczqList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJczqList(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "type", required = false, defaultValue = "02") String type) {
		ResponseData rd = new ResponseData();
		try {
			List<JczqRace> raceList = jczqRaceService.getEnableJczqRace(lotteryType, type);
			if (raceList.size() > 0) {
				Collections.sort(raceList, new Comparator<JczqRace>() {
					@Override
					public int compare(JczqRace o1, JczqRace o2) {
						return Long.valueOf(o1.getMatchNum()).compareTo(Long.valueOf(o2.getMatchNum()));
					}

				});
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取竞彩足球赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	
	@Deprecated   //officialDate时间意义已经更改
	@RequestMapping(value = "/jczqResultList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJczqList(@RequestParam(value = "officialDate", required = true) String officialDate) {
		ResponseData rd = new ResponseData();
		try {
			List<JczqRace> raceList = jczqRaceService.getJczqResult(officialDate);
			if (raceList.size() > 0) {
				Collections.sort(raceList, new Comparator<JczqRace>() {
					@Override
					public int compare(JczqRace o1, JczqRace o2) {
						return Long.valueOf(o1.getMatchNum()).compareTo(Long.valueOf(o2.getMatchNum()));
					}

				});
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取竞彩足球赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	@Deprecated  //officialDate时间意义已经更改
	@RequestMapping(value = "/jclqResultList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJclqList(@RequestParam(value = "officialDate", required = true) String officialDate) {
		ResponseData rd = new ResponseData();
		try {
			List<JclqRace> raceList = jclqRaceService.getJclqResult(officialDate);
			if (raceList.size() > 0) {
				Collections.sort(raceList, new Comparator<JclqRace>() {
					@Override
					public int compare(JclqRace o1, JclqRace o2) {
						return Long.valueOf(o1.getMatchNum()).compareTo(Long.valueOf(o2.getMatchNum()));
					}

				});
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取竞彩足球赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	
	//=====
	
	@RequestMapping(value = "/jczqPhaseResultList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJczqPhaseList(@RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			List<JczqRace> raceList = jczqRaceService.getJczqPhaseResult(phase);
			if (raceList.size() > 0) {
				Collections.sort(raceList, new Comparator<JczqRace>() {
					@Override
					public int compare(JczqRace o1, JczqRace o2) {
						return Long.valueOf(o1.getMatchNum()).compareTo(Long.valueOf(o2.getMatchNum()));
					}

				});
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取竞彩足球赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/jclqPhaseResultList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJclqPhaseList(@RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			List<JclqRace> raceList = jclqRaceService.getJclqPhaseResult(phase);
			if (raceList.size() > 0) {
				Collections.sort(raceList, new Comparator<JclqRace>() {
					@Override
					public int compare(JclqRace o1, JclqRace o2) {
						return Long.valueOf(o1.getMatchNum()).compareTo(Long.valueOf(o2.getMatchNum()));
					}

				});
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取竞彩足球赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取竞彩过关历史sp
	 * @param matchNum 比赛编码
	 * @param lotteryType 彩种
	 * */
	@RequestMapping(value = "/getJcHistorySP", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJcHistorySP(@RequestParam(value = "matchNum", required = true) String matchNum,
			@RequestParam(value = "lotteryType", required = true) int lotteryType
			) {
		ResponseData rd = new ResponseData();
		try {
			if(LotteryType.getJclq().contains(LotteryType.getPhaseType(lotteryType))){
				MatchSpDomain jlcq=jclqMatchSpService.get(matchNum);
				rd.setValue(jlcq);
			}
			if(LotteryType.getJczq().contains(LotteryType.getPhaseType(lotteryType))){
				MatchSpDomain jczq=jczqMatchSpService.get(matchNum);
				rd.setValue(jczq);
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("获取竞彩过关历史sp出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * @param lotteryType
	 *            彩种
	 * @param type
	 *            类型 01单关，02过关
	 * */
	@RequestMapping(value = "/jclqList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getJclqList(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "type", required = false, defaultValue = "01") String type) {
		ResponseData rd = new ResponseData();
		try {
			List<JclqRace> raceList = jclqRaceService.getEnableJclqRace(lotteryType, type);
			if (raceList.size() > 0) {
				Collections.sort(raceList, new Comparator<JclqRace>() {
					@Override
					public int compare(JclqRace o1, JclqRace o2) {
						return Long.valueOf(o1.getMatchNum()).compareTo(Long.valueOf(o2.getMatchNum()));
					}

				});
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取竞彩篮球赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 竞彩足球对阵查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * */
	@RequestMapping(value = "/zcList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getZcList(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			List<ZcRace> raceList = zcRaceService.getByLotteryTypeAndPhase(lotteryType, phase);
			if (raceList != null && raceList.size() > 0) {
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取足彩赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 北京单场对阵查询
	 * 
	 * @param dcType
	 *            对阵类型 1普通单场，2单场胜负
	 * @param phase
	 *            期号
	 * */

	@RequestMapping(value = "/dcList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getDcList(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "dcType", required = true, defaultValue = "1") int dcType,
			@RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			List<DcRace> raceList = dcRaceService.enableRace(lotteryType, phase, dcType);
			if (raceList != null && raceList.size() > 0) {
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(raceList);
			} else {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			}
		} catch (Exception e) {
			logger.error("获取单场赛程出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 比赛对阵查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param matchNum
	 *            场次号
	 * */
	@RequestMapping(value = "/getRace", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getRaceById(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "phase", required = false, defaultValue = "") String phase,
			@RequestParam(value = "matchNum", required = true) String matchNum) {
		ResponseData rd = new ResponseData();
		try {
			String key = lotteryType + phase + matchNum;
			LotteryType type = LotteryType.getLotteryType(lotteryType);
			if (LotteryType.getJclq().contains(type)) {
				JclqRace jclqRace = cacheService.get(key);
				if (jclqRace == null) {
					jclqRace = jclqRaceService.getByMatchNum(matchNum);
					if (jclqRace != null) {
						cacheService.set(key, 60, jclqRace);
					}
				}
				if (jclqRace != null) {
					rd.setValue(jclqRace);
				}
			}

			if (LotteryType.getJczq().contains(type)) {
				JczqRace jczqRace = cacheService.get(key);
				if (jczqRace == null) {
					jczqRace = jczqRaceService.getByMatchNum(matchNum);
					if (jczqRace != null) {
						cacheService.set(key, 60, jczqRace);
					}
				}
				if (jczqRace != null) {
					rd.setValue(jczqRace);
				}
			}
			if (LotteryType.getZc().contains(type)) {
				ZcRace zcRace = cacheService.get(key);
				if (zcRace == null) {
					zcRace = zcRaceService.getByLotteryPhaseAndNum(lotteryType, phase, Integer.valueOf(matchNum));
					if (zcRace != null) {
						cacheService.set(key, 60, zcRace);
					}
				}
				if (zcRace != null) {
					rd.setValue(zcRace);
				}
			}
			if (LotteryType.getDc().contains(type)) {
				DcRace dcRace = cacheService.get(key);
				if (dcRace == null) {
					int dcType=DcType.normal.intValue();
					if(type==LotteryType.DC_SFGG){
						dcType=DcType.sfgg.intValue();
					}
					dcRace = dcRaceService.getByPhase(phase, Integer.valueOf(matchNum),dcType);
					if (dcRace != null) {
						cacheService.set(key, 60, dcRace);
					}
				}
				if (dcRace != null) {
					rd.setValue(dcRace);
				}
			}

			if (rd.getValue() == null) {
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			} else {
				rd.setErrorCode(ErrorCode.Success.getValue());
			}

		} catch (Exception e) {
			logger.error("获取单个场次出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 比赛对阵查询 
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * */
	@RequestMapping(value = "/getRaceList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getRaceList(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			LotteryType type = LotteryType.getLotteryType(lotteryType);
			if(LotteryType.getJclq().contains(type)){
				String key="jclq_race_list_"+phase;
				ListSerializable<JclqRace> listSerializable=cacheService.get(key,3000);
				if(listSerializable==null||listSerializable.getList()==null){
					listSerializable=new ListSerializable<JclqRace>();
					List<JclqRace> list=jclqRaceService.getByPhase(phase);
					if(list!=null){
						listSerializable.setList(list);
						cacheService.set(key, 120, listSerializable);
					}
				}
				rd.setValue(listSerializable.getList());
			}
			if(LotteryType.getJczq().contains(type)){
				String key="jczq_race_list_"+phase;
				ListSerializable<JczqRace> listSerializable=cacheService.get(key,3000);
				if(listSerializable==null||listSerializable.getList()==null){
					listSerializable=new ListSerializable<JczqRace>();
					List<JczqRace> list=jczqRaceService.getByPhase(phase);
					if(list!=null){
						listSerializable.setList(list);
						cacheService.set(key, 120, listSerializable);
					}
				}
				rd.setValue(listSerializable.getList());
			}
			if(LotteryType.getDc().contains(type)){
				DcType dcType = DcType.normal;
				if(type.equals(LotteryType.DC_SFGG)) {
					dcType = DcType.sfgg;
				}
				String key="dc_race_list_"+phase+"_"+dcType.value();
				ListSerializable<DcRace> listSerializable=cacheService.get(key,3000);
				if(listSerializable==null||listSerializable.getList()==null){
					listSerializable=new ListSerializable<DcRace>();
					List<DcRace> list=dcRaceService.getByPhaseAndType(phase, dcType.value());
					if(list!=null){
						listSerializable.setList(list);
						cacheService.set(key, 120, listSerializable);
					}
				}
				rd.setValue(listSerializable.getList());
			}
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("获取彩种{}对阵出错出错",lotteryType,e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 修改足彩对阵的平均赔率,比分
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param matchNum
	 *            场次号
	 * @param finalScore
	 *            全场比分
	 * @param halfScore
	 *            半场比分
	 * @param averageIndex
	 *            平均赔率,格式：胜_平_负 多个用逗号分割
	 * 
	 * */
	@RequestMapping(value = "/updateZcOddsAndScore", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData updateAverageIndex(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "matchNum", required = true) int matchNum,
			@RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "averageIndex", required = false) String averageIndex,
			@RequestParam(value = "finalScore", required = false) String finalScore, @RequestParam(value = "halfScore", required = false) String halfScore) {
		ResponseData rd = new ResponseData();
		try {
			// logger.error("修改彩种:{}期号:{}场次:{}的平均赔率为:{},全场比分:{},半场比分:{}",new
			// Object[]{lotteryType,phase,matchNum,averageIndex,finalScore,halfScore});
			ZcRace race = zcRaceService.getByLotteryPhaseAndNum(lotteryType, phase, matchNum);
			if (race == null) {
				rd.setErrorCode(ErrorCode.match_not_exist.getValue());
			} else {
				boolean flag = false;
				if (StringUtils.isNotBlank(averageIndex)) {
					race.setAverageIndex(averageIndex);
					flag = true;
				}
				if (StringUtils.isNotBlank(halfScore)) {
					race.setHalfScore(halfScore);
					flag = true;
				}
				if (StringUtils.isNotBlank(finalScore)) {
					race.setFinalScore(finalScore);
					flag = true;
				}
				if (flag)
					zcRaceService.update(race);
				rd.setErrorCode(ErrorCode.Success.getValue());
			}
		} catch (Exception e) {
			logger.error("修改足彩赔率,比分出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 增加足彩对阵
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param matchNum
	 *            场次号
	 * @param homeTeam
	 *            主队
	 * @param awayTeam
	 *            客队
	 * @param matchName
	 *            赛事
	 * @param matchDate
	 *            比赛日期 yyyy-mm-dd hh:mm:ss
	 * 
	 * */
	@RequestMapping(value = "/insertRace", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData insertRace(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "matchNum", required = true) int matchNum,
			@RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "homeTeam", required = true) String homeTeam,
			@RequestParam(value = "awayTeam", required = true) String awayTeam, @RequestParam(value = "matchName", required = true) String matchName,
			@RequestParam(value = "matchDate", required = true) Date matchDate) {
		ResponseData rd = new ResponseData();
		try {
			// logger.error("修改彩种:{}期号:{}场次:{}的平均赔率为:{},全场比分:{},半场比分:{}",new
			// Object[]{lotteryType,phase,matchNum,averageIndex,finalScore,halfScore});
			ZcRace race = zcRaceService.getByLotteryPhaseAndNum(lotteryType, phase, matchNum);
			if (race == null) {
				race = new ZcRace();
				race.setAwayTeam(awayTeam);
				race.setHomeTeam(homeTeam);
				race.setCreateTime(new Date());
				race.setAwayTeam(awayTeam);
				race.setLotteryType(lotteryType);
				race.setPhase(phase);
				race.setMatchName(matchName);
				race.setMatchNum(matchNum);
				race.setMatchDate(matchDate);
				zcRaceService.save(race);
			} else {
				race.setAwayTeam(awayTeam);
				race.setHomeTeam(homeTeam);
				race.setCreateTime(new Date());
				race.setAwayTeam(awayTeam);
				race.setLotteryType(lotteryType);
				race.setPhase(phase);
				race.setMatchName(matchName);
				race.setMatchNum(matchNum);
				race.setMatchDate(matchDate);
				zcRaceService.update(race);
			}
			rd.setValue(race);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("创建足彩对阵出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	
	

	/**
	 * spring MVC对时间的处理,如果不加此项，传入date类型，会返回400错误
	 * */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// true:允许输入空值，false:不能为空值
	}
	
	

}
