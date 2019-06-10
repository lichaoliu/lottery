package com.lottery.core.service.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.caselot.CaseLotState;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryCaseLotBuyDao;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.UserRebateDAO;
import com.lottery.core.dao.UserRebateInstantDAO;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.account.UserRebate;
import com.lottery.core.domain.account.UserRebateInstant;
import com.lottery.core.domain.ticket.LotteryOrder;

@Service
public class UserRebateInstantService {
	
	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());
    @Autowired
	protected UserRebateInstantDAO userRebateInstantDAO;
    @Autowired
    protected LotteryOrderDAO lotteryOrderDAO;
    @Autowired
    protected UserRebateDAO userRebateDAO;
    @Autowired
    protected UserAccountDetailDAO userAccountDetailDAO;
    @Autowired
    protected UserAccountDAO userAccountDAO;
    @Autowired
    protected IdGeneratorDao idDao;
	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;
	@Autowired
	private LotteryCaseLotBuyDao lotteryCaseLotBuyDao;
    @Transactional
    public UserRebateInstant get(String id){
    	return userRebateInstantDAO.find(id);
    }
    /**
     * 投注返点
     * */
    @Transactional
    public void betRebate(String orderId){
    	LotteryOrder lotteryOrder=lotteryOrderDAO.find(orderId);
    	if(lotteryOrder==null){
    		 logger.error("订单{}不存在",orderId);
    		 return;
    	}
    	String userno=lotteryOrder.getUserno();
    	if(lotteryOrder.getOrderStatus()==OrderStatus.PRINTED.value||lotteryOrder.getOrderStatus()==OrderStatus.HALF_PRINTED.value){
    		UserRebate userRebate=null;
        	try{
        		 userRebate=userRebateDAO.getFix(userno, LotteryType.getSingleValue(lotteryOrder.getLotteryType()));
        	}catch(Exception e){
        		
        	}
        	if(userRebate==null||userRebate.getIsPaused()==YesNoStatus.yes.value){
        		return;
        	}
        	
        	UserRebateInstant rebateInstant=userRebateInstantDAO.find(orderId);
        	if(rebateInstant!=null){
        		 logger.error("订单{}返点已存在",orderId);
        		 return;
        	}
        	
        	Integer account_Type = lotteryOrder.getAccountType();
        	AccountDetailType accountDetailType= AccountDetailType.deduct;
        	if(lotteryOrder.getOrderStatus()==OrderStatus.HALF_PRINTED.value){
        		accountDetailType=AccountDetailType.half_deduct;
        	}
    		AccountType accountType = AccountType.getAccountType(account_Type);
        	UserAccountDetail uad2 = userAccountDetailDAO.getByPayIdAndType(orderId, accountType, accountDetailType);
        	if(uad2==null){
        		logger.error("{}扣款记录不存在",orderId);
        		return;
        	}
        	 
        	UserAccount userAccount=userAccountDAO.findWithLock(userno, true);
        	if(userAccount==null){
        		return;
        	}
        	
        	Double point=userRebate.getPointLocation()/100;
        	BigDecimal  amt=uad2.getAmt();
        	BigDecimal pointAmount=amt.multiply(new BigDecimal(point));
        	UserRebateInstant entity=new UserRebateInstant();
        	entity.setAmount(amt);
        	entity.setCreateTime(new Date());
        	entity.setOrderId(orderId);
        	entity.setPointLocation(userRebate.getPointLocation());
        	entity.setRebateAmount(pointAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        	entity.setUserno(userno);
        	entity.setBuyAmount(BigDecimal.ZERO);
        	entity.setSafeAmount(BigDecimal.ZERO);
        	entity.setBetType(lotteryOrder.getBetType());
        	userRebateInstantDAO.insert(entity);
        	userAccount.setBalance(userAccount.getBalance().add(pointAmount));
        	userAccount.setGiveBalance(userAccount.getGiveBalance().add(pointAmount));
        	userAccount.setLastTradeTime(new Date());
        	userAccount.setTotalBalance(userAccount.getTotalBalance().add(pointAmount));
        	userAccount.setLastTradeamt(pointAmount);
        	userAccountDAO.merge(userAccount);
    		String id = idDao.getUserAccountDetailId();
			UserAccountDetail uad = new UserAccountDetail(id, userno,userAccount.getUserName(), orderId, new Date(),pointAmount, AccountType.rebate, CommonStatus.success, userAccount.getBalance(), AccountDetailType.add, userAccount.getDrawbalance(), userAccount.getFreeze(), "购彩返点 ", SignStatus.in);
			userAccountDetailDAO.insert(uad);
    	}
    }
    
    /**
     * 合买根据订单金额返点
     * 
     * */
    @Transactional
    public void hemaiRebateOrder(String orderId){
    	LotteryOrder lotteryOrder=lotteryOrderDAO.find(orderId);
    	if(lotteryOrder==null){
    		 logger.error("订单{}不存在",orderId);
    		 return;
    	}
    	String hemaiId=lotteryOrder.getHemaiId();
    	if(StringUtils.isBlank(hemaiId)){
    		return;
    	}
    	LotteryCaseLot caseLot=lotteryCaseLotDao.find(hemaiId);
    	if(caseLot==null){
    		logger.error("合买{}不存在",hemaiId);
    		return;
    	}
    	
    	String userno=caseLot.getStarter();
    	int lotteryType=caseLot.getLotteryType();
    	UserRebate userRebate=null;
    	try{
    		 userRebate=userRebateDAO.getFix(userno, LotteryType.getSingleValue(lotteryType));
    	}catch(Exception e){
    		
    	}
    	if(userRebate==null||userRebate.getIsPaused()==YesNoStatus.yes.value){
    		return;
    	}
    	
    	UserRebateInstant rebateInstant=userRebateInstantDAO.find(hemaiId);
    	if(rebateInstant!=null){
    		 logger.error("合买{}返点已存在",hemaiId);
    		 return;
    	}
    
    	UserAccount userAccount=userAccountDAO.findWithLock(userno, true);
    	if(userAccount==null){
    		return;
    	}
	
    	BigDecimal buyAmount=BigDecimal.ZERO;
    	BigDecimal safeAmount=BigDecimal.ZERO;
    	BigDecimal totalAmount=lotteryOrder.getAmount();
    	Double point=userRebate.getPointLocation()/100;
    	BigDecimal pointAmount=totalAmount.multiply(new BigDecimal(point));
    	
    	UserRebateInstant entity=new UserRebateInstant();
    	entity.setAmount(totalAmount);
    	entity.setCreateTime(new Date());
    	entity.setOrderId(hemaiId);
    	entity.setPointLocation(userRebate.getPointLocation());
    	entity.setRebateAmount(pointAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
    	entity.setUserno(userno);
    	entity.setBuyAmount(buyAmount);
    	entity.setSafeAmount(safeAmount);
    	entity.setBetType(BetType.hemai.value);
    	userRebateInstantDAO.insert(entity);
    	userAccount.setBalance(userAccount.getBalance().add(pointAmount));
    	userAccount.setGiveBalance(userAccount.getGiveBalance().add(pointAmount));
    	userAccount.setLastTradeTime(new Date());
    	userAccount.setTotalBalance(userAccount.getTotalBalance().add(pointAmount));
    	userAccount.setLastTradeamt(pointAmount);
    	userAccountDAO.merge(userAccount);
		String id = idDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, userno,userAccount.getUserName(), hemaiId, new Date(),pointAmount, AccountType.rebate, CommonStatus.success, userAccount.getBalance(), AccountDetailType.add, userAccount.getDrawbalance(), userAccount.getFreeze(), "合买返点 ", SignStatus.in);
		userAccountDetailDAO.insert(uad);
    }
    /**
     * 合买根据发起人认购金额+保底金额 计算 
     * */
    @Transactional
    public void hemaiRebate(String hemaiId){
    	
    	LotteryCaseLot caseLot=lotteryCaseLotDao.find(hemaiId);
    	if(caseLot==null){
    		logger.error("合买{}不存在",hemaiId);
    		return;
    	}
    	if (!caseLot.getState().equals(CaseLotState.end.value)) {
			logger.error("合买{}未截止",hemaiId);
			return;
		}
    	String userno=caseLot.getStarter();
    	int lotteryType=caseLot.getLotteryType();
    	UserRebate userRebate=null;
    	try{
    		 userRebate=userRebateDAO.getFix(userno, LotteryType.getSingleValue(lotteryType));
    	}catch(Exception e){
    		
    	}
    	if(userRebate==null||userRebate.getIsPaused()==YesNoStatus.yes.value){
    		return;
    	}
    	
    	UserRebateInstant rebateInstant=userRebateInstantDAO.find(hemaiId);
    	if(rebateInstant!=null){
    		 logger.error("合买{}返点已存在",hemaiId);
    		 return;
    	}
    	
    	
    	BigDecimal buyAmount=BigDecimal.ZERO;
    	BigDecimal safeAmount=BigDecimal.ZERO;
    	
     	List<LotteryCaseLotBuy> caseLotBuyList=lotteryCaseLotBuyDao.findCaseLotBuysByCaselotIdAndUserno(hemaiId, userno);
    	for(LotteryCaseLotBuy caseLotBuy:caseLotBuyList){
    		buyAmount=buyAmount.add(caseLotBuy.getNum());
    		safeAmount=safeAmount.add(caseLotBuy.getFreezeSafeAmt());
    	}
    	BigDecimal totalAmount=buyAmount.add(safeAmount);
    	
    	
   
    	Double point=userRebate.getPointLocation()/100;
    	BigDecimal pointAmount=totalAmount.multiply(new BigDecimal(point));
    	UserAccount userAccount=userAccountDAO.findWithLock(userno, true);
    	if(userAccount==null){
    		return;
    	}
  
    	UserRebateInstant entity=new UserRebateInstant();
    	entity.setAmount(totalAmount);
    	entity.setCreateTime(new Date());
    	entity.setOrderId(hemaiId);
    	entity.setPointLocation(userRebate.getPointLocation());
    	entity.setRebateAmount(pointAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
    	entity.setUserno(userno);
    	entity.setBuyAmount(buyAmount);
    	entity.setSafeAmount(safeAmount);
    	entity.setBetType(BetType.hemai.value);
    	userRebateInstantDAO.insert(entity);
    	userAccount.setBalance(userAccount.getBalance().add(pointAmount));
    	userAccount.setGiveBalance(userAccount.getGiveBalance().add(pointAmount));
    	userAccount.setLastTradeTime(new Date());
    	userAccount.setTotalBalance(userAccount.getTotalBalance().add(pointAmount));
    	userAccount.setLastTradeamt(pointAmount);
    	userAccountDAO.merge(userAccount);
		String id = idDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, userno,userAccount.getUserName(), hemaiId, new Date(),pointAmount, AccountType.rebate, CommonStatus.success, userAccount.getBalance(), AccountDetailType.add, userAccount.getDrawbalance(), userAccount.getFreeze(), "合买返点 ", SignStatus.in);
		userAccountDetailDAO.insert(uad);
    }
}
