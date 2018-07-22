package com.lottery.controller.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.give.UserRedPackage;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.give.UserRedPackageService;

/**
 * Created by fengqinyun on 2017/3/28.
 */
@RestController
@RequestMapping("redpackage")

public class UserRedPackageController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRedPackageService userRedPackageService;
    /**
     * 赠送红包
     * @param userno 赠送人
     * @param amount  赠送金额(元)
     * @param phone  赠送手机号
     * */
    @RequestMapping(value = "/give", method = RequestMethod.POST)
    public ResponseData give(@RequestParam(value = "userno", required = true) String userno,
                             @RequestParam(value = "amount", required = true) String amount,@RequestParam(value = "phone", required = true) String phone){
        ResponseData responseData=new ResponseData();
        try{

            UserInfo userInfo=userInfoService.getByPhoneNo(phone);
            String receiveUserno="";
            if(userInfo!=null){
                receiveUserno=userInfo.getUserno();
            }
            BigDecimal giveamount=new BigDecimal(amount);
            userRedPackageService.save(userno,phone,giveamount.multiply(new BigDecimal(100)),receiveUserno);
            responseData.setErrorCode(ErrorCode.Success.value);
        }catch (LotteryException ex){
        	ex.printStackTrace();
            responseData.setErrorCode(ex.getErrorCode().getValue());
        }catch (Exception e){
        	e.printStackTrace();
            responseData.setErrorCode(ErrorCode.system_error.value);
        }
        return responseData;
    }
    
    
    /**
     * 赠送红包
     * @param userno 赠送人
     * @param amount  赠送金额(元)
     * @param phone  赠送手机号
     * */
    @RequestMapping(value = "/giveMultipleUser", method = RequestMethod.POST)
    public ResponseData giveMultipleUser(@RequestParam(value = "userno", required = true) String userno,
                             @RequestParam(value = "amount", required = true) String amount,@RequestParam(value = "phones", required = true) String phones){
        ResponseData responseData=new ResponseData();
        
        Map<String,String> results = new HashMap<String, String>();
        
        for(String phone:phones.split(";")) {
        	try {
        		UserInfo userInfo=userInfoService.getByPhoneNo(phone);
                String receiveUserno="";
                if(userInfo!=null){
                    receiveUserno=userInfo.getUserno();
                }
                BigDecimal giveamount=new BigDecimal(amount);
                userRedPackageService.save(userno,phone,giveamount.multiply(new BigDecimal(100)),receiveUserno);
                results.put(phone, ErrorCode.Success.value);
        	}catch (LotteryException ex){
        		ex.printStackTrace();
        		results.put(phone, ex.getErrorCode().getValue());
            }catch (Exception e){
            	e.printStackTrace();
            	results.put(phone, ErrorCode.system_error.value);
            }
        }
        responseData.setErrorCode(ErrorCode.Success.value);
        responseData.setValue(results);
        return responseData;
    }

    /**
     * 领取红包
     * @param phone 领取手机号码
     * */
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public ResponseData receive(@RequestParam(value = "phone", required = true) String phone) {
        ResponseData responseData = new ResponseData();
        try{

            UserInfo userInfo=userInfoService.getByPhoneNo(phone);
            if (userInfo==null){
                responseData.setErrorCode(ErrorCode.no_user.value);
                return responseData;
            }
            userRedPackageService.receive(userInfo.getUserno());
            responseData.setErrorCode(ErrorCode.Success.value);
        }catch (LotteryException ex){
            responseData.setErrorCode(ex.getErrorCode().getValue());
        }catch (Exception e){
            responseData.setErrorCode(ErrorCode.system_error.value);
        }
        return responseData;
    }
    /**
     * 领取红包
     * @param redPackageId 红包id
     * */
    @RequestMapping(value = "/receiveRedPackageId", method = RequestMethod.POST)
    public ResponseData receiveRedPackage(@RequestParam(value = "redPackageId", required = true) String redPackageId){
        ResponseData responseData = new ResponseData();
        try{
            userRedPackageService.reciveRedPackageById(redPackageId);
            responseData.setErrorCode(ErrorCode.Success.value);
        }catch (LotteryException ex){
            responseData.setErrorCode(ex.getErrorCode().getValue());
        }catch (Exception e){
            responseData.setErrorCode(ErrorCode.system_error.value);
        }
        return responseData;
    }

    
    @RequestMapping(value="/getReceiveRedPackages",method=RequestMethod.POST)
	public @ResponseBody ResponseData getReceiveRedPackages(@RequestParam("userno") String userno,
			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult){
    	ResponseData rd=new ResponseData();
		try{
			PageBean<UserRedPackage> page=new PageBean<UserRedPackage>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			
			List<UserRedPackage> list = userRedPackageService.findReceiveRedPackages(userno, page);
			page.setList(list);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			e.printStackTrace();
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
    }
    
    
    
    @RequestMapping(value="/getGiveRedPackages",method=RequestMethod.POST)
	public @ResponseBody ResponseData getGiveRedPackages(@RequestParam("userno") String userno,
			@RequestParam(value="startLine",required=false,defaultValue="1") int startLine,
			@RequestParam(value="maxLine",required=false,defaultValue="15") int maxResult){
    	ResponseData rd=new ResponseData();
		try{
			PageBean<UserRedPackage> page=new PageBean<UserRedPackage>();
			page.setPageIndex(startLine);
			page.setMaxResult(maxResult);
			
			List<UserRedPackage> list = userRedPackageService.findGiveRedPackages(userno, page);
			page.setList(list);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(page);
		}catch(LotteryException e){
			rd.setErrorCode(e.getErrorCode().getValue());
		}catch(Exception e){
			e.printStackTrace();
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
    }
}




