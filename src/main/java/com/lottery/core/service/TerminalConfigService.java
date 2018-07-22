package com.lottery.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.TerminalConfigDAO;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
@Service
public class TerminalConfigService {
	
    @Autowired
	private TerminalConfigDAO dao;
    @Autowired
    private TerminalDAO terminalDAO;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Transactional
    public Long[] getByLotteryType(int lotteryType) {
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("lotteryType", lotteryType);
    	map.put("playType",PlayType.mix.value);
    	List<TerminalConfig> list=dao.findByCondition(map);
        Long[] longarr=new Long[list.size()];
        for(int i=0;i<list.size();i++){
        	longarr[i]=list.get(i).getId();
        }
        return longarr;
    }
    @Transactional
    public TerminalConfig get(Long id){
    	return dao.find(id);
    }
    
    @Transactional
    public Long[] getByTerminalId(Long terminalId) {
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("terminalId", terminalId);
    	List<TerminalConfig> list=dao.findByCondition(map);
        Long[] longarr=new Long[list.size()];
        for(int i=0;i<list.size();i++){
        	longarr[i]=list.get(i).getId();
        }
        return longarr;
    }
    
    public Map<Long,TerminalConfig> mget(List<Long> keylist){
    	Map<Long,TerminalConfig> map=new HashMap<Long,TerminalConfig>();
    	for(Long key:keylist){
    		TerminalConfig terminalConfig=this.get(key);
    		if(terminalConfig!=null){
    			map.put(key, terminalConfig);
    		}
    	}
    	  return map;
    }
	/***
	 * 根据彩种类型,终端id,玩法查询终端
	 * @param terminalId 终端id
	 * @param lotteryType 彩种类型
	 * @param playType 玩法
	 * */
    @Transactional
    public TerminalConfig get(int lotteryType, Long terminalId,int playType){
    	try{
        	return dao.get(lotteryType, terminalId, playType);
    	}catch(Exception e){
    		
    	  return null;
    	}
    }
   
    @Transactional
    public TerminalConfig saveOrUpdate(TerminalConfig terminalConfig, String mixContain){
    	if(terminalConfig.getId() != null && terminalConfig.getId() != 0L){
			TerminalConfig tc = dao.find(terminalConfig.getId());
			tc.setAllotForbidPeriod(terminalConfig.getAllotForbidPeriod());
			tc.setIsEnabled(terminalConfig.getIsEnabled());
			tc.setIsPaused(terminalConfig.getIsPaused());
			tc.setLotteryType(terminalConfig.getLotteryType());
			if(terminalConfig.getPlayType()==null){
		       tc.setPlayType(PlayType.mix.getValue());
			}else{
				tc.setPlayType(terminalConfig.getPlayType());
			}
			tc.setSendForbidPeriod(terminalConfig.getSendForbidPeriod());
			tc.setTerminalId(terminalConfig.getTerminalId());
			tc.setIsCheckEnabled(terminalConfig.getIsCheckEnabled());
			tc.setCheckForbidPeriod(terminalConfig.getCheckForbidPeriod());
			Terminal terminal = terminalDAO.find(terminalConfig.getTerminalId());
			if(terminal != null){
				tc.setTerminalType(terminal.getTerminalType());
				tc.setTerminalName(terminal.getName());
			}
			tc.setTerminateForward(terminalConfig.getTerminateForward());
			tc.setIsCheckEnabled(terminalConfig.getIsCheckEnabled());
			tc.setWeight(terminalConfig.getWeight());
			tc.setTerminateAllotForward(terminalConfig.getTerminateAllotForward());
			
			tc.setAmountEnabled(terminalConfig.getAmountEnabled());
			tc.setPreferAmountRegion(terminalConfig.getPreferAmountRegion());
			
			tc.setPlayTypeNotContain(terminalConfig.getPlayTypeNotContain());
		
			tc.setPlayTypeNotContainEnabled(terminalConfig.getPlayTypeNotContainEnabled());
			tc.setMixContain(mixContain);
			tc.setSendWait(terminalConfig.getSendWait());
			tc.setCheckWait(terminalConfig.getCheckWait());
			dao.merge(tc);
			return tc;
		}else{
            Long id=idGeneratorDao.getTerminalConfigId();
            terminalConfig.setId(id);
			Terminal terminal = terminalDAO.find(terminalConfig.getTerminalId());
			if(terminal != null){
				terminalConfig.setTerminalType(terminal.getTerminalType());
				terminalConfig.setTerminalName(terminal.getName());
			}
			if(terminalConfig.getPlayType()==null){
				terminalConfig.setPlayType(PlayType.mix.getValue());
			}
			terminalConfig.setMixContain(mixContain);
			dao.insert(terminalConfig);
			return terminalConfig;
		}
    }
    @Transactional
    public Long[] getAllTerminalConfigId(){
    	try{
    		List<TerminalConfig> list=dao.findAll();
    		if(list!=null&&list.size()>0){
    			Long[] ids=new Long[list.size()];
    			for(int i=0;i<list.size();i++){
    				ids[i]=list.get(i).getId();
    			}
    			return ids;
    		}
    	}catch(Exception e){
    		
    	}
    	return null;
    }
    @Transactional
    public Long[] getTerminalConfigIdListByPlayType(PlayType playType){
    	try{
    		List<TerminalConfig> list=dao.getTerminalConfigListByPlayType(playType);
    		if(list!=null&&list.size()>0){
    			Long[] ids=new Long[list.size()];
    			for(int i=0;i<list.size();i++){
    				ids[i]=list.get(i).getId();
    			}
    			return ids;
    		}	
    	}catch(Exception e){
    		
    	}
    	return null;
    }
	/***
	 * 根据终端类型,彩种,玩法查询终端
	 * @param terminalType 终端类型
	 * @param lotteryType 彩种类型
	 * @param playType 玩法
	 * */
    @Transactional
    public List<TerminalConfig> getByTerminalTypeAndLotteryType(int terminalType,int lotteryType,int playType){
    	return dao.get(terminalType, lotteryType, playType);
    }
    @Transactional
    public void update(TerminalConfig entity){
    	dao.update(entity);
    }
    
    @Transactional
	public void delete(String strChecked) {
    	String[] ids = strChecked.split(",");
		for (String id : ids) {
			TerminalConfig terminal  = dao.find(Long.parseLong(id));
			dao.remove(terminal);
		}
	}
    
    /***
	 * 根据终端类型,彩种查询终端
	 * @param terminalType 终端类型
	 * @param lotteryType 彩种类型
	 * */
    @Transactional
	public  List<TerminalConfig> getByTerminalTypeAndLotteryType(int terminalType,int lotteryType){
		return dao.getByTerminalTypeAndLotteryType(terminalType, lotteryType);
	}
	/***
	 * 根据终端类型,彩种查询终端
	 * @param terminalId 终端id
	 * @param lotteryType 彩种类型
	 * */
	public List<TerminalConfig> getByTerminalIdAndLotteryType(Long terminalId,int lotteryType){
		return dao.getByTerminalIdAndLotteryType(terminalId, lotteryType);
	}
}
