/**
 * 
 */
package com.lottery.core.terminal.impl;

import com.lottery.common.AmountRegion;
import com.lottery.common.ListSerializable;
import com.lottery.common.TimeRegion;
import com.lottery.common.cache.IMemcachedObject;
import com.lottery.common.contains.EnabledStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.common.util.CoreTimeUtils;
import com.lottery.core.cache.MemcachedService;
import com.lottery.core.cache.TerminalFailureCache;
import com.lottery.core.cache.model.*;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.domain.terminal.LotteryTerminalConfig;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.TerminalConfigService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.core.terminal.ITerminalSelectorFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author fengqinyun
 * 
 */
public abstract class AbstractTerminalSelector implements ITerminalSelector {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * 终端缓存模型
	 */
	@Autowired
	private TerminalCacheModel terminalCacheModel;

	/**
	 * 终端配置缓存模型
	 */
	@Autowired
	private TerminalConfigCacheModel terminalConfigCacheModel;

	/**
	 * 终端配置缓存模型
	 */
	@Autowired
	private TerminalTypeTerminalCacheModel terminalByTypeCacheModel;

	/**
	 * 终端缓存模型
	 */
	@Autowired
	private TerminalPropertyCacheModel terminalPropertyCacheModel;

	/**
	 * 彩种对应的终端配置缓存模型
	 */
	@Autowired
	private LotteryTypeTerminalConfigIdCacheModel lotteryTypeTerminalConfigIdCacheModel;
	@Autowired
	protected  LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
	/**
	 * 玩法对应的终端配置缓存模型
	 */
	@Autowired
	private PlayTypeTerminalConfigIdCacheModel playTypeTerminalConfigIdCacheModel;
	@Autowired
	private TerminalLotteryTypeConfigIdCacheModel terminalLotteryTypeConfigIdCacheModel;
	@Resource(name = "memcachedService")
	private MemcachedService memcachedService;
	@Autowired
	private TerminalConfigService terminalConfigService;
	@Autowired
	private LotteryTerminalConfigCacheModel lotteryTerminalConfigCacheModel;
	
	@Autowired
    protected LottypeConfigCacheModel lottypeConfigCacheModel;
	/**
	 * 默认缓存时间：一小时
	 */
	private static final int ALIVE_TIME = 3600;
	private static final long TIMEOUT = 10;
	private static final String PREFIX = "TERMINAL:FAILURECOUNT";
	private static final String DELIMITER = ":";

	// private static TimeRegion globalSendForbidTimeRegion =
	// TimeRegion.parse("6:00|7:00");

	@Override
	public boolean isGlobalSendPausedOrDuringSendForbidPeriod(LotteryType lotteryType) {
		if (this.isSendPausedOrDuringSendForbidPeriod(LotteryType.ALL)) {
			return true;
		}
		return this.isSendPausedOrDuringSendForbidPeriod(lotteryType);
	}

