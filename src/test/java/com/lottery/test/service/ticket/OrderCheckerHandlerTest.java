package com.lottery.test.service.ticket;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.handler.OrderCheckerHandler;
import com.lottery.core.handler.UserAccountHandler;
import com.lottery.test.SpringBeanTest;
import com.lottery.ticket.vender.process.datacenter.GetMatchResultFromDateCenter;

public class OrderCheckerHandlerTest extends SpringBeanTest {

    protected GetMatchResultFromDateCenter gt;
 
    
    @Autowired
    protected OrderCheckerHandler och;
    @Autowired
    protected UserAccountHandler ud;
    @Resource
    public void setGt(GetMatchResultFromDateCenter gt) {
		this.gt = gt;
	}


	@Test
    public void testDc(){
    	try {
			//ps.drawExcetor(LotteryType.CJDLT.value, "15008", "10,22,14,27,11|05,11", null);
    		//och.check("TY150129000000223568");
    		ud.unfreeze("TY150213000000722786");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
