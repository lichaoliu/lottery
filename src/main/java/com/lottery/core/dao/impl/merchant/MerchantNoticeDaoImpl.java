package com.lottery.core.dao.impl.merchant;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.merchant.MerchantNoticeDao;
import com.lottery.core.domain.merchant.MerchantNotice;
import org.springframework.stereotype.Repository;

/**
 * Created by fengqinyun on 16/8/5.
 */
@Repository
public class MerchantNoticeDaoImpl extends AbstractGenericDAO<MerchantNotice, String> implements MerchantNoticeDao {
}
