package com.lottery.draw;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.MemberAccountService;
import com.lottery.core.service.TerminalService;
import com.lottery.ticket.IVenderConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractVenderBalanceWork implements IVenderBalanceWork {
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private VenderConfigHandler venderConfigService;

	protected abstract TerminalType getTerminalType();

	protected abstract MemberAccount getAccount(IVenderConfig config);
    @Autowired
	protected TerminalService terminalService;
	@Autowired
	protected MemberAccountService memberAccountService;

	/**
	 * 同步余额
	 */
	public List<MemberAccount> syncBalance() {
		List<IVenderConfig> configList = venderConfigService.getAllByTerminalType(getTerminalType());
		List<MemberAccount> list = new ArrayList<MemberAccount>();
		for (IVenderConfig config : configList) {
            MemberAccount memberAccount=memberAccountService.get(config.getTerminalId());
            if (memberAccount!=null&&memberAccount.getIsSync()!=null){
                if (memberAccount.getIsSync()==YesNoStatus.no.value){
                	list.add(memberAccount);
                    continue;
                }
            }
			MemberAccount member = getAccount(config);
			if (member != null) {
				Terminal terminal=terminalService.get(config.getTerminalId());
				if (terminal!=null&& StringUtils.isNotBlank(terminal.getName())){
					member.setTerminalName(terminal.getName());
				}
				member.setSmsFlag(YesNoStatus.no.value);
				member.setAgentCode(config.getAgentCode());
				member.setUpdateTime(new Date());
			    memberAccountService.merge(member);
				list.add(member);
			}
		}
		return list;
	}

}
