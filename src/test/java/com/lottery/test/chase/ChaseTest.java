package com.lottery.test.chase;

import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.dto.ChaseRequest;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.JsonUtil;

public class ChaseTest {

	public static void main(String[] args) {
		/**2191756
		 * 追号投注
		 * @param userno 用户编号
		 * @param lotteryType 彩种
		 * @param betcode 注码
		 * @param amt 单倍金额
		 * @param chaseType 追号类型
		 * @param amount 总金额 单期
		 * @param num 追号期数
		 * @param beginPhase 开始期号
		 * @param multiple 倍数
		 * @param addition 是否追加(0否，1是)
		 * @param advancedChase 高级追号
		 * @param totalPrize 中奖总金额
		 * */
		String user="2014072900000001";
		//user="2014060200000052";
		List<ChaseRequest> crList=new ArrayList<ChaseRequest>();
		ChaseRequest cr2=new ChaseRequest();
		cr2.setAmt(400);
		cr2.setMultiple(2);
		cr2.setPhase("140727002");
		cr2.setAmount(400);
		crList.add(cr2);
		ChaseRequest cr1=new ChaseRequest();
		cr1.setAmt(200);
		cr1.setMultiple(1);
		cr1.setPhase("140727001");
		cr1.setAmount(200);
		crList.add(cr1);
		String se=JsonUtil.toJson(crList);
		se="";
		String param="lotteryType=1005&betcode=100540-01,02,03^&amt=200&multiple=2&amount=400&totalPrize=16000&num=10&chaseType=2&beginPhase=140730038&userno="+user+"&advancedChase="+se;
        String url="http://118.26.65.147/lottery/chase/bet";
       // url="http://192.168.8.150/lottery/chase/bet";
        
        try {
        	System.out.println(param);
			String res=HTTPUtil.post(url, param ,CharsetConstant.CHARSET_UTF8);
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
