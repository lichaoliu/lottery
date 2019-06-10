package com.lottery.controller;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.DrawType;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.UserDrawBank;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.service.UserDrawBankService;
import com.lottery.core.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/drawbank")
public class UserDrawBankController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDrawBankService userDrawBankService;
    @Autowired
    protected UserInfoService userInfoService;
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData getDrawBank(@RequestParam(value = "userno", required = true) String userno) {
		ResponseData rd = new ResponseData();
		try {
			List<UserDrawBank> drawBankList=userDrawBankService.getByUserno(userno);
			rd.setErrorCode(ErrorCode.Success.getValue());
			rd.setValue(drawBankList);
		} catch (LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			logger.error("获取银行卡列表出错",e);
			rd.setErrorCode(ErrorCode.system_error.getValue());
		}
		return rd;
	}
    @RequestMapping(value = "/getDrawCard", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData getDrawTypeBank(@RequestParam(value = "userno", required = true) String userno,@RequestParam(value = "drawType", required = true) int drawType) {
        ResponseData rd = new ResponseData();
        try {
            UserDrawBank userDrawBank=userDrawBankService.get(userno,drawType);
            rd.setErrorCode(ErrorCode.Success.getValue());
            rd.setValue(userDrawBank);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("获取绑定列表出错",e);
            rd.setErrorCode(ErrorCode.system_error.getValue());
        }
        return rd;
    }
	/**
	 * 绑定银行卡
	 * @param  userno 用户编号
	 * @param  bankCard 银行卡号
	 * @param  branch 网点(xx街道xx分行)
	 * @param  province 开户省
	 * @param  city 开户城市
	 * @param  bankName 银行名称
	 * @param  bankType 银行类型
	 *
	 * */
	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData bindBankCard(@RequestParam(value = "userno") String userno, @RequestParam(value = "branch") String branch,
                          @RequestParam(value = "province") String province, @RequestParam("city") String city,
						  @RequestParam("bankCard") String bankCard,
                          @RequestParam("bankName") String bankName,
						  @RequestParam(value="bankType",required=false,defaultValue="0" ) String bankType) {
		ResponseData rd = new ResponseData();
		try {
			UserDrawBank userbank=userDrawBankService.getByBankCard(bankCard);
			if(userbank!=null){
				rd.setErrorCode(ErrorCode.card_bind_exits.getValue());
				rd.setValue("该账号已绑定");
				return rd;
			}
            userbank=userDrawBankService.get(userno,DrawType.bank_draw.value);
            if(userbank!=null){
                rd.setErrorCode(ErrorCode.card_bind_exits.getValue());
                rd.setValue("用户已绑定银行卡");
                return rd;
            }
            UserInfo userInfo=userInfoService.get(userno);
            if(userInfo==null){
                rd.setErrorCode(ErrorCode.no_user.getValue());
            }else  if (StringUtils.isBlank(userInfo.getRealName())){
				rd.setErrorCode(ErrorCode.user_realName_noexits.getValue());
			}else {
                String realname=userInfo.getRealName();
                UserDrawBank userDrawBank = new UserDrawBank();
                userDrawBank.setCreateTime(new Date());
                userDrawBank.setUserno(userno);
                userDrawBank.setBranch(branch);
                userDrawBank.setCity(city);
                userDrawBank.setBankCard(bankCard);
                userDrawBank.setRealname(realname);
                userDrawBank.setBankName(bankName);
                userDrawBank.setProvince(province);
                userDrawBank.setDrawType(DrawType.bank_draw.value);
				userDrawBank.setBankType(bankType);
                userDrawBankService.save(userDrawBank);
                rd.setErrorCode(ErrorCode.Success.getValue());
                rd.setValue(userDrawBank);
            }

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}绑定银行卡出错", userno, e);
		}
		return rd;
	}


	/**
	 * 账号绑定
	 * @param  userno 用户编号
	 * @param  bankCard 账号
	 * @param  drawType 提现类型

	 * */
	@RequestMapping(value = "/bindCard", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData bindCard(@RequestParam(value = "userno",required = true) String userno,
						  @RequestParam(value = "bankCard",required = true) String bankCard,
						  @RequestParam(value="drawType",required=false,defaultValue = "2") int drawType
						  ) {
		ResponseData rd = new ResponseData();
		try {
			UserDrawBank userbank=userDrawBankService.getByBankCard(bankCard);
			if(userbank!=null){
				rd.setErrorCode(ErrorCode.card_bind_exits.getValue());
				rd.setValue("该账号已绑定");
				return rd;
			}

            userbank=userDrawBankService.get(userno,drawType);
            if(userbank!=null){
                rd.setErrorCode(ErrorCode.card_bind_exits.getValue());
                rd.setValue("用户已绑定");
                return rd;
            }

			UserInfo userInfo=userInfoService.get(userno);
			if(userInfo==null){
				rd.setErrorCode(ErrorCode.no_user.getValue());
			}else  if (StringUtils.isBlank(userInfo.getRealName())){
				rd.setErrorCode(ErrorCode.user_realName_noexits.getValue());
			}else {
				String realname=userInfo.getRealName();
				UserDrawBank userDrawBank = new UserDrawBank();
				userDrawBank.setCreateTime(new Date());
				userDrawBank.setUserno(userno);
				userDrawBank.setBankCard(bankCard);
				userDrawBank.setRealname(realname);
				userDrawBank.setDrawType(drawType);
				if(2==drawType) {
					userDrawBank.setBankName("支付宝");
				}
				userDrawBankService.save(userDrawBank);
				rd.setErrorCode(ErrorCode.Success.getValue());
				rd.setValue(userDrawBank);
			}

		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}绑定银行卡出错", userno, e);
		}
		return rd;
	}

	/**
	 * 修改银行卡
	 * 
	 * @param id
	 *            主键
	 * @param bankCard
	 *            卡号
	 * @param branch
	 *            营业网点
	 * @param province
	 *            开户省
	 * @param city
	 *            开户城市
	 * @param bankName
	 *            银行名称
	 * */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData modify(@RequestParam(value = "id", required = true) String id, 
			@RequestParam(value = "branch") String branch, 
			@RequestParam(value = "province") String province, 
			@RequestParam("city") String city, 
			@RequestParam(value = "bankCard", required = true) String bankCard, 

			@RequestParam("bankName") String bankName) {
		ResponseData rd = new ResponseData();

		try {
			UserDrawBank userDrawBank = userDrawBankService.get(id);
			if (userDrawBank == null) {
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			} else {
				userDrawBank.setBranch(branch);
				userDrawBank.setCity(city);
				userDrawBank.setBankCard(bankCard);
				userDrawBank.setBankName(bankName);
				userDrawBank.setProvince(province);
				userDrawBank.setUpdateTime(new Date());
				userDrawBankService.update(userDrawBank);
				rd.setValue(userDrawBank);
				rd.setErrorCode(ErrorCode.Success.getValue());
			}
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}修改银行卡出错", id, e);
		}
		return rd;
	}

	/**
	 * 解除绑定银行卡
	 * @param id 主键
	 * */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData delete(@RequestParam(value = "id", required = true) String id) {
		ResponseData rd = new ResponseData();
		try {
			UserDrawBank userDrawBank = userDrawBankService.get(id);
			if (userDrawBank == null) {
				rd.setErrorCode(ErrorCode.no_exits.getValue());
			} else {
				userDrawBankService.delete(userDrawBank);
				rd.setValue(userDrawBank);
				rd.setErrorCode(ErrorCode.Success.getValue());
			}
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}解除银行卡绑定", id, e);
		}
		return rd;
	}
	
	
	
	/**
	 * 解除绑定银行卡
	 * @param id 主键
	 * */
	@RequestMapping(value = "/unBind", method = RequestMethod.POST)
	public @ResponseBody
	ResponseData unBind(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "userno", required = true) String userno) {
		ResponseData rd = new ResponseData();
		rd.setErrorCode(ErrorCode.Success.getValue());
		try {
			userDrawBankService.unBind(id, userno);
		} catch(LotteryException e) {
			rd.setErrorCode(e.getErrorCode().getValue());
		} catch (Exception e) {
			rd.setErrorCode(ErrorCode.system_error.getValue());
			logger.error("{}解除银行卡绑定", id, e);
		}
		return rd;
	}
}
