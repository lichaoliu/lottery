/**
 * 
 */
package com.lottery.ticket;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.ticket.Ticket;

import java.util.List;

/**
 * 出票商格式转换器
 * @author fengqinyun
 *
 */
public interface IVenderConverter {
	
	/**
	 * 根据外部的彩种编号查找内部彩种
	 * @param lotId 出票商彩种编号
	 * @return 平台彩种编号
	 */
	public LotteryType findLotteryType(String lotId);
	
	/**
	 * 对外的彩期号
	 * @param phase
	 * @return
	 */
	public String convertPhase(LotteryType lotteryType, String phase);
	
	/**
	 * 从外部彩期号转换成内部使用的彩期号
	 * 出票商期号--->平台期号
	 * @param lotteryType
	 * @param phase
	 */
	public String convertPhaseReverse(LotteryType lotteryType, String phase);
	/**
	 * 对外的彩期号
	 * @param phase
	 * @return
	 */
	public String convertPhase(int lotteryType, String phase);

	/**
	 * 从外部彩期号转换成内部使用的彩期号
	 * 出票商期号--->平台期号
	 * @param lotteryType
	 * @param phase
	 */
	public String convertPhaseReverse(int lotteryType, String phase);
	
	/**
	 * 对外的彩种代号
	 * 
	 * 平台彩种--->出票商彩种
	 * @param lotteryType
	 * @return
	 */
	public String convertLotteryType(LotteryType lotteryType);
	
	public String convertLotteryType(int lotteryType);
	
	/**
	 * 对外的玩法代号
	 * 平台玩法--->出票商玩法
	 * @param playType
	 * @return
	 */
	public String convertPlayType(String playType);
	
	public String convertPlayType(int playType);

	
	/**
	 * 对外的投注内容,多注票
	 * 平台票内容--->出票商内容
	 * @param ticket
	 * @return
	 */
	public List<String> convertContentList(Ticket ticket);
	
	/**
	 * 对外的投注内容,单注票
	 * @param ticket
	 * @return
	 */
	public String convertContent(Ticket ticket);
	
    /**
     * 彩种反转
     * @param lotteryId
     * @return
     */
	public LotteryType getLotteryMap(String lotteryId);
    public boolean isDuringSendForbidPeriod(LotteryType lotteryType);
    /**
	 * 竞彩赔率转换
	 * @param  venderSp 出票商赔率
	 * */
	public String convertSp(Ticket tickt,String venderSp);
}
