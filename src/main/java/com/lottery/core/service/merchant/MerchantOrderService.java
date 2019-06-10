package com.lottery.core.service.merchant;


import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.merchant.MerchantDAO;
import com.lottery.core.dao.merchant.MerchantOrderDAO;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.bet.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MerchantOrderService {
	@Autowired
	private MerchantOrderDAO merchantOrderDAO;
	@Autowired
	protected BetService betservice;
	@Autowired
	protected UserAccountDAO userAccountDAO;
	@Autowired
	protected MerchantDAO merchantDao;
    @Autowired
    protected UserAccountService userAccountService;
	@Autowired
	protected UserAccountDetailDAO userAccountDetailDao;
	@Autowired
	protected IdGeneratorDao idDao;

	/**
	 * 根据用户编号，订单号查询
	 * 
	 * @param merchantCode
	 *            用户编号
	 * @param merchantNo
	 *            用户订单
	 * */
	@Transactional
	public MerchantOrder getByMerchantCodeAndMerchantNO(String merchantCode, String merchantNo) {
		try {
			return merchantOrderDAO.getByMerchantCodeAndMerchantNO(merchantCode, merchantNo);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据订单号查询出票商票信息
	 * 
	 * @param orderId
	 *            订单号
	 * */
	@Transactional
	public MerchantOrder get(String orderId) {
		return merchantOrderDAO.find(orderId);
	}

	@Transactional
	public void update(MerchantOrder merchantOrder) {
		merchantOrderDAO.merge(merchantOrder);
	}


	/**
	 * 修改订单状态
	 * @param orderid 订单号
	 * @param orderStatus 订单状态
	 * */
	@Transactional
	public int updateMerchantOrderStatus(String orderid,int orderStatus){
		return merchantOrderDAO.updateMerchantOrderStatus(orderid, orderStatus);
	}
	/**
	 * 修改订单中奖状态
	 * @param orderid 订单号
	 * @param orderStatus 订单状态
	 * @param amount 中奖金额
	 * */
	@Transactional
	public int updateMerchantOrderResultStatus(String orderid,int orderStatus,BigDecimal amount){
		return merchantOrderDAO.updateMerchantOrderResultStatus(orderid, orderStatus, amount);
	}
	/**
	 * 修改派奖状态
	 * @param orderId 订单号
	 * @param status 状态
	 * */
	@Transactional
	public int updateIsExchanged(String orderId,int status){
		return merchantOrderDAO.updateIsExchanged(orderId, status);
	}
	/**
	 * 修改订单状态和成功时间
	 * @param orderid 订单号
	 * @param orderStatus 订单状态
	 * */
	@Transactional
	public int updateMerchantOrderStatusAndPrintTime(String orderid,int orderStatus,Date date){
		return merchantOrderDAO.updateMerchantOrderStatusAndPrintTime(orderid, orderStatus, date);
	}
	/**
	 *@param  timeout 超时时间(单位:分)
	 * */
	@Transactional
	public MerchantOrder bet(String merchantCode, String merchantNo, String betcode, String orderId, int lotteryType, String phase, int playType, int multiple, int amount, int addition,int timeout,String endtime,String terminalId){
	MerchantOrder merchantOrder=betservice.merchantBet(merchantCode, merchantNo, betcode, orderId, lotteryType, phase, playType, multiple, amount, addition, endtime,terminalId);
		if (merchantOrder.getEndTime()!=null){
			long out=merchantOrder.getEndTime().getTime()-(new Date()).getTime();
			if (out<=timeout*60*1000){
				throw new LotteryException(ErrorCode.phase_outof,"订单已截止,merchantNo="+merchantNo);
			}
		}

		return merchantOrder;

	}

}
