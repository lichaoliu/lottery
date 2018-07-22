package com.lottery.controller.pay;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.pay.IRechargeProcess;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Map;

/**
 * Created by fengqinyun on 15/10/29.
 * 充值同步返回
 */
@RestController
@RequestMapping("/rechargeReturn")
public class RechargeReturnController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name="payChannelProcessMap")
    private Map<PayChannel,IRechargeProcess> process;
    /**
     * 充值返回
     * */
    @RequestMapping(value = "/elinkpc", method = RequestMethod.POST)
    public
    ResponseData syncElinkpc(HttpServletRequest request){

        return requestReturn(request,PayChannel.elinkpcpay);
    }


    /**
     * 充值返回
     * */
    @RequestMapping(value = "/elinkpcwy", method = RequestMethod.POST)
    public
    ResponseData syncElinkWypc(HttpServletRequest request){

        return requestReturn(request,PayChannel.elinkpcpay);
    }
    
    @RequestMapping(value = "/zfbweb", method = RequestMethod.POST)
    public
    ResponseData syncZfbWeb(HttpServletRequest request){

        return requestReturn(request,PayChannel.zfbwebpay);
    }
    protected ResponseData requestReturn(HttpServletRequest request,PayChannel payChannel){
        ResponseData responseData=new ResponseData();
        try{

            String requestDate=readRequestString(request);
            logger.error("接受到请求的原始字符数据:{}",requestDate);

            if (StringUtils.isBlank(requestDate)){
                return null;
            }
            IRechargeProcess processpay=process.get(payChannel);

            if (processpay==null){
                responseData.setErrorCode(ErrorCode.no_exits.value);
                responseData.setValue("渠道处理方法不存在");
                return  responseData;
            }

            boolean res=processpay.syncResponse(requestDate);
            if (res){
                responseData.setErrorCode(ErrorCode.Success.value);
                responseData.setValue("success");
                return responseData;
            }else {
                responseData.setErrorCode(ErrorCode.Faile.value);
                responseData.setValue("充值失败");
                return responseData;
            }


        }catch (Exception e){
            responseData.setErrorCode(ErrorCode.system_error.value);
            responseData.setValue(e);
        }
        return responseData;
    }
    protected String readRequestString(HttpServletRequest request) {
        try {
            request.setCharacterEncoding(CharsetConstant.CHARSET_DEFAULT);
            BufferedReader reader = request.getReader();
            StringBuilder buffer = new StringBuilder();
            int n;
            while ((n = reader.read()) != -1) {
                buffer.append((char) n);
            }
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            logger.error("接收http请求出错", e);
            return null;
        }

    }

}
