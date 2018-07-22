package com.lottery.controller;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.agency.LotteryChannelPartner;
import com.lottery.core.service.LotteryChannelPartnerService;
import com.lottery.core.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fengqinyun on 16/5/24.
 */
@Controller
@RequestMapping("/channelpartner")
public class LotteryChannelPartnerController {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private LotteryChannelPartnerService lotteryChannelPartnerService;
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping(value ="/get",method=RequestMethod.POST)
    public @ResponseBody ResponseData get(@RequestParam(name="agencyno",required = true) String agencyno,@RequestParam(name = "agencyuser",required = true) String agencyuser,
                                          @RequestParam(name="agencytoken",required = true) String agencytoken){
        ResponseData responseData=new ResponseData();
        try {
            LotteryChannelPartner channelPartner  = lotteryChannelPartnerService.saveOrUpdate(agencyno,agencyuser,agencytoken);

            if (!agencytoken.equals(channelPartner.getAgencytoken())) {
                //和第一次联合登陆时候渠道token不一致
                responseData.setErrorCode(ErrorCode.Faile.value);
                responseData.setValue("渠道token验证未通过");
                return responseData;
            }

            responseData.setValue(channelPartner);
            responseData.setErrorCode(ErrorCode.Success.value);

        }catch (Exception e){
            logger.error("出错",e);
            responseData.setErrorCode(ErrorCode.Faile.value);
            responseData.setValue(e.getMessage());
        }
        return responseData;
    }

    /**
     * 用户名,手机号注册
     * @param username 用户名
     * @param mobilephone 手机号码
     * @param password 密码
     * @param agencyno 渠道号
     * @param terminalType 指定出票中心
     * @param status 用户状态
     * @param channelparterid      合作渠道主键
     * */
    @RequestMapping(value = "/usernameMobileRegister", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData usernameMobileRegister(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password,
                                        @RequestParam(value = "mobilephone", required = true) String mobilephone, @RequestParam(value = "agencyno",required = false,defaultValue = "0") String agencyno,
                                        @RequestParam(value = "terminalType",required=false, defaultValue = "0") int terminalType, @RequestParam(value = "status", defaultValue = "1") int status,
                                        @RequestParam(value = "channelparterid", required = true) Long  channelparterid

    ) {
        ResponseData rd = new ResponseData();
        try {
            UserInfo info=userInfoService.channelPartnerRegister(username, mobilephone, password, agencyno, terminalType,status,channelparterid);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue(info);
        }catch(LotteryException e){
            ErrorCode errorCode=e.getErrorCode();
            rd.setErrorCode(errorCode.getValue());
            rd.setValue(errorCode.getMemo());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.getValue());
            logger.error("合作渠道注册出错",e);
        }
        return rd;
    }



}
