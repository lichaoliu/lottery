package com.lottery.core.terminal;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.cache.TerminalFailureCache;

/**
 * 根据过滤规则进行过滤, 返回false表示不被过滤掉
 *
 */
public interface ITerminalSelectorFilter {

    public boolean filter(TerminalFailureCache terminalFailureCache, LotteryType lotteryType, String phase, PlayType playType);
}
