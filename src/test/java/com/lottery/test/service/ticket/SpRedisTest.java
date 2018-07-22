package com.lottery.test.service.ticket;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.cache.redis.RedisPersist;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.service.JczqRaceService;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.JczqMatchSpService;
import com.lottery.test.SpringBeanTest;

public class SpRedisTest extends SpringBeanTest {

	
	@Autowired
	JczqRaceService jczqRaceService;
	@Autowired
	protected JczqMatchSpService jczqMatchSpService;
	@Autowired
	private SharedJedisPoolManager shareJedisPoolManager;
	@Test
	public void testSp(){
		
		try{
			List<JczqRace> raceList=jczqRaceService.getByStatus(RaceStatus.OPEN.value);
			for(JczqRace jczqRace:raceList){
				MatchSpDomain sp=jczqMatchSpService.get(jczqRace.getMatchNum());
				jczqRace.setSpStr(JsonUtil.toJson(sp));
			}
			
	     RedisPersist persist=new RedisPersist(shareJedisPoolManager);
	     
	     persist.save("jczqrace", raceList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
}
