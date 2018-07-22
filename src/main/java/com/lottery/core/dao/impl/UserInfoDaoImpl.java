package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.domain.UserInfo;

@Repository
public class UserInfoDaoImpl extends AbstractGenericDAO<UserInfo, String> implements UserInfoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083444356337319087L;

	@Override
	public UserInfo getByUserName(String username) throws Exception {
		Map<String, Object> containtMap = new HashMap<String, Object>();
		containtMap.put("username", username);
		return findByUnique(containtMap);
	}

	@Override
	public UserInfo getByPhoneNo(String phoneNO) throws Exception {
		Map<String, Object> containtMap = new HashMap<String, Object>();
		containtMap.put("phoneno", phoneNO);
		return findByUnique(containtMap);
	}

}
