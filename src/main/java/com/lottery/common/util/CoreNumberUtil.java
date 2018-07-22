package com.lottery.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 数字工具类
 * 
 *
 */
public class CoreNumberUtil {
	
	/**
	 * 格式化数字-设置不以','隔开并保留2位小数
	 * @param amount 被格式化的数字
	 * @return
	 * @throws Exception 
	 */
	public static String formatNumBy2Digits(double amount) throws Exception{
		return CoreNumberUtil.formatNumber(amount, false, 2, 2);
	}
	
	/**
	 * 格式化数字-设置不以','隔开并保留0位小数
	 * @param amount 被格式化的数字
	 * @return
	 * @throws Exception 
	 */
	public static String formatNumBy0Digits(double amount) throws Exception{
		return CoreNumberUtil.formatNumber(amount, false, 0, 0);
	}
	
	/**
	 * 格式化数字-设置是否以','隔开，设置最多小数位数，设置最少小数位数
	 * @param amount 被格式化的数字
	 * @param groupUsed 设置是否以','隔开
	 * @param maximumFractionDigits 设置最多小数位数
	 * @param minimumFractionDigits 设置最少小数位数
	 * @return
	 */
	public static String formatNumber(double amount, boolean groupUsed, 
			int maximumFractionDigits,int minimumFractionDigits){
        try {
            NumberFormat format = NumberFormat.getInstance();

            format.setGroupingUsed(groupUsed);		//设成false，不以','隔开
            format.setMaximumFractionDigits(maximumFractionDigits);	//设置最多小数位数
            format.setMinimumFractionDigits(minimumFractionDigits);	//设置最少小数位数

            return format.format(amount);
        }catch (Exception e){
            return  null;
        }

	}
    private CoreNumberUtil() {

    }

    ///////////////////////////////////////////////////////////////////////////
    //DOUBLE 类型运算。
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 精确的加法运算.
     */
    public static float add(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).floatValue();
    }

    /**
     *
     * 精确的减法运算.
     *
     * @param v1 被减数
     * @param v2 减数
     */
    public static float subtract(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    /**
     * 提供精确的乘法运算.
     */
    public static float multiply(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).floatValue();
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     *
     * @param scale 运算结果小数后精确的位数
     */
    public static float multiply(float v1, float v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }


    /**
     * 提供（相对）精确的除法运算.
     *
     * @see #divide(double, double, int)
     */
    public static float divide(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2).floatValue();
    }

    /**
     * 提供（相对）精确的除法运算.
     * 由scale参数指定精度，以后的数字四舍五入.
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     */
    public static float divide(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 提供精确的小数位四舍五入处理.
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     */
    public static float round(float v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    //////////////////////////////////////////////////////////////////////////
    //LONG型运算。
    //////////////////////////////////////////////////////////////////////////

    /**
     * 精确的加法运算.
     */
    public static long add(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).longValue();
    }

    /**
     *
     * 精确的减法运算.
     *
     * @param v1 被减数
     * @param v2 减数
     */
    public static long subtract(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.subtract(b2).longValue();
    }

    /**
     * 提供精确的乘法运算.
     */
    public static long multiply(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).longValue();
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     *
     * @param scale 运算结果小数后精确的位数
     */
    public static long multiply(long v1, long v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     *
     * @param scale 运算结果小数后精确的位数
     */
    public static double multiply(double v1, double v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 提供（相对）精确的除法运算.
     *
     * @see #divide(long, long, int)
     */
    public static long divide(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2).longValue();
    }

    /**
     * 提供（相对）精确的除法运算.
     * 由scale参数指定精度，以后的数字四舍五入.
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     */
    public static long divide(long v1, long v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * 提供精确的小数位四舍五入处理.
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     */
    public static long round(long v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).longValue();
    }

























    //////////////////////////////////////////////////////////////////////////
    //BigDecimal型运算。
    //////////////////////////////////////////////////////////////////////////

    /**
     * 精确的加法运算.
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return v1.add(v2);
    }

    /**
     *
     * 精确的减法运算.
     *
     * @param v1 被减数
     * @param v2 减数
     */
    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }

    /**
     * 提供精确的乘法运算.
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        return v1.multiply(v2);
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     *
     * @param scale 运算结果小数后精确的位数
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v1.multiply(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 提供（相对）精确的除法运算.
     *
     * @see #divide(long, long, int)
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2);
    }

    /**
     * 提供（相对）精确的除法运算.
     * 由scale参数指定精度，以后的数字四舍五入.
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理.
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     */
    public static BigDecimal round(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

}
