package com.lottery.core.dao.impl;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserDrawBankDAO;
import com.lottery.core.domain.UserDrawBank;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class UserDrawBankDAOImpl extends AbstractGenericDAO<UserDrawBank, String> implements UserDrawBankDAO {
    @Override
    public UserDrawBank get(String userno, int drawType) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userno",userno);
        map.put("drawType",drawType);
        return findByUnique(map);
    }

    @Override
	public UserDrawBank getByBankCard(String bankCard) {
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("bankCard", bankCard);
		return findByUnique(searchMap);
	}

	@Override
	public List<UserDrawBank> getByUserno(String userno) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userno",userno);
		return findByCondition(map);
	}


}
