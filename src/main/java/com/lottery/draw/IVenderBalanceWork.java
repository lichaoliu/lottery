package com.lottery.draw;

import java.util.List;

import com.lottery.core.domain.terminal.MemberAccount;


/**
 * 从出票商抓取同步余额
 * */
public interface IVenderBalanceWork {
    
	/**
	 * 同步余额
	 */
	public List<MemberAccount> syncBalance();
}
