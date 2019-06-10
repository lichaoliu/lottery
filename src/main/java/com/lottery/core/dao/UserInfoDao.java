package com.lottery.core.dao;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.UserInfo;
public interface UserInfoDao extends IGenericDAO<UserInfo, String>{
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * */
    public UserInfo getByUserName(String username) throws Exception;
    /**
     * 根据手机号码查询用户
     * @param phaseNo 手机号码
     * */
    public UserInfo getByPhoneNo(String phoneNO) throws Exception;
  
	
}
