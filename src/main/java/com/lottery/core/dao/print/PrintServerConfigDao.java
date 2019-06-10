package com.lottery.core.dao.print;

import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.print.PrintServerConfig;

import java.util.List;

/**
 * Created by fengqinyun on 16/11/9.
 */

public interface PrintServerConfigDao extends IGenericDAO<PrintServerConfig,String> {

    /**
     * 查询可以打票的机器
     * */
    public  List<PrintServerConfig> getEnablePrintServer();

	public int updateStatus(List<String> ids, Integer status);

	public int updateReport(List<String> ids, String beginDate, Integer reportType);

	public Long findCountByStatus(List<String> ids, Integer status);

	public int updateStatusByAreaId(String areaId, Integer type);

	public int updateServerBalance(String id, Double balance);

	public int lotteryTypes(String ids, String lotteryTypes);

	public int weight(String ids, Integer weight);

	public int minSecodes(String ids, Long minSecodes);

	public int updateIsBig(String ids, Integer isBig);

	public void updateControl(Integer maxAmount1, Long minSecodes1, Long maxSeconds1);
}