	protected boolean isSendPausedOrDuringSendForbidPeriod(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("未指定彩种");
			return false;
		}
		LotteryTerminalConfig lotteryTerminalConfig = this.getLotteryTerminalConfigFromCache(lotteryType);
		if (lotteryTerminalConfig == null) {
			//logger.error("未从缓存中取到彩种配置,不做判断, lotteryType={}", lotteryType);
			return false;
		}
		if (lotteryTerminalConfig.getIsPaused() != null && lotteryTerminalConfig.getIsPaused() == YesNoStatus.yes.getValue()) {
			// 状态已禁用
			return true;
		}
		// 状态已启用则继续向下判断
		// 如果设置了禁止送票时段，开始判断
		if (lotteryTerminalConfig.getSendForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(lotteryTerminalConfig.getSendForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取单一彩种配置信息
	 * 
	 * @param lotteryType
	 *            彩种
	 * @return 查找到的彩种配置缓存
	 */
	protected LotteryTerminalConfig getLotteryTerminalConfigFromCache(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("批量获取彩种配置内存级缓存信息参数不能为空!");
			return null;
		}

        //彩种单一配置暂时用不到
		LotteryTerminalConfig lotteryTerminalConfig=null;
		try {
			lotteryTerminalConfig= lotteryTerminalConfigCacheModel.get(lotteryType);
		}catch (CacheNotFoundException e1){

		}catch (CacheUpdateException e2){

		}catch (Exception e) {
			logger.error("获取[{}]彩种配置信息过程出现异常!", lotteryType);
			logger.error(e.getMessage(), e);
		}

		if(lotteryTerminalConfig!=null&&lotteryTerminalConfig.getId()==null){
			return null;
		}

		return lotteryTerminalConfig;
	}

	/**
	 * 获取内存级彩种配置缓存
	 * 
	 * @param lotteryTypeList
	 *            要查询的彩种列表
	 * @return 查找到的彩种配置缓存
	 */
	protected Map<LotteryType, LotteryTerminalConfig> getLotteryTerminalConfigFromCache(List<LotteryType> lotteryTypeList) {

		if (lotteryTypeList == null || lotteryTypeList.isEmpty()) {
			logger.error("批量获取彩种配置内存级缓存信息参数不能为空!");
			return null;
		}

		try {
			return lotteryTerminalConfigCacheModel.mget(lotteryTypeList);
		}catch (CacheNotFoundException e1){

		}catch (CacheUpdateException e2){

		} catch (Exception e) {
			logger.error("获取[{}]彩种配置信息过程出现异常!", StringUtils.join(lotteryTypeList, ","));
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	public boolean isDuringGlobalSendForbidPeriod(LotteryType lotteryType) {

		// return
		// CoreTimeUtils.isCurrentDuringPeriod(globalSendForbidTimeRegion);
		return false;
	}

	@Override
	public boolean isDuringSendForbidPeriod(Long terminalId, LotteryType lotteryType) {
		if (terminalId == null) {
			logger.error("未指定终端号");
			return false;
		}
		// 先获取终端全局配置
		Terminal terminal = this.getTerminalFromCache(terminalId);
		if (terminal == null) {
			logger.error("未从缓存中取到终端，不做判断, terminalId={}", terminalId);
			return false;
		}
		// 如果设置了禁止送票时段，开始判断
		if (terminal.getSendForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(terminal.getSendForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}

		// 获取针对此彩种的终端配置
		if (lotteryType == null) {
			logger.error("未指定彩种");
			return false;
		}
		TerminalConfig terminalConfig = this.getTerminalConfigFromCache(terminalId, lotteryType);
		if (terminalConfig == null) {
			logger.error("未从缓存中取到终端配置，不做判断, terminalId={}, lotteryType={}", terminalId, lotteryType);
			return false;
		}
		// 如果设置了禁止送票时段，开始判断
		if (terminalConfig.getSendForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(terminalConfig.getSendForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 过滤出启用的终端
	 * 
	 * @param allTerminalConfigList
	 *            待过滤的终端配置列表
	 * @return 配置状态启用且终端状态也启用的终端配置列表
	 */
	protected List<TerminalConfig> filterEnabledTerminalConfig(List<TerminalConfig> allTerminalConfigList) {
		if (allTerminalConfigList == null || allTerminalConfigList.isEmpty()) {
			logger.info("终端配置信息为空!");
			return null;
		}

		List<TerminalConfig> enabledTerminalConfigList = new ArrayList<TerminalConfig>();

		for (TerminalConfig terminalConfig : allTerminalConfigList) {
			if (terminalConfig.getIsEnabled() == EnabledStatus.enabled.getValue()) {
				enabledTerminalConfigList.add(terminalConfig);
			}
		}

		if (enabledTerminalConfigList.isEmpty()) {
			// 没有启用的终端配置
			return null;
		}

		// 获取查询到终端配置所对应终端对象
		List<Long> terminalIdList = new ArrayList<Long>();

		for (TerminalConfig terminalConfig : enabledTerminalConfigList) {
			terminalIdList.add(terminalConfig.getTerminalId());
		}

		Map<Long, Terminal> terminalMap = getTerminalFromCache(terminalIdList);
		if (terminalMap == null || terminalMap.isEmpty()) {
			logger.error("终端信息为空");
			return null;
		}

		List<TerminalConfig> returnTerminalConfigList = new ArrayList<TerminalConfig>();
		for (TerminalConfig terminalConfig : enabledTerminalConfigList) {
			Long terminalId = terminalConfig.getTerminalId();
			Terminal terminal = terminalMap.get(terminalId);
			if (terminal == null) {
				logger.error("指定编号的终端不存在, terminalId={}", terminalId);
				continue;
			}
			if (terminal.getIsEnabled() == EnabledStatus.enabled.getValue()) {
				returnTerminalConfigList.add(terminalConfig);
			}
		}

		return returnTerminalConfigList;
	}

	/**
	 * 查询某一彩种可用终端配置信息
	 * 
	 * @param lotteryType
	 *            彩种
	 * @return 指定彩种启用状态的终端配置
	 */
	protected List<TerminalConfig> getEnabledTerminalConfig(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("获取彩种启用中的终端配置信息参数为空!");
			return null;
		}

		// 获取彩种对应的终端配置缓存
		List<TerminalConfig> allTerminalConfigList = this.getTerminalConfigFromCache(lotteryType);

		return this.filterEnabledTerminalConfig(allTerminalConfigList);
	}

	/**
	 * 过滤出暂停的终端
	 * 
	 * @param allTerminalConfigList
	 *            待过滤的终端配置列表
	 * @return 配置状态为暂停或者终端状态为暂停的终端配置列表
	 */
	protected List<TerminalConfig> filterPausedTerminalConfig(List<TerminalConfig> allTerminalConfigList) {
		if (allTerminalConfigList == null || allTerminalConfigList.isEmpty()) {
			logger.info("终端配置信息为空!");
			return null;
		}

		List<TerminalConfig> returnTerminalConfigList = new ArrayList<TerminalConfig>();

		List<TerminalConfig> unpausedTerminalConfigList = new ArrayList<TerminalConfig>();

		// 保存已暂停的配置，且记录未暂停的用来查询终端是否已暂停
		for (TerminalConfig terminalConfig : allTerminalConfigList) {
			if (terminalConfig.getIsPaused() == YesNoStatus.yes.getValue()) {
				returnTerminalConfigList.add(terminalConfig);
			} else {
				unpausedTerminalConfigList.add(terminalConfig);
			}
		}

		if (unpausedTerminalConfigList.isEmpty()) {
			// 终端配置全都暂停了，就不用再次查询
			return returnTerminalConfigList;
		}

		// 获取查询到终端配置所对应终端对象
		List<Long> terminalIdList = new ArrayList<Long>();

		for (TerminalConfig terminalConfig : unpausedTerminalConfigList) {
			terminalIdList.add(terminalConfig.getTerminalId());
		}

		Map<Long, Terminal> terminalMap = getTerminalFromCache(terminalIdList);
		if (terminalMap == null || terminalMap.isEmpty()) {
			logger.error("终端信息为空");
			return returnTerminalConfigList;
		}

		for (TerminalConfig terminalConfig : unpausedTerminalConfigList) {
			Long terminalId = terminalConfig.getTerminalId();
			Terminal terminal = terminalMap.get(terminalId);
			if (terminal == null) {
				logger.error("指定编号的终端不存在, terminalId={}", terminalId);
				continue;
			}
			if (terminal.getIsPaused() == YesNoStatus.yes.getValue()) {
				returnTerminalConfigList.add(terminalConfig);
			}
		}

		return returnTerminalConfigList;
	}


	@Override
	public List<Long> getEnabledTerminalIdList(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("获取彩种启用中, 可用送票的终端配置信息参数为空!");
			return null;
		}

		Set<Long> terminalIdSet = new HashSet<Long>();

		// 首先取彩种配置下的所有的终端配置
		List<TerminalConfig> lotteryTypeTerminalConfigList = this.getTerminalConfigFromCache(lotteryType);




		if (lotteryTypeTerminalConfigList != null) {
			for (TerminalConfig terminalConfig : lotteryTypeTerminalConfigList) {

                 if (terminalConfig.getIsPaused()==YesNoStatus.no.value){
					 terminalIdSet.add(terminalConfig.getTerminalId());
				 }

			}
		}

		List<TerminalConfig> playTypeTerminalConfigList = new ArrayList<TerminalConfig>();
		try {
			List<TerminalConfig> allPlayTypeTerminalConfigList = this.getTerminalConfigFromCache(PlayType.ALL);
			if (allPlayTypeTerminalConfigList != null) {
				for (TerminalConfig terminalConfig : allPlayTypeTerminalConfigList) {
					if (terminalConfig.getLotteryType() == lotteryType.getValue()) {
						playTypeTerminalConfigList.add(terminalConfig);
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取玩法的终端配置出错",e);
		}

		if(playTypeTerminalConfigList!=null&&!playTypeTerminalConfigList.isEmpty()){
			for (TerminalConfig terminalConfig:playTypeTerminalConfigList){

				if (terminalConfig.getIsPaused()==YesNoStatus.no.value){
					terminalIdSet.add(terminalConfig.getTerminalId());
				}
			}
		}

		Set<Long> returnIdSet = new HashSet<Long>();

		for(Long id:terminalIdSet){
			try {
				Terminal terminal =terminalCacheModel.get(id);
				if (terminal == null) {
					logger.error("指定编号的终端不存在, terminalId={}", id);
					continue;
				}
				if (terminal.getIsPaused() == YesNoStatus.no.getValue()) {
					returnIdSet.add(id);
				}
			}catch (Exception e) {

			}
		}




		return new ArrayList<Long>(returnIdSet);
	}


	@Override
	public List<Long> getPausedTerminalIdList(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("获取彩种启用中, 暂停送票的终端配置信息参数为空!");
			return null;
		}

		Set<Long> terminalIdSet = new HashSet<Long>();

		// 首先取彩种配置下的暂停的终端配置
		List<TerminalConfig> lotteryTypeTerminalConfigList = this.filterPausedTerminalConfig(this.getTerminalConfigFromCache(lotteryType));
		if (lotteryTypeTerminalConfigList != null) {
			for (TerminalConfig terminalConfig : lotteryTypeTerminalConfigList) {
				terminalIdSet.add(terminalConfig.getTerminalId());
			}
		}
		
	    List<TerminalConfig> playTypeTerminalConfigList = new ArrayList<TerminalConfig>();
        try {
            List<TerminalConfig> allPlayTypeTerminalConfigList = this.getTerminalConfigFromCache(PlayType.ALL);
            if (allPlayTypeTerminalConfigList != null) {
                for (TerminalConfig terminalConfig : allPlayTypeTerminalConfigList) {
                     if (terminalConfig.getLotteryType() == lotteryType.getValue()) {
                         playTypeTerminalConfigList.add(terminalConfig);
                     }
                }
            }
        } catch (Exception e) {
            logger.error("读取玩法的终端配置出错",e);
        }
        
        if(playTypeTerminalConfigList!=null&&!playTypeTerminalConfigList.isEmpty()){
        	  playTypeTerminalConfigList = this.filterPausedTerminalConfig(playTypeTerminalConfigList);
              if (playTypeTerminalConfigList != null) {
                  for (TerminalConfig terminalConfig : playTypeTerminalConfigList) {
                      terminalIdSet.add(terminalConfig.getTerminalId());
                  }
              }
        }
      

		return new ArrayList<Long>(terminalIdSet);
	}

	/**
	 * 获取终端配置内存级缓存
	 * 
	 * @param lotteryType
	 *            彩种编号
	 * @return 指定彩种对应的终端配置集合列表
	 */
	protected List<TerminalConfig> getTerminalConfigFromCache(LotteryType lotteryType) {

		if (lotteryType == null) {
			logger.error("获取彩种对应的终端配置内存级缓存信息参数不能为null!");
			return null;
		}

		Long[] terminalConfigIdList = null;
		try {
			terminalConfigIdList = lotteryTypeTerminalConfigIdCacheModel.get(lotteryType);
		} catch (Exception e) {
			logger.error("从缓存中获取终端配置ID列表出错, lotteryType={}", lotteryType);
			logger.error(e.getMessage(), e);
		}

		if (terminalConfigIdList == null || terminalConfigIdList.length == 0) {
			logger.error("未获取到lotteryType={}对应的终端配置ID列表", lotteryType);
			return null;
		}

		try {
			Map<Long, TerminalConfig> result = terminalConfigCacheModel.mget(Arrays.asList(terminalConfigIdList));
			if (result != null && !result.isEmpty()) {
				return new ArrayList<TerminalConfig>(result.values());
			}
		} catch (Exception e) {
			logger.error("从缓存中批量获取终端配置出错, terminalConfigIdList={}", StringUtils.join(terminalConfigIdList, ","));
		}

		return null;
	}

	/**
	 * 获取指定终端的某彩种下的终端配置
	 * 
	 * @param terminalId
	 *            终端号
	 * @param lotteryType
	 *            彩种编号
	 * @return 对应的终端配置
	 */
	protected TerminalConfig getTerminalConfigFromCache(Long terminalId, LotteryType lotteryType) {
		// 获取彩种对应的终端配置缓存
		List<TerminalConfig> terminalConfigList = this.getTerminalConfigFromCache(lotteryType);

		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			logger.error("获取[{}]相关终端配置信息为空!", lotteryType.getName());
			return null;
		}

		for (TerminalConfig terminalConfig : terminalConfigList) {
			if (terminalConfig.getTerminalId() != null && terminalConfig.getTerminalId().longValue() == terminalId.longValue()) {
				return terminalConfig;
			}
		}

		return null;
	}

	/**
	 * 获取指定终端的某彩种下某玩法的终端配置
	 * 
	 * @param terminalId
	 *            终端号
	 * @param lotteryType
	 *            彩种编号
	 *@param playType 玩法            
	 * @return 对应的终端配置
	 */
	protected TerminalConfig getTerminalConfigFromCache(Long terminalId, LotteryType lotteryType,PlayType playType) {
		// 获取彩种对应的终端配置缓存
		List<TerminalConfig> terminalConfigList = this.getTerminalConfigFromCache(lotteryType);

		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			logger.error("获取[{}]相关终端配置信息为空!", lotteryType.getName());
			return null;
		}

		for (TerminalConfig terminalConfig : terminalConfigList) {
			if (terminalConfig.getTerminalId() != null && terminalConfig.getTerminalId().longValue() == terminalId.longValue()&&playType!=null&&terminalConfig.getPlayType()==playType.value) {
				return terminalConfig;
			}
		}

		return null;
	}

	
	
	/**
	 * 获取单一终端信息
	 * 
	 * @param terminalId
	 *            终端号
	 * @return 终端对象
	 */
	protected Terminal getTerminalFromCache(Long terminalId) {
		List<Long> terminalIdList = new ArrayList<Long>();
		terminalIdList.add(terminalId);

		Map<Long, Terminal> terminalMap = this.getTerminalFromCache(terminalIdList);

		if (terminalMap == null || terminalMap.isEmpty()) {
			logger.error("未找到对应的终端, terminalId={}", terminalId);
			return null;
		}

		return terminalMap.get(terminalId);
	}

	/**
	 * 获取内存级终端缓存
	 * 
	 * @param terminalIdList
	 *            要查询的终端id列表
	 * @return 查找到的终端缓存
	 */
	protected Map<Long, Terminal> getTerminalFromCache(List<Long> terminalIdList) {

		if (terminalIdList == null || terminalIdList.isEmpty()) {
			logger.error("批量获取终端内存级缓存信息参数不能为空!");
			return null;
		}

		try {
			return terminalCacheModel.mget(terminalIdList);
		} catch (Exception e) {
			logger.error("获取[id={}]终端信息过程出现异常!", StringUtils.join(terminalIdList, ","));
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	public List<Terminal> getTerminalList(TerminalType terminalType) throws Exception {

		if (terminalType == null) {
			logger.error("获取彩种对应的终端内存级缓存信息参数不能为空");
			return null;
		}

		Long[] terminalIdList = null;
		try {
			terminalIdList = terminalByTypeCacheModel.get(terminalType);
		} catch (Exception e) {
			logger.error("从缓存中获取终端ID列表出错, terminalType={}", terminalType.getValue());
			logger.error(e.getMessage(), e);
			return null;
		}

		if (terminalIdList == null || terminalIdList.length == 0) {
			logger.info("未获取到{}对应的终端ID列表", terminalType);
			return null;
		}

		try {
			Map<Long, Terminal> result = terminalCacheModel.mget(Arrays.asList(terminalIdList));
			if (result != null && !result.isEmpty()) {
				return new ArrayList<Terminal>(result.values());
			}
		} catch (Exception e) {
			logger.error("从缓存中批量获取终端出错, terminalIdList={}", StringUtils.join(terminalIdList, ","));
		}
		return null;
	}

	@Override
	public List<TerminalProperty> getTerminalPropertyFromCache(Long terminalId) {

		List<TerminalProperty> terminalPropertyList = null;
		try {
			ListSerializable<TerminalProperty> listSerializable = terminalPropertyCacheModel.get(terminalId);
			if (listSerializable != null) {
				terminalPropertyList = listSerializable.getList();
			}
		} catch (Exception e) {
			logger.error("获取[id={}]终端配置属性信息过程出现异常!", terminalId);
			logger.error(e.getMessage(), e);
		}

		if (terminalPropertyList == null || terminalPropertyList.isEmpty()) {
			logger.error("未找到对应的终端属性, terminalId={}", terminalId);
			return null;
		}

		return terminalPropertyList;
	}

	/**
	 * 根据玩法获取终端配置
	 * 
	 * @param playType
	 *            玩法
	 * @return 指定玩法对应的终端配置
	 */
	protected List<TerminalConfig> getTerminalConfigFromCache(PlayType playType) throws Exception {
		if (playType == null) {
			logger.error("获取彩种对应的终端配置内存级缓存信息参数不能为空");
			return null;
		}

		Long[] terminalConfigIdList = null;
		try {
			terminalConfigIdList = playTypeTerminalConfigIdCacheModel.get(playType);
		} catch (Exception e) {
			logger.error("从缓存中获取终端配置ID列表出错, playType={}", playType);
			logger.error(e.getMessage(), e);
			throw e;
		}

		if (terminalConfigIdList == null || terminalConfigIdList.length == 0) {
			logger.info("未获取到playtype={}对应的终端配置ID列表", playType);
			return null;
		}

		try {
			Map<Long, TerminalConfig> result = this.terminalConfigCacheModel.mget(Arrays.asList(terminalConfigIdList));
			if (result != null && !result.isEmpty()) {
				return new ArrayList<TerminalConfig>(result.values());
			}
		} catch (Exception e) {
			logger.error("从缓存中批量获取终端配置出错, terminalConfigIdList={}", StringUtils.join(terminalConfigIdList, ","));
		}

		return null;
	}

	@Override
	public boolean hasSpecifyTerminalConfigForPlayType(PlayType playType) throws Exception {
		// 以ALL作为一个特殊的Key存储了所有针对玩法进行配置的终端规则
		List<TerminalConfig> allTerminalConfigList = this.getTerminalConfigFromCache(playType);

		if (allTerminalConfigList == null || allTerminalConfigList.isEmpty()) {
			// 没有终端配置，说明未给该玩法指定独立分配终端
			return false;
		}

		// 查找是否有针对此玩法进行的配置规则且处于启用状态
		List<TerminalConfig> enabledTerminalConfigList = new ArrayList<TerminalConfig>();

		for (TerminalConfig terminalConfig : allTerminalConfigList) {
			if (terminalConfig.getIsEnabled() == EnabledStatus.enabled.getValue() && terminalConfig.getPlayType() == playType.getValue()) {
				enabledTerminalConfigList.add(terminalConfig);
			}
		}

		if (enabledTerminalConfigList.isEmpty()) {
			// 没有启用的终端配置，视为未指定
			return false;
		}

		// 获取查询到终端配置所对应终端对象
		List<Long> terminalIdList = new ArrayList<Long>();

		for (TerminalConfig config : enabledTerminalConfigList) {
			terminalIdList.add(config.getTerminalId());
		}

		Map<Long, Terminal> terminalMap = getTerminalFromCache(terminalIdList);
		for (Terminal terminal : terminalMap.values()) {
			if (terminal.getIsEnabled() == EnabledStatus.enabled.getValue()) {
				// 存在启用的终端配置
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取某彩种或者某玩法已配置的所有可用终端配置规则列表
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param playType
	 *            玩法
	 * @return 所有可用终端配置规则列表
	 * @throws Exception
	 *             区分获取终端异常出错和未配置终端的情况
	 */
	public List<TerminalConfig> getEnabledTerminalConfigList(LotteryType lotteryType, PlayType playType) throws Exception {
		if (playType != null && playType.getValue() != PlayType.mix.getValue()) {
			// 通过玩法获取终端配置
			return this.getEnabledTerminalConfig(playType);
		} else {
			return this.getEnabledTerminalConfig(lotteryType);
		}

	}

	/**
	 * 查询某一彩种可用终端配置信息
	 * 
	 * @param playType
	 *            玩法
	 * @return 指定玩法启用状态的终端配置
	 */
	protected List<TerminalConfig> getEnabledTerminalConfig(PlayType playType) throws Exception {
		if (playType == null) {
			logger.error("获取玩法启用中的终端配置信息参数为空!");
			return null;
		}

		// 获取彩种对应的终端配置缓存
		List<TerminalConfig> allTerminalConfigList = this.getTerminalConfigFromCache(playType);

		return this.filterEnabledTerminalConfig(allTerminalConfigList);
	}

	@Override
	public List<Long> getEnabledTerminalIdList(LotteryType lotteryType, PlayType playType) throws Exception {
		List<TerminalConfig> terminalConfigList = this.getEnabledTerminalConfigList(lotteryType, playType);
		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			return null;
		}
		List<Long> terminalIdList = new ArrayList<Long>();
		for (TerminalConfig terminalConfig : terminalConfigList) {
			terminalIdList.add(terminalConfig.getTerminalId());
		}
		return terminalIdList;
	}

	@Override
	public List<Long> getAvailableTerminalIdList(LotteryType lotteryType, PlayType playType, Date deadline) throws Exception {
		if (deadline == null) {
			logger.error("未指定截止期，无法进行判断终端是否过期");
			throw new RuntimeException("未指定截止期，无法进行判断终端是否过期");
		}

		List<TerminalConfig> terminalConfigList = this.getEnabledTerminalConfigList(lotteryType, playType);

		List<TerminalConfig> availableTerminalConfigList = this.filterAvailableTerminalConfig(terminalConfigList, deadline);

		List<Long> availableTerminalIdList = new ArrayList<Long>();
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			availableTerminalIdList.add(terminalConfig.getTerminalId());
		}

		return availableTerminalIdList;
	}

	@Override
	public Long getTopPriorityTerminalId(LotteryType lotteryType, String phase, PlayType playType, Date deadline) {

		return this.getTopPriorityTerminalIdWithAmount(lotteryType, phase, playType, deadline, null,null);
	}

	@Override
	public Long getTopPriorityTerminalId(LotteryType lotteryType, String phase, PlayType playType, Date deadline, ITerminalSelectorFilter filter) {
		List<TerminalConfig> terminalConfigList;

		try {
			terminalConfigList = this.getEnabledTerminalConfigList(lotteryType, playType);
		} catch (Exception e) {
			logger.error("获取可用终端配置时出错, lotteryType={}, playType={}", lotteryType, playType);
			logger.error(e.getMessage(), e);
			return null;
		}

		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			logger.error("未找到可用终端, lotteryType={}, playType={}", lotteryType, playType);
			return null;
		}

		List<TerminalConfig> availableTerminalConfigList = this.filterAvailableTerminalConfig(terminalConfigList, deadline);
		return  this.getEnableTermianlId(availableTerminalConfigList,lotteryType,phase,playType,deadline,filter);
	}

	protected List<TerminalConfig> filterAvailableTerminalConfig(List<TerminalConfig> terminalConfigList, Date deadline) {
		List<TerminalConfig> availableTerminalConfigList = new ArrayList<TerminalConfig>();

		long currentTimeMillis = System.currentTimeMillis();

		// 排除所有已经过截止期的终端，得到一个可用终端ID的列表
		for (TerminalConfig terminalConfig : terminalConfigList) {
			long terminatedTime = deadline.getTime() - terminalConfig.getTerminateForward();
			if (terminatedTime > currentTimeMillis) {
				// 未过截止期，再排除所有在禁止分票期的终端
				// 当前时间在禁止分票时段内不允许分票，截止期在禁止分票时段内不允许分票
		
				// 如果设置了禁止分票时段，开始判断
				if (terminalConfig.getAllotForbidPeriod() != null) {
					TimeRegion timeRegion = TimeRegion.parse(terminalConfig.getAllotForbidPeriod());
					if (timeRegion != null) {
						if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
							continue;
						}
						
					}
				}

				// 先获取终端全局配置
				Terminal terminal = this.getTerminalFromCache(terminalConfig.getTerminalId());
				if (terminal != null && terminal.getAllotForbidPeriod() != null) {
					// 如果设置了禁止分票时段，开始判断
					TimeRegion timeRegion = TimeRegion.parse(terminal.getAllotForbidPeriod());
					if (timeRegion != null) {
						if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
							continue;
						}
					}
				}
				availableTerminalConfigList.add(terminalConfig);
			}
		}
		return availableTerminalConfigList;
	}

	/**
	 * 实现从所有可用终端里根据失败计数和权重等算法取出一个最高优先级的终端
	 * 
	 * @param failureCacheList
	 *            所有带失败计数的可用终端列表
	 * @param deadline
	 *            截止期
	 * @param lotteryType
	 *            彩种
	 * @return 终端号
	 */
	abstract protected Long chooseTopPriorityTerminalId(List<TerminalFailureCache> failureCacheList, Date deadline, LotteryType lotteryType, PlayType playType);

	/**
	 * 生成一个终端失败缓存对象
	 * 
	 * @param terminalId
	 *            终端号
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            彩期
	 * @param playType
	 *            玩法
	 * @return 初始化的终端失败缓存
	 */
	private TerminalFailureCache generateNewTerminalFailureCache(Long terminalId, LotteryType lotteryType, String phase, PlayType playType) {
		TerminalConfig config = terminalConfigService.get(lotteryType.getValue(), terminalId, playType == null ? PlayType.mix.getValue() : playType.getValue());
		if (config == null) {
			logger.error("数据库中不存在和此彩种({}, {})对应的终端, terminalId={}", new Object[] { lotteryType, playType, terminalId });
			return null;
		}
		if (config.getIsEnabled() != EnabledStatus.enabled.getValue()) {
			logger.error("此终端未启用, terminalId={}, lotteryType={}", terminalId, lotteryType.getValue());
			return null;
		}
		TerminalFailureCache cache = new TerminalFailureCache();
		cache.setFailureCount(0);
		cache.setLastFailedTime(0);
		cache.setTerminalId(terminalId);
		cache.setWeight(config.getWeight());
		return cache;
	}

	@Override
	public void increaseFailureCount(Long terminalId, LotteryType lotteryType, String phase, PlayType playType) {
		// 从MC中获取对象
		String key = generateMemcachedKey(terminalId, lotteryType, phase, playType);
		TerminalFailureCache cache = null;
		try {
			cache = (TerminalFailureCache) memcachedService.get(key);
		} catch (Exception e) {
			logger.error("从MC中读取终端失败计数缓存失败");
			cache = null;
		}
		// 建立缓存
		if (cache == null) {
			cache = generateNewTerminalFailureCache(terminalId, lotteryType, phase, playType);
			if (cache == null) {
				logger.error("创建终端送票失败缓存出错");
				return;
			}
		}
		cache.setFailureCount(cache.getFailureCount() + 1);
		cache.setLastFailedTime(new Date().getTime());
		// 更新缓存
		try {
			memcachedService.set(key, cache, ALIVE_TIME);
		} catch (Exception e) {
			logger.error("往缓存中设置终端信息出错", e);
		}
	}

	@Override
	public TerminalType getTerminalType(Long terminalId) {
		Terminal terminal = this.getTerminalFromCache(terminalId);

		if (terminal == null) {
			logger.error("未找到对应的终端, terminalId={}", terminalId);
			return null;
		}

		TerminalType type = TerminalType.get(Integer.valueOf(terminal.getTerminalType()));
		return type;
	}

	/**
	 * 生成MC查询用的key
	 * 
	 * @param terminalId
	 *            终端号
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            彩期
	 * @param playType
	 *            玩法
	 * @return MC key
	 */
	protected String generateMemcachedKey(Long terminalId, LotteryType lotteryType, String phase, PlayType playType) {
		StringBuffer sb = new StringBuffer(PREFIX);
		sb.append(DELIMITER).append(lotteryType.getValue());
		sb.append(DELIMITER).append(phase);
		sb.append(DELIMITER).append(terminalId.longValue());
		sb.append(DELIMITER);
		if (playType == null) {
			sb.append(PlayType.mix.getValue());
		} else {
			sb.append(playType.getValue());
		}
		return sb.toString();
	}

	@Override
	public boolean isCheckEnabled(Long terminalId) {
		if (terminalId == null) {
			logger.error("未指定终端号");
			return false;
		}
		// 先获取终端全局配置
		Terminal terminal = this.getTerminalFromCache(terminalId);
		if (terminal == null) {
			logger.error("未从缓存中取到终端，不做判断, terminalId={}", terminalId);
			return false;
		}
		// 禁用异步检票的终端不执行本次查票

		if (terminal.getIsCheckEnabled() != null && terminal.getIsCheckEnabled() == EnabledStatus.disabled.value) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isCheckEnabledForLotteryType(Long terminalId, LotteryType lotteryType) {
		boolean terminalGlobalCheck = this.isCheckEnabled(terminalId);
		if (!terminalGlobalCheck) {
			return false;
		}

		// 获取针对此彩种的终端配置
		if (lotteryType == null) {
			logger.info("未指定彩种");
			return true;
		}
		TerminalConfig terminalConfig = this.getTerminalConfigFromCache(terminalId, lotteryType);
		if (terminalConfig == null) {
			logger.info("未从缓存中取到终端配置，不做判断, terminalId={}, lotteryType={}", terminalId, lotteryType);
			return true;
		}
		// 已禁用、禁用异步检票的终端配置
        if (terminalConfig.getIsCheckEnabled() != null && terminalConfig.getIsCheckEnabled() == EnabledStatus.disabled.getValue()) {
            return false;
        }

		return true;
	}

	@Override
	public List<TerminalConfig> getTerminalConfigList(Long terminalId) {
		// 先获取终端全局配置
		Terminal terminal = this.getTerminalFromCache(terminalId);
		if (terminal == null) {
			logger.error("未从缓存中取到终端，不做判断, terminalId={}", terminalId);
			return null;
		}
		Long[] terminalConfigIdList = null;
		try {
			terminalConfigIdList = terminalLotteryTypeConfigIdCacheModel.get(terminalId);
		} catch (Exception e) {
			logger.error("从缓存中获取终端配置ID列表出错, terminalId={}", terminalId);
			logger.error(e.getMessage(), e);
			return null;
		}
		if (terminalConfigIdList == null || terminalConfigIdList.length == 0) {
			// 就是没有配置
			return null;
		}
		try {
			Map<Long, TerminalConfig> result = terminalConfigCacheModel.mget(Arrays.asList(terminalConfigIdList));
			if (result != null && !result.isEmpty()) {
				return new ArrayList<TerminalConfig>(result.values());
			}
		} catch (Exception e) {
			logger.error("从缓存中批量获取终端配置出错, terminalConfigIdList={}", StringUtils.join(terminalConfigIdList, ","));
		}
		return null;
	}

	@Override
	public boolean isDuringCheckForbidPeriod(Long terminalId, LotteryType lotteryType) {
		if (terminalId == null) {
			logger.error("未指定终端号");
			return false;
		}
		// 先获取终端全局配置
		Terminal terminal = this.getTerminalFromCache(terminalId);
		if (terminal == null) {
			logger.error("未从缓存中取到终端，不做判断, terminalId={}", terminalId);
			return false;
		}
		// 如果设置了禁止检票时段，开始判断
		if (terminal.getCheckForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(terminal.getCheckForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}

		// 获取针对此彩种的终端配置
		if (lotteryType == null) {
			logger.error("未指定彩种");
			return false;
		}
		TerminalConfig terminalConfig = this.getTerminalConfigFromCache(terminalId, lotteryType);
		if (terminalConfig == null) {
			logger.error("未从缓存中取到终端配置，不做判断, terminalId={}, lotteryType={}", terminalId, lotteryType);
			return false;
		}
		// 如果设置了禁止检票时段，开始判断
		if (terminalConfig.getCheckForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(terminalConfig.getCheckForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isEnabledForLotteryType(Long terminalId, LotteryType lotteryType) {
		boolean terminalGlobalCheck = this.isEnabled(terminalId);
		if (!terminalGlobalCheck) {
			return false;
		}

		// 获取针对此彩种的终端配置
		if (lotteryType == null) {
			logger.info("未指定彩种");
			return true;
		}
		TerminalConfig terminalConfig = this.getTerminalConfigFromCache(terminalId, lotteryType);
		if (terminalConfig == null) {
			logger.warn("未从缓存中取到终端配置，按启用处理, terminalId={}, lotteryType={}", terminalId, lotteryType);
			return true;
		}
		// 已禁用、禁用异步检票的终端配置
		if (terminalConfig.getIsEnabled() != null && terminalConfig.getIsEnabled() == EnabledStatus.disabled.getValue()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEnabledForLotteryType(Long terminalId, LotteryType lotteryType,PlayType playType) {
		boolean terminalGlobalCheck = this.isEnabled(terminalId);
		if (!terminalGlobalCheck) {
			return false;
		}

		// 获取针对此彩种的终端配置
		if (lotteryType == null) {
			logger.info("未指定彩种");
			return true;
		}
		TerminalConfig terminalConfig = this.getTerminalConfigFromCache(terminalId, lotteryType,playType);
		if (terminalConfig == null) {
			logger.warn("未从缓存中取到终端配置，按启用处理, terminalId={}, lotteryType={}", terminalId, lotteryType);
			return true;
		}
		// 已禁用、禁用异步检票的终端配置
		if (terminalConfig.getIsEnabled() != null && terminalConfig.getIsEnabled() == EnabledStatus.disabled.getValue()) {
			return false;
		}
		return true;
	}

	
	@Override
	public boolean isEnabled(Long terminalId) {
		if (terminalId == null) {
			logger.error("未指定终端号");
			return false;
		}
		// 先获取终端全局配置
		Terminal terminal = this.getTerminalFromCache(terminalId);
		if (terminal == null) {
			logger.error("未从缓存中取到终端，按启用处理, terminalId={}", terminalId);
			return true;
		}
		if (terminal.getIsEnabled() != null && terminal.getIsEnabled() == EnabledStatus.disabled.getValue()) {
			return false;
		}
		return true;
	}

	@Override
	public Terminal getTerminal(Long terminalId) {
		Terminal terminal = this.getTerminalFromCache(terminalId);

		if (terminal == null) {
			logger.error("未找到对应的终端, terminalId={}", terminalId);
			return null;
		}

		return terminal;
	}

	@Override
	public boolean isConfigured(Long terminalId, LotteryType lotteryType) {
		if (terminalId == null || lotteryType == null) {
			logger.error("未指定终端号或未指定彩种");
			return false;
		}
		// 先获取终端全局配置
		if (this.getTerminalFromCache(terminalId) == null) {
			logger.warn("未从缓存中取到终端，按未配置处理, terminalId={}", terminalId);
			return false;
		}
		return this.getTerminalConfigFromCache(terminalId, lotteryType) != null;
	}

	@Override
	public boolean isGlobalCheckDisabledOrDuringCheckForbidPeriod(LotteryType lotteryType) {
		if (this.isCheckDisabledOrDuringCheckForbidPeriod(LotteryType.ALL)) {
			return true;
		}
		return this.isCheckDisabledOrDuringCheckForbidPeriod(lotteryType);
	}

	protected boolean isCheckDisabledOrDuringCheckForbidPeriod(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("未指定彩种");
			return false;
		}
		LotteryTerminalConfig lotteryTerminalConfig = this.getLotteryTerminalConfigFromCache(lotteryType);
		if (lotteryTerminalConfig == null) {
			logger.error("未从缓存中取到彩种配置,不做判断,lotteryType={}", lotteryType);
			return false;
		}
		if (lotteryTerminalConfig.getIsCheckEnabled() != null && lotteryTerminalConfig.getIsCheckEnabled() == EnabledStatus.disabled.getValue()) {
			// 全局检票状态已禁用
			return true;
		}
		// 如果设置了禁止检票时段，开始判断
		if (lotteryTerminalConfig.getCheckForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(lotteryTerminalConfig.getCheckForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isGlobalDisabledOrDuringAllotForbidPeriod(LotteryType lotteryType) {
		if (this.isDisabledOrDuringAllotForbidPeriod(LotteryType.ALL)) {
			return true;
		}
		return this.isDisabledOrDuringAllotForbidPeriod(lotteryType);
	}

	protected boolean isDisabledOrDuringAllotForbidPeriod(LotteryType lotteryType) {
		if (lotteryType == null) {
			logger.error("未指定彩种");
			return false;
		}
		LotteryTerminalConfig lotteryTerminalConfig = this.getLotteryTerminalConfigFromCache(lotteryType);
		if (lotteryTerminalConfig == null) {
			logger.info("未从缓存中取到彩种配置，不做判断, lotteryType={}", lotteryType);
			return false;
		}
		if (lotteryTerminalConfig.getIsEnabled() != null && lotteryTerminalConfig.getIsEnabled() == EnabledStatus.disabled.getValue()) {
			// 全局检票状态已禁用
			return true;
		}
		// 如果设置了禁止检票时段，开始判断
		if (lotteryTerminalConfig.getAllotForbidPeriod() != null) {
			TimeRegion timeRegion = TimeRegion.parse(lotteryTerminalConfig.getAllotForbidPeriod());
			if (timeRegion != null) {
				if (CoreTimeUtils.isCurrentDuringPeriod(timeRegion)) {
					return true;
				}
			}
		}
		return false;
	}
    public boolean isSaleEnabledForLotteryType(LotteryType lotteryType){
    	
    	try {
    		lotteryType=LotteryType.getSingle(lotteryType);
        	if(lotteryType==null){
    			return false;
    		}
			LottypeConfig lottypeConfig=lottypeConfigCacheModel.get(lotteryType.value);
			if(lottypeConfig!=null&&lottypeConfig.getSaleEnabled()!=null&&lottypeConfig.getSaleEnabled()==EnabledStatus.enabled.value){
				return true;
			}
		} catch (Exception e) {
			
			
		}
    	return false;
    }
	public Long getTopPriorityTerminalIdWithAmount(LotteryType lotteryType, String phase, PlayType playType, Date deadline, BigDecimal amount){

		return  this.getTopPriorityTerminalIdWithAmount(lotteryType, phase, playType, deadline, amount,null);
	}
	@Override
	public Long getTopPriorityTerminalIdWithAmount(LotteryType lotteryType, String phase, PlayType playType, Date deadline, BigDecimal amount, ITerminalSelectorFilter filter) {
		// 首先获取此彩种所有可用的终端
		List<TerminalConfig> terminalConfigList;

		try {
			terminalConfigList = this.getEnabledTerminalConfigList(lotteryType, playType);
		} catch (Exception e) {
			logger.error("获取可用终端配置时出错, lotteryType={}, playType={}", lotteryType, playType);
			logger.error(e.getMessage(), e);
			return null;
		}

		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			logger.error("未找到可用终端, lotteryType={}, playType={}", lotteryType, playType);
			return null;
		}

		List<TerminalConfig> availableTerminalConfigList = this.filterAvailableTerminalConfig(terminalConfigList, deadline);

		if (availableTerminalConfigList == null || availableTerminalConfigList.isEmpty()) {
			logger.error("截止期前无可用终端, lotteryType={}, phase={}, playType={}", new Object[]{lotteryType, phase, playType});
			return null;
		}

		// 过滤配置了截止时间前分票提前时间配置的终端
		{
			long currentTimeMillis = System.currentTimeMillis();

			Map<Long,TerminalConfig> newAvailableTerminalConfigMap=new HashMap<Long, TerminalConfig>();
			for (TerminalConfig terminalConfig : availableTerminalConfigList) {
				if (terminalConfig.getTerminateAllotForward() == null || terminalConfig.getTerminateAllotForward() <= 0) {
					// 未配置或者无效配置, 不过滤

					newAvailableTerminalConfigMap.put(terminalConfig.getId(),terminalConfig);
				}else{

				}
				if (terminalConfig.getTerminateForward() != null && terminalConfig.getTerminateAllotForward() <= terminalConfig.getTerminateForward()) {
					// 提前时间在收票截止时间范围内为无效配置, 不过滤
					newAvailableTerminalConfigMap.put(terminalConfig.getId(),terminalConfig);
				}
				// 有效配置, 通过deadline和提前量来判断当前是否可分票
				if (currentTimeMillis >= deadline.getTime() - terminalConfig.getTerminateAllotForward()) {
					// 已经在可分票时段内, 不过滤
					newAvailableTerminalConfigMap.put(terminalConfig.getId(),terminalConfig);
				}
			}

			// 如果过滤完的可用终端配置不为空, 使用本轮过滤结果作为后续使用
			if (!newAvailableTerminalConfigMap.isEmpty()) {
				availableTerminalConfigList = new ArrayList<TerminalConfig>();
				availableTerminalConfigList.addAll(newAvailableTerminalConfigMap.values());
			}
		}

		Map<Long, Long> terminalDeadlineMap = new HashMap<Long, Long>();
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			long terminatedTime = deadline.getTime() - terminalConfig.getTerminateForward();
			terminalDeadlineMap.put(terminalConfig.getTerminalId(), terminatedTime);
		}

		// 构造一个批量查询mc的请求
		List<String> keyList = new ArrayList<String>();
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			keyList.add(this.generateMemcachedKey(terminalConfig.getTerminalId(), lotteryType, phase, playType));
		}

		// 批量查询的结果
		Map<String, IMemcachedObject> foundMap = null;
		try {
			foundMap = memcachedService.mget(keyList, TIMEOUT);
		} catch (Exception e) {
			logger.error("从缓存中获取终端信息出错", e);
		}

		List<TerminalFailureCache> failureCacheList = new ArrayList<TerminalFailureCache>();
		Map<Long, TerminalFailureCache> terminalFailureCacheMap = new HashMap<Long, TerminalFailureCache>();
		// 检查从MC取到的查询结果，对于MC中不存在的项目，需要建立并设置缓存
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			Long terminalId = terminalConfig.getTerminalId();

			String key = this.generateMemcachedKey(terminalId, lotteryType, phase, playType);
			TerminalFailureCache terminalFailureCache = null;
			if (foundMap != null && foundMap.containsKey(key)) {
				terminalFailureCache = (TerminalFailureCache)foundMap.get(key);
			}
			if (terminalFailureCache == null) {
				terminalFailureCache = generateNewTerminalFailureCache(terminalId, lotteryType, phase, playType);
				if (terminalFailureCache != null) {
					Long terminatedTime = terminalDeadlineMap.get(terminalId);
					if (terminatedTime != null) {
						terminalFailureCache.setTerminatedTime(terminatedTime);
					}
					try {
						memcachedService.set(key, terminalFailureCache, ALIVE_TIME);
					} catch (Exception e) {
						logger.error("往缓存中设置终端信息出错", e);
					}
				}
			}
			// 添加到失败计数缓存列表，用于计算
			if (terminalFailureCache != null) {
				failureCacheList.add(terminalFailureCache);
				if (amount != null && amount.doubleValue() > 0) {
					terminalFailureCacheMap.put(terminalId, terminalFailureCache);
				}
			}
		}


		// 根据金额过滤终端
		if (amount != null && amount.doubleValue() > 0) {
			List<TerminalFailureCache> hasPreferAmountRegionFailureCacheList = new ArrayList<TerminalFailureCache>();
			for (TerminalConfig terminalConfig : availableTerminalConfigList) {
				if (terminalConfig.getPreferAmountRegion() != null) {
					AmountRegion region = AmountRegion.parse(terminalConfig.getPreferAmountRegion());
					if (region == null) {
						continue;
					}
					if (region.isDuringRegion(amount.doubleValue())) {
						TerminalFailureCache terminalFailureCache = terminalFailureCacheMap.get(terminalConfig.getTerminalId());
						if (terminalFailureCache != null) {
							long lastFailureTime = terminalFailureCache.getLastFailedTime();
							// 抛弃最近失败记录在一分钟内的记录
							if (lastFailureTime != 0 && System.currentTimeMillis() - lastFailureTime < 60000) {
								logger.error("{}的终端({})配置最近有失败记录，主动抛弃", terminalConfig.getLotteryType(), terminalConfig.getTerminalId());
							} else {
								hasPreferAmountRegionFailureCacheList.add(terminalFailureCache);
							}
						}
					}
				}
			}
			if (!hasPreferAmountRegionFailureCacheList.isEmpty()) {
				failureCacheList = hasPreferAmountRegionFailureCacheList;
			}
		}


		if (filter != null) {
			List<TerminalFailureCache> filteredList = new ArrayList<TerminalFailureCache>();
			for (TerminalFailureCache cache : failureCacheList) {
				boolean result = false;
				try {
					result = filter.filter(cache, lotteryType, phase, playType);
				} catch (Exception e) {
					logger.error("进行终端号({})的过滤时出错, 默认为不过滤", cache.getTerminalId());
					logger.error(e.getMessage(), e);
				}
				if (!result) {
					// 不过滤的结果
					filteredList.add(cache);
				}
			}
			failureCacheList = filteredList;
		}

		return chooseTopPriorityTerminalId(failureCacheList, deadline, lotteryType, playType);
	}




	protected  Long getEnableTermianlId(List<TerminalConfig> availableTerminalConfigList,LotteryType lotteryType, String phase, PlayType playType, Date deadline, ITerminalSelectorFilter filter){
		if (availableTerminalConfigList == null || availableTerminalConfigList.isEmpty()) {
			logger.error("截止期前无可用终端, lotteryType={}, phase={}, playType={}", new Object[]{lotteryType, phase, playType});
			return null;
		}

		// 过滤配置了截止时间前分票提前时间配置的终端
		{
			long currentTimeMillis = System.currentTimeMillis();
			
			Map<Long,TerminalConfig> newAvailableTerminalConfigMap=new HashMap<Long,TerminalConfig>();
			
			for (TerminalConfig terminalConfig : availableTerminalConfigList) {
				if (terminalConfig.getTerminateAllotForward() == null || terminalConfig.getTerminateAllotForward() <= 0) {
					// 未配置或者无效配置, 不过滤
					newAvailableTerminalConfigMap.put(terminalConfig.getId(), terminalConfig);
				}

				if (terminalConfig.getTerminateForward() != null && terminalConfig.getTerminateAllotForward() <= terminalConfig.getTerminateForward()) {
					// 提前时间在收票截止时间范围内为无效配置, 不过滤
					newAvailableTerminalConfigMap.put(terminalConfig.getId(), terminalConfig);
				}
				// 有效配置, 通过deadline和提前量来判断当前是否可分票
				if (currentTimeMillis >= deadline.getTime() - terminalConfig.getTerminateAllotForward()) {
					// 已经在可分票时段内, 不过滤
					newAvailableTerminalConfigMap.put(terminalConfig.getId(), terminalConfig);
				}
			}

			// 如果过滤完的可用终端配置不为空, 使用本轮过滤结果作为后续使用
			if (!newAvailableTerminalConfigMap.isEmpty()) {
				availableTerminalConfigList=new ArrayList<TerminalConfig>();
				availableTerminalConfigList.addAll(newAvailableTerminalConfigMap.values());
			}
		}

		Map<Long, Long> terminalDeadlineMap = new HashMap<Long, Long>();
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			long terminatedTime = deadline.getTime() - terminalConfig.getTerminateForward();
			terminalDeadlineMap.put(terminalConfig.getTerminalId(), terminatedTime);
		}

		// 构造一个批量查询mc的请求
		List<String> keyList = new ArrayList<String>();
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			keyList.add(this.generateMemcachedKey(terminalConfig.getTerminalId(), lotteryType, phase, playType));
		}

		// 批量查询的结果
		Map<String, IMemcachedObject> foundMap = null;
		try {
			foundMap = memcachedService.mget(keyList, TIMEOUT);
		} catch (Exception e) {
			logger.error("从缓存中获取终端信息出错", e);
		}

		List<TerminalFailureCache> failureCacheList = new ArrayList<TerminalFailureCache>();
		//Map<Long, TerminalFailureCache> terminalFailureCacheMap = new HashMap<Long, TerminalFailureCache>();
		// 检查从MC取到的查询结果，对于MC中不存在的项目，需要建立并设置缓存
		for (TerminalConfig terminalConfig : availableTerminalConfigList) {
			Long terminalId = terminalConfig.getTerminalId();

			String key = this.generateMemcachedKey(terminalId, lotteryType, phase, playType);
			TerminalFailureCache terminalFailureCache = null;
			if (foundMap != null && foundMap.containsKey(key)) {
				terminalFailureCache = (TerminalFailureCache)foundMap.get(key);
			}
			if (terminalFailureCache == null) {
				terminalFailureCache = generateNewTerminalFailureCache(terminalId, lotteryType, phase, playType);
				if (terminalFailureCache != null) {
					Long terminatedTime = terminalDeadlineMap.get(terminalId);
					if (terminatedTime != null) {
						terminalFailureCache.setTerminatedTime(terminatedTime);
					}
					try {
						memcachedService.set(key, terminalFailureCache, ALIVE_TIME);
					} catch (Exception e) {
						logger.error("往缓存中设置终端信息出错", e);
					}
				}
			}
			// 添加到失败计数缓存列表，用于计算
			if (terminalFailureCache != null) {
				failureCacheList.add(terminalFailureCache);

			}
		}


		if (filter!= null) {
			List<TerminalFailureCache> filteredList = new ArrayList<TerminalFailureCache>();
			for (TerminalFailureCache cache : failureCacheList) {
				boolean result = false;
				try {
					result = filter.filter(cache, lotteryType, phase, playType);
				} catch (Exception e) {
					logger.error("进行终端号({})的过滤时出错, 默认为不过滤", cache.getTerminalId());
					logger.error(e.getMessage(), e);
				}
				if (!result) {
					// 不过滤的结果
					filteredList.add(cache);
				}
			}
			failureCacheList = filteredList;
		}

		return chooseTopPriorityTerminalId(failureCacheList, deadline, lotteryType, playType);
	}



/**
 * 最新方法过滤
 *
 * */


	public Long getTopPriorityTerminalIdWithPlayTypeAndAmount(LotteryType lotteryType, String phase, PlayType playType,BigDecimal amount, Date deadline,ITerminalSelectorFilter filter,String betCode)throws Exception{
		List<TerminalConfig> terminalConfigList;

		try {
			terminalConfigList = this.getEnabledTerminalConfigList(lotteryType, PlayType.mix);
		} catch (Exception e) {
			logger.error("获取可用终端配置时出错, lotteryType={}, playType={}", lotteryType, playType);
			logger.error(e.getMessage(), e);
			return null;
		}

		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			logger.error("未找到可用终端, lotteryType={}, playType={}", lotteryType, playType);
			return null;
		}



		List<TerminalConfig> availableTerminalConfigList = this.filterAvailableTerminalConfig(terminalConfigList, deadline);


		availableTerminalConfigList=betCodeFilter(availableTerminalConfigList,lotteryType,betCode);
        if(availableTerminalConfigList.size()==0){
			logger.error("混合过关过滤无可用终端");
        	return null;
        }
		
		List<TerminalConfig> amountTermianlconfig=new ArrayList<TerminalConfig>();

		// 根据金额过滤终端
		if (amount != null && amount.doubleValue() > 0) {
			for (TerminalConfig terminalConfig : availableTerminalConfigList) {
				if (terminalConfig.getPreferAmountRegion() != null&&terminalConfig.getAmountEnabled()!=null&&terminalConfig.getAmountEnabled()==YesNoStatus.yes.value) {
					AmountRegion region = AmountRegion.parse(terminalConfig.getPreferAmountRegion());
					if (region == null) {
						continue;
					}
					if (region.isDuringRegion(amount.doubleValue())) {
						amountTermianlconfig.add(terminalConfig);
					}
				}
			}
		}
		
       

        Map<Long,TerminalConfig> playTypeNotContainMap=new HashMap<Long, TerminalConfig>();
		for (TerminalConfig terminalConfig : availableTerminalConfigList){
			if(StringUtils.isNotBlank(terminalConfig.getPlayTypeNotContain())&&terminalConfig.getPlayTypeNotContainEnabled()==EnabledStatus.enabled.value){
				String plays=terminalConfig.getPlayTypeNotContain();
				if (plays.contains(playType.value+"")){
					playTypeNotContainMap.put(terminalConfig.getId(),terminalConfig);
				}
			}
		}



		if(amountTermianlconfig.size()>0){
			if (playTypeNotContainMap.values().size()>0){//有不支持玩法,取差集
				amountTermianlconfig.removeAll(playTypeNotContainMap.values());
			}
			if(amountTermianlconfig.size()>0){
				return getEnableTermianlId(amountTermianlconfig,lotteryType,phase,PlayType.mix,deadline,filter);
			}

		}
		
		if (playTypeNotContainMap.values().size()>0){//有不支持玩法,取差集
			availableTerminalConfigList.removeAll(playTypeNotContainMap.values());
			if(availableTerminalConfigList.size()>0){
				return getEnableTermianlId(availableTerminalConfigList,lotteryType,phase,PlayType.mix,deadline,filter);
			}
		}


       return null;
	}



	protected List<TerminalConfig> betCodeFilter(List<TerminalConfig> terminalConfigList,LotteryType lotteryType,String betCode){

		if (lotteryType==LotteryType.JCLQ_HHGG||lotteryType==LotteryType.JCZQ_HHGG){
			boolean contain=false;
			List<TerminalConfig> returnTerminalConfig=new ArrayList<TerminalConfig>();
			Map<Long,TerminalConfig> newHashMap=new HashMap<Long,TerminalConfig>();
			for(TerminalConfig terminalConfig:terminalConfigList){
				String mixContain=terminalConfig.getMixContain();
				if(StringUtils.isNotBlank(mixContain)){
					contain=true;
					String[] types=StringUtils.split(mixContain,",");
					for(String type:types){
						if(type.contains("&")){
							String [] all=StringUtils.split(type,"&");
							if (all.length==2){
								boolean containSub=true;
								String[] betLotteryTypes=StringUtils.split(betCode,"|");
								for(String betLotteryType:betLotteryTypes){
									betLotteryType=betLotteryType.substring(betLotteryType.indexOf("*"));
									if(betLotteryType.contains(all[0])||betLotteryType.contains(all[1])){

									}else{//只要有一个不合适,退出
										containSub=false;
										break;
									}
								}
								if (containSub){
									if(!newHashMap.containsKey(terminalConfig.getId())){
										newHashMap.put(terminalConfig.getId(), terminalConfig);
									}
								}
							}
						}
						if(type.contains("|")){
							String [] all=StringUtils.split(type,"|");
							if (all.length==2){
								String[] betLotteryTypes=StringUtils.split(betCode,"|");
								for(String betLotteryType:betLotteryTypes){
									betLotteryType=betLotteryType.substring(betLotteryType.indexOf("*"));
									if(betLotteryType.contains(all[0])||betLotteryType.contains(all[1])){
										if(!newHashMap.containsKey(terminalConfig.getId())){
											newHashMap.put(terminalConfig.getId(), terminalConfig);
										}
									}
								}
							}
						}

					}

				}
			}
			if(contain){
				for(TerminalConfig terminalConfig:newHashMap.values()){
					returnTerminalConfig.add(terminalConfig);
				}
				return returnTerminalConfig;
			}else{
				return terminalConfigList;
			}

		}else {
			return terminalConfigList;
		}

	}


	public Long getTopPriorityTerminalIdSpecifyPlayTypeWithAmount(LotteryType lotteryType, String phase,PlayType playType,Date deadline, BigDecimal amount,String betCode)throws Exception{
		List<TerminalConfig> terminalConfigList;

		try {
			terminalConfigList = this.getEnabledTerminalConfig(playType);
		} catch (Exception e) {
			logger.error("获取可单独玩法配置时出错,playType={}", playType);
			logger.error(e.getMessage(), e);
			return null;
		}

		if (terminalConfigList == null || terminalConfigList.isEmpty()) {
			logger.error("获取可单独玩法配置时出错,playType={}", playType);
			return null;
		}

		List<TerminalConfig> availableTerminalConfigList = this.filterAvailableTerminalConfig(terminalConfigList, deadline);

		availableTerminalConfigList=this.betCodeFilter(availableTerminalConfigList,lotteryType,betCode);
		if(availableTerminalConfigList.size()==0){
			return null;
		}


		List<TerminalConfig> amountTermianlconfig=new ArrayList<TerminalConfig>();

		boolean amountFlag=false;
		// 根据金额过滤终端
		if (amount != null && amount.doubleValue() > 0) {
			for (TerminalConfig terminalConfig : availableTerminalConfigList) {
				if (terminalConfig.getPreferAmountRegion() != null&&terminalConfig.getAmountEnabled()!=null&&terminalConfig.getAmountEnabled()==YesNoStatus.yes.value) {
					if(!amountFlag){
						amountFlag=true;
					}
					AmountRegion region = AmountRegion.parse(terminalConfig.getPreferAmountRegion());
					if (region == null) {
						continue;
					}
					if (region.isDuringRegion(amount.doubleValue())) {
						amountTermianlconfig.add(terminalConfig);
					}
				}
			}
		}

		if(amountFlag){
			if(amountTermianlconfig.size()>0){
				return getEnableTermianlId(amountTermianlconfig,lotteryType,phase,playType,deadline,null);
			}
			return null;
		}
		

		return getEnableTermianlId(availableTerminalConfigList,lotteryType,phase,playType,deadline,null);
	}


	/**
	 * 彩种的终端相关的配置记录查询
	 * @param  lotteryType 彩种
	 * */
	public LotteryTicketConfig getLotteryTicketConfig(LotteryType lotteryType){
		LotteryTicketConfig lotteryTicketConfig=null;

			try {
				lotteryTicketConfig=lotteryTicketConfigCacheModel.get(lotteryType);
				if(lotteryTicketConfig!=null&&lotteryTicketConfig.getLotteryType()!=null){
					return lotteryTicketConfig;
				}
				lotteryTicketConfig=lotteryTicketConfigCacheModel.get(LotteryType.ALL);
			} catch (CacheNotFoundException e) {

			} catch (CacheUpdateException e) {

			}catch (Exception e){
				logger.error("获取彩种的终端相关的配置出错",e);
			}
		return  lotteryTicketConfig;
	}



}
