package com.lottery.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.dao.BettingLimitNumberDao;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.core.service.bet.BettingLimitNumberService;

@Controller
@RequestMapping("bettingLimitNumber")
public class BettingLimitNumberController {

	@Autowired
	private BettingLimitNumberService bettingLimitNumberService;
	
	@RequestMapping("page")
	public @ResponseBody AdminPage<BettingLimitNumber> list(int start, int limit, int lotteryType){
		return bettingLimitNumberService.pageForAdmin(start, limit, lotteryType);
	}
	
	@RequestMapping("save")
	public @ResponseBody ResponseData save(Integer limitType, Integer lotteryType, Integer playType, Integer status, String content){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			BettingLimitNumber bln = new BettingLimitNumber();
			bln.setLimitType(limitType);
			bln.setLotteryType(lotteryType);
			bln.setPlayType(playType);
			bln.setStatus(status);
			bln.setContent(content);
			bln.setCreateTime(new Date());
			bln.setUpdateTime(new Date());
			bettingLimitNumberService.save(bln);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping("update")
	public @ResponseBody ResponseData update(Long id, Integer limitType, Integer lotteryType, Integer playType, Integer status, String content){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			bettingLimitNumberService.update(id, limitType, lotteryType, playType, status, content);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
	
	@RequestMapping("delete")
	public @ResponseBody ResponseData delete(String ids){
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.value);
		try {
			bettingLimitNumberService.delete(ids);
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.Faile.value);
			rd.setValue(e.getMessage());
		}
		return rd;
	}
}
