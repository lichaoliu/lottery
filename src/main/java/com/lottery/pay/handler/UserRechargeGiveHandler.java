package com.lottery.pay.handler;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.pay.RechargeGiveType;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.give.UserRechargeGive;
import com.lottery.core.domain.give.UserRechargeGiveDetail;
import com.lottery.core.domain.give.UserRechargeGiveDetailPK;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.give.UserRechargeGiveDetailSerivce;
import com.lottery.core.service.give.UserRechargeGiveSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fengqinyun on 14-3-19.
 * 充值赠送管理
 */
@Service
public class UserRechargeGiveHandler {
	
	private final Logger logger=LoggerFactory.getLogger(getClass().getName());
    @Autowired
    private UserRechargeGiveSerivce userRechargeGiveSerivce;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserRechargeGiveDetailSerivce userRechargeGiveDetailSerivce;
    @Autowired
    private UserInfoService userInfoService;
    public UserRechargeGive  get(String userno,BigDecimal amount){
    	try{
    		amount=amount.multiply(new BigDecimal(100));
    		UserRechargeGive give=userRechargeGiveSerivce.getByAmountAndType(amount, RechargeGiveType.register_recharge_give.value);
    		
        	if(give!=null){
        		if(filterRecharge(userno)){
        			if(give.getForScope()!=null&&give.getForScope()==YesNoStatus.no.value){
        				if(amount.compareTo(give.getRechargeAmount())!=0){
            				return null;
            			}
        		}
        		if(give.getForLimit()!=null&&give.getForLimit()==YesNoStatus.yes.value){
             		UserRechargeGiveDetail rechargeDetail=userRechargeGiveDetailSerivce.get(new UserRechargeGiveDetailPK(give.getId(),userno));
            		if(rechargeDetail!=null){
            			if(rechargeDetail.getCommonStatus()!=CommonStatus.success.value){
            				
            				return give;
            			}
            			return null;
            		}
            	    this.saveGiveDetail(give, userno,amount);
        		}
        		return give;
        		}
        		
        	}
        	UserRechargeGive rechargeGive=userRechargeGiveSerivce.getByAmountAndType(amount, RechargeGiveType.recharge_give.value);
        	if(rechargeGive!=null){
        		if(rechargeGive.getForScope()!=null&&rechargeGive.getForScope()==YesNoStatus.no.value){
        			if(amount.compareTo(rechargeGive.getRechargeAmount())!=0){
        				return null;
        			}
        		}
        		if(rechargeGive.getForLimit()!=null&&rechargeGive.getForLimit()==YesNoStatus.yes.value){
            		UserRechargeGiveDetail rechargeDetail=userRechargeGiveDetailSerivce.get(new UserRechargeGiveDetailPK(rechargeGive.getId(),userno));
            		if(rechargeDetail!=null){
            			if(rechargeDetail.getCommonStatus()!=CommonStatus.success.value){
            				return rechargeGive;
            			}
            			return null;
            		}
            	    this.saveGiveDetail(rechargeGive, userno,amount);
        		}
        		return rechargeGive;
        	}
        	
    	}catch(Exception e){
    		logger.error("查询充值赠送出错",e);
    	}
    	
       return null;
    }
    
    private void saveGiveDetail(UserRechargeGive give,String userno,BigDecimal amount){
    	UserRechargeGiveDetail giveDate=new UserRechargeGiveDetail(new UserRechargeGiveDetailPK(give.getId(),userno));
		giveDate.setCreateTime(new Date());
		giveDate.setGiveAmount(giveDate.getGiveAmount());
		giveDate.setRechargeAmount(amount);
		giveDate.setGiveAmount(give.getGiveAmount());
		giveDate.setCommonStatus(CommonStatus.waiting.value);
		giveDate.setTransationId("0");
		RechargeGiveType type=RechargeGiveType.get(give.getRechargeGiveType());
		if(type!=null)
		giveDate.setMemo(type.name);
		userRechargeGiveDetailSerivce.save(giveDate);
    }
    //第一次注册判断
    private boolean filterRecharge(String userno){
    	UserInfo userInfo=userInfoService.get(userno);
    	if(userInfo==null){
    		return false;
    	}
    	Date registDate=userInfo.getRegisterTime();
    	if((new Date()).getTime()-registDate.getTime()<=7*24*60*60*1000){
    		UserAccount userAccount=userAccountService.get(userInfo.getUserno());
    		if(userAccount!=null){
    			if(userAccount.getTotalRecharge().compareTo(BigDecimal.ZERO)>0){
    				return false;
    			}
    			return true;
    		}
    	}
    	return false;
    }


    
}
