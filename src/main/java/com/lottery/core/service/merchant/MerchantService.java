package com.lottery.core.service.merchant;

import com.lottery.common.contains.AgencyType;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.dao.merchant.MerchantDAO;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MerchantService {
	@Autowired
	private UserInfoDao userDao;
	@Autowired
	private UserAccountDAO userAccountDao;
	@Autowired
	private IdGeneratorDao idDao;
	@Autowired
	private MerchantDAO merchantDAO;

	/**
	 * 增加出票商
	 * 
	 * @param username
	 *            出票商名字
	 * @param password
	 *            秘钥
	 * @param realname
	 *            真实姓名
	 * @param idCard
	 *            身份证
	 * @param mobilePhone
	 *            手机号码
	 * @param ip
	 *            出票ip
	 * @param amount
	 *            信用额度
	 * */
	@Transactional
	public Merchant save(String username, String password, String realname, String idCard, String mobilePhone, String ip, BigDecimal amount, String noticeUrl, Integer isNotice, Integer isIp, Long terminalId) {
		UserInfo info = new UserInfo();
		String uuid = String.format("800%02d", idDao.getDefaultId());
		info.setUserno(uuid);
		info.setRealName(realname);
		info.setUsername(username);
		info.setPasswd(password);
		info.setIdcard(idCard);
		info.setPhoneno(mobilePhone);
		info.setRegisterTime(new Date());
		info.setLastLoginTime(new Date());
		info.setSex(0);
		info.setAgencyNo(AgencyType.merchant.value);
		info.setStatus(YesNoStatus.yes.value); // 用户状态
		info.setTerminalType(TerminalType.all.value);
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
		userAccountDao.insert(account);
		Merchant merchant = new Merchant();
		merchant.setMerchantCode(uuid);
		merchant.setSecretKey(password);
		merchant.setStatus(YesNoStatus.yes.value);
		merchant.setIp(ip);
		merchant.setMerchantName(username);
		merchant.setCreditBalance(amount);
		merchant.setNoticeUrl(noticeUrl);
		merchant.setRealName(realname);
		merchant.setPhoneno(mobilePhone);
		merchant.setIdcard(idCard);
		merchant.setIsNotice(isNotice);
		merchant.setIsIp(isIp);
		merchant.setTerminalId(terminalId);
		merchantDAO.insert(merchant);
		return merchant;
	}

	@Transactional
	public Merchant get(String id) {
		return merchantDAO.find(id);
	}

	@Transactional
	public void update(Merchant merchant) {
		Merchant mer = merchantDAO.find(merchant.getMerchantCode());
		mer.setCreditBalance(merchant.getCreditBalance());
		mer.setIp(merchant.getIp());
		mer.setMerchantName(merchant.getMerchantName());
		mer.setSecretKey(merchant.getSecretKey());
		mer.setStatus(merchant.getStatus());
		mer.setNoticeUrl(merchant.getNoticeUrl());
		mer.setIsNotice(merchant.getIsNotice());
		mer.setIsIp(merchant.getIsIp());
		mer.setTerminalId(merchant.getTerminalId());
		UserInfo userInfo=userDao.find(merchant.getMerchantCode());
		if(merchant.getIdcard()!=null){
			mer.setIdcard(merchant.getIdcard());
			userInfo.setIdcard(merchant.getIdcard());
		}
		if(merchant.getRealName()!=null){
			mer.setRealName(merchant.getRealName());
			userInfo.setRealName(merchant.getRealName());
		}
		
		if(merchant.getPhoneno()!=null){
			mer.setPhoneno(merchant.getPhoneno());
			userInfo.setPhoneno(merchant.getPhoneno());
		}
		userDao.merge(userInfo);
		merchantDAO.update(mer);
	}

	@Transactional
	public void delete(String strChecked) {
		String[] ids = strChecked.split(",");
		for (String id : ids) {
			Merchant mer = merchantDAO.find(id);
			UserInfo userInfo = userDao.find(id);
			UserAccount userAccount = userAccountDao.find(id);
			if (userAccount != null) {
				userAccountDao.remove(userAccount);
			}
			if (userInfo != null) {
				userDao.remove(userInfo);
			}
			if (mer != null)
				merchantDAO.remove(mer);
		}
	}



	
	

}
