package com.lottery.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.contains.lottery.caselot.CaseLotDisplayState;
import com.lottery.common.contains.lottery.caselot.CaseLotState;
import com.lottery.common.dto.CaseLotRequest;
import com.lottery.common.dto.CaselotBuyDTO;
import com.lottery.common.dto.CaselotDTO;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.LotteryCaseLotBuyDao;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.handler.CaseLotHandler;
import com.lottery.core.service.LotteryCaseLotBuyService;
import com.lottery.core.service.LotteryCaseLotService;
import com.lottery.core.service.bet.BetService;
import com.lottery.retrymodel.ApiRetryTaskExecutor;
import com.lottery.ticket.caselot.AutoJoinCaseLotRetryCallback;

@RequestMapping("/caselot")
@Controller
public class CaselotController {
	private Logger logger = LoggerFactory.getLogger(CaselotController.class);
	@Autowired
	private LotteryCaseLotService caseLotService;
    @Autowired
	private LotteryCaseLotBuyService lotteryCaseLotBuyService;
    @Autowired
    private BetService betService;
    @Autowired
    private LotteryCaseLotBuyDao lotteryCaseLotBuyDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private LotteryOrderDAO lotteryOrderDAO;
	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;
	@Autowired
	protected CaseLotHandler caseLotHandler;
	@Resource(name = "apiRetryTaskExecutor")
	protected ApiRetryTaskExecutor apiReryTaskExecutor;

