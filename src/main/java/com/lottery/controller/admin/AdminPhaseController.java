package com.lottery.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.LotteryPhaseDAO;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.SystemService;
import com.lottery.ticket.phase.thread.IPhaseHandler;

/**
 * 期信息操作
 * */
@Controller
@RequestMapping("/adminPhase")
public class AdminPhaseController {
	private final Logger logger=LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private LotteryPhaseService lotteryPhaseService;
	@Resource(name="allPhaseHandlerBinder")
	private Map<LotteryType, IPhaseHandler> allPhaseHandlerBinder;
	@Autowired 
	private LotteryPhaseDAO phaseDAO;
	@Autowired
	private SystemService systemService;
	
	@RequestMapping("/list")
	public @ResponseBody AdminPage<LotteryPhase> list(@RequestParam String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<LotteryPhase> page = new AdminPage<LotteryPhase>(startLine, endLine, " order by phase desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		phaseDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	/**
	 * 修改期信息
	 */
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public @ResponseBody ResponseData merge(Long id, Integer lotteryType, String phase, Date endTicketTime,
			Date endSaleTime,Date hemaiEndTime,Date startSaleTime,Date drawTime,Date switchTime,int phaseEncaseStatus,
			int phaseStatus,int phaseTimeStatus,int terminalStatus, int forCurrent, Integer forSale) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			if(id == null){
				LotteryPhase lp = new LotteryPhase();getClass();
				lp.setLotteryType(lotteryType);
				lp.setPhase(phase);
				lp.setEndTicketTime(endTicketTime);
				lp.setEndSaleTime(endSaleTime);
				lp.setHemaiEndTime(hemaiEndTime);
				lp.setStartSaleTime(startSaleTime);
				lp.setDrawTime(drawTime);
				lp.setSwitchTime(switchTime);
				lp.setPhaseEncaseStatus(phaseEncaseStatus);
				lp.setPhaseStatus(phaseStatus);
				lp.setPhaseTimeStatus(phaseTimeStatus);
				lp.setTerminalStatus(terminalStatus);
				lp.setForCurrent(forCurrent);
				lp.setForSale(forSale);
				lp.setCreateTime(new Date());
				lotteryPhaseService.save(lp);
			}else{
				boolean b = lotteryPhaseService.updatePhase(id, phase, endTicketTime, endSaleTime, hemaiEndTime, startSaleTime, drawTime, switchTime, phaseEncaseStatus, phaseStatus, phaseTimeStatus, terminalStatus, forCurrent, forSale);
				if(b){
					@SuppressWarnings("unchecked")
					Map<LotteryType,IPhaseHandler> phaseHandlerMap=(Map<LotteryType, IPhaseHandler>) systemService.getCtx().getBean("allPhaseHandlerBinder");
					if(phaseHandlerMap==null){
						logger.error("name=IPhaseHandler的bean不存在");
						result=ErrorCode.no_exits;
					}else{
						LotteryType lt=LotteryType.getLotteryType(lotteryType);
						IPhaseHandler container=phaseHandlerMap.get(lt);
						if(container==null){
							logger.error("彩种:{}的线程容器不存在",new Object[]{lt});
						}else{
							container.executeTaskLoad();
						}
					}
				}
			}
		}  catch (Exception e) {
			logger.error("修改期信息异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseData delete(@RequestParam String strChecked) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			lotteryPhaseService.delete(strChecked);
		} catch (Exception e) {
			logger.error("adminPhase delete异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 保存开奖号码，不进行开奖算奖
	 * 
	 * @param lotterytype
	 * @param phase
	 * @param wincode
	 * @return
	 */
	@RequestMapping(value = "/savePrizeInfo", method = RequestMethod.POST)
	public @ResponseBody ResponseData savePrize(@RequestParam Integer lottype,
			@RequestParam String phase,
			@RequestParam String saleAmount,
			@RequestParam String poolAmount,
			@RequestParam String addPoolAmount,
			@RequestParam String prizeDetail) {
		ResponseData rd = new ResponseData();
		try {
			lotteryPhaseService.saveWininfo(lottype, phase,null,prizeDetail, poolAmount, addPoolAmount, saleAmount, null);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (Exception e) {
			logger.error("",e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	/**
	 * @param lottype 彩种
	 * @param num 数量
	 * @return
	 */
	@RequestMapping(value = "/getPhaseNo")
	public @ResponseBody List<String> getPhaseNo(@RequestParam Integer lottype, @RequestParam Integer num) {
		return phaseDAO.getPhaseByLottypeAndNum(lottype, num);
	}
	
	@RequestMapping("/openPrizeList")
	public @ResponseBody List<LotteryPhase>  openPrizeList(@RequestParam Integer[] lottype,
			@RequestParam Integer num,
			@RequestParam String phase,
			@RequestParam(required=false, defaultValue="false") Boolean one){
		try {
			if(!StringUtil.isEmpty(phase)){
				Map<String, Object> whereMap = new HashMap<String, Object>();
				String whereSql = "phaseStatus not in(:phaseStatus) and phase=:phase";
				whereMap.put("phase", phase);
				List<Integer> statusList = new ArrayList<Integer>();
				statusList.add(PhaseStatus.open.getValue());
				statusList.add(PhaseStatus.unopen.getValue());
				whereMap.put("phaseStatus", statusList);
				
				if(one){
					whereSql += " and lotteryType = :lotteryType";
					whereMap.put("lotteryType", lottype[0]);
				}
				return phaseDAO.findByCondition(whereSql, whereMap);
			}else{
				List<LotteryPhase> reList = new ArrayList<LotteryPhase>();
				for (Integer lott : lottype) {
					reList.addAll(phaseDAO.getHistoryPhase(lott, num));
				}
				return reList;
			}
			
		}  catch (Exception e) {
			logger.error("lotteryPhase/openPrizeList error", e);
		}
		return null;
	}
	

	/**
	 * 关闭彩种有效期
	 * @param lotteryType 彩种
	 * **/
	@RequestMapping(value = "/closeEnablePhase")
	public @ResponseBody ResponseData closePhase(@RequestParam(required=true) int lotteryType){
		ResponseData rd = new ResponseData();
		try {
			lotteryPhaseService.closeEnablePhase(lotteryType);
			rd.setErrorCode(ErrorCode.Success.value);
		}  catch (Exception e) {
			 logger.error("关闭有效期出错", e);
			 rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	/**
	 * 重载新期
	 * @param lotteryType 彩种
	 * **/
	@RequestMapping(value = "/reloadPhase")
	public @ResponseBody ResponseData reloadPhase(@RequestParam(required=true) int lotteryType){
		ResponseData rd = new ResponseData();
		try {
			if(lotteryType== LotteryType.ALL.value){
				for (Map.Entry<LotteryType, IPhaseHandler> entry : allPhaseHandlerBinder.entrySet()) {
					   if(entry.getValue()!=null){
						   entry.getValue().executeReload();
						   logger.error("彩种{}彩期守护重载",entry.getKey());
					   }
					  }
				rd.setErrorCode(ErrorCode.Success.value);
			}else{
				LotteryType type=LotteryType.get(lotteryType);
				IPhaseHandler phaseHandler=allPhaseHandlerBinder.get(type);
				if(phaseHandler==null){
					logger.error("彩种{}彩期守护不存在",lotteryType);
					rd.setErrorCode(ErrorCode.no_exits.value);
				}else{
					phaseHandler.executeReload();
					logger.error("彩种{}彩期守护重载",type);
					rd.setErrorCode(ErrorCode.Success.value);
				}
			}

		}  catch (Exception e) {
			 logger.error("重载彩期守护", e);
			 rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
}
