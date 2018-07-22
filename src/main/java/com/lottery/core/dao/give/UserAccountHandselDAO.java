package com.lottery.core.dao.give;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.give.UserAccountHandsel;

public interface  UserAccountHandselDAO extends IGenericDAO<UserAccountHandsel, String>{
	/**
	 * 根据账号相关查询彩金账号
	 * */
	public UserAccountHandsel get(UserAccount userAccount);

}
