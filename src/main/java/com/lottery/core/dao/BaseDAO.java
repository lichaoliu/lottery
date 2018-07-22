package com.lottery.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * 所有的类继续该类，方便开发
 * @author fengqinyun
 * */
public abstract class BaseDAO {
	@PersistenceContext
	protected EntityManager entityManager;
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
