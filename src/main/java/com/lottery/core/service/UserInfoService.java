package com.lottery.core.service;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.pay.RechargeGiveType;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.*;
import com.lottery.core.dao.give.UserRechargeGiveDAO;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.agency.LotteryChannelPartner;
import com.lottery.core.domain.give.UserRechargeGive;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private UserInfoDao userDao;
	@Autowired
	private IdGeneratorDao dao;
	@Autowired
	private UserAccountDAO userAccountDao;
	@Autowired
	protected TerminalDAO terminalDAO;
    @Autowired
    protected UserRechargeGiveDAO userRechargeGiveDAO;
    @Autowired
    protected UserAccountDetailDAO userAccountDetailDAO;
    @Autowired
	private LotteryChannelPartnerDao lotteryChannelPartnerDao;
	@Transactional
	public String save(UserInfo userInfo) {
		String userno = dao.getUserno();
		userInfo.setUserno(userno);
		userDao.insert(userInfo);
		return userno;
	}

	@Transactional
	public UserInfo get(String id) {
		return userDao.find(id);

	}

	/**
	 * 用户注册
	 * 
	 * @author fengqinyun
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param real_name 真实姓名
	 * @param idCard 身份证
	 * @param mobilePhone 手机号
	 * @param agencyno 渠道号
	 * @param terminalType 指定出票渠道
	 * @param status 状态 (YesNoStatus)
	 * */

	@Transactional
	public UserInfo register(String username, String password, String real_name, String idCard, String mobilePhone, String agencyno, int terminalType,int status) {
		UserInfo info = null;

		try {
			info = userDao.getByUserName(username);
		} catch (Exception e) {

		}
		if (info != null) {

			throw new LotteryException(ErrorCode.username_exits, "用户名已经存在");

		}

		try {
			info = userDao.getByPhoneNo(mobilePhone);
		} catch (Exception e) {

		}
		if (info != null) {
			throw new LotteryException(ErrorCode.mobile_exits, "手机号码已经存在");
		}
		try {
			info = new UserInfo();
			String uuid = dao.getUserno();
			info.setUserno(uuid);
			info.setRealName(real_name);
			info.setUsername(username);
			info.setPasswd(password);
			info.setIdcard(idCard);
			info.setPhoneno(mobilePhone);
			info.setRegisterTime(new Date());
			info.setAgencyNo(agencyno);
			info.setTerminalType(terminalType);
			info.setLastLoginTime(new Date());
			info.setSex(0);
			info.setStatus(status); // 用户状态
			userDao.insert(info);
			UserAccount account = new UserAccount();
			account.setUserno(uuid);
			account.setFreeze(BigDecimal.ZERO);
			account.setLastfreeze(BigDecimal.ZERO);
			account.setBalance(BigDecimal.ZERO);
			account.setDrawbalance(BigDecimal.ZERO);
			account.setTotalbetamt(BigDecimal.ZERO);
			account.setTotalgiveamt(BigDecimal.ZERO);
			account.setTotalprizeamt(BigDecimal.ZERO);
			account.setTotalBalance(BigDecimal.ZERO);
			account.setTotalRecharge(BigDecimal.ZERO);
			account.setGiveBalance(BigDecimal.ZERO);
			account.setLastTradeTime(new Date());
			account.setUserName(username);
            account.setPhoneno(mobilePhone);
			registerGive(account, agencyno);

			userAccountDao.insert(account);
			return info;
		} catch (Exception e) {
			logger.error("用户注册出错", e);
			throw new LotteryException(ErrorCode.user_registe_error, e.getMessage());

		}
	}

	@Transactional
	public UserInfo getByUserName(String username) {
		try {

			return userDao.getByUserName(username);
		} catch (Exception e) {
			logger.error("通过userName={}查询出错,错误信息是{}", new Object[] { username, e.getMessage() });
			return null;
		}
	}

	/**
	 * 通过手机号查询用户
	 * 
	 * @param phoneNo 手机号
	 * @throws Exception
	 * */
	@Transactional
	public UserInfo getByPhoneNo(String phoneNo){
		try {
			return userDao.getByPhoneNo(phoneNo);
		}catch (Exception e) {
			logger.error("通过phoneNo={}查询出错,错误信息是{}",phoneNo, e.getMessage());
			return null;
		}
		
	}

	/**
	 * 通过键值对查询用户信息
	 * 
	 * @param pKey
	 *            字段名
	 * @param pValue
	 *            字段值
	 * @return
	 */
	@Transactional
	public List<UserInfo> getByPropertyName(String pKey, String pValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(pKey, pValue);
		return userDao.findByCondition(map);
	}

	@Transactional
	public UserInfo getUserInfo(String uuid) {
		return userDao.find(uuid);
	}

	@Transactional
	public UserInfo updatePassword(String uuid, String oldPassword, String newPassword) {
		UserInfo userInfo = userDao.find(uuid);
		if (userInfo == null) {
			throw new LotteryException(ErrorCode.no_user, "修改用户不存在,userno=" + uuid);
		}
		if(!userInfo.getPasswd().equals(oldPassword)) {
			throw new LotteryException(ErrorCode.user_passwd_error, "原始密码错误,userno=" + uuid);
		}
		try {
			if (userInfo.getPasswd().equals(oldPassword)) {
				userInfo.setPasswd(newPassword);
				userDao.merge(userInfo);
			}
			return userInfo;
		} catch (Exception e) {
			logger.error("修改用户密码失败", e);
			throw new LotteryException(ErrorCode.system_error, e.getMessage());
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userno
	 *            用户名
	 * @param email
	 * @param sex
	 * @param alias
	 *            昵称
	 * @param cardId
	 *            身份证号
	 * @param realname
	 *            真实姓名
	 * */
	@Transactional
	public UserInfo updateUserInfo(String userno, String email, String sex, String alias, String cardId, String realname) {
		UserInfo userInfo = userDao.find(userno);
		if (userInfo == null) {
			throw new LotteryException(ErrorCode.no_user, "修改用户不存在,userno=" + userno);
		}

		if (StringUtils.isNotBlank(sex)) {
			userInfo.setSex(Integer.valueOf(sex));
		}
		if (StringUtils.isNotBlank(alias)) {
			userInfo.setAlias(alias);
		}
		if (StringUtils.isNotBlank(email)) {
			userInfo.setEmail(email);
		}

		if (StringUtils.isNotBlank(cardId)) {// 如果身份证存在，不能修改
			if (StringUtils.isBlank(userInfo.getIdcard())) {
				userInfo.setIdcard(cardId);
			} else {
				logger.error("身份证[{}]已存在,不能修改", cardId);
			}
		}

		if (StringUtils.isNotBlank(realname)) {// 如果真实姓名存在，不能修改
			if (StringUtils.isBlank(userInfo.getRealName())) {
				userInfo.setRealName(realname);
			} else {
				logger.error("真实姓名[{}]已存在，不能修改", realname);
			}

		}
		userDao.merge(userInfo);
		return userInfo;

	}

	@Transactional
	public void mergeInfoForAdmin(UserInfo userinfo) {
		UserInfo userold = userDao.find(userinfo.getUserno());
		if(userold != null){
			/*if (!StringUtil.isEmpty(userinfo.getPasswd()) && !userold.getPasswd().equals(userinfo.getPasswd())) {
				userold.setPasswd(MD5Util.toMd5(userinfo.getPasswd()));
			}
			userold.setRealName(userinfo.getRealName());
			userold.setPhoneno(userinfo.getPhoneno());
			userold.setIdcard(userinfo.getIdcard());
			*/
			
			
			userold.setStatus(userinfo.getStatus());
			userold.setEmail(userinfo.getEmail());
			userold.setTerminalType(userinfo.getTerminalType());
			userDao.merge(userold);
		}
	}

	@Transactional
	public void resetPassword(String userno, String pwd) {
		UserInfo user = userDao.find(userno);
		user.setPasswd(pwd);
		userDao.merge(user);

		// String phoneno = user.getPhoneno();
		// 发送短信
	}

	@Transactional
	public void update(UserInfo entity) {
		userDao.merge(entity);
	}

	/**
	 * 手机号码注册
	 * @param  status (YesNoStatus)
	 * */
	@Transactional
	public UserInfo mobilePhoneRegister(String mobilephone, String passwd, String agencyno, int terminalType,int status) {
		UserInfo info = null;
		try {
			info = userDao.getByPhoneNo(mobilephone);
		} catch (Exception e) {

		}
		if (info != null) {
			throw new LotteryException(ErrorCode.mobile_exits, "手机号码已经存在");
		}
		try {
			info = new UserInfo();
			String uuid = dao.getUserno();
			info.setUserno(uuid);
			String username = "T_" + uuid.substring(9);
			info.setUsername(username);
			info.setPasswd(passwd);
			info.setPhoneno(mobilephone);
			info.setRegisterTime(new Date());
			info.setAgencyNo(agencyno);
			info.setTerminalType(terminalType);
			info.setLastLoginTime(new Date());
			info.setSex(0);
			info.setStatus(status); // 用户状态
			userDao.insert(info);
			UserAccount account = new UserAccount();
			account.setUserno(uuid);
			account.setFreeze(BigDecimal.ZERO);
			account.setLastfreeze(BigDecimal.ZERO);
			account.setBalance(BigDecimal.ZERO);
			account.setDrawbalance(BigDecimal.ZERO);
			account.setTotalbetamt(BigDecimal.ZERO);
			account.setTotalgiveamt(BigDecimal.ZERO);
			account.setTotalprizeamt(BigDecimal.ZERO);
			account.setTotalBalance(BigDecimal.ZERO);
			account.setTotalRecharge(BigDecimal.ZERO);
			account.setGiveBalance(BigDecimal.ZERO);
			account.setLastTradeTime(new Date());
			account.setUserName(username);
			account.setPhoneno(mobilephone);
			registerGive(account,agencyno);//注册送彩金判断

			userAccountDao.insert(account);
			return info;
		} catch (Exception e) {
			logger.error("注册出错", e);
			throw new LotteryException(ErrorCode.user_registe_error, e.getMessage());

		}
	}

	/**
	 * 用户名,手机号码注册
	 * 
	 * @param username
	 *            用户名
	 * @param mobilephone
	 *            手机号码
	 * @param passwd
	 *            用户名
	 * @param agencyno
	 *            渠道
	 * */
	@Transactional
	public UserInfo usernameMobilePhoneRegister(String username, String mobilephone, String passwd, String agencyno, int terminalType,int status) {
		UserInfo info = null;
		try {
			info = userDao.getByPhoneNo(mobilephone);
		} catch (Exception e) {

		}

		if (info != null) {
			throw new LotteryException(ErrorCode.mobile_exits, "手机号码已经存在");
		}

		try {
			info = userDao.getByUserName(username);
		} catch (Exception e) {

		}
		if (info != null) {
			throw new LotteryException(ErrorCode.username_exits, "用户名已经存在");
		}

		try {
			info = new UserInfo();
			String uuid = dao.getUserno();
			info.setUserno(uuid);

			info.setUsername(username);
			info.setPasswd(passwd);
			info.setPhoneno(mobilephone);
			info.setRegisterTime(new Date());
			info.setAgencyNo(agencyno);
			info.setTerminalType(terminalType);
			info.setLastLoginTime(new Date());
			info.setSex(0);
			info.setStatus(status); // 用户状态
			userDao.insert(info);
			UserAccount account = new UserAccount();
			account.setUserno(uuid);
			account.setFreeze(BigDecimal.ZERO);
			account.setLastfreeze(BigDecimal.ZERO);
			account.setBalance(BigDecimal.ZERO);
			account.setDrawbalance(BigDecimal.ZERO);
			account.setTotalbetamt(BigDecimal.ZERO);
			account.setTotalgiveamt(BigDecimal.ZERO);
			account.setTotalprizeamt(BigDecimal.ZERO);
			account.setTotalBalance(BigDecimal.ZERO);
			account.setTotalRecharge(BigDecimal.ZERO);
			account.setGiveBalance(BigDecimal.ZERO);
			account.setLastTradeTime(new Date());
			account.setUserName(username);
			account.setPhoneno(mobilephone);
            registerGive(account,agencyno);//注册送彩金判断

			userAccountDao.insert(account);
			return info;
		} catch (Exception e) {
            logger.error("用户注册出错",e);
			throw new LotteryException(ErrorCode.user_registe_error, e.getMessage());

		}
	}

    protected  void registerGive(UserAccount account,String agencyno){
        List<UserRechargeGive> gives=userRechargeGiveDAO.getByGiveTypeAndAgencyno(RechargeGiveType.register_give.value,agencyno,1);
        if(gives==null||gives.size()==0){//如果看单个渠道没有,看全部渠道
			 gives=userRechargeGiveDAO.getByGiveTypeAndAgencyno(RechargeGiveType.register_give.value,"0",1);
		}

		if(gives==null||gives.size()==0){
			return;
		}

        UserRechargeGive rechargeGive=gives.get(0);
        if(rechargeGive.getStatus()== YesNoStatus.no.value)
            return;
        Date start=rechargeGive.getStartTime();
        Date  end=rechargeGive.getFinishTime();
        if((new Date()).before(start)||(new Date()).after(end))
            return;
        BigDecimal amount=rechargeGive.getGiveAmount();
        account.setBalance(amount);
        account.setTotalBalance(amount);
        account.setTotalRecharge(amount);
        String detailId=dao.getUserAccountDetailId();
        UserAccountDetail uad2 = new UserAccountDetail(detailId, account.getUserno(),account.getUserName(),account.getUserno(), new Date(), amount, AccountType.give, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "注册送彩金", SignStatus.in);
        uad2.setOtherid(account.getUserno());
        uad2.setLotteryType(LotteryType.ALL.value);
        uad2.setPhase("1011");
        uad2.setGiveAmount(amount);
        userAccountDetailDAO.insert(uad2);


    }

	@Transactional
	public UserInfo channelPartnerRegister(String username, String mobilephone, String passwd, String agencyno, int terminalType,int status,Long channelParterId){
		UserInfo userInfo=this.usernameMobilePhoneRegister(username, mobilephone, passwd, agencyno, terminalType, status);
		LotteryChannelPartner lotteryChannelPartner=lotteryChannelPartnerDao.find(channelParterId);
		lotteryChannelPartner.setUserno(userInfo.getUserno());

        lotteryChannelPartnerDao.update(lotteryChannelPartner);
		return userInfo;
	}

}
