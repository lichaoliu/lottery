package com.lottery.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEncaseStatus;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.PhaseTimeStatus;
import com.lottery.common.contains.lottery.TerminalStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.cache.model.LotteryPhaseCacheModel;
import com.lottery.core.cache.model.LotteryPhaseKey;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.service.LotteryPhaseService;

/**
 * 期信息操作
 * */

@Controller
@RequestMapping("/phase")
public class LotteryPhaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private LotteryPhaseService lotteryPhaseService;
    @Autowired
	private LotteryPhaseCacheModel lotteryPhaseCacheModel;
    @Autowired
    protected LotteryPhaseHandler phaseHandler;


	/**
	 * 获取彩种当前期
	 * */
	@RequestMapping(value = "/getCurrent", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getCurrent(@RequestParam(value = "lotteryType", required = true) Integer lotteryType) {
		ResponseData rd = new ResponseData();
		try {
			LotteryPhase phase = phaseHandler.getCurrent(lotteryType);
			rd.setValue(phase);
			rd.setErrorCode(ErrorCode.Success.getValue());
			if(phase==null) {
				rd.setErrorCode(ErrorCode.no_phase.getValue());
			}
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}

	/**
	 * 获取所有彩种当前期
	 * */
	@RequestMapping(value = "/getCurrentList", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getCurrentList() {
		ResponseData rd = new ResponseData();
		try {
			List<LotteryPhase> res = phaseHandler.getCurrentList();
			rd.setValue(res);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}

	/**
	 * 查询所有彩种上一期的开奖详情
	 * */
	@RequestMapping(value = "/getWinDetails")
	public @ResponseBody
	ResponseData getWinDetails() {
		ResponseData rd = new ResponseData();
		try {
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(lotteryPhaseService.getWinDetails());
		} catch (Exception e) {
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 获取开奖号码
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @return
	 */
	@RequestMapping(value = "/getPhase", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getPhase(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "phase", required = true) String phase) {
		ResponseData rd = new ResponseData();
		try {
			LotteryPhase pahPhase = lotteryPhaseCacheModel.get(new LotteryPhaseKey(lotteryType,phase));
			rd.setValue(pahPhase);
			rd.setErrorCode(ErrorCode.Success.getValue());
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}

	/**
	 * 某个彩种历史开奖记录
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param startLine
	 * @param maxResult
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/historyRecord", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData historyRecord(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "maxLine", required = false, defaultValue = "15") int maxResult) {
		ResponseData rd = new ResponseData();
		try {
			PageBean<LotteryPhase> page = new PageBean<LotteryPhase>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			List<LotteryPhase> list = lotteryPhaseService.historyRecord(lotteryType, page);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(list);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/historyRecordPage", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData historyRecordPage(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "maxLine", required = false, defaultValue = "15") int maxResult) {
		ResponseData rd = new ResponseData();
		try {
			PageBean<LotteryPhase> page = new PageBean<LotteryPhase>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			List<LotteryPhase> list = lotteryPhaseService.historyRecord(lotteryType, page);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	
	
	@RequestMapping(value = "/historyRecordWithClosedPage", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData historyRecordWithClosedPage(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "maxLine", required = false, defaultValue = "15") int maxResult) {
		ResponseData rd = new ResponseData();
		try {
			PageBean<LotteryPhase> page = new PageBean<LotteryPhase>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			lotteryPhaseService.historyRecordWithClosed(lotteryType, page);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 查询某彩种上次开奖记录
	 * 
	 * @param lotteryType
	 *            彩种
	 * @return
	 */
	@RequestMapping(value = "/getLastPhase", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData historyRecord(@RequestParam(value = "lotteryType", required = true) int lotteryType) {
		ResponseData rd = new ResponseData();
		try {
			LotteryPhase phase = lotteryPhaseService.getLastRecord(lotteryType);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(phase);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 获取单个彩种开奖详情
	 * 
	 * @param lottype
	 *            彩种
	 * @param num
	 *            期数
	 * */
	@RequestMapping(value = "/getWinfolist")
	public @ResponseBody
	ResponseData selectTwininfolist(@RequestParam("lottype") Integer lottype, @RequestParam(value = "num", required = false, defaultValue = "5") int num) {
		ResponseData rd = new ResponseData();
		try {
			List<LotteryPhase> list = lotteryPhaseService.getWinlist(lottype, num);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(list);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 获取多个彩种开奖详情
	 * 
	 * @param lottyp 彩种集合
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "/getAllWinfolist")
	public @ResponseBody
	ResponseData getAllWinfolist(@RequestParam("lottype") String lottyp, @RequestParam(value = "num", required = false, defaultValue = "1") int num) {
		ResponseData rd = new ResponseData();
		try {
			Map<Integer, List<LotteryPhase>> map = new HashMap<Integer, List<LotteryPhase>>();
			String[] lottypes = lottyp.split(",");
			if (null != lottypes && lottypes.length > 0) {
				for (String lottype : lottypes) {
					int lott = Integer.parseInt(lottype);
					List<LotteryPhase> list = lotteryPhaseService.getWinlist(lott, num);
					map.put(lott, list);
				}
			}
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(map);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("getAllWinfolist error", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 获取足彩可用期号，足彩可售期有多个
	 * **/
	@RequestMapping(value = "/getZcPhase")
	public @ResponseBody
	ResponseData getZcPhase(@RequestParam(value = "lotteryType", required = true) int lotteryType) {
		ResponseData rd = new ResponseData();
		try {
			List<LotteryPhase> phaseList = lotteryPhaseService.getByLotteryTypeAndStatus(lotteryType, PhaseStatus.open.getValue());
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(phaseList);
		} catch (Exception e) {
			logger.error("获取足彩新期错误", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	/**
	 * 获取当前期除了未开售的所有期
	 * @param lotteryType 彩种
	 * @param num 查多少期
	 * **/
	@RequestMapping(value = "/getPhaseList")
	
	public @ResponseBody
	ResponseData getPhaseList(@RequestParam(value = "lotteryType", required = true) int lotteryType
			, @RequestParam(value = "num", required = false, defaultValue = "1") int num) {
		ResponseData rd = new ResponseData();
		try {
			List<LotteryPhase> phaseList = lotteryPhaseService.getPhaseList(lotteryType, num);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(phaseList);
		} catch (Exception e) {
			logger.error("获取除未开售的期错误", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 查询历史期次
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param num
	 *            获取多少期
	 * **/
	@RequestMapping(value = "/getHistoryPhase")
	public @ResponseBody
	ResponseData getHistoryPhase(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "num", required = true) int num) {
		ResponseData rd = new ResponseData();
		try {
			List<LotteryPhase> phaseList = lotteryPhaseService.getHistoryPhase(lotteryType, num);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(phaseList);
		} catch (Exception e) {
			logger.error("获取历史期错误", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 保存新期
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param startDate
	 *            开始销售时间 yyyy-mm-dd hh:mm:ss
	 * @param endDate
	 *            结束销售时间 yyyy-mm-dd hh:mm:ss
	 * **/
	@RequestMapping(value = "/insertPhase")
	public @ResponseBody
	ResponseData insertPhase(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "phase", required = true) String phase,
			@RequestParam(value = "startDate", required = true) Date startDate, @RequestParam(value = "endDate", required = true) Date endDate) {
		ResponseData rd = new ResponseData();
		try {

			LotteryPhase lotteryPhase = lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
			if (lotteryPhase == null) {
				lotteryPhase = new LotteryPhase();
				lotteryPhase.setLotteryType(lotteryType);
				lotteryPhase.setPhase(phase);
				lotteryPhase.setCreateTime(new Date());
				lotteryPhase.setStartSaleTime(startDate);
				lotteryPhase.setEndSaleTime(endDate);
				lotteryPhase.setEndTicketTime(endDate);
				lotteryPhase.setHemaiEndTime(endDate);
				lotteryPhase.setDrawTime(endDate);
				lotteryPhase.setPhaseStatus(PhaseStatus.open.getValue());
				lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
				lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
				lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
				lotteryPhase.setForSale(YesNoStatus.yes.getValue());
				lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
				lotteryPhaseService.insert(lotteryPhase);
				rd.setErrorCode(ErrorCode.Success.value);
				rd.setValue(lotteryPhase);
			} else {
				if (lotteryPhase.getEndSaleTime().getTime() - endDate.getTime() != 0) {
					lotteryPhase.setEndSaleTime(endDate);
					lotteryPhase.setEndTicketTime(endDate);
					lotteryPhase.setHemaiEndTime(endDate);
					lotteryPhase.setDrawTime(endDate);
					lotteryPhaseService.update(lotteryPhase);
				}
				rd.setErrorCode(ErrorCode.exits.value);
			}
			rd.setValue(lotteryPhase);
		} catch (Exception e) {
			logger.error("存入新彩种出错，彩种:{},期号：{},开始时间:{}，结束时间：{}",new Object[]{lotteryType,phase,startDate,endDate});
			logger.error("保存新期出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 中奖修改
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param windcode
	 *            开奖号码,足彩 1,3,0 中间用逗号分割,未开奖的用*号替代
	 * @param prizedetail
	 *            开奖详情,奖级_中奖个数_中奖金额（单位：元）,用逗号分割;
	 * @param poolAmount
	 *            奖池
	 * @param saleAmount
	 *            销量
	 * @param addPoolAmount
	 *            加奖奖池
	 * **/
	@RequestMapping(value = "/updateWinPhase")
	public @ResponseBody
	ResponseData updateWinPhase(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "phase", required = true) String phase,
			@RequestParam(value = "prizedetail", required = false) String prizedetail, @RequestParam(value = "windcode", required = true) String windcode,
			@RequestParam(value = "poolAmount", required = false, defaultValue = "0") String poolAmount, @RequestParam(value = "saleAmount", required = false, defaultValue = "0") String saleAmount,
			@RequestParam(value = "addPoolAmount", required = false, defaultValue = "0") String addPoolAmount) {
		ResponseData rd = new ResponseData();
		try {


			LotteryPhase lotteryPhase = lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
			if (lotteryPhase == null) {
				rd.setErrorCode(ErrorCode.no_exits.value);
			} else {
				boolean flag = false;
				lotteryPhase.setDrawDataFrom("自动抓取");
				if (windcode!=null&&!windcode.equals(lotteryPhase.getWincode())) {
					if (lotteryPhase.getPhaseStatus() == PhaseStatus.prize_end.getValue() || lotteryPhase.getPhaseStatus() == PhaseStatus.prize_start.getValue()
							|| lotteryPhase.getPhaseStatus() == PhaseStatus.prize_open.getValue()) {
						logger.error("彩种{}期号{}算奖状态为{}，请检查", new Object[] { lotteryType, phase, PhaseStatus.getPhaseStatus(lotteryPhase.getPhaseStatus()) });
					} else {
						if (LotteryType.getZc().contains(LotteryType.getLotteryType(lotteryType))) {
							if (lotteryPhase.getPhaseStatus() == PhaseStatus.close.getValue()) {
								lotteryPhase.setPhaseStatus(PhaseStatus.prize_ing.getValue());
							}
							if (lotteryPhase.getPhaseStatus() == PhaseStatus.prize_ing.getValue()) {
								lotteryPhase.setPhaseStatus(PhaseStatus.prize_ing.getValue());
								lotteryPhase.setWincode(windcode);
								flag = true;
							}
						} else {
						
						}
					}
				}
				if (prizedetail != null && !prizedetail.equals(lotteryPhase.getPrizeDetail())) {
					lotteryPhase.setPrizeDetail(prizedetail);
					flag = true;
				}
				if (!poolAmount.equals(lotteryPhase.getPoolAmount())) {
					lotteryPhase.setPoolAmount(poolAmount);
					flag = true;
				}
				if (!saleAmount.equals(lotteryPhase.getSaleAmount())) {
					lotteryPhase.setSaleAmount(saleAmount);
					flag = true;
				}
				if (!addPoolAmount.equals(lotteryPhase.getAddPoolAmount())) {
					lotteryPhase.setAddPoolAmount(addPoolAmount);
					flag = true;
				}

				if (flag) {
					lotteryPhase.setDrawTime(new Date());
					lotteryPhaseService.update(lotteryPhase);
				}
				rd.setErrorCode(ErrorCode.Success.value);
				rd.setValue(lotteryPhase);

			}
			rd.setValue(lotteryPhase);
		} catch (Exception e) {
			logger.error("中奖修改出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	/**
	 * 获取预开期
	 * @param lotteryType  彩种
	 * @param phase        当前期
	 * @param num          查询数量
	 * @return
	 */
	@RequestMapping(value = "/getNextPhases")
	public @ResponseBody
	ResponseData getNextPhases(@RequestParam(value = "lotteryType", required = true) int lotteryType,
			@RequestParam(value = "phase", required = false) String phase,
			@RequestParam(value = "num", required = true) int num) {
		ResponseData rd = new ResponseData();
		try {
			String currPhase = null;
			if(phase == null){
				LotteryPhase currentPhase = lotteryPhaseService.getCurrent(lotteryType); 
				currPhase = currentPhase.getPhase();
			}else{
				currPhase = phase;
			}
			List<LotteryPhase> lotteryPhases = lotteryPhaseService.getNextPhaseWithCurrent(lotteryType, currPhase, num);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(lotteryPhases);
		} catch (Exception e) {
			logger.error("获取预开期出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}



	/**
	 * 期往后顺延
	 * @param lotteryType  彩种
	 * @param seconds        后延时间(单位:秒)
	 * @param number        期数
	 * @return
	 */
	@RequestMapping(value = "/updatePhaseTime")
	public @ResponseBody
	ResponseData updatePhaseTime(@RequestParam(value = "lotteryType", required = true) int lotteryType,
							   @RequestParam(value = "seconds") int  seconds,
							   @RequestParam(value = "number", required = false,defaultValue = "100") int number) {
		ResponseData rd = new ResponseData();
		try {
			phaseHandler.updatePhaseTime(lotteryType,seconds,number);
			rd.setErrorCode(ErrorCode.Success.value);

		} catch (Exception e) {
			logger.error("获取预开期出错", e);
			rd.setErrorCode(ErrorCode.system_error.value);
			rd.setValue(e.getMessage());
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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));// true:允许输入空值，false:不能为空值
	}
}
