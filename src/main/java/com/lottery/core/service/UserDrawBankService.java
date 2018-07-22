package com.lottery.core.service;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserDrawBankDAO;
import com.lottery.core.domain.UserDrawBank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserDrawBankService {
	@Autowired
	private UserDrawBankDAO userDrawBankDAO;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	@Transactional
	public void saveOrUpdate(UserDrawBank userDrawBank){
		if(userDrawBank.getId()==null){
			String id=idGeneratorDao.getDrawBatchId();
			userDrawBank.setId(id);
			userDrawBankDAO.insert(userDrawBank);
		}else{
			String id=userDrawBank.getId();
			if(!userDrawBankDAO.isContains(userDrawBank)){
				UserDrawBank drawBank=userDrawBankDAO.find(id);
				drawBank.setBankCard(userDrawBank.getBankCard());
				drawBank.setBankName(userDrawBank.getBankName());
				drawBank.setBankType(userDrawBank.getBankType());
				drawBank.setBranch(userDrawBank.getBranch());
				drawBank.setCity(userDrawBank.getCity());
				drawBank.setProvince(userDrawBank.getProvince());
				drawBank.setRealname(userDrawBank.getRealname());
				drawBank.setUpdateTime(new Date());
				userDrawBankDAO.merge(drawBank);
			}else{
				userDrawBank.setUpdateTime(new Date());
				userDrawBankDAO.merge(userDrawBank);
			}
		}
		
	}

	
	@Transactional
	public void save(UserDrawBank userDrawBank){
		String id=idGeneratorDao.getDrawBatchId();
		userDrawBank.setId(id);
		userDrawBankDAO.insert(userDrawBank);
	}
	@Transactional
	public void deleteByid(String id){
		Map<String,Object> whereMap=new HashMap<String,Object>();
		whereMap.put("id", id);
		userDrawBankDAO.delete(whereMap);
	}
	@Transactional
	public void delete(UserDrawBank entity){
		userDrawBankDAO.remove(entity);
	}
	@Transactional
	public void update(UserDrawBank entity){
		userDrawBankDAO.merge(entity);
	}

	@Transactional
	public UserDrawBank get(String id){
		return userDrawBankDAO.find(id);
	}


	/**
	 * 根据用户名查询银行卡信息
	 * @param  userno 用户名
	 * */
	@Transactional
	public List<UserDrawBank> getByUserno(String userno){
		return userDrawBankDAO.getByUserno(userno);
	}
	/**
	 * 根据账号查询
	 * @param bankCard
	 * **/
	@Transactional
	public UserDrawBank getByBankCard(String bankCard){
		try{
			return  userDrawBankDAO.getByBankCard(bankCard);
		}catch (Exception e){

		}
		return  null;

	}
    /**
     * 查询用户绑定信息
     * @param  userno 用户名
     * @param drawType 提现形式
     * */
      @Transactional
      public UserDrawBank get(String userno,int drawType){
          try{
              return  userDrawBankDAO.get(userno,drawType);
          }catch (Exception e){

              return null;
          }

    }
      
      
      /**
       * 删除绑定
       * @param id
       */
      @Transactional
      public void unBind(String id,String userno) {
    	  UserDrawBank userDrawBank = get(id);
    	  if(userDrawBank==null||(!userDrawBank.getUserno().equals(userno))) {
    		  throw new LotteryException(ErrorCode.user_card_bind_noexits, ErrorCode.user_card_bind_noexits.memo);
    	  }
    	  
    	  userDrawBankDAO.remove(userDrawBank);
      }

}
