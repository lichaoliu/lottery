package com.lottery.core.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.AdminPage;
import com.lottery.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.SystemExceptionMessageDao;
import com.lottery.core.domain.SystemExceptionMessage;

/**
 * Created by fengqinyun on 16/7/9.
 */
@Service
public class SystemExcepionMessageService {
    @Autowired
    private SystemExceptionMessageDao systemExceptionMessageDao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Transactional
    public void save(SystemExceptionMessage systemExceptionMessage){
        systemExceptionMessage.setId(idGeneratorDao.getSystemExceptionMessageId());
        systemExceptionMessageDao.insert(systemExceptionMessage);
    }

@Transactional
public AdminPage<SystemExceptionMessage> page(int start, int limit, String param) throws UnsupportedEncodingException {
    AdminPage<SystemExceptionMessage> page = new AdminPage<SystemExceptionMessage>(start, limit, "order by createTime desc");
    Map<String, Object> conditionMap = JsonUtil.transferJson2Map(param);
    systemExceptionMessageDao.findPageByCondition(conditionMap, page);
    List<SystemExceptionMessage> list = page.getList();
    for (SystemExceptionMessage sem : list) {
        sem.setMessage(new String(sem.getMessage().getBytes("ISO-8859-1")));
    }
    return page;
}
    
    @Transactional
    public int updateRead(String ids){
    	Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("isRead", YesNoStatus.yes.value);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<Long> lid = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			lid.add(Long.parseLong(id));
		}
		whereMap.put("ids", lid);
		return systemExceptionMessageDao.update(conditionMap, "id in (:ids)", whereMap);
    }
}
