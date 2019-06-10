package com.lottery.b2b.message;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;


public interface IExternalResponseMessage extends IExternalMessage {

    /**
     * @return 处理结果
     */
    public ErrorCode getErrorCode();
    public BigDecimal getCreditBalance();
    
    public String getKey();

    public Long getTerminalId();
    
}
