package com.lottery.ticket.vender.impl.xmac;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 15/9/1.
 */
@Service
public class XmacConfigFactory extends AbstractVenderConfigFactory {
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_XMAC;
    }

    @Override
    protected void init() {
        configFactoryMap.put(TerminalType.T_XMAC, this);
    }
}
