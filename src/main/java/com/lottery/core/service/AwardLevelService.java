package com.lottery.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.dao.AwardLevelDao;
import com.lottery.core.domain.AwardLevel;
import com.lottery.core.domain.AwardLevelPK;

@Service
public class AwardLevelService {

	@Autowired
	protected AwardLevelDao awardLevelDao;
	@Transactional
	public void merge(AwardLevel awardLevel){
		if(awardLevelDao.isContains(awardLevel)){
			awardLevelDao.merge(awardLevel);
		}else{
			AwardLevelPK pk=awardLevel.getId();
			AwardLevel leven=awardLevelDao.find(pk);
			int lotteryType=pk.getLotterytype();
			LotteryDrawPrizeAwarder awarder=LotteryDrawPrizeAwarder.get(pk.getPrizelevel(), LotteryType.get(lotteryType));
			if(leven==null){
				leven=new AwardLevel();
				leven.setId(pk);
				leven.setLevelname(awarder.name);
				leven.setPrize(awardLevel.getPrize());
				leven.setExtraPrize(0);
				awardLevelDao.insert(leven);
			}else{
				leven.setLevelname(awarder.name);
				leven.setPrize(awardLevel.getPrize());
				awardLevelDao.merge(leven);
			}
		}
		
	}
	@Transactional
	public void update(AwardLevel awardLevel){
		awardLevelDao.merge(awardLevel);
	}
	@Transactional
	public void delete(AwardLevel awardLevel){
		awardLevelDao.remove(awardLevel);
	}
	@Transactional
	public void delete(String pks){
		String[] ids=pks.split(",");
		for(int i=0;i<ids.length;i++){
			String[] levelprizes=ids[i].split("_");
			AwardLevelPK pk=new AwardLevelPK();
			pk.setLotterytype(Integer.valueOf(levelprizes[0]));
			pk.setPrizelevel(levelprizes[1]);
			AwardLevel awardLevel=awardLevelDao.find(pk);
			awardLevelDao.remove(awardLevel);
			
					
		}
	}
	
	
}
