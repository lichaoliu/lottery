package com.lottery.controller;

import com.lottery.core.handler.IdGeneratorHandler;
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
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryUploadFileService;
import com.lottery.core.service.bet.BetService;

import java.math.BigDecimal;

/**
 * 普通投注
 * @author fengqinyu
 *
 */
@Controller
@RequestMapping("bet")
public class BetController {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private BetService betService;
	@Autowired
	private IdGeneratorService idService;
	@Autowired
	private LotteryUploadFileService fileService;

	@Autowired
	private IdGeneratorHandler idGeneratorHandler;
	/**
	 * 投注接口
	 * @param userno 用户编号
	 * @param betcode 投注内容
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param multiple 倍数
	 * @param amount 总金额:单位(分)
	 * @param oneAmount 单注金额,一般为200,大乐透追加为:300
	 * @param buyAgencyno 购买渠道
	 * @param prizeOptimize 1表示奖金优化订单 0表示非奖金优化订单
	 * @param codeFilter 1表示矩阵过滤订单 0表示非矩阵过滤订单
	 * @param userCouponId 优惠卷id
	 * */
	@RequestMapping(value="/toBet",method=RequestMethod.POST)
	public @ResponseBody  ResponseData toBet(@RequestParam(value="userno",required=true) String userno,
			@RequestParam(value="betcode",required=true) String betcode,
			@RequestParam(value="lotteryType",required=true) int lotteryType,
			@RequestParam(value="phase",required=true) String phase,
			@RequestParam(value="multiple",required=true) int multiple,
			@RequestParam(value="amount",required=true) long amount,
			@RequestParam(value="oneAmount",defaultValue="200") int oneAmount,
			@RequestParam(value="buyAgencyno",required=false,defaultValue="0") String buyAgencyno,
			@RequestParam(value="prizeOptimize",required=false,defaultValue="0") int prizeOptimize,
			@RequestParam(value="codeFilter",required=false,defaultValue="0") int codeFilter,
											 @RequestParam(value="userCouponId",required=false) String userCouponId
			){
		ResponseData rd=new ResponseData();
		try{
			prizeOptimize = prizeOptimize == YesNoStatus.yes.value?prizeOptimize:YesNoStatus.no.value;
			codeFilter = codeFilter == YesNoStatus.yes.value?codeFilter:YesNoStatus.no.value;
			betService.lotteryTypeValidate(lotteryType,BetType.bet.value);
            betService.codeValidate(lotteryType, phase, betcode, multiple, oneAmount, new BigDecimal(amount), prizeOptimize);
			String lotteryOrderId=idGeneratorHandler.getLotteryOrderId();
			LotteryOrder order=betService.couponBet(userno, betcode,lotteryOrderId,lotteryType, phase, multiple, new BigDecimal(amount),oneAmount,BetType.bet,buyAgencyno,prizeOptimize,codeFilter,userCouponId);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(order);
			betService.sendFreezeJms(lotteryOrderId,lotteryType);
		}catch(LotteryException e){

			logger.error("投注失败原因",e);
			logger.error("betcode={}",betcode);
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			logger.error("投注失败原因",e);
			logger.error("betcodeException={}",betcode);
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}

	/**
	 * 单式上传接口
	 * @param userno 用户编号
	 * @param betcode 投注内容
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param multiple 倍数
	 * @param amount 总金额:单位(分)
	 * @param oneAmount 单注金额,一般为200,大乐透追加为:300
	 * @param buyAgencyno 购买渠道
	 * @param prizeOptimize 1表示奖金优化订单 0表示非奖金优化订单
	 * @param codeFilter 1表示矩阵过滤订单 0表示非矩阵过滤订单
	 *@param userCouponId 优惠卷id
	 * */
	
	@RequestMapping(value="/singleUpload",method=RequestMethod.POST)
	public @ResponseBody  ResponseData singleUpload(@RequestParam(value="userno",required=true) String userno,
			@RequestParam(value="betcode",required=true) String betcode,
			@RequestParam(value="lotteryType",required=true) int lotteryType,
			@RequestParam(value="phase",required=true) String phase,
			@RequestParam(value="multiple",required=true) int multiple,
			@RequestParam(value="amount",required=true) int amount,
			@RequestParam(value="oneAmount",defaultValue="200") int oneAmount,
			@RequestParam(value="buyAgencyno",required=false,defaultValue="0") String buyAgencyno,
			@RequestParam(value="prizeOptimize",required=false,defaultValue="0") int prizeOptimize,
			@RequestParam(value="codeFilter",required=false,defaultValue="0") int codeFilter,
			@RequestParam(value="fileName",required=false,defaultValue="0") String fileName,
			@RequestParam(value="userCouponId",required=false) String userCouponId
			){
		ResponseData rd=new ResponseData();
		try{
			prizeOptimize = prizeOptimize == YesNoStatus.yes.value?prizeOptimize:YesNoStatus.no.value;
			codeFilter = codeFilter == YesNoStatus.yes.value?codeFilter:YesNoStatus.no.value;
			betService.lotteryTypeValidate(lotteryType,BetType.upload.value);
			betService.codeValidate(lotteryType,phase,betcode,multiple,oneAmount,new BigDecimal(amount),YesNoStatus.no.value);
			String lotteryOrderId=idService.getLotteryOrderId();
			fileService.save(userno, betcode, lotteryOrderId, lotteryType, phase, multiple, amount, oneAmount, fileName);
			LotteryOrder order=betService.fileUploadBet(userno, betcode, lotteryOrderId, lotteryType, phase, multiple, amount, oneAmount, buyAgencyno,prizeOptimize,codeFilter,userCouponId);
			rd.setErrorCode(ErrorCode.Success.value);
			rd.setValue(order);
			if(order!=null&&order.getId()!=null){
				betService.sendFreezeJms(order.getId(),lotteryType);
			}
		}catch(LotteryException e){
			logger.error("投注失败原因",e);
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			logger.error("投注失败原因",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
}
