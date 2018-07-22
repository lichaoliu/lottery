/**
 * 
 */
package com.lottery.core.domain.terminal;

import java.util.HashSet;
import java.util.Set;

import com.lottery.common.contains.lottery.TerminalType;

/**
 * @author fengqinyun
 * 终端类型是否单线程
 */
public class TerminalTypeSingletonMapping {

    private static Set<TerminalType> singletonTerminalTypeSet = new HashSet<TerminalType>();

    static {
    	singletonTerminalTypeSet.add(TerminalType.T_TJFC_CENTER);
    	singletonTerminalTypeSet.add(TerminalType.T_XCS);
    }

    public static boolean isSingleton(TerminalType terminalType) {
        return singletonTerminalTypeSet.contains(terminalType);
    }
}
