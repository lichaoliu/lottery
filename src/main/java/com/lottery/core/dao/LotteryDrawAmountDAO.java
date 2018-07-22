package com.lottery.core.dao;

import com.lottery.common.AdminPage;
import com.lottery.common.PageBean;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.controller.admin.dto.CashRecord;
import com.lottery.core.domain.LotteryDrawAmount;

import java.util.Date;
import java.util.List;

public interface LotteryDrawAmountDAO extends
		IGenericDAO<LotteryDrawAmount, String> {
	
	/**
	 * 查询所有审核成功的提现记录
	 * @param drawType
	 * @return
	 */
	public List<LotteryDrawAmount> queryDrawAmountList(int drawType,String id);

	

	/**
	 * 修改提现状态
	 * @param  id 主键
	 * @param  status 状态
	 *
	 * @param  batchId 批次号
	 * */
	public void updateLotteryDrawStatus(String id,int status,String batchId);
	/**
	 * 更新状态
	 * @param id 主键
	 * @param  status 状态
	 */
	public void updateStatusById(String id,int status);
	/**
	 * 查看处理中的提现记录
	 * @param status
	 * @return
	 */
	public List<String> findLotteryDrawList(int status,int drawType,int operateType);
	/**
	 * 查看批量提现数量
	 * @param status
	 * @param drawType
	 * @param batchId
	 * @return
	 */
	public Integer findLotteryDrawCount(int status,int drawType,String batchId);
	/**
	 * 根据批次id更新状态
	 * @param batchId
	 * @param status
	 */
	public void  updateStatusByBatchId(String batchId,int status);
	
	
	public void findExcelRecord(Integer status, String batchId, AdminPage<CashRecord> page);
	
	/**
	 * 提现记录查询
	 * @param userno 用户名
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 分页
	 * **/
	public List<LotteryDrawAmount> getDrawAmountList(String userno,Date startTime,Date endTime,PageBean<LotteryDrawAmount> page);

	/**
	 * 根据批次id查询提现记录
	 * */
	public List<LotteryDrawAmount> getByBatchId(String batchId);
	/**
	 * 根据提现类型，状态，批次号查询
	 * @param  drawType 提现类型
	 * @param  status 状态
	 * @param  batchId 批次编号
	 * */
	List<LotteryDrawAmount> queryDrawAmountList(int drawType,int status,String batchId);
	/**
	 * 根据状态，提现类型查询
	 * @param max 最大条数
	 * @param drawType 提现类型
	 * @param status 状态
	 *
	 * */

	public List<LotteryDrawAmount> getByStatusAndDrawType(int max,int status,int drawType);

}
