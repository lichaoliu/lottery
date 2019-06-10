package com.lottery.core.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lottery.common.AdminPage;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.LotteryLogicMachineDAO;
import com.lottery.core.domain.ticket.LogicMachinePK;
import com.lottery.core.domain.ticket.LotteryLogicMachine;

@Repository
public class LottertLogicMachineDAOImpl extends AbstractGenericDAO<LotteryLogicMachine, LogicMachinePK> implements LotteryLogicMachineDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 742913374180643913L;

	@Override
	public void findPageById(Long id, Integer terminalType, Integer lotteryType, AdminPage<LotteryLogicMachine> page) {

		String sql = "SELECT o FROM LotteryLogicMachine o ";
		String countSql = "SELECT count(o.pk.id) FROM LotteryLogicMachine o ";
		StringBuilder whereSql = new StringBuilder();
		if (id != null) {
			whereSql.append("WHERE o.pk.id = :id");
		}
		if (terminalType != null) {
			whereSql.append("WHERE o.pk.terminalType = :terminalType");
		}
		if (lotteryType != null) {
			whereSql.append("WHERE o.pk.lotteryType = :lotteryType");
		}
		String normalSql = sql + whereSql.toString() + page.getOrderby();
		String normalCountSql = countSql + whereSql;
		TypedQuery<LotteryLogicMachine> q = this.getEntityManager().createQuery(normalSql, LotteryLogicMachine.class);
		TypedQuery<Long> total = this.getEntityManager().createQuery(normalCountSql, Long.class);
		if (id != null) {
			q.setParameter("id", id);
			total.setParameter("id", id);
		}
		if (terminalType != null) {
			q.setParameter("terminalType", terminalType);
			total.setParameter("terminalType", terminalType);
		}
		if (lotteryType != null) {
			q.setParameter("lotteryType", lotteryType);
			total.setParameter("lotteryType", lotteryType);
		}
		q.setFirstResult(page.getStart()).setMaxResults(page.getLimit());
		List<LotteryLogicMachine> resultList = q.getResultList();
		int count = total.getSingleResult().intValue();
		page.setList(resultList);
		page.setTotalResult(count);
	}

	public LotteryLogicMachine getLotteryLogicMachine(int lottery, Long terminalid, int status) {
		String sql = "select o from LotteryLogicMachine o where o.pk.lotteryType=:lotteryType and o.terminalId=:terminalId " + "  and o.status=:status order by o.currentId desc";
		TypedQuery<LotteryLogicMachine> q = this.getEntityManager().createQuery(sql, LotteryLogicMachine.class);
		q.setParameter("lotteryType", lottery);
		q.setParameter("terminalId", terminalid);
		q.setParameter("status", status);
		List<LotteryLogicMachine> lotMachines = q.getResultList();
		if (lotMachines != null && lotMachines.size() > 0) {
			return lotMachines.get(0);
		}
		return null;
	}

	

	@Override
	public List<LotteryLogicMachine> getByTerminalAndLotteryType(int terminalType, int lotteryType) {
		String sql = "select o from LotteryLogicMachine o where o.pk.lotteryType=:lotteryType and o.pk.terminalType=:terminalType";
		TypedQuery<LotteryLogicMachine> query = this.getEntityManager().createQuery(sql, LotteryLogicMachine.class);
		query.setParameter("lotteryType", lotteryType);
		query.setParameter("terminalType", terminalType);
		return query.getResultList();
	}

	@Override
	public List<LotteryLogicMachine> getByTerminalLotteryAndId(int terminalType, int lotteryType, Long terminalId) {
		String sql = "select o from LotteryLogicMachine o where o.pk.lotteryType=:lotteryType and o.pk.terminalType=:terminalType and o.terminalId=:terminalId order by currentId asc";
		TypedQuery<LotteryLogicMachine> query = this.getEntityManager().createQuery(sql, LotteryLogicMachine.class);
		query.setParameter("lotteryType", lotteryType);
		query.setParameter("terminalType", terminalType);
		query.setParameter("terminalId", terminalId);
		return query.getResultList();
	}

	@Override
	public List<LotteryLogicMachine> getByTerminalType(int terminalType) {
		String sql = "select o from LotteryLogicMachine o where  o.pk.terminalType=:terminalType";
		TypedQuery<LotteryLogicMachine> query = this.getEntityManager().createQuery(sql, LotteryLogicMachine.class);
		query.setParameter("terminalType", terminalType);
		return query.getResultList();
	}

}
