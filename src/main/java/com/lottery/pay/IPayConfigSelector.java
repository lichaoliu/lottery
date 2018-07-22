/**
 * 
 */
package com.lottery.pay;

import java.util.List;


import com.lottery.core.domain.PayProperty;

/**
 * 充值
 * 
 * 
 *
 */
public interface IPayConfigSelector {

    
    public List<PayProperty> getPayConfigFromCache(String payConfigId);
    
   
}
