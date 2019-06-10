/**
 * 
 */
package com.lottery.ticket.vender;

import com.lottery.common.TimeRegion;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreTimeUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fengqinyun
 *
 */
public abstract class AbstractVenderConverter implements IVenderConverter {

	protected static ThreadLocal<String> matchTypeThreadLocal = new ThreadLocal<String>();
	@Resource(name="venderConverterBinder")
	protected Map<TerminalType, IVenderConverter> venderConverterBinder;
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * 彩种映射
	 * @return
	 */
	abstract protected Map<LotteryType, String> getLotteryTypeMap();
	/**
	 * 彩种反转
	 * @param lotteryId
	 * @return
	 */
	public abstract LotteryType getLotteryMap(String lotteryId);
	/**
	 * 玩法映射
	 * @return
	 */
	 abstract protected Map<Integer, String> getPlayTypeMap();
	
	/**
	 * 彩期转换器的映射
	 * @return
	 */
	abstract protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap();
	
	/**
	 * 彩期反向转换器的映射
	 * @return
	 */
	abstract protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap();
	
	/**
	 * 票内容转换器的映射
	 * @return
	 */
	abstract protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap();
	
	protected Map<LotteryType, TimeRegion> getSendForbidPeriodMap() {
        return null;
    }
	
	/**
	 * 彩种反向映射
	 * @return
	 */
	abstract protected Map<String, LotteryType> getReverseLotteryTypeMap();
	public static String getMatchType() {
		return matchTypeThreadLocal.get();
	}
	@Override
	public LotteryType findLotteryType(String lotId) {
		if (getReverseLotteryTypeMap() == null) {
			return null;
		}
		return getReverseLotteryTypeMap().get(lotId);
	}


	@Override
	public List<String> convertContentList(Ticket ticket){
		PlayType playType = PlayType.get(ticket.getPlayType());
		ITicketContentConverter converter = getTicketContentConverterMap().get(playType);
		if (converter == null) {
			logger.error("不支持的玩法转换, playType={},value={}", playType.getName(), playType.getValue());
			return null;
		}
		
		
		
		try {
			
			
			List<String> convertedList = new ArrayList<String>();
			// 设置玩法
	        //具体票内容的转换
			
			return convertedList;
		} catch (Exception e) {
			logger.error("解析票内容失败, ticket content = {}", ticket.getContent());
			return null;
		}
	}

	@Override
	public String convertLotteryType(LotteryType lotteryType){
		String external = this.getLotteryTypeMap().get(lotteryType);
		if (external == null) {
			logger.error("不支持此彩种, lotteryType={}", lotteryType.getName());
			return null;
		}
		return external;
	}
	
	public String convertLotteryType(int lotteryType){
		return this.convertLotteryType(LotteryType.get(lotteryType));
	}
	
	@Override
	public String convertPhase(LotteryType lotteryType, String phase){
		String external = this.getLotteryTypeMap().get(lotteryType);
		if (external == null) {
			logger.error("不支持此彩种, lotteryType={}", lotteryType.getName());
			return null;
		}
		IPhaseConverter converter = this.getPhaseConverterMap().get(lotteryType);
		if (converter == null) {
			// 如果未设置转换器，直接返回原来的彩期号
			return phase;
		}
		external = converter.convert(phase);
		if (external == null) {
			logger.error("彩期号转换失败, lotteryType={}, phase={}", lotteryType.getName(), phase);
			return null;
		}

		return external;
	}

	@Override
	public String convertPhaseReverse(LotteryType lotteryType, String phase){
		String external = this.getLotteryTypeMap().get(lotteryType);
		if (external == null) {
			logger.error("不支持此彩种, lotteryType={}", lotteryType.getName());
			return null;
		}
		if (this.getPhaseReverseConverterMap() == null) {
			logger.error("没有给该出票商设置彩期反向转换器, lotteryType={}", lotteryType.getName());
			return null;
		}
		IPhaseConverter converter = this.getPhaseReverseConverterMap().get(lotteryType);
		if (converter == null) {
			// 如果未设置转换器，直接返回原来的彩期号
			return phase;
		}
		external = converter.convert(phase);
		if (external == null) {
			logger.error("彩期号转换失败, lotteryType={}, phase={}", lotteryType.getName(), phase);
			return null;
		}

		return external;
	}

	 
	@Override
	public String convertPlayType(String playType){
		return this.convertPlayType(Integer.parseInt(playType));
	}
	@Override
	public String convertPlayType(int playType){
		String external = this.getPlayTypeMap().get(playType);
		if (external == null) {
			logger.error("不支持此玩法, playType={}, value={}", playType);
			return null;
		}
		return external;
	}
	
	 protected boolean isDuringCustomSendForbidPeriod(LotteryType lotteryType) {
	       
	        return false;
	    }
	@Override
    public boolean isDuringSendForbidPeriod(LotteryType lotteryType) {
        if (this.isDuringCustomSendForbidPeriod(lotteryType)) {
            return true;
        }
        if (this.getSendForbidPeriodMap() == null) {
            return false;
        }
        if (this.getSendForbidPeriodMap().containsKey(lotteryType)) {
            TimeRegion timeRegion = this.getSendForbidPeriodMap().get(lotteryType);
            return CoreTimeUtils.isCurrentDuringPeriod(timeRegion);
        }

        // 是否有设置默认禁止送票时间
        if (this.getSendForbidPeriodMap().containsKey(LotteryType.ALL)) {
            TimeRegion timeRegion = this.getSendForbidPeriodMap().get(LotteryType.ALL);
            return CoreTimeUtils.isCurrentDuringPeriod(timeRegion);
        }

        return false;
    }

	public String convertPhase(int lotteryType, String phase){
		return this.convertPhase(LotteryType.getLotteryType(lotteryType),phase);
	}


	public String convertPhaseReverse(int lotteryType, String phase){
		return  this.convertPhaseReverse(LotteryType.getLotteryType(lotteryType),phase);
	}
	public String convertSp(Ticket tickt,String venderSp){
		return  venderSpConvert(tickt,venderSp);
	}

	/**
	 * 具体赔率转换
	 * */
	 abstract  protected String venderSpConvert(Ticket tickt,String venderSp);



	@PostConstruct
	abstract protected void init();
}
