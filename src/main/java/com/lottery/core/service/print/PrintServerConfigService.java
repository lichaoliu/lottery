package com.lottery.core.service.print;

import com.lottery.common.contains.PrintServerConfigStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.dao.print.PrintServerConfigDao;
import com.lottery.core.dao.print.PrintTicketDao;
import com.lottery.core.domain.print.PrintServerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by fengqinyun on 16/11/10.
 */
@Service
public class PrintServerConfigService {
    @Autowired
    private PrintServerConfigDao dao;
    @Autowired
    private PrintTicketDao printTicketDao;
    
    /**
     * 查询可以打票的机器
     * */
     @Transactional
    public List<PrintServerConfig> getEnablePrintServer(){
        return dao.getEnablePrintServer();
    }


    public void sort(List<PrintServerConfig> printServerConfigList){
        Collections.sort(printServerConfigList, new Comparator<PrintServerConfig>() {

            @Override
            public int compare(PrintServerConfig o1, PrintServerConfig o2) {
                // 权重一样,按金额排序
                if(o1.getWeight().intValue()==o2.getWeight().intValue()){
                    return o2.getBalance().compareTo(o1.getBalance());
                }

                // 否则按照权重升序
                return o2.getWeight().compareTo(o1.getWeight());
            }
        });
    }

    @Transactional
	public int updateServerStatus(List<String> ids, Integer status) {
    	Long i = dao.findCountByStatus(ids, PrintServerConfigStatus.stop.value);
    	if(i != ids.size()){
    		//必须全停止的 才能修改
    		return -1;
    	}
		return dao.updateStatus(ids, status);
	}

    @Transactional
	public int updateReport(List<String> ids, String beginDate, Integer reportType) {
    	Long i = dao.findCountByStatus(ids, PrintServerConfigStatus.stop.value);
    	if(i != ids.size()){
    		//必须全停止的 才能修改
    		return -1;
    	}
		return dao.updateReport(ids, beginDate, reportType);
	}

    @Transactional
	public int updateServerStop(List<String> ids) {
    	Long i = dao.findCountByStatus(ids, PrintServerConfigStatus.print.value);
		if(i > 0){
			printTicketDao.updateStatusByServerIdsAndStatus(ids, TicketStatus.UNINIT.value, TicketStatus.UNSENT.value);
		}
		return dao.updateStatus(ids, PrintServerConfigStatus.stop.value);
	}

    @Transactional
	public int batchprint(String province, Integer type) {
    	return dao.updateStatusByAreaId(province, type);
	}

    @Transactional
	public int updateServerBalance(String id, Double balance) {
    	return dao.updateServerBalance(id, balance);
	}

    @Transactional
	public void save(String id, String shortId, String areaName, String areaId, String machineType, Integer weight, String lotteryTypes,
			Integer isBigMoney, Integer maxAmount, Integer minAmount, Long minSecodes, Long maxSeconds, Integer isDel) {
		PrintServerConfig p = new PrintServerConfig();
		p.setId(id);
		p.setShortId(shortId);
		p.setAreaName(areaName);
		p.setAreaId(areaId);
		p.setMachineType(machineType);
		p.setWeight(weight);
		p.setLotteryTypes(lotteryTypes);
		p.setIsBigMoney(isBigMoney);
		p.setMaxAmount(maxAmount);
		p.setMinAmount(minAmount);
		p.setMinSecodes(minSecodes);
		p.setMaxSeconds(maxSeconds);
		p.setIsDel(isDel);
		p.setIsOnline(YesNoStatus.no.value);
		p.setStatus(PrintServerConfigStatus.stop.value);
		p.setCreatTime(new Date());
		dao.insert(p);
	}

    @Transactional
	public void update(String id, String shortId, String areaName, String areaId, String machineType, Integer weight,
			String lotteryTypes, Integer isBigMoney, Integer maxAmount, Integer minAmount, Long minSecodes,
			Long maxSeconds, Integer isDel) {
    	PrintServerConfig p = dao.find(id);
    	p.setShortId(shortId);
		p.setAreaName(areaName);
		p.setAreaId(areaId);
		p.setMachineType(machineType);
		p.setWeight(weight);
		p.setLotteryTypes(lotteryTypes);
		p.setIsBigMoney(isBigMoney);
		p.setMaxAmount(maxAmount);
		p.setMinAmount(minAmount);
		p.setMinSecodes(minSecodes);
		p.setMaxSeconds(maxSeconds);
		p.setIsDel(isDel);
    	dao.merge(p);
	}

    @Transactional
	public int updateIsBig(String ids, Integer isBig) {
		return dao.updateIsBig(ids, isBig);
	}

    @Transactional
	public int minSecodes(String ids, Long minSecodes) {
    	return dao.minSecodes(ids, minSecodes);
	}

    @Transactional
	public int weight(String ids, Integer weight) {
    	return dao.weight(ids, weight);
	}

    @Transactional
	public int lotteryTypes(String ids, String lotteryTypes) {
    	return dao.lotteryTypes(ids, lotteryTypes);
	}


    @Transactional
	public void updateControl(String shortId, String areaName, String areaId, String machineType,
			Integer weight, String lotteryTypes, Integer isBigMoney, Integer maxAmount, Integer minAmount,
			Long minSecodes, Long maxSeconds, Integer isDel) {
    	PrintServerConfig server = dao.find("0");
    	Integer maxAmount1 = null;
    	if(server.getMaxAmount() != maxAmount){
    		maxAmount1 = maxAmount;
    	}
    	Long minSecodes1 = null;
    	if(server.getMinSecodes() != minSecodes){
    		minSecodes1 = minSecodes;
    	}
    	Long maxSeconds1 = null;
    	if(server.getMaxSeconds() != maxSeconds){
    		maxSeconds1 = maxSeconds;
    	}
		dao.updateControl(maxAmount1, minSecodes1, maxSeconds1);
		this.update("0", shortId, areaName, areaId, machineType, weight, lotteryTypes, isBigMoney, maxAmount, minAmount, minSecodes, maxSeconds, isDel);
	}
}
