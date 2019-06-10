package com.lottery.core.service;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryAgencyDao;
import com.lottery.core.dao.LotteryAgencyPointLocationDao;
import com.lottery.core.domain.agency.LotteryAgency;
import com.lottery.core.domain.agency.LotteryAgencyPointLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LotteryAgencyService {

	@Autowired
	private LotteryAgencyDao agencyDao;
	@Autowired
	private LotteryAgencyPointLocationDao agencyPointLocationDao;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	
	private BigDecimal level1defaultPoint = new BigDecimal(2);
	private BigDecimal level2defaultPoint = BigDecimal.ZERO;
	private BigDecimal level3defaultPoint = BigDecimal.ZERO;
	/**
	 * 创建渠道
	 * @param agencyNo
	 * @param parentAgency
	 * @param agencyType
	 * @return
	 */
	@Transactional
	public LotteryAgency createAgency(String agencyNo,String agencyName, String parentAgency,
			Integer agencyType) {
		LotteryAgency lotteryAgency = agencyDao.find(agencyNo);
		if(lotteryAgency != null){
			throw new LotteryException(ErrorCode.agency_data_hasexists, "代理已存在");
		}
		
		Integer level = 1;
        if (!"0".equals(parentAgency)){//如果不是根渠道0
			LotteryAgency parentLotteryAgency = agencyDao.find(parentAgency);
			if(parentLotteryAgency == null){
				throw new LotteryException(ErrorCode.agency_data_notexists, "上级代理用户不存在");
			}
			if(parentLotteryAgency.getLeaf() == 1){
				parentLotteryAgency.setLeaf(0);
				agencyDao.merge(parentLotteryAgency);
			}
			level = parentLotteryAgency.getLevel() + 1;
		}
		
		LotteryAgency newAgency = new LotteryAgency();
		newAgency.setAgencyNo(agencyNo);
		newAgency.setParentAgency(parentAgency);
		newAgency.setAgencyType(agencyType);
		newAgency.setStatus(1);
		newAgency.setLevel(level);
		newAgency.setCreateTime(new Date());
		newAgency.setLeaf(1);
		newAgency.setAgencyName(agencyName);
		agencyDao.insert(newAgency);
		
		if(level == 1){
			createAgencyPoint(agencyNo, LotteryType.ALL.getValue(), level1defaultPoint);
		}else if(level == 2){
			createAgencyPoint(agencyNo, LotteryType.ALL.getValue(), level2defaultPoint);
		}else if(level == 2){
			createAgencyPoint(agencyNo, LotteryType.ALL.getValue(), level3defaultPoint);
		}
		
		return newAgency;
	}


	/**
	 * 删除渠道
	 * @param agencyNo
	 */
	@Transactional
	public void deleteAgency(String agencyNo) {
		LotteryAgency lotteryAgency = agencyDao.find(agencyNo);
		if(lotteryAgency == null){
			throw new LotteryException(ErrorCode.agency_data_notexists, "代理用户不存在");
		}
		
		String parentAgencyNo = lotteryAgency.getParentAgency();
		if(!"0".equals(parentAgencyNo)){
			List<LotteryAgency> agencys = agencyDao.findAgencyByParent(parentAgencyNo);
			//判断修改叶子节点
			if(agencys.size() == 1){
				LotteryAgency parentLotteryAgency = agencyDao.find(parentAgencyNo);
				if(parentLotteryAgency == null){
					throw new LotteryException(ErrorCode.agency_data_notexists, "上级代理用户不存在");
				}
				parentLotteryAgency.setLeaf(1);
				agencyDao.merge(parentLotteryAgency);
			}
		}
		//删除所有彩种点位
		List<LotteryAgencyPointLocation> points = agencyPointLocationDao.findAgencyPointsByAgencyNo(agencyNo);
		for (LotteryAgencyPointLocation point : points) {
			agencyPointLocationDao.remove(point);
		}
		agencyDao.remove(lotteryAgency);
	}
	
	/**
	 * 创建点位
	 * @param agencyNo
	 * @param lotteryType
	 * @param pointLocation
	 * @return
	 */
	@Transactional
	public LotteryAgencyPointLocation createAgencyPoint(String agencyNo, Integer lotteryType, BigDecimal pointLocation) {
		LotteryAgency agency = agencyDao.find(agencyNo);
		if(agency == null){
			throw new LotteryException(ErrorCode.agency_data_notexists, "代理用户不存在");
		}
		
		LotteryAgencyPointLocation point = null;
		try {
			point = agencyPointLocationDao.findByAgencyNoAndLotteryType(agencyNo, lotteryType);
		} catch (Exception e) {
		}
		
		if(point != null){
			throw new LotteryException(ErrorCode.agency_data_hasexists, "点位已存在");
		}
		
		String parentAgencyno = agency.getParentAgency();
		BigDecimal parentPointLocation = BigDecimal.ZERO;
		if(agency.getLevel() != 1){
			if(StringUtil.isEmpty(parentAgencyno)){
				throw new LotteryException(ErrorCode.agency_data_notexists, "上级代理用户不存在");
			}
			LotteryAgencyPointLocation parentPoint = null;
			try {
				parentPoint = agencyPointLocationDao.findByAgencyNoAndLotteryType(parentAgencyno, lotteryType);
			} catch (Exception e) {
				
			}
			if (parentPoint == null) {
				throw new LotteryException(ErrorCode.agency_data_notexists, "上级代理用户点位不存在");
			}
			parentPointLocation = parentPoint.getPointLocation();
			if(pointLocation.compareTo(parentPointLocation) > 0){
				throw new LotteryException(ErrorCode.agency_modify_percent_not_parent, "下级代理比例不能超过上级代理比例");
			}
		}
			
		LotteryAgencyPointLocation newpoint = new LotteryAgencyPointLocation();
		
		newpoint.setId(idGeneratorDao.getLotteryAgencyPointLocation());
		newpoint.setLotteryType(lotteryType);
		newpoint.setAgencyNo(agencyNo);
		newpoint.setPointLocation(pointLocation);
		
		newpoint.setParentAgency(parentAgencyno);
		newpoint.setParentPointLocation(parentPointLocation);
		newpoint.setStatus(1);
		newpoint.setAgencyPointLocation(BigDecimal.ZERO);
		agencyPointLocationDao.insert(newpoint);
		return newpoint;
	}
	
	/**
	 * 修改点位
	 * @param id
	 * @param parentAgencyno
	 * @param pointLocation
	 * @return
	 */
	@Transactional
	public LotteryAgencyPointLocation updateAgencyPoint(Long id, String parentAgencyno, BigDecimal pointLocation) {
		LotteryAgencyPointLocation point = agencyPointLocationDao.find(id);
		if(point == null){
			throw new LotteryException(ErrorCode.agency_data_notexists, "代理用户点位不存在");
		}
		String parentAgency = point.getParentAgency();
		if(StringUtil.isNotEmpt(parentAgency) && !"0".equals(parentAgency)){
			if(!parentAgency.equals(parentAgencyno)){
				throw new LotteryException(ErrorCode.agency_modify_percent_not_parent,"只有上级代理可以修改下级代理比例");
			}
			
			LotteryAgencyPointLocation parentPoint = null;
			try {
				parentPoint = agencyPointLocationDao.findByAgencyNoAndLotteryType(parentAgency, point.getLotteryType());
			} catch (Exception e) {
			}
			
			if(parentPoint == null){
				throw new LotteryException(ErrorCode.agency_data_notexists, "上级代理用户点位不存在");
			}
			
			if(pointLocation.compareTo(parentPoint.getPointLocation()) > 0){
				throw new LotteryException(ErrorCode.agency_child_not_bigger_parent, "下级代理比例不能超过上级代理比例");
			}
		}
		point.setPointLocation(pointLocation);
		agencyPointLocationDao.merge(point);
		return point;
	}
	
	@Transactional
	public void deleteAgencyPoint(String ids) {
		String[] idss = ids.split(",");
		for (String id : idss) {
			LotteryAgencyPointLocation point = agencyPointLocationDao.find(Long.parseLong(id));
			if(point != null){
				agencyPointLocationDao.remove(point);
			}
		}
	}
	@Transactional
	public List<LotteryAgency> getAll(){
		return agencyDao.findAll();
	}


}
