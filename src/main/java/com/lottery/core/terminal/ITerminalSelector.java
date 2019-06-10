/**
 * 
 */
package com.lottery.core.terminal;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.core.domain.ticket.LotteryTicketConfig;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 终端选择器
 * 根据一定的算法获取可用终端
 * @author fengqinyun
 *
 */
public interface ITerminalSelector {

	/**
	 * 增加指定终端的失败计数（指定彩种指定彩期）
     * @param terminalId 终端号
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param playType 玩法
     */
	public void increaseFailureCount(Long terminalId, LotteryType lotteryType, String phase, PlayType playType);
	
	/**
	 * 计算最高优先级的可用终端，返回终端号
	 * @param lotteryType 彩种
	 * @param phase 彩期
	 * @param deadline 截止期
	 * @return 终端编号
	 */
	public Long getTopPriorityTerminalId(LotteryType lotteryType, String phase, PlayType playType, Date deadline);
    /**
     * 计算最高优先级的可用终端，返回终端号
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param playType 玩法
     * @param deadline 截止期
     * @param amount 批次金额
     * @return 终端编号
     */
    public Long getTopPriorityTerminalIdWithAmount(LotteryType lotteryType, String phase, PlayType playType, Date deadline, BigDecimal amount);

    /**
     * 计算最高优先级的可用终端, 且可根据传入的自定义过滤器进行过滤，返回终端号
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param playType 玩法
     * @param deadline 截止期
     * @param amount 批次金额
     * @return 终端编号
     */
    public Long getTopPriorityTerminalIdWithAmount(LotteryType lotteryType, String phase, PlayType playType, Date deadline, BigDecimal amount, ITerminalSelectorFilter filter);

	/**
	 * 计算最高优先级的可用终端，返回终端号
	 * @param lotteryType 彩种
	 * @param phase 彩期
	 * @param deadline 截止期
	 * @param filter 过滤
	 * @return 终端编号
	 */
	public Long getTopPriorityTerminalId(LotteryType lotteryType, String phase, PlayType playType, Date deadline,ITerminalSelectorFilter filter);
	/**
	 * 根据终端ID获取终端类型
	 * @param terminalId 终端编号
	 * @return 指定编号的终端类型
	 */
	public TerminalType getTerminalType(Long terminalId);

	/**
	 * 某终端是否在对应彩种终端配置的禁止送票期间，先判断全局限制，再看彩种单独配置
	 * @param terminalId 终端编号
	 * @param lotteryType 彩种
	 * @return 是否当前禁止送票
	 */
	public boolean isDuringSendForbidPeriod(Long terminalId, LotteryType lotteryType);
	
	/**
	 * 是否在某彩种的全局禁止送票期间
	 * @param lotteryType 彩种
	 * @return 当前是否全局禁止送票
	 */
	public boolean isDuringGlobalSendForbidPeriod(LotteryType lotteryType);

    /**
     * 某终端是否在对应彩种终端配置的禁止检票期间，先判断全局限制，再看彩种单独配置
     * @param terminalId 终端编号
     * @param lotteryType 彩种
     * @return 是否当前禁止检票
     */
    public boolean isDuringCheckForbidPeriod(Long terminalId, LotteryType lotteryType);
    
    /**
	 * 是否已禁止送票或在禁止送票期间
	 * @param lotteryType 彩种
	 * @return 当前是否全局禁止送票
	 */
	public boolean isGlobalSendPausedOrDuringSendForbidPeriod(LotteryType lotteryType);

    /**
     * 是否在某彩种的全局禁止检票期间
     * @param lotteryType 彩种
     * @return 当前是否全局禁止检票
     */
    public boolean isGlobalCheckDisabledOrDuringCheckForbidPeriod(LotteryType lotteryType);

    /**
     * 是否在某彩种的全局禁止分票期间
     * @param lotteryType 彩种
     * @return 当前是否全局禁止分票
     */
    public boolean isGlobalDisabledOrDuringAllotForbidPeriod(LotteryType lotteryType);

    
    
    
	/**
	 * 获取彩种配置和此彩种的玩法配置中暂停送票的终端
	 * @param lotteryType 彩种
	 * @return 暂停送票的终端编号列表
	 */
	public List<Long> getPausedTerminalIdList(LotteryType lotteryType);

	/**
	 * 获取彩种配置和此彩种的玩法配置中可用送票的终端
	 * @param lotteryType 彩种
	 * @return 可用送票的终端编号列表
	 */

	public List<Long> getEnabledTerminalIdList(LotteryType lotteryType);
    /**
     * 是否已为独立玩法指定终端配置
     * 判断规则：该玩法下存在可用的终端配置（全局终端配置和该玩法终端配置均处于启用状态，此处忽略对应彩种的终端配置）
     * @param playType 指定的玩法
     * @return true or false
     * @throws Exception 需要显示的抛出异常，区分出错和未指定终端配置的情况
     */
    public boolean hasSpecifyTerminalConfigForPlayType(PlayType playType) throws Exception;

