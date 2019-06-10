package com.lottery.ticket.vender.impl.jydp;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Liuxy
 *
 */
@Component
public class JydpConfigFactory extends AbstractVenderConfigFactory {
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_JYDP;
    }

    
}
