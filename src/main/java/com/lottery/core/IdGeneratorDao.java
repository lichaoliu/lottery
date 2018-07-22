package com.lottery.core;

import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import com.lottery.common.util.MD5;
import com.lottery.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.dao.BaseDAO;

@Service
@Transactional
public class IdGeneratorDao extends BaseDAO{
	@Value("${id.prefix}")
	private String prefix;

	public Long get(SeqEnum seq,int count){
		IdGenerator Id=entityManager.find(IdGenerator.class,seq.id,LockModeType.PESSIMISTIC_WRITE);
		Long returnValue = 0L;
		Long nextValue = 0L;
		int add=count;
		if(add<=0){
			add=1;
		}
		if(Id==null){
			returnValue=1l;
			nextValue += add;
			Id=new IdGenerator();
			Id.setId(seq.id);
			Id.setSeqid(nextValue);
			Id.setSequeueName(seq.name);
			entityManager.persist(Id);
			entityManager.flush();
		}else{
			returnValue=Id.getSeqid()+1;
			nextValue =Id.getSeqid()+ add;
			Id.setSeqid(nextValue);
			entityManager.merge(Id);
			entityManager.flush();
		}
		
		return returnValue;
	}
	
    public String getCommonId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "MMdd");
		return String.format("%s%08d",dateStr, get(SeqEnum.all, 1));
    }
	public Long get(SeqEnum seq){
		return get(seq, 1);
	}
	//获取用户编号
	public String getUserno(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%08d",dateStr, get(SeqEnum.userid, 1));
	}

	//以下方法是获取 订单id  
	//---start--
	
	public Long getOrderId(int count){//获得多个订单
		return get(SeqEnum.orderid,count);
	}
	public String getOrderId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%s%012d", prefix, dateStr, get(SeqEnum.orderid, 1));
	}
	
	public String getOrderId(Long id){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%s%012d", prefix,dateStr,id);
	}
	
	//----end----
    //票id生成方法
	//---start---
	public Long getTikcetId(int count){//多个
		return get(SeqEnum.ticketId, count);
	}
	public String getTicketid(){//单个票id
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		String datd=String.format("%012d",get(SeqEnum.ticketId, 1));
		return String.format("%s%s%s", prefix, dateStr, getMd5Str(datd));

		//return String.format("%s%s%012d", prefix, dateStr, get(SeqEnum.ticketId, 1));
	}

	public String getTicketId(Long id){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		String datd=String.format("%012d",id);
		return String.format("%s%s%s", prefix, dateStr, getMd5Str(datd));
	}


	//-----end----
	//批次id生成方法
	public String getBatchTicketId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr,get(SeqEnum.batchTicketId, 1));
	}
	
	public Long getBatchTicketId(int count){
		return get(SeqEnum.batchTicketId, count);
	}
	
	public String getId(Long id){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr, id);
	}


	
	
	/**user_account_detail
	 * 用户交易记录id生成
	 * */
	public String getUserAccountDetailId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr,get(SeqEnum.useraccount_detail, 1));
	}
	public Long getUserAccountDetailId(int count){//多个
		return get(SeqEnum.useraccount_detail, count);
	}
	

	/**
	 * 充值生成规则
	 * @return
	 */
	public String getTransactionId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyyyMMdd");
		return String.format("%s%s%010d", prefix, dateStr, get(SeqEnum.usertransaction, 1));
	}
	
	
	/**
	 * 流水号规则
	 * @return
	 */
	public String getMessageId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyyyMMdd");
		return String.format("%s%010d",dateStr,get(SeqEnum.messageid, 1));
	}
	/**
	 * 提现记录生产id
	 * */
	public String getDrawId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyyyMMdd");
		return String.format("%s%010d",dateStr,get(SeqEnum.drawid, 1));	
	}
	/**
	 * 提现批次id生成
	 * */
	public String getDrawBatchId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyyyMMdd");
		return String.format("%s%s%010d", prefix,dateStr,get(SeqEnum.drawaccount_batchid, 1));
	}
    /**
     * 表terminal，id生成规则
     * */
    public Long getTeriminalId(){
        return  get(SeqEnum.terminalId, 1);
    }

    /**
     *terminal_config表id生成规则
     * */
     public Long getTerminalConfigId(){
         return get(SeqEnum.terminalConfigId,1);
     }
    /**
     * terminal_property表id生成规则
     * */
    public  Long getTerminalPropertyId(){
        return get(SeqEnum.terminalPropertyId,1);
    }
    /**
     * terminal_ticket_config表id生成规则
     * */
    public  Long getTerminalTicketConfigId(){
        return get(SeqEnum.terminalTicketConfigId,1);
    }
    /**
     * lottery_terminal_config表id生成规则
     * */
    public  Long getLotteryTerminalConfigId(){
        return get(SeqEnum.lotteryTerminalConfig,1);
    }
    /**
     * pay_property表id生成规则
     * */
    public  Long getPayPropertyId(){
        return get(SeqEnum.payPropertyId,1);
    }

    /**
     * lottery_order_chase表id生成规则
     * */
    public  String getLotteryOrderChaseId(){
        String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
        return String.format("%s%012d", dateStr, get(SeqEnum.lotteryOrderChaseId, 1));
    }
    /**
     * user_draw_bank表id生成规则
     * */
    public String getUserDrawBankId(){
    	 String dateStr=CoreDateUtils.formatDate(new Date(), "yyyyMMdd");
         return String.format("%s%010d",dateStr,get(SeqEnum.userDrawBankId, 1));
    }
    /**
     * dc_race 表id生成规则
     * */
    public Long getDcRaceId(){
    	return get(SeqEnum.dcRaceId, 1);
    }
    /**
     * zc_race 表id生成规则
     * */
    public Long getZcRaceId(){
    	return get(SeqEnum.zcRaceId,1);
    }
    /**
     * lottery_phase 表id生成规则
     * */
    public Long getLotteryPhaseId(){
    	return get(SeqEnum.lotteryPhaseId,1);
    }
    public Long getLotteryPhaseId(int count){
    	return get(SeqEnum.lotteryPhaseId,count);
    }
    /**
     * lottery_chase 表id生成规则
     * */
    public String getLotteryChaseId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
    	return String.format("%s%012d", dateStr, get(SeqEnum.chaseid, 1));
    }
    
    /**
     * lottery_caselot 表id生成规则
     * */
    public String getLotteryCaselotId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
    	return String.format("%s%010d",dateStr,get(SeqEnum.caselotid, 1));
    }
    
    /**
     * lottery_caselot 表id生成规则
     * */
    public String getLotteryCaselotBuyId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
    	return String.format("%s%010d",dateStr,get(SeqEnum.caselotbuyid, 1));
    }
    
    public List<IdGenerator> getAll(){
    	return entityManager.createQuery("from IdGenerator", IdGenerator.class).getResultList();
	} 
    
    /**
     * user_achievement表id生成规则
     * */
    public Long getUserachievementId(){
        return get(SeqEnum.userachievementId, 1);
    }
    
    /**
     * user_achievement_detail表id生成规则
     * */
    public Long getUserachievementdetailId(){
        return get(SeqEnum.userachievementdetailId,1);
    }
    
    public IdGenerator find(Long id){
    	return entityManager.find(IdGenerator.class, id);
    }
    
    public void merge(IdGenerator id){
    	entityManager.merge(id);
    }
    
    
    /**
     * user_achievement表id生成规则
     * */
    public Long getAutoJoinId(){
        return get(SeqEnum.autojoinId, 1);
    }
    
    /**
     * user_achievement_detail表id生成规则
     * */
    public Long getAutoJoinDetailId(){
        return get(SeqEnum.autojoin_detailId, 1);
    }
    /**
     * ticket_allot_log 表id生成规则
     * */
    public  String getTicketAllotLogId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
    	
		return String.format("%s%012d", dateStr, get(SeqEnum.ticketallotlogId, 1));
    }
    /**
     * ticket_allot_log 表id生成规则
     * */
    public  String getTicketBatchSendLogId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr, get(SeqEnum.ticketbatchsendlogId, 1));
    }
    /**
     * user_account_add 表id生成规则
     * */
    public  String getUserAccountAddId(){
    	String dateStr=CoreDateUtils.formatDate(new Date(), "yyyyMMdd");
		return String.format("%s%010d",dateStr, get(SeqEnum.useraccountaddId, 1));
    }
    /**
     * 默认id生成方法
     * */
    public Long getDefaultId(){
    	return get(SeqEnum.all, 1); 
    }
   
    
    /***
     * lottery_chase_buy表id生成方法
     * */
	 public  String getLotteryChaseBuyId(){
		 String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
			return String.format("%s%012d", dateStr, get(SeqEnum.lotteryChaseBuyId, 1));
	    }
	
	public Long getLotteryChaseBuyId(int count){//多个
			return get(SeqEnum.lotteryChaseBuyId, count);
	}
	public  String getLotteryChaseBuyId(Long id){
		 String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
			return String.format("%s%012d", dateStr, id);
	    }
	/**
	 * user_recharge_give表id生成方式
	 * */
	public String getUserRechargeGiveId(){
		 String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr, get(SeqEnum.userRechargeGiveId, 1));
	}
	/**
	 * user_rebate表id生成方式
	 * */
	public String getUserRebateId(){
		 String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr, get(SeqEnum.userRebate, 1));
	}
    /**
     *lottery_agency_point_location 获取id方式
     * */
    public Long getLotteryAgencyPointLocation(){
        return get(SeqEnum.lotteryAgencyPointLocation, 1);
    }
    /**
	 * lottery_phase_draw_config 获取id
	 * **/
	public Long getLotteryPhaseDrawConfgId(){
		return get(SeqEnum.lotteryphasedrawconfig, 1);
	}

	/**
	 * lottery_phase_draw_config 获取id
	 * **/
	public Long getCouponTypeId(){
		return get(SeqEnum.couponTypeId, 1);
	}
	
	public Long getUserCouponId(int count) {// 多个
		return get(SeqEnum.userCouponId, count);
	}

	public String getUserCouponId(Long id) {
		String dateStr = CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d", dateStr, id);
	}
	public String getUserCouponId() {
		String dateStr = CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d", dateStr, getUserCouponId(1));
	}

	/**
	 * system_exception_message 获取id
	 * */
	public Long getSystemExceptionMessageId(){
		return get(SeqEnum.systemExceptionMessge, 1);
	}
/**
 * betting_limit_number 获取id
 * */
	public Long getBettingLimitNumber(){
		String dateStr = CoreDateUtils.formatDate(new Date(), "yyMMdd")+"0"+get(SeqEnum.bettingLimitNumber, 1);
		return Long.valueOf(dateStr);
	}



	/**
	 * user_red_package表id生成方式
	 * */
	public String getUserRedPackageId(){
		String dateStr=CoreDateUtils.formatDate(new Date(), "yyMMdd");
		return String.format("%s%012d",dateStr, get(SeqEnum.userRedPackage, 1));
	}

	public String getMd5Str(String number){
		try {
            //return MD5Util.toMd5(number);
		}catch (Exception e){

		}
		return number;
	}




	public void clear(){
		entityManager.clear();
	}
}
