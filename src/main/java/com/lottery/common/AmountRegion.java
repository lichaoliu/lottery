package com.lottery.common;


import com.lottery.common.util.CoreNumberUtil;
import com.lottery.common.util.PairValue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 金额范围 或 其他任何数字范围
 * @author fengqinyun
 *
 */
public class AmountRegion {
	
	private static final Logger logger = LoggerFactory.getLogger(AmountRegion.class.getName());

	private List<PairValue<Double, Double>> regionList = new ArrayList<PairValue<Double,Double>>();

    public static boolean isDuringRegion(AmountRegion region, Double amount) {
        if (region == null || amount == null) {
            logger.error("数字范围为空，不做判断");
            return false;
        }

        List<PairValue<Double, Double>> regionList = region.getRegionList();
        if (regionList == null || regionList.isEmpty()) {
            logger.error("数字范围段为空，不做判断");
            return false;
        }

        for (PairValue<Double, Double> pairValue : regionList) {
            Double amountLeft = pairValue.getLeft();
            Double amountRight = pairValue.getRight();

            if (amountLeft == null || amountRight == null) {
                logger.error("不是有效的范围，跳过");
                continue;
            }

            if (amountLeft == 0 && amount < amountRight) {
                return true;
            } else if (amountRight == 0 && amount >= amountLeft) {
                return true;
            } else if (amount < amountRight && amount >= amountLeft) {
                return true;
            }
        }

        return false;
    }

    public boolean isDuringRegion(Double amount) {
        return isDuringRegion(this, amount);
    }

	/**
	 * 从字符串反向解析回对象
	 * @param str
	 * @return
	 */
	public static AmountRegion parse(String str) {
		if (str == null) {
			return null;
		}
		
		List<PairValue<Double, Double>> regionList = new ArrayList<PairValue<Double,Double>>();

		String[] pairValueStrs = StringUtils.split(str, " ,");
		for (String pairValueStr : pairValueStrs) {
			String[] amountStrs = StringUtils.split(pairValueStr, "|");
			if (amountStrs.length != 2) {
				logger.error("错误的数字范围，直接忽略, {}", pairValueStr);
				continue;
			}
            try {
                Double amountLeft = Double.valueOf(amountStrs[0]);
                Double amountRight = Double.valueOf(amountStrs[1]);

                if (amountLeft == null || amountRight == null) {
                    logger.error("数字解析出错，直接忽略, {}, {}", amountStrs[0], amountStrs[1]);
                    continue;
                }

                if (amountLeft == 0 && amountRight == 0) {
                    logger.error("左右范围值均为0，直接忽略, {}, {}", amountStrs[0], amountStrs[1]);
                    continue;
                }

                if (amountLeft != 0 && amountRight != 0 && amountRight < amountLeft) {
                    logger.error("右值小于左值，直接忽略, {}, {}", amountStrs[0], amountStrs[1]);
                    continue;
                }

                regionList.add(new PairValue<Double, Double>(amountLeft, amountRight));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                logger.error("转换成数值出错, amountStr={}", pairValueStr);
            }

		}
		
		if (regionList.isEmpty()) {
			return null;
		}
		
		AmountRegion region = new AmountRegion();
        region.setRegionList(regionList);
		
		return region;
	}

	/**
	 * 转换成 range1_begin|range1_end,range2_begin|range2_end...
	 */
	@Override
	public String toString() {
		if (regionList == null) {
			return null;
		}
		List<String> strList = new ArrayList<String>();
		for (PairValue<Double, Double> pair : regionList) {
			if (pair == null) {
				continue;
			}
			
			if (pair.getLeft() == null || pair.getRight() == null) {
				// 一对都必须有值
				continue;
			}
			
			strList.add(CoreNumberUtil.formatNumber(pair.getLeft(), false, 2, 0) + "|" + CoreNumberUtil.formatNumber(pair.getRight(), false, 2, 0));
		}
		return StringUtils.join(strList, ",");
	}

    public List<PairValue<Double, Double>> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<PairValue<Double, Double>> regionList) {
        this.regionList = regionList;
    }
}
