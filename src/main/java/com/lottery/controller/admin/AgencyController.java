package com.lottery.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.core.dao.LotteryAgencyDao;
import com.lottery.core.dao.LotteryAgencyPointLocationDao;
import com.lottery.core.domain.agency.LotteryAgency;
import com.lottery.core.domain.agency.LotteryAgencyPointLocation;
import com.lottery.core.service.LotteryAgencyService;

@Controller
@RequestMapping("/agency")
public class AgencyController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryAgencyService agencyService;
	@Autowired
	private LotteryAgencyDao agencyDao;
	@Autowired
	private LotteryAgencyPointLocationDao agencyPointLocationDao;
	/**
	 * 查询代理用户树
	 */
	@RequestMapping(value = "/findAgencyTree")
	public @ResponseBody List<LotteryAgency> findAgencyTree(@RequestParam String parentAgency) {
		try {
			return agencyDao.findAgencyByParent(parentAgency);
		} catch (Exception e) {
			logger.error("查询代理用户异常", e);
		}
		return new ArrayList<LotteryAgency>();
	}

	@RequestMapping("/getAll")
	public @ResponseBody List<LotteryAgency> getAll(){
		try {
			return agencyService.getAll();
		} catch (Exception e) {
			logger.error("渠道查询异常",  e);
		}
		return null;
	}
	
	/**
	 * 创建代理用户
	 * @param agencyNo 用户编号
	 * @param parentAgency 上级代理用户编号
	 */
	@RequestMapping(value = "/createAgency", method = RequestMethod.POST)
	public @ResponseBody ResponseData createAgency(@RequestParam(value = "agencyNo") String agencyNo,
			@RequestParam(value = "agencyName") String agencyName,
			@RequestParam(value = "parentAgency") String parentAgency,
			@RequestParam(value = "agencyType") Integer agencyType) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			agencyService.createAgency(agencyNo, agencyName, parentAgency, agencyType);
		} catch (Exception e) {
			logger.error("创建代理用户异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	/**
	 * 删除代理
	 * @param agencyNo
	 * @return
	 */
	@RequestMapping(value = "/deleteAgency", method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteAgency(@RequestParam(value = "agencyNo") String agencyNo) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			agencyService.deleteAgency(agencyNo);
		} catch (Exception e) {
			logger.error("创建代理用户异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	
	/**
	 * 找到某用户的代理点位
	 * @param agencyNo 用户编号
	 */
	@RequestMapping(value = "/findAgencyPoints")
	public @ResponseBody List<LotteryAgencyPointLocation> findAgencyPoints(@RequestParam String agencyNo) {
		try {
			return agencyPointLocationDao.findAgencyPointsByAgencyNo(agencyNo);
		}  catch (Exception e) {
			logger.error("查询用户代理比率异常", e);
		}
		return new ArrayList<LotteryAgencyPointLocation>();
	}
	
	/**
	 * 创建用户代理点位
	 */
	@RequestMapping(value = "/createAgencyPoint", method = RequestMethod.POST)
	public @ResponseBody ResponseData createAgencyPoint(@RequestParam(value = "agencyNo") String agencyNo,
			@RequestParam(value = "lotteryType") Integer lotteryType,
			@RequestParam(value = "pointLocation") BigDecimal pointLocation) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			rd.setValue(agencyService.createAgencyPoint(agencyNo, lotteryType, pointLocation));
		}  catch (Exception e) {
			logger.error("创建代理点位异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}

	/**
	 * 修改用户代理点位
	 * @return
	 */
	@RequestMapping(value = "/updateAgencyPoint", method = RequestMethod.POST)
	public @ResponseBody ResponseData updateAgencyPoint(@RequestParam(value = "id") Long id, 
			@RequestParam(value = "parentAgencyno", required=false) String parentAgencyno,
			@RequestParam(value = "pointLocation") BigDecimal pointLocation) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			rd.setValue(agencyService.updateAgencyPoint(id, parentAgencyno, pointLocation));
		}  catch (Exception e) {
			logger.error("创建或修改用户代理点位异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
	
	
	/**
	 * 删除用户代理点位
	 * @return
	 */
	@RequestMapping(value = "/deleteAgencyPoint", method = RequestMethod.POST)
	public @ResponseBody ResponseData deleteAgencyPoint(@RequestParam(value = "ids") String ids) {
		ResponseData rd = new ResponseData();
		ErrorCode result = ErrorCode.Success;
		try {
			agencyService.deleteAgencyPoint(ids);
		}  catch (Exception e) {
			logger.error("删除代理点位异常", e);
			result = ErrorCode.Faile;
			rd.setValue(e.getMessage());
		}
		rd.setErrorCode(result.value);
		return rd;
	}
}
