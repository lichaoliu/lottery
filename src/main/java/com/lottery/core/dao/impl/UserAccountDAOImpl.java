package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.domain.account.UserAccount;
@Repository
public class UserAccountDAOImpl extends AbstractGenericDAO<UserAccount, String> implements UserAccountDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1634093095053265971L;

}
