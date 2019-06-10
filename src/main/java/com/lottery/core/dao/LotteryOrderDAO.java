package com.lottery.core.dao;


import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.ticket.LotteryOrder;


import java.util.Date;
import java.util.List;

public interface LotteryOrderDAO extends IGenericDAO<LotteryOrder, String>{
	
	/**
	 * 更新订单(竞彩北单)第一个场次索引
	 * 在算奖的时候，如果某个场次延迟未开奖，更新第一个场次索引为未开奖的场次，方便下次开奖找出订单算奖
	 * @param orderid
	 * @param matchno
	 */
	public int updateOrderLastMatchNum(String orderid,String matchno);

	
	
	/***
	 * 根据彩种，期号，场次号,订单 状态，开奖状态查询
	 * @param lotteryType 彩种
	 * @oparm phase 期号
	 * @param matchNum 场次号
	 * @param orderStatusList 订单状态
	 * @param orderResultStatusList 开奖状态
	 * 
	 * */
	
	public List<LotteryOrder> getByLotteryPhaseMatchNumAndStatus(Integer lotteryType,String phase,String matchNum,List<Integer> orderStatusList,List<Integer> orderResultStatusList,PageBean<LotteryOrder> page);

	

	

	public List<LotteryOrder> findBeidanByAndHemaiNotNull(String phase,	Long matchNum);
	public List<LotteryOrder> findJcByfirstMatchNumAndHemaiNotNull(Long matchNum);
	
	/**
	 * 根据开始时间，结束时间，状态查询订单
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param status 状态
	 * */
	public List<LotteryOrder> getByStatus(Date startDate,Date endDate,Integer status,PageBean<LotteryOrder> page);

	
	/**
	 * 根据用户编号，开始时间，结束时间，查询投注明细
	 * @param userno 用户编号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * */
	public List<LotteryOrder> getOrderListByUserno(String userno,Date startTime,Date endTime,PageBean<LotteryOrder> page);

    /**
     * 根据彩种，期号，投注类型，开奖类型查询
     * 根据彩种，期号，投注类型，开奖类型查询
     * @param lotteryType 彩种编号
     * @param phase 期号
     * @param resultstatus 开奖结果
     * */
    public List<LotteryOrder> getByLotteryTypePhaseBetTypeAndResultStatus(Integer lotteryType,String phase,int betType,int resultstatus,PageBean<LotteryOrder> page);
    /**
     * 根据用户，彩种，订单状态，时间查询消费额
     * @param userno 用户名
     * @param orderStatus 订单状态
     * @param lotteryType 彩种
     * @param startDate 开始时间
     * @param endDate 结束时间
     * */
    
    public Object[] getOrderUserBet(String userno,int orderStatus,int lotteryType,Date startDate,Date endDate);

    
	/**
	 * 根据用户编号,彩种,开始时间，结束时间，状态查询订单
	 * @param userno 用户名
	 * @param lotteryType 彩种
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param status 状态
	 * */
	public List<LotteryOrder> getByStatus(String userno,int lotteryType,Date startDate,Date endDate,int status);
	
	
	
	
	/**
	 *根据彩种，期号，订单状态，开奖状态查询
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param orderStatusList 订单状态
	 * @param orderResultStatusList 开奖状态
	 * @param sortType 排序方式(asc,desc)
	 * @param LastMatchNum 最大场次号(北单，竞彩)
	 * */
	public List<LotteryOrder> getByStatusAndType(int lotteryType,String phase,List<Integer> orderStatusList,List<Integer> orderResultStatusList,String sortType,Long LastMatchNum,PageBean<LotteryOrder> page);
	/**
	 *根据彩种，期号，订单状态，开奖状态查询
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param orderStatusList 订单状态
	 * @param orderResultStatusList 开奖状态
	 * @param beginTime  开始时间
	 * @param endTime 结束时间
	 * @param LastMatchNum 最大场次号(北单，竞彩)
	 * */
	public List<LotteryOrder> getByStatusAndDate(int lotteryType, String phase,List<Integer> orderStatusList,List<Integer> orderResultStatusList, Date beginTime, Date endTime, Long LastMatchNum,PageBean<LotteryOrder> page);
	/**
	 * 查询用户订单
	 * @param userno
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param type
	 * @param lotteryType
	 * @param orderStatusList
	 * @param orderResultStatusList
	 * @return
	 */
	public List<LotteryOrder> getOrder(String userno,String startTime,String endTime,PageBean<LotteryOrder> page,String type,String lotteryType,List<Integer> orderStatusList,List<Integer> orderResultStatusList);
	
	
	/**
	 * 查询用户订单
	 * @param userno
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param type
	 * @param lotteryType
	 * @param orderStatusList
	 * @param orderResultStatusList
	 * @return
	 */
	public List<LotteryOrder> getOrder(String userno,String startTime,String endTime,PageBean<LotteryOrder> page,String type,List<Integer> lotteryType,List<Integer> orderStatusList,List<Integer> orderResultStatusList);
	
	
	/**
	 * 修改订单的支付状态
	 * @param payStatus 支付状态
	 * @param id 订单号
	 * */
	public int updatePayStatus(int payStatus,String id);
	/**
	 * @param lotteryType 彩种
	 * @param statusList 票状态
	 * @param dealLine 结束时间
	 * */
	
