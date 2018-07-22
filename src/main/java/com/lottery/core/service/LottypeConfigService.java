package com.lottery.core.service;

import com.lottery.core.dao.LottypeConfigDAO;
import com.lottery.core.domain.LottypeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LottypeConfigService {
    @Autowired
	private LottypeConfigDAO dao;
    
	@Transactional
    public LottypeConfig saveOrUpdate(LottypeConfig lc2){
		LottypeConfig lc = dao.find(lc2.getLotteryType());
    	if(lc != null){
    		lc.setSaleEnabled(lc2.getSaleEnabled());
    		lc.setState(lc2.getState());
    		lc.setPrePhaseNum(lc2.getPrePhaseNum());
    		
    		lc.setOnprize(lc2.getOnprize());
    		lc.setAutoencash(lc2.getAutoencash());
    		lc.setLotcenterisvalid(lc2.getLotcenterisvalid());
    		
    		lc.setHeimaiForward(lc2.getHeimaiForward());
    		lc.setUploadForward(lc2.getUploadForward());
    		lc.setSingleHemaiForward(lc2.getSingleHemaiForward());
    		lc.setB2bForward(lc2.getB2bForward());
    		lc.setEndForward(lc2.getEndForward());
    		
    		lc.setWebEndSale(lc2.getWebEndSale());
    		lc.setIosEndSale(lc2.getIosEndSale());
    		lc.setAndroidEendSale(lc2.getAndroidEendSale());
    		lc.setB2bEndSale(lc2.getB2bEndSale());
    		lc.setIsAddPrize(lc2.getIsAddPrize());
    		
    		lc.setHemaiEndSale(lc2.getHemaiEndSale());
    		lc.setChaseEndSale(lc2.getChaseEndSale());
    		dao.merge(lc);
		}else{
			dao.insert(lc2);
		}
    	return lc;
    }
	
	@Transactional
    public void delete(String strs){
		String[] ids = strs.split(",");
		for (String id : ids) {
			dao.remove(dao.find(Integer.parseInt(id)));
		}
    }
	@Transactional
	public LottypeConfig get(Integer lotteryType){
		return dao.find(lotteryType);
	}
	
	@Transactional
	public List<LottypeConfig> getList(){
		return dao.findAll();
	}
}
