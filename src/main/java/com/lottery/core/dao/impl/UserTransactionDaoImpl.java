package com.lottery.core.dao.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.UserTransactionDao;
import com.lottery.core.domain.UserTransaction;
@Repository
public class UserTransactionDaoImpl extends AbstractGenericDAO<UserTransaction, String>
		implements UserTransactionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083444356337319087L;

	@Override
	public void updateStatus(String id,int status) {
		Map<String, Object> contentMap=new HashMap<String, Object>();
		contentMap.put("status", status);

		Map<String, Object> whereMap=new HashMap<String, Object>();
		whereMap.put("id", id);
		this.update(contentMap,whereMap);
	}


	public UserTransaction getUserTransaction(String id,int status) throws Exception {
		Map<String, Object> containtMap = new HashMap<String, Object>();
		containtMap.put("id", id);
		containtMap.put("status", status);
		return findByUnique(containtMap);
	}
	/**
	 * 根据用户查询充值成功订单数量
	 *
     * @param userId@return
	 */
	@Override
	public int findTransactionsSuccess(String userId,int status){
		
		int count=getEntityManager().createQuery("select count(u)  from  UserTransaction u where userno=? and status=?",Long.class).setParameter(1, userId)
		.setParameter(2, status).getSingleResult().intValue();
		return count;
	}

	@Override
	public List<UserTransaction> getTransationList(String userno,
			Date startTime, Date endTime, PageBean<UserTransaction> page) {
		String whereSql="userno=:userno and createTime>=:startTime and createTime<:endTime";
		String orderBysql="order by createTime desc";
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userno", userno);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return findPageByCondition(whereSql, map, page, orderBysql);
	}

	
}
