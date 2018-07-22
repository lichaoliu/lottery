package com.lottery.core.dao.impl;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TicketSplitConfigDao;
import com.lottery.core.domain.ticket.TicketSplitConfig;
import com.lottery.core.domain.ticket.TicketSplitConfigPK;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;



/**
 * Created by fengqinyun on 2016/12/25.
 */
@Repository
public class TicketSplitConfigDaoImpl extends AbstractGenericDAO<TicketSplitConfig,TicketSplitConfigPK> implements TicketSplitConfigDao {

    public TicketSplitConfig find(TicketSplitConfigPK pk){
        String sql="select o from TicketSplitConfig o where o.id.lotteryType=:lotteryType and o.id.playType=:playType and o.id.splitType=:splitType";
        TypedQuery<TicketSplitConfig> q=getEntityManager().createQuery(sql,TicketSplitConfig.class);
        q.setParameter("lotteryType",pk.getLotteryType());
        q.setParameter("playType",pk.getPlayType());
        q.setParameter("splitType",pk.getSplitType());
        return q.getSingleResult();
    }
}
