package com.lottery.controller;

import com.lottery.common.contains.pay.PayStatus;
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
import com.lottery.core.domain.UserTransaction;
import com.lottery.core.service.UserTransactionService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transaction")
public class UserTransactionController {
	private static final Logger logger=LoggerFactory.getLogger(UserTransactionController.class);
/**
 * 交易记录查询
 * **/
	@Autowired
	private UserTransactionService userTransactionService;
	/**
	 * 用户交易记录查询
	 * */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public @ResponseBody ResponseData getDrawBank(@RequestParam(value="userno",required=true) 
	String userno,@RequestParam(value="startTime",required=false) String startTime,@RequestParam(value="endTime",required=false) String endTime,
	@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
	@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult){
		ResponseData rd=new ResponseData();
		try{
			PageBean<UserTransaction> page=new PageBean<UserTransaction>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			userTransactionService.getTransationList(userno, startTime, endTime,  page);
			rd.setValue(page);
			rd.setErrorCode(ErrorCode.Success.getValue());
		}catch(Exception e){
			logger.error("查询充值记录出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
    /**
     * 充值重新检查
     * */
    @RequestMapping(value="/retryCheck",method=RequestMethod.POST)
    public @ResponseBody ResponseData retryCheck(@RequestParam(value="id",required=true) String id){
        ResponseData rd=new ResponseData();
        try{
            rd.setErrorCode(ErrorCode.Success.getValue());
            UserTransaction transaction=userTransactionService.get(id);
            if(transaction==null){
                rd.setValue("充值未存在");
                return rd;
            }
            if(transaction.getStatus()== PayStatus.PAY_SUCCESS.value){
                rd.setValue("已充值成功");
            }else  if (transaction.getStatus()==PayStatus.ALREADY_PAY.value){
                rd.setValue("充值中");
            }else{
                userTransactionService.updateStatus(transaction.getId(),PayStatus.ALREADY_PAY.value);
                rd.setValue("设置成功");
            }
        }catch(Exception e){
            logger.error("重置充值查询出错",e);
            rd.setErrorCode(ErrorCode.system_error.getValue());
        }
        return rd;
    }


    /**
     * 充值结果回调
     * @param  id 充值id
     * @param  tradeno 充值渠道编号
     * @param  amount 充值金额(单位:元)
     * @param  flag 是否成功(true,false)
     * */
    @RequestMapping(value="/resultBack",method=RequestMethod.POST)
    public @ResponseBody ResponseData resultBack(@RequestParam(value="id",required=true) String id,
                                                        @RequestParam(value="tradeno",required=true) String tradeno,
                                                        @RequestParam(value="amount",required=true) BigDecimal amount,
                                                        @RequestParam(value="flag",required=true) Boolean flag, @RequestParam(value="memo",required=false,defaultValue ="" ) String memo){
        ResponseData rd=new ResponseData();
        try{

            userTransactionService.chargeResult(id,tradeno,amount,flag,memo);
        }catch(Exception e){
            logger.error("回调出错",e);
            rd.setErrorCode(ErrorCode.system_error.getValue());
        }
        return rd;
    }

}
