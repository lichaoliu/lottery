package com.lottery.test.lottype;

import java.util.List;

import org.junit.Test;

import com.lottery.lottype.JiangSuKuai3;
import com.lottery.lottype.SplitedLot;

public class Jsk3Test {
	
	
	private JiangSuKuai3 k3 = new JiangSuKuai3();

	
	@Test
	public void split() {
		List<SplitedLot> splits = k3.split("100960-3,4^", 1, 400, 200);
		for(SplitedLot lot :splits) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
	}
}
