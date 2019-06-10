package com.lottery.core.dao.give;

import java.util.List;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.give.UserRedPackage;

/**
 * Created by fengqinyun on 2017/3/27.
 */
public interface UserRedPackageDao extends IGenericDAO<UserRedPackage,String> {
    /**
     * 查询接收的红包
     * @param userno 用户编号
     * */
    public List<UserRedPackage> getReceive(String userno);
    /**
     * 查询接收的红包
     * @param userno 用户编号
     * @param status 红包状态
     * */
    public List<UserRedPackage> getReceive(String userno,int status);
    /**
     * 通过手机号码修改用户编号
     * @param phoneno 手机号码
     * @param userno 用户编码
     * */
    public int updateUserno(String phoneno,String userno);
    
    /**
     * 查询接收的红包
     * @param userno 用户编号
     * @param status 红包状态
     * */
    public List<UserRedPackage> getReceiveRedPackages(String userno,PageBean<UserRedPackage> page);
    
    
    /**
     * 查询赠送的红包
     * @param userno 用户编号
     * @param status 红包状态
     * */
    public List<UserRedPackage> getGiveRedPackages(String userno,PageBean<UserRedPackage> page);
}
