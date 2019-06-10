package com.lottery.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.core.IdGeneratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.dao.TerminalPropertyDAO;
import com.lottery.core.domain.terminal.TerminalProperty;

@Service
public class TerminalPropertyService {
    @Autowired
	private TerminalPropertyDAO dao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    public List<TerminalProperty> getList(Long terminalId){
    	Map<String,Object> map =new HashMap<String,Object>();
    	map.put("terminalId", terminalId);
    	return dao.findByCondition(map);
    }
    
    @Transactional
    public TerminalProperty saveOrUpdate(TerminalProperty terminalProperty){
    	if(terminalProperty.getId() != null){
    		TerminalProperty tc = dao.find(terminalProperty.getId());
    		tc.setTerminalId(terminalProperty.getTerminalId());
    		tc.setTerminalKey(terminalProperty.getTerminalKey());
    		tc.setTerminalValue(terminalProperty.getTerminalValue());
			dao.merge(tc);
		}else{
            Long id=idGeneratorDao.getTerminalPropertyId();
            terminalProperty.setId(id);
			dao.insert(terminalProperty);
		}
    	return terminalProperty;
    }

	@Transactional
	public void delete(String strChecked) throws CacheNotFoundException, CacheUpdateException {
		String[] ids = strChecked.split(",");
		for (String id : ids) {
			TerminalProperty property = dao.find(Long.parseLong(id));
			dao.remove(property);
		}
	}
}
