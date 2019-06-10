package com.lottery.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.MemberAccountDAO;
import com.lottery.core.domain.terminal.MemberAccount;

@Repository
public class MemberAccountDAOImpl extends AbstractGenericDAO<MemberAccount, Long> implements MemberAccountDAO{

}
