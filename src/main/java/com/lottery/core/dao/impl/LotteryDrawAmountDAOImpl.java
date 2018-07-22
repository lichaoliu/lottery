package com.lottery.core.dao.impl;

import java.util.*;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.AdminPage;
import com.lottery.common.PageBean;
import com.lottery.common.contains.DrawAmountStatus;
import com.lottery.common.dao.AbstractGenericDAO;
import com.lottery.common.util.StringUtil;
import com.lottery.controller.admin.dto.CashRecord;
import com.lottery.core.dao.LotteryDrawAmountDAO;
import com.lottery.core.domain.LotteryDrawAmount;

@Repository
public class LotteryDrawAmountDAOImpl extends AbstractGenericDAO<LotteryDrawAmount, String> implements LotteryDrawAmountDAO {

    /**
     *
     */
    private static final long serialVersionUID = -9127347619397576806L;

    /**
     * 查询所有审核完成的提现记录
     *
     * @return
     */
    @Transactional
    public List<LotteryDrawAmount> queryDrawAmountList(int drawType, String id) {
        List<String> idList = new ArrayList<String>();
        String ids[] = id.split(",");
        if (ids != null)
            for (int i = 0; i < ids.length; i++) {
                idList.add(ids[i]);
            }
        StringBuffer sb = new StringBuffer();
        sb.append("drawType =:drawType and status=:status");
        if (idList.size() > 0) {
            sb.append(" and id in(:ids)");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("drawType", drawType);
        map.put("status", DrawAmountStatus.haschecked.getValue());
        if (idList.size() > 0) {
            map.put("ids", idList);
        }
        List<LotteryDrawAmount> list = this.findByCondition(sb.toString(), map);
        return list;
    }




    /**
     * 更新
     */
    public void updateLotteryDrawStatus(String id,int status,String batchId) {
        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("batchId",batchId);
        contentMap.put("status", status);
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("id", id);
        this.update(contentMap, whereMap);
    }


    public void updateStatusById(String id, int status) {
        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("status", status);
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("id", id);
        this.update(contentMap, whereMap);
    }


    public void updateStatusByBatchId(String batchId, int status) {
        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("status", status);
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("batchId", batchId);
        this.update(contentMap, whereMap);
    }


    @Override
    public List<String> findLotteryDrawList(int status, int drawType,int operateType) {
        TypedQuery<String> query = getEntityManager().createQuery("select distinct(o.batchId) from LotteryDrawAmount o where o.status=? and drawType=? and operateType=? and  submitTime<=? ", String.class);
        query.setParameter(1, status).setParameter(2, drawType).setParameter(3, operateType);
        Calendar deadlineCalendar = Calendar.getInstance();
        deadlineCalendar.add(Calendar.MINUTE, -15);
        query.setParameter(4, deadlineCalendar.getTime());
        return query.getResultList();

    }

    /**
     * 根据批次查看数量
     */
    @Override
    public Integer findLotteryDrawCount(int status, int drawType, String batchId) {
        return getEntityManager()
                .createQuery("select count(o.batchId) from LotteryDrawAmount o where o.status=? and drawType=? and batchId=?", String.class)
                .setParameter(1, status).setParameter(2, drawType).setParameter(3, batchId).getFirstResult();

    }

    @Override
    public void findExcelRecord(Integer status, String batchId, AdminPage<CashRecord> page) {
        String where = "";
        if (!StringUtil.isEmpty(batchId)) {
            where = " and batch_id like '" + batchId + "%'";
        }
        String sql = "select count(t.id), batch_id from lottery_draw_amount t where status = :status " + where + " group by batch_id ";


        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("status", status);
        q.setFirstResult(page.getStart()).setMaxResults(page.getLimit());
        List<Object[]> list = q.getResultList();
        List<CashRecord> cashRecords = new ArrayList<CashRecord>();
        for (Object[] objects : list) {
            CashRecord record = new CashRecord();
            record.setCount(objects[0].toString());
            record.setBatchId(objects[1]==null?"":objects[1].toString());
            cashRecords.add(record);
        }
        page.setList(cashRecords);

        String countSql = "SELECT count(*) FROM (" + sql + ") o";
        Query total = getEntityManager().createNativeQuery(countSql);
        total.setParameter("status", status);
        page.setTotalResult(Integer.parseInt(total.getSingleResult().toString()));

        getEntityManager().clear();
    }

    @Override
    public List<LotteryDrawAmount> getDrawAmountList(String userno, Date startTime, Date endTime, PageBean<LotteryDrawAmount> page) {
        String whereSql = "userno=:userno ";
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userno", userno);
        if (startTime != null) {
            whereSql += " and createTime>=:startTime";
            condition.put("startTime", startTime);
        }
        if (endTime != null) {
            whereSql += " and createTime<:endTime";
            condition.put("endTime", endTime);
        }
        String orderBy = "order by createTime desc";
        return findPageByCondition(whereSql, condition, page, orderBy);
    }


    public List<LotteryDrawAmount> getByBatchId(String batchId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("batchId", batchId);
        return findByCondition(map);
    }

    @Override
    public List<LotteryDrawAmount> queryDrawAmountList(int drawType, int status, String batchId) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("drawType",drawType);
        map.put("status",status);
        map.put("batchId",batchId);

        return findByCondition(map);
    }

    @Override
    public List<LotteryDrawAmount> getByStatusAndDrawType(int max, int status, int drawType) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("drawType",drawType);
        map.put("status",status);
        return  findByCondition(max,map);
    }

}
