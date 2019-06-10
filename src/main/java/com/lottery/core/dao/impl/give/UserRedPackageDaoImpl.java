package com.lottery.core.dao.impl.give;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.common.PageBean;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.core.dao.give.UserRedPackageDao;
import com.lottery.core.domain.give.UserRedPackage;

/**
 * Created by fengqinyun on 2017/3/27.
 */
@Repository
public class UserRedPackageDaoImpl extends AbstractGenericDAO<UserRedPackage,String> implements UserRedPackageDao{
    @Override
    public List<UserRedPackage> getReceive(String userno) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("receiveUserno",userno);
        return findByCondition(map);
    }

    @Override
    public List<UserRedPackage> getReceive(String userno, int status) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("receiveUserno",userno);
        map.put("status",status);
        return findByCondition(map);
    }

    @Override
    public int updateUserno(String phoneno, String userno) {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("receiveUserno",userno);
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("receivePhone",phoneno);
        return update(conditionMap,whereMap);
    }
    
    @Override
    public List<UserRedPackage> getReceiveRedPackages(String userno,PageBean<UserRedPackage> page) {
    	Map<String,Object> map=new HashMap<String,Object>();
        map.put("receiveUserno",userno);
    	return findPageByCondition(map, page, "order by giveTime desc");
    }
    
    @Override
    public List<UserRedPackage> getGiveRedPackages(String userno,PageBean<UserRedPackage> page) {
    	Map<String,Object> map=new HashMap<String,Object>();
        map.put("giveUserno",userno);
    	return findPageByCondition(map, page, "order by giveTime desc");
    }
}
