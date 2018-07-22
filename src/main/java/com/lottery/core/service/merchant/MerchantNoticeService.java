package com.lottery.core.service.merchant;

import com.lottery.core.dao.merchant.MerchantNoticeDao;
import com.lottery.core.domain.merchant.Merchant;
import com.lottery.core.domain.merchant.MerchantNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fengqinyun on 16/8/5.
 */
@Service
public class MerchantNoticeService {
    @Autowired
    private MerchantNoticeDao merchantNoticeDao;
    @Transactional
    public void save(MerchantNotice merchantNotice){
        merchantNoticeDao.insert(merchantNotice);
    }
    @Transactional
    public MerchantNotice get(String orderid){
       return merchantNoticeDao.find(orderid);
    }
}
