package com.lottery.core.dao.impl.print;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.print.PrintTicketInfoDao;
import com.lottery.core.domain.print.PrintTicketInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by fengqinyun on 16/11/12.
 */
@Repository
public class PrintTicketInfoDaoImpl  extends AbstractGenericDAO<PrintTicketInfo,String> implements PrintTicketInfoDao {
}
