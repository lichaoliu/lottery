package com.lottery.draw;

import java.io.Serializable;


/**
 * 开奖队列任务数据
 * @author fengqinyun
 *
 */
public interface ILotteryDrawTask extends Serializable{
	/**
	 * 彩种
	 * @return
	 */
	public Integer getLotteryType();
	/**
	 * 期号
	 * @return
	 */
	public String  getPhase();
	
	/**
	 * 最多场次号
	 * @return
	 */
	public Long getLastMatchNum();
	
	public String getWinCode();
	
}
