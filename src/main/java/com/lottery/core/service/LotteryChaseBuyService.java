package com.lottery.core.service;

import com.lottery.common.PageBean;
import com.lottery.core.dao.LotteryChaseBuyDAO;
import com.lottery.core.domain.LotteryChaseBuy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LotteryChaseBuyService {
	private final Logger logger=LoggerFactory.getLogger(getClass());
    @Autowired
	private LotteryChaseBuyDAO lotteryChaseBuyDAO;
    @Transactional
    public void save(LotteryChaseBuy entity){
    	lotteryChaseBuyDAO.insert(entity);
    }
    /**
     * 根据追号id,彩种，期号查询执行追号
     * @param  chaseId 追号编号
     * @param  lotteryType 彩种
     * @param  phase 期号
     * */
    @Transactional
    public LotteryChaseBuy getByChaseIdLotteryTypePhase(String chaseId,int lotteryType,String phase){
           List<LotteryChaseBuy> list=lotteryChaseBuyDAO.getByChaseIdLotteryTypePhase(chaseId, lotteryType, phase);
           if(list.size()>1||list.size()==0){
        	   logger.error("追号chaseId={},彩种:{},期号:{},追号数量为:{}不止一个,请检查",new Object[]{chaseId, lotteryType, phase,list.size()});
               return null;
           }
           return list.get(0);
           
    }
    /**
     * 根据彩种，期号查询需要执行的追号
     * @param  lotteryType 彩种
     *                     @param  phase 期号
     * */
     @Transactional
     public List<LotteryChaseBuy> getByLotteryTypePhase(int lotteryType,String phase){
        return lotteryChaseBuyDAO.getByLotteryTypePhase(lotteryType,phase);
    }


     /**
      * 根据彩种，期号,追号类型查询执行追号
      * @param  lotteryType 彩种
      * @param  phase 期号
      * @param  chaseType 追号类型
      * */
      @Transactional
      public List<LotteryChaseBuy> getByLotteryTypePhaseChaseType(int lotteryType,String phase,int chaseType){
         return lotteryChaseBuyDAO.getByLotteryTypePhaseChaseType(lotteryType, phase, chaseType);
     }
      
  	/**
  	 * 根据追号id查询所有执行追号
  	 * @param chaseId 追号id
  	 * */
     @Transactional
  	public List<LotteryChaseBuy> getByChaseId(String chaseId){
  		return lotteryChaseBuyDAO.getByChaseId(chaseId);
  	}
  	/**
  	 * 根据追号id查询所有执行追号
  	 * @param chaseId 追号id
  	 * */
     @Transactional
  	public List<LotteryChaseBuy> getByChaseId(String chaseId,PageBean<LotteryChaseBuy> page){
  		return lotteryChaseBuyDAO.getByChaseId(chaseId,page);
  	}
	/**
	 * 根据订单 追号id,订单号查询执行追号
	 * @param chaseId 追号id
	 * @param orderId 订单号
	 * */
     @Transactional
	public LotteryChaseBuy getByChaseIdAndOrderId(String chaseId,String orderId){
         try{
             return lotteryChaseBuyDAO.getByChaseIdAndOrderId(chaseId, orderId);
         }catch (Exception e){
             return null;
         }

	}
	@Transactional
    public void update(LotteryChaseBuy lotteryChaseBuy){
    	lotteryChaseBuyDAO.merge(lotteryChaseBuy);
    }

    /**
     * 修改追号期的开始结束时间
     * @param lotteryType  彩种
     * @param phase  期号
     * @param startTime  开始时间
     * @param endTime 结束时间
     * */
    @Transactional
    public int updateLotteryChaseBuyPhaseTime(int lotteryType, String phase, Date startTime, Date endTime){
        return lotteryChaseBuyDAO.updateLotteryChaseBuyPhaseTime(lotteryType, phase, startTime, endTime);
    }

    /**
     * 根据追号id,状态查询所有执行追号
     * @param chaseId 追号id
     * @param chaseBuyStatus 执行追号状态
     * */
    @Transactional
    public List<LotteryChaseBuy> getByChaseIdAndStatus(String chaseId,int chaseBuyStatus){
        return lotteryChaseBuyDAO.getByChaseIdAndStatus(chaseId, chaseBuyStatus);
    }
}
