package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.dao.LotteryUploadFileDAO;
import com.lottery.core.domain.ticket.LotteryUploadFile;

@Service
public class LotteryUploadFileService {
    @Autowired
	private LotteryUploadFileDAO dao;
    @Transactional
    public void save(LotteryUploadFile entity){
    	dao.insert(entity);
    }
    @Transactional
    public LotteryUploadFile get(String id){
    	return dao.find(id);
    }
    @Transactional
    public LotteryUploadFile save(String userno, String betcode,String orderId,int lotteryType,String phase, int multiple, int amount,int oneAmount,String fileName){
    	LotteryUploadFile entity=new LotteryUploadFile();
    	entity.setAmount(new BigDecimal(amount));
    	entity.setContent(betcode);
    	entity.setCreateTime(new Date());
    	entity.setId(orderId);
    	entity.setFileName(fileName);
    	entity.setLotteryType(lotteryType);
    	entity.setPhase(phase);
    	entity.setMultiple(multiple);
    	entity.setUserno(userno);
    	dao.insert(entity);
    	return entity;
    }
    
}
