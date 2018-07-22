package com.lottery.lottype;

import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
/**
 * 拆票
 * @author liuhongxing
 *
 */
public class SplitedLot {
	
	/**投注内容**/
	private String betcode;
	/**倍数     **/
	private int lotMulti;
	/**金额    **/
	private long amt;
	
	private int playtype;
	
	private int lotterytype;
	public String getBetcode() {
		return betcode;
	}
	public void setBetcode(String betcode) {
		this.betcode = betcode;
	}
	public int getLotMulti() {
		return lotMulti;
	}
	public void setLotMulti(int lotMulti) {
		this.lotMulti = lotMulti;
	}
	public long getAmt() {
		return amt;
	}
	public void setAmt(long amt) {
		this.amt = amt;
	}
	
	public int getPlaytype() {
		return playtype;
	}
	public void setPlaytype(int playtype) {
		this.playtype = playtype;
	}
	public int getLotterytype() {
		return lotterytype;
	}
	public void setLotterytype(int lotterytype) {
		this.lotterytype = lotterytype;
	}
//	public SplitedLot() {
//		super();
//	}
	
	public SplitedLot(int lotterytype) {
		this.lotterytype = lotterytype;
	}
	
	public SplitedLot(String betcode, int lotMulti, long amt,int lotterytype) {
		super();
		this.betcode = betcode;
		this.lotMulti = lotMulti;
		this.amt = amt;
		this.lotterytype = lotterytype;
	}
	
	/**
	 * 按倍数拆单
	 * @param betcode
	 * @param lotmulti
	 * @param permission 倍数
	 * @return
	 */
	public static List<SplitedLot> splitToPermissionMulti(String betcode,int lotmulti,int permission,int lotterytype) {
		if(permission<=0) {
			throw new LotteryException(ErrorCode.betcode_lotmulti_error,ErrorCode.betcode_lotmulti_error.memo);
		}else if(permission>99) {
			throw new LotteryException(ErrorCode.betcode_lotmulti_error,ErrorCode.betcode_lotmulti_error.memo);
		}
		List<SplitedLot> betcodeList = new ArrayList<SplitedLot>();
		int lotmultiPermission = lotmulti/permission;
		int lotmultiLeft = lotmulti%permission;
		for(int i = 0;i < lotmultiPermission;i++) {
			SplitedLot splitedLot = new SplitedLot(betcode, permission, 0,lotterytype);
			betcodeList.add(splitedLot);
		}
		if(lotmultiLeft>0) {
			betcodeList.add(new SplitedLot(betcode, lotmultiLeft, 0,lotterytype));
		}
		
		return betcodeList;
		
	}
	
	
	/**
	 * 判断该注码是否需要拆分,福彩，50倍,2万
	 * @param betcode
	 * @param lotmulti
	 * @param amt
	 * @return
	 */
	public static boolean isToBeSplitFC(int lotmulti,long amt) {
		if(lotmulti<=50&&amt<=2000000) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 判断该注码是否需要拆分,福彩，50倍,2万
	 * @param betcode
	 * @param lotmulti
	 * @param amt
	 * @return
	 */
	public static boolean isToBeSplit99(int lotmulti,long amt) {
		if(lotmulti<=99&&amt<=2000000) {
			return false;
		}
		return true;
	}
	
	
}
