package com.lottery.core.handler;

import com.lottery.common.contains.lottery.*;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.account.UserAccountDetailService;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.bet.BetService;
import com.lottery.core.service.coupon.UserCouponService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserAccountHandler {

	protected  final Logger logger= LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private UserAccountService uas;
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected BetService betSerive;
	@Autowired
	protected UserAccountDetailService detailService;
	@Autowired
	protected UserCouponService userCouponService;
	public void deduct(String orderId) throws Exception{
		LotteryOrder order = lotteryOrderService.get(orderId);
		if (order == null) {
			logger.error("账号扣款,订单{}不存在", orderId);
			return ;
		}
		if (isHemai(order)) {
			return ;
		}
		if (order.getPayStatus() != PayStatus.ALREADY_PAY.getValue()) {
			logger.error("订单{}的支付状态是{},不做扣款处理", new Object[] { orderId, order.getPayStatus() });
			return ;
		}
		if (order.getOrderStatus() != OrderStatus.PRINTED.getValue()) {
			logger.error("订单{}不是成功出票状态,状态为:{}，不做扣款", orderId,order.getOrderStatus());
			return ;
		}

		Long priting = ticketService.getCountByOrderIdAndStatus(orderId, TicketStatus.PRINTING.getValue());
		if (priting.intValue() > 0) {
			logger.error("订单{}还有正在出票中的票,数量是：{}不能扣款", new Object[] { orderId, priting });
			return ;
		}
		UserAccountDetail accountDetail=detailService.getByPayIdAndType(orderId,order.getAccountType(),AccountDetailType.deduct.value);
		BigDecimal amount=order.getAmount();
		if(order.getPreferentialAmount().intValue()>0){

			amount=amount.subtract(order.getPreferentialAmount());
		}
		if(accountDetail==null){
			uas.deduct(order.getUserno(),orderId,amount,order.getAccountType(),order.getLotteryType(),order.getPhase());
		}
		lotteryOrderService.updatePayStatus(PayStatus.PAY_SUCCESS.value, orderId);
	}
	
	public void unfreeze(String orderId) throws Exception{
		LotteryOrder order = lotteryOrderService.get(orderId);
		if (order == null) {
			logger.error("账号解冻,订单{}不存在", orderId);
			return;
		}
		if (isHemai(order)) {
			return;
		}

		if (order.getPayStatus() != PayStatus.ALREADY_PAY.getValue()) {
			logger.error("订单{}的支付状态是{},不做解冻处理", new Object[] { orderId, order.getPayStatus() });
			return;
		}
		if (order.getPayStatus() == PayStatus.REFUNDED.getValue()) {
			logger.error("订单{}已解冻完成,不做解冻处理", orderId);
			return;
		}
		if (order.getOrderStatus() == OrderStatus.PRINTED_FAILED.getValue() || order.getOrderStatus() == OrderStatus.HALF_PRINTED.getValue() || order.getOrderStatus() == OrderStatus.PRINTED_FAILED.getValue() || order.getOrderStatus() == OrderStatus.CANCELLED.getValue()
				) {

		} else {
			logger.error("订单{}的状态为{},不做解冻处理", new Object[] { orderId, OrderStatus.getOrderStatus(order.getOrderStatus()) });
			return;
		}
		Long priting = ticketService.getCountByOrderIdAndStatus(orderId, TicketStatus.PRINTING.getValue());
		if (priting.intValue() > 0) {
			logger.error("订单{}还有正在出票中的票,数量是：{}不能解冻", new Object[] { orderId, priting });
			return;
		}
		BigDecimal successAmount = BigDecimal.ZERO;// 成功订单
		int payStatus = PayStatus.REFUNDED.getValue();
		if (order.getOrderStatus() == OrderStatus.HALF_PRINTED.getValue()) {
			successAmount = ticketService.getSumAmountByOrderIdAndStatus(orderId, TicketStatus.PRINT_SUCCESS.value);
			logger.error("订单orderId={}部分成功的金额是：{}", new Object[] { orderId, successAmount });
			payStatus = PayStatus.PART_REFUNDED.getValue();
		}
		UserAccountDetail accountDetail=detailService.getByPayIdAndType(orderId,order.getAccountType(),AccountDetailType.unfreeze.value);
		if (accountDetail==null){
			BigDecimal orderAmount=order.getAmount();
			if(order.getPreferentialAmount()!=null&&order.getPreferentialAmount().intValue()>0){
				orderAmount=orderAmount.subtract(order.getPreferentialAmount());
			}
			uas.unfreeze(order.getUserno(),orderId,order.getAccountType(),orderAmount,successAmount);
		}
		lotteryOrderService.updatePayStatus(payStatus, orderId);
		//优惠卷操作
		if (order.getPreferentialAmount().intValue()>0&& StringUtils.isNotBlank(order.getChaseId())){
			userCouponService.orderFailUpdateCoupon(order.getChaseId());
		}


	}
	public void freeze(String orderId)throws Exception{
		LotteryOrder order = lotteryOrderService.get(orderId);
		if (order == null) {
			logger.error("账号冻结,订单{}不存在", orderId);
			return;
		}
		if (isHemai(order)) {
			return;
		}
		if (order.getPayStatus() != PayStatus.NOT_PAY.getValue()) {
			logger.error("订单{}的支付状态是{},不做冻结处理", new Object[] { orderId, order.getPayStatus() });
			return;
		}
		UserAccountDetail accountDetail=detailService.getByPayIdAndType(orderId,AccountType.bet.value,AccountDetailType.freeze.value);
		boolean accountflag=true;
		if (order.getBetType()==BetType.bet_merchant.value){
			accountflag=false;
		}
		String memo="投注冻结";
		BigDecimal amount=order.getAmount();
		if(order.getPreferentialAmount().intValue()>0){
			memo="投注冻结,优惠"+order.getPreferentialAmount().divide(new BigDecimal(100)).doubleValue()+"元";
			amount=amount.subtract(order.getPreferentialAmount());
		}
        if (accountDetail==null){
			uas.freezeAmount(order.getUserno(), order.getId(),amount , AccountType.bet, AccountDetailType.freeze, memo, order.getAgentId(), order.getLotteryType(), order.getPhase(), order.getAgencyNo(), accountflag);
		}
		lotteryOrderService.updateOrderStatusAndPayStatus(orderId, OrderStatus.NOT_SPLIT.value, PayStatus.ALREADY_PAY.value);


	}
	
	
	
	public void refund(String orderId) {
		LotteryOrder order = lotteryOrderService.get(orderId);
		if (order == null) {
			logger.error("账号解冻,订单{}不存在", orderId);
			return;
		}
		if (isHemai(order)) {
			return;
		}

		if(order.getOrderStatus()!= OrderStatus.PRINTED.value){
			logger.error("订单{}的状态是{},不是出票成功", orderId, order.getOrderStatus());
			return;
		}

		if (order.getPayStatus() != PayStatus.PAY_SUCCESS.getValue()) {
			logger.error("订单{}的支付状态是{},未支付成功,不做退款处理", orderId, order.getPayStatus());
			return;
		}
		if (order.getPayStatus() == PayStatus.REFUNDED.getValue()) {
			logger.error("订单{}已退款完成,不做解冻处理", orderId);
			return;
		}
		uas.refund(order.getUserno(),orderId,order.getAmount());
		lotteryOrderService.updatePayStatus(PayStatus.REFUNDED.value, orderId);
		lotteryOrderService.updateOrderResultStatus(orderId,OrderResultStatus.unable_to_draw.value,null);
	}

	protected boolean isHemai(LotteryOrder order) {
		if (order.getBetType() == BetType.hemai.value) {
			return true;
		}
		return false;
	}

}
