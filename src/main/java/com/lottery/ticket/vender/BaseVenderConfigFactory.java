package com.lottery.ticket.vender;

import com.lottery.common.contains.lottery.TerminalType;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 15/6/17.
 */
@Service
public class BaseVenderConfigFactory extends AbstractVenderConfigFactory {
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.all;
    }

    @Override
    protected void init() {
        configFactoryMap.put(TerminalType.all, this);
    }
}
