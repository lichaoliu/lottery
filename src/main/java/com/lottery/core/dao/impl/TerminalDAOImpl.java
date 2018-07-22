package com.lottery.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.TerminalDAO;
import com.lottery.core.domain.terminal.Terminal;
@Repository
public class  TerminalDAOImpl extends AbstractGenericDAO<Terminal, Long>
		implements TerminalDAO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2869408380761543608L;

	@Override
	public Long[] getTerminalIdByType(int terminalType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("terminalType", terminalType);
		List<Terminal> list=findByCondition(map);
		Long[] longarry=new Long[list.size()];
		for(int i=0;i<list.size();i++){
			longarry[i]=list.get(i).getId();
		}
		return longarry;
	}

	@Override
	public Terminal getByType(int terminalType) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("terminalType", terminalType);
			return findByUnique(map);
	}

}
