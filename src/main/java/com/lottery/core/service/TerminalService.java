package com.lottery.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.domain.terminal.Terminal;
@Service 
public class TerminalService {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private TerminalDAO terminalDAO;
    @Autowired
    private IdGeneratorDao  idDao;
	@Transactional
	public Long[] getIdsAllByTerminalType(int terminalType){
		Long[] ids=null;
		try{
			ids=terminalDAO.getTerminalIdByType(terminalType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ids;
	}
	@Transactional
	public Terminal get(Long id){
		return terminalDAO.find(id);
	}
	
	public Map<Long,Terminal> mget(List<Long> keylist){
		Map<Long,Terminal> map=new HashMap<Long,Terminal>();
		try{
			for(Long key:keylist){
				Terminal terminal=this.get(key);
				if(terminal!=null){
					map.put(key, terminal);
				}
				
			}
		}catch(Exception e){
			
		}
	
		return map;
	}
	   /**
	    * 插入新终端
	    */
	   @Transactional
		public void insert(String name,String isEnabled,String isPaused,String terminalType,String allotforbidperiod){
			Terminal terminal = new Terminal();
			try {
                Long id=idDao.getTeriminalId();
                terminal.setId(id);
				terminal.setAllotForbidPeriod(allotforbidperiod);
				terminal.setIsEnabled(Integer.parseInt(isEnabled));
				terminal.setIsPaused(Integer.parseInt(isPaused));
				terminal.setTerminalType(Integer.parseInt(terminalType));
				terminal.setName(name);
				terminalDAO.insert(terminal);
			} catch (LotteryException e) {
				logger.error("插入新终端失败",e);
			}
		}
	   @Transactional
	   public void save(Terminal terminal){
		   try{
               Long id=idDao.getTeriminalId();
               terminal.setId(id);
			   terminalDAO.insert(terminal);
		   }catch(Exception e){
			   logger.error("插入终端失败",e);
		   }
	   }
	   @Transactional
		public Terminal saveOrUpdate(Long id, String name, Integer isEnabled,
				Integer isPaused, Integer isCheckEnabled, Integer terminalType, String allotForbidPeriod,
				String sendForbidPeriod,String checkForbidPeriod) {
	    	Terminal terminal = null;
			if(id == null){
                id=idDao.getTeriminalId();
				terminal =  new Terminal();
                terminal.setId(id);
				terminal.setName(name);
				terminal.setIsEnabled(isEnabled);
				terminal.setIsPaused(isPaused);
				terminal.setTerminalType(terminalType);
				terminal.setAllotForbidPeriod(allotForbidPeriod);
				terminal.setSendForbidPeriod(sendForbidPeriod);
				terminal.setIsCheckEnabled(isCheckEnabled);
				terminal.setCheckForbidPeriod(checkForbidPeriod);
				terminalDAO.insert(terminal);
			}else{
				terminal = terminalDAO.find(id);
				terminal.setName(name);
				terminal.setIsEnabled(isEnabled);
				terminal.setIsPaused(isPaused);
				terminal.setTerminalType(terminalType);
				terminal.setAllotForbidPeriod(allotForbidPeriod);
				terminal.setSendForbidPeriod(sendForbidPeriod);
				terminal.setIsCheckEnabled(isCheckEnabled);
				terminal.setCheckForbidPeriod(checkForbidPeriod);
				terminalDAO.merge(terminal);
			}
			return terminal;
		}
	   @Transactional
	   public Terminal getByTerminalType(int terminalType){
		   Terminal terminal=null;
		   try{
			   terminal=terminalDAO.getByType(terminalType);
		   }catch(Exception e){
			   logger.error("通过terminalType={}查询出错",terminalType);
		   }
		   return terminal;
	   }
	   
	@Transactional
	public void delete(String strChecked) {
		String[] ids = strChecked.split(",");
		for (String id : ids) {
			Terminal terminal  = terminalDAO.find(Long.parseLong(id));
			terminalDAO.remove(terminal);
		}
	}
}
