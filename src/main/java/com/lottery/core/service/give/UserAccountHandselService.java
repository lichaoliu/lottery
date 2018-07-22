package com.lottery.core.service.give;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.dao.give.UserAccountHandselDAO;
import com.lottery.core.domain.give.UserAccountHandsel;

@Service
public class UserAccountHandselService {
    @Autowired
	protected UserAccountHandselDAO userAccountHandselDAO;
    @Transactional
    public void save(UserAccountHandsel entity){
    	userAccountHandselDAO.insert(entity);
    }
    @Transactional
    public UserAccountHandsel get(String userno){
    	return userAccountHandselDAO.find(userno);
    }
    @Transactional
    public UserAccountHandsel getWithLock(String userno,boolean lock){
    	return userAccountHandselDAO.findWithLock(userno, lock);
    }
}
