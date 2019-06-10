package com.lottery.core.dao;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.UserDrawBank;

import java.util.List;

public interface UserDrawBankDAO extends IGenericDAO<UserDrawBank, String> {
    /**
     * 根据账号查询
     * @param bankCard
     * **/
	public UserDrawBank getByBankCard(String bankCard);
    /**
     * 根据用户名查询银行卡信息
     * @param  userno 用户名
     * */
    public List<UserDrawBank> getByUserno(String userno);
    /**
     * 查询用户绑定信息
     * @param  userno 用户名
     * @param drawType 提现形式
     * */
    public UserDrawBank get(String userno,int drawType);

}
