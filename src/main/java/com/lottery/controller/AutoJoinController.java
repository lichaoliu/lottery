package com.lottery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.lottery.common.dto.AutoJoinDTO;
import com.lottery.common.dto.AutoJoinDetailDTO;
import com.lottery.common.dto.AutoJoinUserDTO;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.AutoJoinDao;
import com.lottery.core.dao.AutoJoinDetailDao;
import com.lottery.core.dao.LotteryCaseLotBuyDao;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.AutoJoinDetail;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.UserAchievement;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.service.AutoJoinService;
import com.lottery.core.service.UserAchievementService;
import com.lottery.core.service.UserInfoService;

/**
 * 自动跟单
 */
@RequestMapping("/autojoin")
@Controller
public class AutoJoinController {
	private Logger logger = LoggerFactory.getLogger(AutoJoinController.class);
	@Autowired
	private AutoJoinService autoJoinService;
	@Autowired
	private AutoJoinDao autoJoinDao;
	@Autowired
	private AutoJoinDetailDao autoJoinDetailDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LotteryCaseLotDao caseLotDao;
	@Autowired
	private LotteryCaseLotBuyDao caseLotBuyDao;
	@Autowired
	private UserAchievementService userAchievementService;
	
	/**
	 * 创建自动跟单
	 * @param userno 用户编号
	 * @param lottype 彩种
	 * @param starter 要定制的用户编号
	 * @param times  跟单次数
	 * @param joinAmt  跟单金额
	 * @param forceJoin 是否强制跟单 1：强制跟单，0：不强制跟单.当合买剩余金额小于跟单金额时，是否继续购买。
	 * @return AutoJoin
	 */
	@RequestMapping(value = "/createAutoJoin", method = RequestMethod.POST)
	public @ResponseBody ResponseData createAutoJoin(@RequestParam(value = "userno") String userno,
			@RequestParam(value = "lottype") Integer lotterType, 
			@RequestParam(value = "starter") String starter,
			@RequestParam(value = "times") Integer times,
			@RequestParam(value = "joinAmt") BigDecimal joinAmt,
			@RequestParam(value = "forceJoin") Integer forceJoin) {
		ResponseData rd = new ResponseData();
		try {
			AutoJoin createAutoJoin = autoJoinService.createAutoJoin(userno, lotterType, starter, times, joinAmt, forceJoin);
			rd.setValue(createAutoJoin);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("创建自动跟单异常 errorcode:{}", e.getErrorCode());
			rd.setValue(e.getErrorCode().memo);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("创建自动跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 更新自动跟单 当更改参与类型，参与金额，重置跟单时间（即排序将靠后）
	 * @param id 跟单ID
	 * @param joinAmt 跟单金额
	 * @param forceJoin 是否强制跟单 1：强制跟单，0：不强制跟单.当合买剩余金额小于跟单金额时，是否继续购买。
	 * @return AutoJoin
	 */
	@RequestMapping(value = "/updateAutoJoin", method = RequestMethod.POST)
	public @ResponseBody ResponseData updateAutoJoin(@RequestParam(value = "id") Long id,
			@RequestParam(value = "joinAmt") BigDecimal joinAmt,
			@RequestParam(value = "forceJoin") Integer forceJoin,
			@RequestParam(value = "userno",required=false) String userno) {
		ResponseData rd = new ResponseData();
		try {
			AutoJoin autoJoin = autoJoinService.updateAutoJoin(id, joinAmt, forceJoin, userno);
			rd.setValue(autoJoin);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("更新自动跟单异常 errorcode:{}", e.getErrorCode());
			rd.setValue(e.getErrorCode().memo);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("更新自动跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 取消自动跟单
	 * @param id 跟单ID
	 * @return AutoJoin
	 */
	@RequestMapping(value = "/cancelAutoJoin", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData cancelAutoJoin(@RequestParam(value = "id") Long id,
			@RequestParam(value = "userno", required=false) String userno) {
		ResponseData rd = new ResponseData();
		try {
			AutoJoin autoJoin = autoJoinService.cancel(id, userno);
			rd.setValue(autoJoin);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("取消自动跟单异常 errorcode:{}", e.getErrorCode());
			rd.setValue(e.getErrorCode().memo);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("取消自动跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
	
	/**
	 * 取消自动跟单
	 * @param id 跟单ID
	 * @return AutoJoin
	 */
	@RequestMapping(value = "cancelAutoJoinByStarter", method = RequestMethod.POST)
	public @ResponseBody ResponseData cancelAutoJoinByStarter(@RequestParam Long id, @RequestParam String starter) {
		ResponseData rd = new ResponseData();
		try {
			AutoJoin autoJoin = autoJoinService.cancelByStarter(id, starter);
			rd.setValue(autoJoin);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (LotteryException e) {
			logger.error("取消自动跟单异常 errorcode:{}", e.getErrorCode());
			rd.setValue(e.getErrorCode().memo);
			rd.setErrorCode(e.getErrorCode().value);
		} catch (Exception e) {
			logger.error("取消自动跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 查询定制跟单带分页
	 * @param condition  查询条件json串
	 * @param startLine 开始行数
	 * @param endLine 每页显示行数
	 * @param orderBy 排序字段
	 * @param orderDir 排序规则
	 * @return Page
	 */
	@RequestMapping(value = "/selectAutoJoin")
	public @ResponseBody ResponseData selectAutoJoin(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "lottype", required = false) Integer[] lotteryType,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "20") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir,
			@RequestParam(value = "needUser", required = false, defaultValue = "true") Boolean needUser) {
		ResponseData rd = new ResponseData();
		PageBean<AutoJoin> page = new PageBean<AutoJoin>(startLine, endLine, orderBy, orderDir);
		List<AutoJoinDTO> resultList = new ArrayList<AutoJoinDTO>();
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			autoJoinDao.findAutoJoinByPage(lotteryType, conditionMap, page);
			List<AutoJoin> list = page.getList();
			for (AutoJoin autoJoin : list) {
				AutoJoinDTO dto = new AutoJoinDTO();
				dto.setAutoJoin(autoJoin);
				if(needUser){
					dto.setUser(userInfoService.get(autoJoin.getUserno()));
				}
				dto.setStarter(userInfoService.get(autoJoin.getStarter()));
				UserAchievement userAchievement = userAchievementService.findIfNotExistCreate(autoJoin.getStarter(), autoJoin.getLotteryType());
				dto.setUserachievement(userAchievement);
				resultList.add(dto);
			}
			PageBean<AutoJoinDTO> resultPage = new PageBean<AutoJoinDTO>(startLine, endLine, page.getTotalResult(), orderBy, orderDir, resultList);
			rd.setValue(resultPage);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("查询自动跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 查询定制跟单参与情况带分页
	 * @param condition  查询条件json串
	 * @param startLine 开始行数
	 * @param endLine 每页显示行数
	 * @param orderBy 排序字段
	 * @param orderDir 排序规则
	 * @return Page
	 */
	@RequestMapping(value = "/selectAutoJoinDetail")
	public @ResponseBody ResponseData selectAutoJoinDetail(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "20") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir) {
		ResponseData rd = new ResponseData();
		PageBean<AutoJoinDetail> page = new PageBean<AutoJoinDetail>(startLine, endLine, orderBy, orderDir);
		List<AutoJoinDetailDTO> resultList = new ArrayList<AutoJoinDetailDTO>();
		try {
			Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
			autoJoinDetailDao.findAutoJoinDetailByPage(conditionMap, page);
			List<AutoJoinDetail> list = page.getList();
			for (AutoJoinDetail autoJoinDetail : list) {
				AutoJoinDetailDTO dto = new AutoJoinDetailDTO();
				dto.setAutoJoinDetail(autoJoinDetail);
				LotteryCaseLot caselot = caseLotDao.find(autoJoinDetail.getCaselotId());
				dto.setCaseLot(caselot);
				if(caselot != null){
					dto.setStarter(userInfoService.get(caselot.getStarter()));
				}
				if(!StringUtil.isEmpty(autoJoinDetail.getCaseLotBuyId())){
					dto.setCaseLotBuy(caseLotBuyDao.find(autoJoinDetail.getCaseLotBuyId()));
				}
				resultList.add(dto);
			}
			PageBean<AutoJoinDetailDTO> resultPage = new PageBean<AutoJoinDetailDTO>(startLine, endLine, page.getTotalResult(), orderBy, orderDir, resultList);
			rd.setValue(resultPage);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("查询自动跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 查询可定制人
	 * @param username 用户名 传用户名只按用户查询
	 * @param lottype 彩种编号 不传没用户战绩
	 * @return Page
	 */
	@RequestMapping(value = "/selectAutoJoinUser")
	public @ResponseBody ResponseData selectAutoJoinUser(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "lottype", required = false) Integer lotteryType,
			@RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "20") int endLine,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderDir", required = false) String orderDir) {
		ResponseData rd = new ResponseData();
		PageBean<AutoJoinUserDTO> page = new PageBean<AutoJoinUserDTO>(startLine, endLine, orderBy, orderDir);
		try {
			if(StringUtil.isEmpty(username)){
				autoJoinDao.findAutoJoinUser(lotteryType, page);
				List<AutoJoinUserDTO> list = page.getList();
				for (AutoJoinUserDTO aju : list) {
					aju.setStarter(userInfoService.get(aju.getStarterUserno()));
					if(lotteryType != null){
						aju.setUserachievement(userAchievementService.findIfNotExistCreate(aju.getStarterUserno(), lotteryType));
					}
				}
			}else{
				UserInfo starter =userInfoService.getByUserName(username);
				if(starter != null){
					AutoJoinUserDTO aju = new AutoJoinUserDTO();
					int i = autoJoinDao.findCountByStarterAndLotteryType(starter.getUserno(), lotteryType);
					aju.setCount(i+"");
					aju.setStarterUserno(starter.getUserno());
					aju.setStarter(starter);
					if(lotteryType != null){
						aju.setUserachievement(userAchievementService.findIfNotExistCreate(aju.getStarterUserno(), lotteryType));
					}
					List<AutoJoinUserDTO> list = new ArrayList<AutoJoinUserDTO>();
					list.add(aju);
					page.setList(list);
				}
			}
			rd.setValue(page);
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("查询可定制人异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}

	/**
	 * 查询定制某用户定制跟单的有效参与人数
	 * @param userno 要查询的用户编号
	 * @param lottype 彩种编号
	 * @return Page
	 */
	@RequestMapping(value = "/selectCountByStarter")
	public @ResponseBody ResponseData selectCountByStarter(@RequestParam(value = "userno") String userno,
			@RequestParam(value = "lottype", required = false) Integer lotteryType) {
		ResponseData rd = new ResponseData();
		try {
			rd.setValue(autoJoinDao.findCountByStarterAndLotteryType(userno, lotteryType));
			rd.setErrorCode(ErrorCode.Success.value);
		} catch (Exception e) {
			logger.error("查询跟单异常", e);
			rd.setValue(e.getMessage());
			rd.setErrorCode(ErrorCode.Faile.value);
		}
		return rd;
	}
}
