package com.lottery.core.dao;

import java.util.Date;
import java.util.List;

import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.LotteryPhase;


public interface LotteryPhaseDAO extends IGenericDAO<LotteryPhase, Long>{
  
	/**
	 * 获取当前，所有的彩种都有且只有一个当前期
	 * 
	 * @param lotteryType  彩种
	 *           
	 * */
	public LotteryPhase getCurrent(int lotteryType) throws Exception;
	   /**
     * 根据彩种，期号查询
     * @param lotteryType 彩种
     * @param phase 彩期期号
     * @return phase
     * */
	public LotteryPhase getByTypeAndPhase(int lotteryType,String phase)throws Exception;
	
	
	/**
	 * 保存开奖号码
	 * @param lotteryType
	 * @param phase
	 * @return
	 */
	public void saveWincode(int lotteryType,String phase,String wincode) throws Exception;
	
	
	/**
	 * 保存开奖号码
	 * @param lotteryType
	 * @param phase
	 * @return
	 */
	public void saveZcWincode(int lotteryType,String phase,String wincode) throws Exception;
	
	
	/**
	 * 更新奖期开奖状态
	 * @param lotteryType
	 * @param phase
	 */
	public void updatePhasePrizeState(int lotteryType, String phase,int status)throws Exception;
	
	
	/**
	 * 开奖 添加开奖号码 修改状态为已开奖
	 * @param lotteryType
	 * @param phase
	 * @param wincode
	 * @return
	 */
	public int onPrize(int lotteryType,String phase,String wincode);
	
	
	/**
	 * 更新奖期派奖状态
	 * @param lotteryType
	 * @param phase
	 */
	public void updatePhaseEncashState(int lotteryType, String phase,int status)throws Exception;

	/**
	 * 根据彩种状态查询彩期
	 * @param lotteryType 彩种
	 * @param phaseStatus 状态
	 * @return list
	 * */
	public List<LotteryPhase> getByLotteryTypeAndStatus(int lotteryType,int phaseStatus);
	/**
	 * 根据彩种状态查询彩期
	 * @param lotteryType 彩种
	 * @param statusList 状态集合
	 * @return list
	 * */
	public List<LotteryPhase> getByLotteryAndStatuses(int lotteryType,List<Integer> statusList);
	/**
	 * 查询某彩种的历史开奖号码
	 * @param lotteryType 彩种
	 * @param  num 数量
 	 * */
	
	public List<LotteryPhase> findWininfoListByLottype(int lotteryType, int num);
	/**
	 * 获取彩种上一期的开奖记录
	 * @param lotteryType 彩种
	 * */
	public LotteryPhase getLastRecord(int lotteryType);


	public List<String> getPhaseByLottypeAndNum(Integer lotteryType, Integer num);
	
	/**
	 * 查询彩种历史开奖记录,包含开奖号码，详情
	 * @param lotteryType 彩种
	 * @param page 分页
	 * */
	public List<LotteryPhase> getHistoryList(int lotteryType,PageBean<LotteryPhase> page);
	
	
	/**
	 * 查询历史开奖，同时包含已经结束未开奖金
	 * @param lotteryType
	 * @param page
	 * @return
	 */
	public List<LotteryPhase> getHistoryListWithClosed(int lotteryType, PageBean<LotteryPhase> page);
	
	/**
	 * 查询历史开奖,只要期关闭即可,可以不包含开奖号码，开奖详情
	 * @param lotteryType 彩种
	 * @param num 查询数量
	 * */
	public List<LotteryPhase> getHistoryPhase(int lotteryType,int num);
	
	/**
	 * 查询彩期某期后的彩期
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param num 查询数量
	 * */
	public List<LotteryPhase> getNextPhase(int lotteryType,String phase,int num);
	/**
	 * 查询彩期某期后的彩期(包括当前)
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param num 查询数量
	 * */
	
	public List<LotteryPhase> getNextPhaseWithCurrent(int lotteryType,String phase,int num);

	/**
	 * 查询彩期某期后的彩期(不包括当前)
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param num 查询数量
	 * */

	public List<LotteryPhase> getNextPhaseNotWithCurrent(int lotteryType,String phase,int num);
	/**
	 * 查询除了未开售的所有期
	 * @param lotteryType 彩种
	 * @param num 查询数量
	 * */
	public List<LotteryPhase> getPhaseList(int lotteryType,int num);


	public int saveWininfo(int lotteryType, String phase,String wincode,String info,String poolAmount,String addPoolAmount,String saleAmount,String whereFrom);
	/**
	 * 根据彩种，彩种状态查询
	 * 
	 * @param lotteryType 彩种
	 * @param terminalStatus
	 *            彩期状态
	 * @return list
	 * */

	public List<LotteryPhase> getByTerminalStatus(int lotteryType, int terminalStatus);
	
	/**
	 * 根据彩种查询期信息
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param max
	 *            最大条数
	 * */
	
	public List<LotteryPhase> getByLotteryType(Integer lotteryType, int max);


	public int updateStatus(int lotteryType, String phase, int phaseStatus, int terminalStatus, int forSale, int forCurrent);
	
	/**
	 * 新期切换
	 * @param phaseStatus 彩期状态
	 * @param forSale 销售状态
	 * @param forCurrent 是否当前期
	 * @param terminalStatus 可出票状态
	 * @param id 
	 * */
	public int phaseSwitch(int phaseStatus, int forSale, int forCurrent,int terminalStatus,Long id);

	/**
	 * 新期切换
	 * @param phaseStatus 彩期状态
	 * @param forSale 销售状态
	 * @param forCurrent 是否当前期
	 * @param terminalStatus 可出票状态
	 * @param lotteryType 彩种
	 * @param phase  期号
	 * */
	
	public int phaseSwitch(int phaseStatus, int forSale, int forCurrent,int terminalStatus,int lotteryType, String phase);
	/**
	 * 修改彩期终端状态
	 * 
	 * */
	public int updateTerminalStatus(int terminalStatus,Long id);
	
	/**
	 * 修改彩期终端状态
	 * @param lotteryType 彩种
	 * @param  phase 期号
	 * */
	public int updateTerminalStatus(int terminalStatus,int lotteryType,String phase);

    /**
	 * 修改出票截止时间
	 * @param lotteryType  彩种
	 * @param phase 期号
	 * @param endTicketTime 停止出票时间
	 * */
	public int updateEndTicketTime(int lotteryType,String phase,Date endTicketTime);


	
}
