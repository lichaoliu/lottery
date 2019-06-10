/**
 * 
 */
package com.lottery.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 公用数学算法
 * 
 *
 */
public class CoreMathUtils {

	/**
	 * 排列算法，P(n, m)
	 * @param n
	 * @param m
	 * @return
	 */
	public static long permute(int n, int m) {
		long num = 1;
		for (int i = 0; i < m; i ++) {
			num *= (n - i);
		}
		return num;
	}
	
	/**
	 * 组合算法，C(n, m) = P(n, m) / P(m, m)
	 * @param n
	 * @param m
	 * @return
	 */
	public static long combine(int n, int m) {
		long num = permute(n, m);
		num /= permute(m, m);
		return num;
	}
	/**
	 * 指定方式保留指定位数的小数<br/>
	 * RoundingMode.UP  有值取大值(有后续位数就进位)<br/>
	 * RoundingMode.DOWN  有值取小值(抹掉后续位数)<br/>
	 * RoundingMode.HALF_UP 四舍五入<br/>
	 * @param value
	 * @param count
	 * @param roundingMode
	 * @return
	 */
	public static double keepPointCountByMode(double value, int count,RoundingMode roundingMode){
		double keepValue = BigDecimal.valueOf(value).setScale(count,roundingMode).doubleValue();
		return keepValue;
	}
	/**
	 * 保留小数点位数(抹掉后续数字)
	 * @param value
	 * @param count
	 * @return
	 */
	public static double keepPointCount(double value, int count){
		double keepValue = BigDecimal.valueOf(value).setScale(count,RoundingMode.DOWN).doubleValue();
		return keepValue;
	}
	/**
	 * 保留小数点后2位(抹掉后续数字)
	 * @param value
	 * @return
	 */
	public static double keepPoint2Bit(double value){
		return keepPointCount(value, 2);
	}
	/**
	 * 保留小数点后4位(百分比后2位)(抹掉后续数字)
	 * @param value
	 * @return
	 */
	public static double keepPoint4Bit(double value){
		return keepPointCount(value, 4);
	}
	/**
	 * 四舍五入保留2位
	 * @param value
	 * @return
	 */
	public static double keepPointRound2Bit(double value){
		return keepPointCountByMode(value, 2, RoundingMode.HALF_UP);
	}
	/**
	 * 四舍五入保留4位
	 * @param value
	 * @return
	 */
	public static double keepPointRound4Bit(double value){
		return keepPointCountByMode(value, 4, RoundingMode.HALF_UP);
	}

    /**
     * 提供精确的加法运算。
     *
     * @param v 加数
     * @return 两个参数的和
     */
    public static double add(double... v) {
        double result = v[0];
        for (int i = 1; i < v.length; i ++) {
            BigDecimal r = new BigDecimal(Double.toString(result));
            BigDecimal b = new BigDecimal(Double.toString(v[i]));
            result =  r.add(b).doubleValue();
        }
        return result;
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v 乘数
     * @return 两个参数的积
     */
    public static double mul(double... v) {
        double result = v[0];
        for (int i = 1; i < v.length; i ++) {
            BigDecimal r = new BigDecimal(Double.toString(result));
            BigDecimal b = new BigDecimal(Double.toString(v[i]));
            result =  r.multiply(b).doubleValue();
        }
        return result;

    }

    //默认除法运算精度
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }


    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 按指定精度（小数点后位数）进行舍入运算（默认为四舍五入）
     *
     * @param value
     * @param count
     * @return
     */
    public static double round(double value, int count) {
        return roundHalfUp(value, count);
    }

    /**
     * 按指定精度（小数点后位数）进行四舍五入运算
     *
     * @param value
     * @param count
     * @return
     */
    public static double roundHalfUp(double value, int count) {
        double keepValue = BigDecimal.valueOf(value).setScale(count, RoundingMode.HALF_UP).doubleValue();
        return keepValue;
    }

    /**
     * 按指定精度（小数点后位数）进行四舍六入五成双运算
     *
     * @param value
     * @param count
     * @return
     */
    public static double roundHalfEven(double value, int count) {
        double keepValue = BigDecimal.valueOf(value).setScale(count, RoundingMode.HALF_EVEN).doubleValue();
        return keepValue;
    }

    /**
     * 按指定精度（小数点后位数）进行四舍六入运算
     *
     * @param value
     * @param count
     * @return
     */
    public static double roundHalfDown(double value, int count) {
        double keepValue = BigDecimal.valueOf(value).setScale(count, RoundingMode.HALF_DOWN).doubleValue();
        return keepValue;
    }

    /**
     * 按指定精度（小数点后位数）进行向上进位运算
     *
     * @param value
     * @param count
     * @return
     */
    public static double ceil(double value, int count) {
        double keepValue = BigDecimal.valueOf(value).setScale(count, RoundingMode.CEILING).doubleValue();
        return keepValue;
    }

    /**
     * 按指定精度（小数点后位数）进行向下舍位运算
     *
     * @param value
     * @param count
     * @return
     */
    public static double floor(double value, int count) {
        double keepValue = BigDecimal.valueOf(value).setScale(count, RoundingMode.FLOOR).doubleValue();
        return keepValue;
    }
}
