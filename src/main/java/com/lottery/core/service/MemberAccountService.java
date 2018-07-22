package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.core.dao.MemberAccountDAO;
import com.lottery.core.domain.terminal.MemberAccount;

@Service
public class MemberAccountService {
    @Autowired
	protected MemberAccountDAO memberAccountDAO;
    
    @Transactional
	public MemberAccount get(Long id){
		return memberAccountDAO.find(id);
	}
    @Transactional
    public  void update(MemberAccount entity){
    	memberAccountDAO.merge(entity);
    }
    
	@Transactional
	public MemberAccount merge(MemberAccount member) {
		if(member == null||member.getId()==null){
			return null;
		}
		MemberAccount ma  = memberAccountDAO.find(member.getId());
		if(ma != null){
			ma.setBalance(member.getBalance());
			ma.setBigPrize(member.getBigPrize());
			ma.setAgentCode(member.getAgentCode());
			ma.setCreditBalance(member.getCreditBalance());
			ma.setSmallPrize(member.getSmallPrize());
			ma.setTerminalName(member.getTerminalName());
			ma.setTotalPrize(member.getTotalPrize());
			ma.setUpdateTime(new Date());
			memberAccountDAO.update(ma);
			return ma;
		}else{
			memberAccountDAO.insert(member);
		}
		return member;
		
	}
	
	@Transactional
	public void updateMemberAccount(Long id, BigDecimal warnAmount,int smsFlag,int isSync) {
		MemberAccount ma = memberAccountDAO.find(id);
		ma.setWarnAmount(warnAmount);
		ma.setSmsFlag(smsFlag);
		ma.setIsSync(isSync);
		memberAccountDAO.merge(ma);
		
	}
}