    /**
     * 获取某彩种或者某玩法已配置的所有可用终端列表
     * @param lotteryType 彩种
     * @param playType 玩法
     * @return 已配置的所有可用终端列表
     * @throws Exception 获取终端时异常出错，需要区别出错和没有可用终端的情况
     */
    public List<Long> getEnabledTerminalIdList(LotteryType lotteryType, PlayType playType) throws Exception;

    /**
     * 获取某彩种或者某玩法的当前所有可用终端列表
     * @param lotteryType 彩种
     * @param playType 玩法
     * @param deadline 截止期
     * @return 当前可用的终端编号列表
     * @throws Exception 获取终端时异常出错，需要区别出错和没有可用终端的情况
     */
    public List<Long> getAvailableTerminalIdList(LotteryType lotteryType, PlayType playType, Date deadline) throws Exception;

    /**
	 * 获取单一终端配置属性信息
	 * @param terminalId 终端号
	 * @return 终端配置列表
	 */
    public List<TerminalProperty> getTerminalPropertyFromCache(Long terminalId);
    
    /**
     * 根据终端类型获取终端信息
     * @param terminalType 终端类型
     * @return 指定终端类型对应的终端列表
     */
    public List<Terminal> getTerminalList(TerminalType terminalType) throws Exception;
    /**
	 * 获取单一终端是否启用检票
	 * @param terminalId 终端号
	 * @return 终端对象
	 */
    public boolean isCheckEnabled(Long terminalId);
    
    /**
	 * 获取指定终端的某彩种下是否启用检票
	 * @param terminalId 终端号
	 * @param lotteryType 彩种编号
	 * @return 对应的终端配置
	 */
    public boolean isCheckEnabledForLotteryType(Long terminalId, LotteryType lotteryType);
    
    /**
	 * 获取指定终端的终端配置列表
	 * @param terminalId 终端号
	 * @return 对应的终端配置列表
	 */
    public List<TerminalConfig> getTerminalConfigList(Long terminalId);
    
    /**
     * 获取指定终端的某彩种下是否已启用
     * @param terminalId 终端号
     * @param lotteryType 彩种编号
     * @return 是否启用
     */
    public boolean isEnabledForLotteryType(Long terminalId, LotteryType lotteryType);
    
    /**
     * 获取指定终端的某彩种下玩法是否已启用
     * @param terminalId 终端号
     * @param lotteryType 彩种编号
     * @param playType 玩法
     * @return 是否启用
     */
    public boolean isEnabledForLotteryType(Long terminalId, LotteryType lotteryType,PlayType playType);
    
    /**
     * 获取指定终端是否全局启用
     * @param terminalId 终端号
     * @return 是否启用
     */
    public boolean isEnabled(Long terminalId);
    /**
   	 * 根据终端ID获取终端类型
   	 * @param terminalId 终端编号
   	 * @return 指定编号的终端
   	 */
   	public Terminal getTerminal(Long terminalId);

    public boolean isConfigured(Long terminalId, LotteryType lotteryType);
    
    /**
	 * 彩种是否可售
	 * @param lotteryType 彩种
	 */
    public boolean isSaleEnabledForLotteryType(LotteryType lotteryType);


    /**
     * 彩种的终端相关的配置记录查询
     * @param  lotteryType 彩种
     * */
    public LotteryTicketConfig getLotteryTicketConfig(LotteryType lotteryType);


	/**
	 * 计算最高优先级的可用终端，返回终端号
	 * @param lotteryType 彩种
	 * @param phase 彩期
	 * @param playType 玩法
	 * @param amount 投注金额
	 * @param deadline 截止期
	 * @param filter 过滤
	 * @param betCode 注码
	 * @return 终端编号
	 */
	public Long getTopPriorityTerminalIdWithPlayTypeAndAmount(LotteryType lotteryType, String phase, PlayType playType,BigDecimal amount, Date deadline,ITerminalSelectorFilter filter,String betCode)throws Exception;

	/**
	 *独立玩法,金额指定终端配置
	 * @param lotteryType 彩种
	 *@param phase 期号
	 *@param playType 玩法
	 *@param amount 金额
	 *@param deadline 结束时间
	 *@param  betCode 注码
	 * */

	public Long getTopPriorityTerminalIdSpecifyPlayTypeWithAmount(LotteryType lotteryType, String phase,PlayType playType, Date deadline,BigDecimal amount,String betCode)throws Exception;

	/**
	 * 获取某彩种或者某玩法已配置的所有可用终端配置规则列表
	 *
	 * @param lotteryType
	 *            彩种
	 * @param playType
	 *            玩法
	 * @return 所有可用终端配置规则列表
	 * @throws Exception
	 *             区分获取终端异常出错和未配置终端的情况
	 */
	public List<TerminalConfig> getEnabledTerminalConfigList(LotteryType lotteryType, PlayType playType) throws Exception;

}