	public List<LotteryOrder> getByOrderStatusAndDealLine(int lotteryType,List<Integer> statusList,Date dealLine,PageBean<LotteryOrder> pageBean);
	/**
	 * 根据彩种，期号查询订单
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @return list 返回数据
	 * */

	public List<LotteryOrder> getByLotteryTypeAndPhase(Integer lotteryType, String phase, PageBean<LotteryOrder> page);

	/**
	 * 修改订单状态
	 * @param status 订单状态
	 * @param id 订单号
	 * */
	public int updateOrderStatus(int status,String id);
	/**
	 * 修改订单派奖状态
	 * @param orderid 订单号
	 * @param rewarStatus 派奖状态
	 * */
	public int updateOrderRewardStatus(int rewarStatus,String orderid);
	/**
	 * 
	 * */
	public int updateOrderStatusPrintTime(String orderId,int orderStatus,Date date);
    /**
     * 修改票的状态和票总数
     * @param  orderId 票号
     * @param orderStatus 订单状态
     * @param ticketCount 票总数
     * */
    public int updateOrderStatusAndTicketCount(String orderId,Integer orderStatus,Integer ticketCount );
    /**
     * 修改票的状态,失败票总数
     * @param  orderId 票号
     * @param orderStatus 订单状态
     * @param failCount 失败票数
     * */
    public int updateOrderStatusAndFailCount(String orderId,Integer orderStatus,Integer failCount );
	/**
	 * 修改订单状态，支付状态
	 * @param  orderId 订单号
	 * @param  orderStatus 订单状态
	 * @param  payStatus 支付状态
	 * */
	public int updateOrderStatusAndPayStatus(String orderId,Integer orderStatus,Integer payStatus);
	/**
	 * 根据彩种,订单状态查询
	 * @param  orderStatus 订单状态
	 * @param  pageBean 分页
	 * */
	public List<LotteryOrder> getByOrderStatus(Integer lotteryType,int orderStatus,PageBean<LotteryOrder> pageBean);
	/**
	 * 修改订单中奖状态
	 * */
	public  void updateOrderPrize(LotteryOrder lotteryOrder);
    /**
	 * 查询中奖记录
	 * @param  userno 用户编号
	 * @param  endDate 结束时间
	 * @param  startDate 开始时间
	 * */
	public List<LotteryOrder> getAllPrize(String userno,Date startDate,Date endDate,PageBean<LotteryOrder> pageBean);
    /**
	 * 修改订单中奖状态
	 * @param orderId 订单号
	 * @param  orderResultstatus 中奖状态
	 * @param wincode 中奖号码,可为空
	 * */
	public  void updateOrderResultStatus(String orderId,int orderResultstatus,String wincode);
	/**
	 * 根据彩种,彩期,状态,开奖状态查询
	 * @param lotteryType 彩种
	 *  @param phase 彩期
	 * @param  orderResultStatusList 中奖状态
	 * @param  orderStatusList
	 * */
	public List<LotteryOrder> getByLotteryPhaseAndStatus(Integer lotteryType,String phase,List<Integer> orderStatusList,List<Integer> orderResultStatusList,int max);
	/**
	 *根据彩种，期号，订单状态，开奖状态查询,场次,时间
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param orderStatusList 订单状态
	 * @param orderResultStatusList 开奖状态
	 * @param beginTime  开始时间
	 * @param endTime 结束时间
	 * @param matchNum 最大场次号(北单，竞彩)
	 * */
	public List<LotteryOrder> getByLotteryStatusMatchNumAndDate(int lotteryType, String phase,List<Integer> orderStatusList,List<Integer> orderResultStatusList, Date beginTime, Date endTime, String matchNum,PageBean<LotteryOrder> page);

    /**
	 * 根据用户编号,订单状态查询
	 * @param userno 用户编号
	 * @param orderStatus  订单状态
	 * */
	public List<LotteryOrder> getByUsernoAndStatus(String userno,int orderStatus);
	
	/***
	 * 检查未派奖的订单个数
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * */
	public Long countNotPrize(int  lotteryType,String phase);
	
	/***
	 * 检查未算奖的订单
	 * @param lotteryType 彩种
	 * @param phase 期号 
	 * */
	public Long countNotEncash(int  lotteryType,String phase);


	public List<String> getIdByStatusAndDate(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, Date beginTime, Date endTime, Long lastMatchNum, PageBean<LotteryOrder> page);


	/**
	 * 根据状态查询
	 * @param orderStatus  订单状态
	 * @param orderResultStatus  开奖状态
	 * @param max 最大条数
	 * */
	public List<LotteryOrder> getByStatus(int orderStatus,int orderResultStatus,int max);


	/**
	 *
	 * 修改订单开奖号码
	 * @param orderId  订单号
	 * @param wincode 开奖号码
	 * */

	public void updateWincode(String orderId,String wincode);

	/**
	 * 根据 彩种，期号，票状态查询
	 * @param lotteryType  彩种
	 * @param phase 期号
	 *@param status 状态
	 * @param max  最大条数
	 * */

	public List<LotteryOrder> getByTypePhaseAndStatus(int lotteryType,String phase,int status,int max);
}