	/**
	 * 创建订单接口。参数名为body的OrderRequest对象类型的JSON字符串
	 */
	@RequestMapping(value = "/caselotOrder", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData caselotOrder(@RequestParam("body") String body, 
			@RequestParam(value = "buyagencyno", required=false, defaultValue="0") String buyagencyno) {
		logger.info("创建合买 caselotOrder body:{},buyagencyno:{}", body, buyagencyno);
		ResponseData rd = new ResponseData();
		CaseLotRequest caseLotRequest = null;
		try {
			caseLotRequest = JsonUtil.fromJsonToObject(body, CaseLotRequest.class);
			if (caseLotRequest == null) {
				rd.setErrorCode(ErrorCode.paramter_error.value);
				return rd;
			}
		} catch (Exception e) {
			logger.error("合买参数出错 body:"+body ,e);
			rd.setErrorCode(ErrorCode.paramter_error.value);
			return rd;
		}
		try {
			LotteryCaseLot caseLot = caseLotService.createCaseLot(caseLotRequest, buyagencyno);
			rd.setValue(caseLot);
			if(caseLot.getState() == CaseLotState.alreadyBet.value || caseLot.getState() == CaseLotState.finished.value){
				betService.sendJms(caseLot.getOrderid());
				logger.error("合买发起成功,caselotid={},orderid={}",caseLot.getId(),caseLot.getOrderid());
			}
			autoJoin(caseLot.getId());
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("用户发起合买出错,body:{},message={}, errorCode:{}", body,e.getMessage(),e.getErrorCode());
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("用户发起合买出错,body:{}", body, e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}
	
	private void autoJoin(String caselotid) {
		AutoJoinCaseLotRetryCallback joincallBack = new AutoJoinCaseLotRetryCallback();
		joincallBack.setCaseLotHandler(caseLotHandler);
		joincallBack.setRetry(1);
		joincallBack.setCaseLotId(caselotid);
		apiReryTaskExecutor.invokeAsync(joincallBack);
	}
	
	/**
	 * 参与合买
	 * @param userno 合买用户编号
	 * @param num 合买金额
	 * @param caseId 合买订单ID
	 * @param safeAmt 保底金额
	 * @return
	 */
	@RequestMapping(value = "/betCaselot", method = RequestMethod.POST)
	public @ResponseBody ResponseData bet(@RequestParam("userno") String userno, 
			@RequestParam("amt") Long num, 
			@RequestParam("caseId") String caseId,
			@RequestParam(value="safeAmt", required=false, defaultValue="0") Long safeAmt) {
		logger.info("参与合买 betCaselot userno:{},num:{},caseId:{},safeAmt:{}", new Object[]{userno, num, caseId, safeAmt});
		ResponseData rd = new ResponseData();
		try {
			caseLotService.betCaselot(caseId, userno, num, safeAmt);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("参与合买出错,userno:{},caseId:{},num:{},safeAmt:{}", new Object[]{userno,caseId,num,safeAmt}, e);
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getErrorCode().memo);
		} catch (Exception e) {
			logger.error("参与合买出错", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 合买撤单
	 * @param userno 用户编号
	 * @param caseId 合买订单ID
	 * @return
	 */
	@RequestMapping(value = "/cancelCaselot", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelCaselot(@RequestParam("userno") String userno, 
			@RequestParam("caseId") String caseId) {
		logger.error("合买撤单  userno:{}, caselotId:{}", new Object[]{userno, caseId});
		ResponseData rd = new ResponseData();
		try {
			caseLotService.cancelCaseLot(true, userno, caseId, CaseLotDisplayState.canceledByUser, CaseLotBuyState.handRetract);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getErrorCode().memo);
			logger.error("合买撤单出错", e);
		} catch (Exception e) {
			logger.error("合买撤单出错", e);
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}

	/**
	 * 合买撤资
	 * @param userno 用户编号
	 * @param caseId 合买订单ID
	 * @return
	 */
	@RequestMapping(value = "/cancelCaselotbuy", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelCaselotbuy(@RequestParam("userno") String userno, 
			@RequestParam("caseId") String caseId) {
		logger.error("合买撤资 cancelCaselotbuy userno:{},caselotId:{}", new Object[]{userno, caseId});
		ResponseData rd = new ResponseData();
		try {
			caseLotService.cancelCaseLot(userno, caseId);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("合买撤资出错, errorcode:{}", e.getErrorCode());
			rd.setErrorCode(e.getErrorCode().value);
			rd.setValue(e.getErrorCode().memo);
		} catch (Exception e) {
			logger.error("合买撤资出错", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.system_error.value);
		}
		return rd;
	}

	/**
	 * 合买查询分页列表
	 * @param state 合买状态数组。1：新发起，2：已投注，3：完成，4：取消
	 * @param lottype 彩种编号数组
	 * @param search 用户昵称或合买编号的模糊查询字段
	 * @param condition 查询条件json字符串
	 * @param startLine 开始记录
	 * @param endLine 获取记录数
	 * @param orderBy 排序字段
	 * @param orderDir 排序方式
	 * @return Page<CaselotDTO>
	 */
	@RequestMapping(value = "/selectCaseLotsByPage")
	public @ResponseBody ResponseData selectCaseLotsByPage(@RequestParam(value = "state", required = false) Integer[] state,
			@RequestParam(value = "lottype", required = false) Integer[] lottype,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir,
			@RequestParam(value = "needUser", required = false, defaultValue = "true") Boolean needUser) {
		ResponseData rd = new ResponseData();
		PageBean<LotteryCaseLot> page = new PageBean<LotteryCaseLot>(startLine, endLine, orderBy, orderDir);
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			lotteryCaseLotDao.findCaseLotsByPage(state, lottype, search, conditionMap, page);
			List<LotteryCaseLot> caseLots = page.getList();
			List<CaselotDTO> caselotDTOs = new ArrayList<CaselotDTO>();
			for (LotteryCaseLot caseLot : caseLots) {
				CaselotDTO caselotDTO = new CaselotDTO();
				caselotDTO.setCaseLot(caseLot);
				if(needUser){
					caselotDTO.setStarter(userInfoDao.find(caseLot.getStarter()));
				}
				caselotDTOs.add(caselotDTO);
			}
			PageBean<CaselotDTO> resultPage = new PageBean<CaselotDTO>(startLine, endLine, page.getTotalResult(), orderBy, orderDir, caselotDTOs);
			rd.setValue(resultPage);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("合买查询分页列表出错, errorcode:" + e.getErrorCode().value, e);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("合买查询分页列表出错", e);
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
	/**
	 * 合买方案详细查询
	 * @param caselotid 合买方案ID
	 * @param userno 当前登录人编号，未登录可以不穿或传空值
	 * @return CaselotDTO
	 */
	@RequestMapping(value = "/selectCaseLotDetail")
	public @ResponseBody ResponseData selectCaseLotDetailById(@RequestParam("caselotid") String caselotid,
			@RequestParam(value = "userno", required = false) String userno) {
		ResponseData rd = new ResponseData();
		try {
			LotteryCaseLot caseLot = lotteryCaseLotDao.find(caselotid);
			CaselotDTO caselotDTO = new CaselotDTO();
			if (caseLot != null) {
				caselotDTO.setCaseLot(caseLot);
				caselotDTO.setStarter(userInfoDao.find(caseLot.getStarter()));
				caselotDTO.setOrder(lotteryOrderDAO.find(caseLot.getOrderid()));
				boolean flag =	caseLotService.judgeVisibility(userno, caseLot);//是否显示方案内容
				caselotDTO.setDisPlayContent(flag);
				rd.setValue(caselotDTO);
			}
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("合买详细查询出错", e);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("合买详细查询出错", e);
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
	/**
	 * 查询用户参与的合买
	 * @param state 合买状态数组。1：新发起，2：已投注，3：完成，4：取消
	 * @param loginuserno  当前登录人编号，未登录可以不穿或传空值
	 * @param userno （必须有的），查询的用户编号
	 * @param buyType  参与类型。null:全部，0:参与合买，1，发起合买
	 * @param condition 查询条件json字符串
	 * @param startLine  开始记录
	 * @param endLine  获取记录数
	 * @param orderBy  排序字段
	 * @param orderDir 排序方式
	 * @return Page<CaselotDTO>
	 */
	@RequestMapping(value = "/selectCaseLotsByUserno")
	public @ResponseBody ResponseData selectCaseLotsByUserno(
			@RequestParam(value = "state", required = false) Integer[] state,
			@RequestParam(value = "userno") String userno,
			@RequestParam(value = "buyType", required = false) Integer buyType,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir) {
		ResponseData rd = new ResponseData();
		PageBean<LotteryCaseLot> page = new PageBean<LotteryCaseLot>(startLine, endLine, orderBy, orderDir);
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			lotteryCaseLotDao.findCaseLotsByUserno(state, userno, buyType, conditionMap, page);
			rd.setValue(page);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("查询用户参与的合买出错, errorcode:{}", e.getErrorCode());
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("查询用户参与的合买出错", e);
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
	
	/**
	 * 查询合买用户参与列表，只包含参与人信息和参与金额等情况。
	 * @param caselotid  合买方案ID
	 * @param condition查询条件json字符串
	 * @param startLine开始记录
	 * @param endLine 获取记录数
	 * @param orderBy排序字段
	 * @param orderDir排序方式
	 * @return Page<CaselotBuyDTO>
	 * @return
	 */
	@RequestMapping(value = "/selectCaseLotBuys")
	public @ResponseBody ResponseData selectCaseLotBuys(@RequestParam(value = "userno", required = false) String userno,
			@RequestParam(value = "caselotid", required = false) String caselotid,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir,
			@RequestParam(value = "needCaseLot", required = false, defaultValue = "false") Boolean needCaseLot,
			@RequestParam(value = "needOrder", required = false, defaultValue = "false") Boolean needOrder,
			@RequestParam(value = "needUser", required = false, defaultValue = "true") Boolean needUser) {
		ResponseData rd = new ResponseData();
		PageBean<LotteryCaseLotBuy> page = new PageBean<LotteryCaseLotBuy>(startLine, endLine, orderBy, orderDir);
		List<CaselotBuyDTO> caseLotBuyDTOList = new ArrayList<CaselotBuyDTO>();
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			lotteryCaseLotBuyDao.findCaseLotBuys(userno, caselotid, conditionMap, page);
			List<LotteryCaseLotBuy> caseLotBuylist = page.getList();
			for (LotteryCaseLotBuy caseLotBuy : caseLotBuylist) {
				CaselotBuyDTO caselotBuyDTO = new CaselotBuyDTO();
				caselotBuyDTO.setCaseLotBuy(caseLotBuy);
				if(needUser){
					caselotBuyDTO.setUserinfo(userInfoDao.find(caseLotBuy.getUserno()));
				}
				
				LotteryCaseLot lotteryCaseLot=null;
				if(needCaseLot){
					lotteryCaseLot = lotteryCaseLotDao.find(caseLotBuy.getCaselotId());
					caselotBuyDTO.setCaseLot(lotteryCaseLot);
				}
				if(needOrder){
					if(lotteryCaseLot == null){
						lotteryCaseLot = lotteryCaseLotDao.find(caseLotBuy.getCaselotId());
					}
					caselotBuyDTO.setOrder(lotteryOrderDAO.find(lotteryCaseLot.getOrderid()));
				}
				caseLotBuyDTOList.add(caselotBuyDTO);
			}
			PageBean<CaselotBuyDTO> resultPage = new PageBean<CaselotBuyDTO>(startLine, endLine, page.getTotalResult(), orderBy, orderDir, caseLotBuyDTOList);
			rd.setValue(resultPage);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("查询用户或方案的合买详细出错", e);
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
	/**
	 * 更加用户查询合买详情
	 * */
	@RequestMapping(value = "/getCaseLotBuy",method=RequestMethod.POST)
	public @ResponseBody ResponseData getCaseLotBuy(
			@RequestParam(value = "userno", required = true,defaultValue = "") String userno,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "maxLine", required=false, defaultValue="15") int maxResult,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime){
		ResponseData rd=new ResponseData();
		PageBean<LotteryCaseLotBuy> page=new PageBean<LotteryCaseLotBuy>();
		page.setPageIndex(startLine);
		page.setMaxResult(maxResult);
		if(StringUtil.isEmpty(endTime)){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 1);
			endTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATE);
		}
		if(StringUtil.isEmpty(startTime)){
			Date end=CoreDateUtils.parseDate(endTime,CoreDateUtils.DATE);
			Calendar c=Calendar.getInstance();
			c.setTime(end);
			c.add(Calendar.MONTH, -3);
			startTime=CoreDateUtils.formatDate(c.getTime(), CoreDateUtils.DATE);
		}
		if((CoreDateUtils.parseDate(startTime, CoreDateUtils.DATE)).after(CoreDateUtils.parseDate(endTime, CoreDateUtils.DATE))){
			rd.setErrorCode(ErrorCode.select_date_error.getValue());
			return rd;
		}
		List<LotteryCaseLotBuy> list=lotteryCaseLotBuyService.getByUserno(userno, startTime, endTime, page);
		if(list==null){
			rd.setErrorCode(ErrorCode.no_exits.getValue());
		}else{
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(page);
		}
		return rd;
	}
	@RequestMapping(value = "/canelCaselotBuy",method=RequestMethod.POST)
	public @ResponseBody ResponseData canelCaselotBuy(@RequestParam("caselotbuyid") String caselotbuyid) {
		ResponseData rd = new ResponseData();
		try {
			caseLotService.canleCaseLotBuy(caselotbuyid);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("单个合买撤销出错", e);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("单个合买撤销出错", e);
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
}
