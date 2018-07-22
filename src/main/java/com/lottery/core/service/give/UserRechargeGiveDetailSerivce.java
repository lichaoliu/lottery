package com.lottery.core.service.give;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.dao.give.UserRechargeGiveDetailDAO;
import com.lottery.core.domain.give.UserRechargeGiveDetail;
import com.lottery.core.domain.give.UserRechargeGiveDetailPK;

@Service
public class UserRechargeGiveDetailSerivce {
	@Autowired
	private UserRechargeGiveDetailDAO userRechargeGiveDetailDAO;

	@Transactional
	public void save(UserRechargeGiveDetail entity) {
		userRechargeGiveDetailDAO.insert(entity);
	}
	@Transactional
	public UserRechargeGiveDetail get(UserRechargeGiveDetailPK pk){
		return userRechargeGiveDetailDAO.find(pk);
	}
	@Transactional
	public UserRechargeGiveDetail update(UserRechargeGiveDetail entity){
		return userRechargeGiveDetailDAO.merge(entity);
	}
	
}
