package com.lottery.core.handler;

import com.lottery.common.cache.redis.RedisLock;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.LotteryUploadFile;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryUploadFileService;
import com.lottery.core.service.OrderSplitService;
import com.lottery.lottype.LotManager;
import com.lottery.lottype.SplitedLot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderSplitHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	protected OrderSplitService oss;

	@Autowired
	private LotManager lotmanager;
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	@Autowired
	protected IdGeneratorService idGeneratorService;
	@Autowired
	protected SharedJedisPoolManager shareJedisPoolManager;
	@Autowired
	protected LotteryUploadFileService uploadFileService;

	public void handler(String orderid) {
		// 为了防止重复拆票,锁的时间为3分钟
		RedisLock lock = new RedisLock(this.shareJedisPoolManager, String.format("lottery_order_split_%s", orderid), 180);

		boolean hasLocked = false;

		try {
			// 等待获取锁30秒
			hasLocked = lock.tryLock(30000l, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("获取redis锁出错", e);
		}
		
		try {
			LotteryOrder lotteryOrder = lotteryOrderService.get(orderid);
			if (lotteryOrder == null) {
				logger.error("订单orderId={},不存在", orderid);
				return;
			}

			if (lotteryOrder.getOrderStatus() != OrderStatus.NOT_SPLIT.value) {
				logger.error("订单:{}状态为:{},本次拆票退出", new Object[] { orderid, lotteryOrder.getOrderStatus() });
				return;
			}
			String betcode=lotteryOrder.getContent();

			if(lotteryOrder.getBetType()==BetType.upload.value){
				LotteryUploadFile uploadFile=uploadFileService.get(orderid);
				if(uploadFile!=null){
					betcode=uploadFile.getContent();
				}
			}
			
			List<SplitedLot> lots = null;
			if(YesNoStatus.yes.value==lotteryOrder.getPrizeOptimize()) {
				lots = lotmanager.getLot(String.valueOf(lotteryOrder.getLotteryType())).splitPrizeOptimize(betcode, lotteryOrder.getMultiple(), lotteryOrder.getAmount().longValue(), lotteryOrder.getAddition() == YesNoStatus.yes.getValue() ? 300 : 200);
			}else {
				lots = lotmanager.getLot(String.valueOf(lotteryOrder.getLotteryType())).split(betcode, lotteryOrder.getMultiple(), lotteryOrder.getAmount().longValue(), lotteryOrder.getAddition() == YesNoStatus.yes.getValue() ? 300 : 200);
			}
					
			if (lots == null || lots.size() == 0) {
				logger.error("订单({})没有要拆的票,请检查", orderid);
				return;
			}
			Long id = idGeneratorService.gerTicketId(lots.size());
			oss.ticketSplit(lotteryOrder, lots, id);
		} catch (Exception e) {
			logger.error("拆票异常orderid={}",orderid);
			logger.error(e.getMessage(),e);
		} finally {
			if (hasLocked) {
				lock.unlock();
			}
		}
	}
	
	
	
	
}
