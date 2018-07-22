package com.lottery.core.dao.impl;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.SystemExceptionMessageDao;
import com.lottery.core.domain.SystemExceptionMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by fengqinyun on 16/7/9.
 */
@Repository
public class SystemExceptionMessageDaoImpl extends AbstractGenericDAO<SystemExceptionMessage,Long> implements SystemExceptionMessageDao {
	
	
}
