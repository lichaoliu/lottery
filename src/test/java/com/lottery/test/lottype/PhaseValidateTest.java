package com.lottery.test.lottype;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.AnHuiKuai3;
import com.lottery.lottype.Cqssc;
import com.lottery.lottype.DLT;
import com.lottery.lottype.HLJkl10;
import com.lottery.lottype.JiangXi11c5;
import com.lottery.lottype.NXKuai3;
import com.lottery.lottype.PLS;
import com.lottery.lottype.PLW;
import com.lottery.lottype.QLC;
import com.lottery.lottype.QXC;
import com.lottery.lottype.SSQ;
import com.lottery.lottype.Sd11x5;
import com.lottery.lottype.TJkl10;
import com.lottery.lottype.ThreeD;

public class PhaseValidateTest {

	AnHuiKuai3 ahk3 = new AnHuiKuai3();
	Cqssc cqssc = new Cqssc();
	DLT dlt = new DLT();
	HLJkl10 hljk10 = new HLJkl10();
	JiangXi11c5 jx11x5 = new JiangXi11c5();
	NXKuai3 nxk3 = new NXKuai3();
	PLS pls = new PLS();
	PLW plw = new PLW();
	QLC qlc = new QLC();
	QXC qxc = new QXC();
	Sd11x5 sd11x5 = new Sd11x5();
	SSQ ssq = new SSQ();
	ThreeD d3 = new ThreeD();
	TJkl10 tj10 = new TJkl10();
	
	
	
	@Test
	public void test() {
		Assert.assertTrue(ahk3.validatePhase("141024020"));
		Assert.assertTrue(cqssc.validatePhase("20141120092"));
		Assert.assertTrue(dlt.validatePhase("2014145"));
		Assert.assertTrue(hljk10.validatePhase("14080801"));
		Assert.assertTrue(jx11x5.validatePhase("14112117"));
		Assert.assertTrue(nxk3.validatePhase("141120065"));
		Assert.assertTrue(pls.validatePhase("2014330"));
		Assert.assertTrue(plw.validatePhase("2014330"));
		Assert.assertTrue(qlc.validatePhase("2014330"));
		Assert.assertTrue(qxc.validatePhase("2014330"));
		Assert.assertTrue(sd11x5.validatePhase("14112118"));
		Assert.assertTrue(ssq.validatePhase("2014138"));
		Assert.assertTrue(d3.validatePhase("2014331"));
		Assert.assertTrue(tj10.validatePhase("20141121010"));
	}
	
}
