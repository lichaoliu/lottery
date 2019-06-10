package com.lottery.common.contains.lottery.caselot;


/**
 * 合买置顶状态
 */
public enum CaseLotSortState {

	// sortState(4以下包括4，属于普通合买，4以上属于置顶合买)
	normal(0, "普通合买"), 
	applyTopSaloon(3, "申请置顶合买大厅"), 
	applyTopCenter(4, "申请置顶合买中心"), 
	topSaloon(8, "置顶合买大厅"), 
	topCenter(9, "置顶合买中心");

	public int value;
	public String memo;
	private CaseLotSortState(int value, String memo) {
		this.value = value;
		this.memo = memo;
	}
	


}
