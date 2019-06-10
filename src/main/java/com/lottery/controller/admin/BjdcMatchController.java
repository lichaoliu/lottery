package com.lottery.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.DcRaceDAO;
import com.lottery.core.domain.DcRace;
import com.lottery.core.service.DcRaceService;
import com.lottery.core.service.SystemService;
import com.lottery.ticket.phase.thread.AsyncDcPhaseRunnable;
import com.lottery.ticket.vender.process.datacenter.GetMatchResultFromDateCenter;


@Controller
@RequestMapping("/dcMatch")
public class BjdcMatchController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DcRaceDAO dcRaceDAO;
	@Autowired
	private SystemService systemService;
	@Autowired
	private DcRaceService dcRaceService;
	@Autowired
	private GetMatchResultFromDateCenter dateCenter;
	
	@RequestMapping("/dclist")
	public @ResponseBody AdminPage<DcRace> dclist(@RequestParam String condition, int start, int limit) {
		AdminPage<DcRace> page = new AdminPage<DcRace>(start, limit, " order by phase desc, matchNum desc");
		dcRaceDAO.findPageByCondition(JsonUtil.transferJson2Map(condition), page);
		return page;
	}
	
	@RequestMapping(value = "/mergeDcMatch", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(@RequestParam String body) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			DcRace dcRace = JsonUtil.fromJsonToObject(body, DcRace.class);
			dcRaceService.merge(dcRace);
		} catch (Exception e) {
			logger.error("mergeDcMatch异常",  e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 *  手动按期获取单场赛果
	 * @param phaseNo
	 * @return
	 */
	@RequestMapping(value = "/doUpdateDcResult", method = RequestMethod.POST)
	public @ResponseBody ResponseData doUpdateDcResult(@RequestParam String phaseNo) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			AsyncDcPhaseRunnable asyncDcPhaseRunnable=(AsyncDcPhaseRunnable) systemService.getCtx().getBean("dcPhaseRunnable");
			if(asyncDcPhaseRunnable==null){
				result=ErrorCode.no_exits;
			}else{
				asyncDcPhaseRunnable.match(LotteryType.DC_SPF, phaseNo);
				asyncDcPhaseRunnable.proccessResult(phaseNo,null);
			}
		} catch (Exception e) {
			logger.error("获取单场赛果异常",e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	@RequestMapping(value = "/tavoDoUpdateDcResult", method = RequestMethod.POST)
	public @ResponseBody ResponseData tavoDoUpdateDcResult(@RequestParam String phaseNo, @RequestParam Integer dcType, Integer matchNum) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			if(dcType == DcType.normal.value()){
				dateCenter.updateDcResult(phaseNo, null);
			}else if(dcType == DcType.sfgg.value()){
				if(matchNum != null && matchNum != 0){
					//一个
					dateCenter.updateDcSFGGResult(phaseNo, matchNum);
				}else{
					//多个
					dateCenter.updateCloseAndPending(phaseNo);
				}
			}
		} catch (Exception e) {
			logger.error("获取单场赛果异常",e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
