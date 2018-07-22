package com.lottery.core.dao;

import java.util.Date;
import java.util.List;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.UserTransaction;
public interface UserTransactionDao extends IGenericDAO<UserTransaction, String>{
    
	public void updateStatus(String id,int status);
	
	public int findTransactionsSuccess(String userno,int status);

	/**
	 * 查询交易记录
	 * @param userno 用户编号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 分页
	 * 
	 * */
	public List<UserTransaction> getTransationList(String userno,Date startTime,Date endTime,PageBean<UserTransaction> page);
	
	public UserTransaction getUserTransaction(String id,int status) throws Exception;
}
