package com.lottery.core.dao.impl.give;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.give.UserAccountHandselDAO;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.give.UserAccountHandsel;

@Repository
public class UserAccountHandselDAOImpl extends AbstractGenericDAO<UserAccountHandsel, String> implements UserAccountHandselDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7835115678978073811L;

	@Override
	public UserAccountHandsel get(UserAccount userAccount) {
		UserAccountHandsel userAccountHandsel = find(userAccount.getUserno());
		if (userAccountHandsel == null) {

			userAccountHandsel = new UserAccountHandsel();
			userAccountHandsel.setBalance(BigDecimal.ZERO);
			userAccountHandsel.setLastTransation(BigDecimal.ZERO);
			userAccountHandsel.setTotalGive(BigDecimal.ZERO);
			userAccountHandsel.setUserName(userAccount.getUserName());

		}
		return userAccountHandsel;
	}

}
