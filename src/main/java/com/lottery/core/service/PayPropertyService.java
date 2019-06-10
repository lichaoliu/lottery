package com.lottery.core.service;

import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.PayPropertyDAO;
import com.lottery.core.domain.PayProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PayPropertyService {
//	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	PayPropertyDAO payPropertyDao;
	@Autowired
    private IdGeneratorDao idGeneratorDao;
	

	/**
	 * 
	 * 根据渠道查支付信息
	 */
	@Transactional
	public List<PayProperty> getPayConfigList(String payChannelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payChannel",payChannelId);
		return payPropertyDao.findByCondition(map);
	}

	@Transactional
	public PayProperty get(Long id){
	   return payPropertyDao.find(id);
	}
	
	@Transactional
    public PayProperty saveOrUpdate(PayProperty payConfig){
    	if(payConfig.getId() != null){
    		PayProperty pc = payPropertyDao.find(payConfig.getId());
    		pc.setConfigName(payConfig.getConfigName());
    		pc.setConfigValue(payConfig.getConfigValue());
    		pc.setPayChannel(payConfig.getPayChannel());
    		payPropertyDao.merge(pc);
		}else{
            Long id=idGeneratorDao.getPayPropertyId();
            payConfig.setId(id);
			payPropertyDao.insert(payConfig);
		}
    	return payConfig;
    }

	@Transactional
	public void delete(String strChecked) throws CacheNotFoundException, CacheUpdateException {
		String[] ids = strChecked.split(",");
		for (String id : ids) {
			PayProperty payConfig = payPropertyDao.find(Long.parseLong(id));
			payPropertyDao.remove(payConfig);
		}
	}
}
