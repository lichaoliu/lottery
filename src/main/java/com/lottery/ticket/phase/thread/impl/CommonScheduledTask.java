/**
 * 
 */
package com.lottery.ticket.phase.thread.impl;

import com.lottery.common.util.CoreDateUtils;

import java.util.Calendar;

/**
 * 常规彩种事件任务
 *
 *
 */
public class CommonScheduledTask extends AbstractScheduleTask {

    private static final long serialVersionUID = -8603842944577855499L;




	@Override
	public String getTaskInfo() {
		StringBuffer sb = new StringBuffer();
		
		if (this.getLotteryType() == null) {
			sb.append("[未指定彩票类型]");
		} else {
			sb.append("[");
			sb.append(this.getLotteryType().getName());
			sb.append("]");
		}
		
		sb.append("第[").append(this.getCurrentPhaseNo()).append("]期,时间:[");
		
		if (this.getTaskTime() == null) {
			sb.append("null]");
		} else {
			sb.append(CoreDateUtils.formatDateTime(this.getTaskTime()));
            sb.append("],执行时间:[");
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(getTaskTime());
            if (getOffsetTime()>0){
                calendar.add(Calendar.MILLISECOND, -1*(int)getOffsetTime());
            }
            sb.append(CoreDateUtils.formatDateTime(calendar.getTime())).append("]");
		}
		
		sb.append(",执行事件类型[");


		if (this.getEventType() == null) {
			sb.append("null");
		} else {
			sb.append(this.getEventType().getName());
		}
		sb.append("]");
		return sb.toString();
	}

}
