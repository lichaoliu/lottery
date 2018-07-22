package com.lottery.core.service.give;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.pay.RedPackageStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.give.UserRedPackageDao;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.give.UserRedPackage;

/**
 * Created by fengqinyun on 2017/3/27.
 */
@Service
public class UserRedPackageService {
    @Autowired
    private UserRedPackageDao dao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private UserAccountDetailDAO userAccountDetailDAO;
    /**
     * 生成红包
     * @param userno 赠送人userno
     * @param phone 被赠送人手机号
     * @param amount 赠送金额 (分)
     * @param receiveUserno  接受人userno
     * */
    @Transactional
    public void save(String userno, String phone, BigDecimal amount,String receiveUserno){
        UserAccount userAccount=userAccountDAO.findWithLock(userno,true);
        if(userAccount==null){
            throw new LotteryException(ErrorCode.no_account,"用户账号不存在");
        }
        if(userAccount.getBalance().longValue()<amount.longValue()){
            throw new LotteryException(ErrorCode.account_no_enough,"用户账号余额不足,amount="+userAccount.getBalance());
        }

        String userRedPackageId=idGeneratorDao.getUserRedPackageId();
        UserRedPackage userRedPackage=new UserRedPackage();
        userRedPackage.setId(userRedPackageId);
        userRedPackage.setAmount(amount);
        userRedPackage.setGivePhone(userAccount.getPhoneno());
        userRedPackage.setGiveTime(new Date());
        userRedPackage.setStatus(RedPackageStatus.no_receive.value);
        userRedPackage.setReceiveUserno(receiveUserno);
        userRedPackage.setGiveUserno(userno);
        userRedPackage.setReceivePhone(phone);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);//一天过期
        userRedPackage.setExpiryTime(calendar.getTime());
        dao.insert(userRedPackage);


        userAccount.setBalance(userAccount.getBalance().subtract(amount));
        userAccount.setLastTradeTime(new Date());
        userAccount.setLastTradeamt(amount);
        BigDecimal drawAmt = BigDecimal.ZERO;
        if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
            drawAmt = userAccount.getDrawbalance().subtract(userAccount.getBalance());
            userAccount.setDrawbalance(userAccount.getBalance());
        }
        userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额

        userAccountDAO.merge(userAccount);




        UserAccountDetail uad = new UserAccountDetail(idGeneratorDao.getUserAccountDetailId(), userAccount.getUserno(),userAccount.getUserName(), userRedPackageId, new Date(), amount, AccountType.deduct, CommonStatus.success, userAccount.getBalance(), AccountDetailType.red_package_give, userAccount.getDrawbalance(), userAccount.getFreeze(), "增送红包", SignStatus.out);
        uad.setOtherid(userRedPackageId);
        uad.setLotteryType(1);
        uad.setPhase("100");
        uad.setGiveAmount(amount);
        uad.setDrawAmount(drawAmt);
        uad.setNotDrawAmount(BigDecimal.ZERO);
        uad.setAgencyNo("0");
        userAccountDetailDAO.insert(uad);

    }
/**领取所有红包
 * */
    @Transactional
    public void receive(String userno){
        UserAccount userAccount=userAccountDAO.findWithLock(userno,true);
        if(userAccount==null){
            throw new LotteryException(ErrorCode.no_account,"用户账号不存在");
        }
        List<UserRedPackage> userRedPackageList=dao.getReceive(userno,RedPackageStatus.no_receive.value);
        if(userRedPackageList==null||userRedPackageList.size()==0){
            throw new LotteryException(ErrorCode.user_red_package_notexits,"没有需要领取的红包");
        }
        Long id = idGeneratorDao.getUserAccountDetailId(userRedPackageList.size());
        for(UserRedPackage userRedPackage:userRedPackageList){
            userRedPackage.setStatus(RedPackageStatus.success.value);
            userRedPackage.setReceiveTime(new Date());
            BigDecimal amount=userRedPackage.getAmount();
            userAccount.setBalance(userAccount.getBalance().add(amount));
            userAccount.setLastTradeTime(new Date());
            userAccount.setLastTradeamt(amount);
            userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额
            userAccountDAO.merge(userAccount);
            String tid = idGeneratorDao.getId(id);
            UserAccountDetail uad = new UserAccountDetail(tid, userAccount.getUserno(),userAccount.getUserName(), userRedPackage.getId(), new Date(), amount, AccountType.give, CommonStatus.success, userAccount.getBalance(), AccountDetailType.red_package_receive, userAccount.getDrawbalance(), userAccount.getFreeze(), "领取红包", SignStatus.in);
            uad.setOtherid(userRedPackage.getId());
            uad.setLotteryType(1);
            uad.setPhase("100");
            uad.setGiveAmount(amount);
            uad.setDrawAmount(BigDecimal.ZERO);
            uad.setNotDrawAmount(BigDecimal.ZERO);
            uad.setAgencyNo("0");
            userAccountDetailDAO.insert(uad);
            id ++;
        }

    }

    /**
     * 通过手机号码修改用户编号
     * @param phoneno 手机号码
     * @param userno 用户编码
     * */
    @Transactional
    public int updateUserno(String phoneno,String userno){
        return dao.updateUserno(phoneno,userno);
    }

    /***
     * 通过红包id领取
     * */
    @Transactional
    public void reciveRedPackageById(String redPackageId){
        UserRedPackage userRedPackage=dao.findWithLock(redPackageId,true);
        if(userRedPackage==null){
            throw new LotteryException(ErrorCode.no_exits,"红包不存在");
        }
        if(userRedPackage.getStatus()==RedPackageStatus.success.value){
            throw new LotteryException(ErrorCode.user_red_package_notexits,"红包已领取");
        }
        userRedPackage.setStatus(RedPackageStatus.success.value);
        userRedPackage.setReceiveTime(new Date());
        BigDecimal amount=userRedPackage.getAmount();
        UserAccount userAccount=userAccountDAO.findWithLock(userRedPackage.getReceiveUserno(),true);
        userAccount.setBalance(userAccount.getBalance().add(amount));
        userAccount.setLastTradeTime(new Date());
        userAccount.setLastTradeamt(amount);
        userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额
        userAccountDAO.merge(userAccount);
        String tid = idGeneratorDao.getUserAccountDetailId();
        UserAccountDetail uad = new UserAccountDetail(tid, userAccount.getUserno(),userAccount.getUserName(), userRedPackage.getId(), new Date(), amount, AccountType.give, CommonStatus.success, userAccount.getBalance(), AccountDetailType.red_package_receive, userAccount.getDrawbalance(), userAccount.getFreeze(), "领取红包", SignStatus.in);
        uad.setOtherid(userRedPackage.getId());
        uad.setLotteryType(1);
        uad.setPhase("100");
        uad.setGiveAmount(amount);
        uad.setDrawAmount(BigDecimal.ZERO);
        uad.setNotDrawAmount(BigDecimal.ZERO);
        uad.setAgencyNo("0");
        userAccountDetailDAO.insert(uad);

    }
    
    
    
    public List<UserRedPackage> findReceiveRedPackages(String userno,PageBean<UserRedPackage> page) {
    	return dao.getReceiveRedPackages(userno,  page);
    }

    public List<UserRedPackage> findGiveRedPackages(String userno,PageBean<UserRedPackage> page) {
    	return dao.getGiveRedPackages(userno,  page);
    }

}
