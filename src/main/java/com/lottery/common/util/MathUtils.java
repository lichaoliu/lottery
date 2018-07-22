package com.lottery.common.util;
/**
 * 
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 公用数学算法
 * @author fengqinyun
 *
 */
public class MathUtils {

    /**
     * 排列算法，P(n, m)
     *
     * @param n
     * @param m
     * @return
     */
    public static long permute(int n, int m) {
        long num = 1;
        for (int i = 0; i < m; i++) {
            num *= (n - i);
        }
        return num;
    }

    /**
     * 组合算法，C(n, m) = P(n, m) / P(m, m)
     *
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
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
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
     * @param v1 被乘数
     * @param v2 乘数
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
    
    
    
    /**
	 * 组合的递归算法
	 * @param a 原始数据
	 * @param n 原始数据个数
	 * @param m 选择数据个数
	 * @param b 存放被选择的数据
	 * @param M 常量，选择数据个数
	 * @param list 存放计算结果
	 */
	public static void combine(int a[], int n, int m, int b[], final int M, List<int[]> list) {
		for (int i = n; i >= m; i--) {
			b[m - 1] = i - 1;
			if (m > 1)
				combine(a, i - 1, m - 1, b, M, list);
			else {
				int[] result = new int[M];
				for (int j = M - 1; j >= 0; j--) {
					result[j] = a[b[j]];
				}
				list.add(result);
			}
		}
	}
	
	
	/**
	 * 输出全部组合数
	 * @param betcodes  被选择的数据
	 * @param select  选择的个数
	 * @return
	 */
	public static List<List<String>> getCodeCollection(List<String> betcodes,
			int select) {
		// 初始化原始数据
		int[] a = new int[betcodes.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		// 接收数据
		int[] b = new int[select];

		List<int[]> list = new ArrayList<int[]>();

		// 进行组合
		combine(a, a.length, select, b, select, list);

		// 返回数据对象
		List<List<String>> reList = new ArrayList<List<String>>();
		for (int[] result : list) {
			List<String> codeList = new ArrayList<String>();
			for (int p : result) {
				codeList.add(betcodes.get(p));
			}
			reList.add(codeList);
		}

		return reList;
	}
	
	
	/**
	 * 输出全部组合数
	 * @param <T>
	 * @param betcodes  被选择的数据
	 * @param select  选择的个数
	 * @return
	 */
	public static <T> List<List<T>> getCodeCollectionE(List<T> betcodes,
			int select) {
		// 初始化原始数据
		int[] a = new int[betcodes.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		// 接收数据
		int[] b = new int[select];

		List<int[]> list = new ArrayList<int[]>();

		// 进行组合
		combine(a, a.length, select, b, select, list);

		// 返回数据对象
		List<List<T>> reList = new ArrayList<List<T>>();
		for (int[] result : list) {
			List<T> codeList = new ArrayList<T>();
			for (int p : result) {
				codeList.add(betcodes.get(p));
			}
			reList.add(codeList);
		}

		return reList;
	}
	
	
	/**
	 * 阶乘
	 * @param d
	 * @param z
	 * @return
	 */
	public static long exp(long d, long z) {
		long result = 1L;
		for (int i = 0; i < z; i++) {
			result = result * d;
		}
		return result;
	}
	
	
	public static long getFoueSixInfive(String valueOf) {
		if (valueOf.indexOf(".") == -1) {
			return Long.valueOf(valueOf) * 100;
		} else {
			String xiaoshu = valueOf.substring(valueOf.indexOf(".") + 1);
			if (xiaoshu.length() >= 3) {
				valueOf = valueOf.substring(0, valueOf.indexOf(".") + 4);
				xiaoshu = valueOf.substring(valueOf.indexOf(".") + 1);
				int c = Integer.parseInt(xiaoshu.substring(2, 3));
				if (c < 5) {
					return new BigDecimal(valueOf.substring(0,
							valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).longValue();
				} else if (c > 5) {
					return new BigDecimal(valueOf.substring(0,
							valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).add(BigDecimal.ONE).longValue();
				} else {
					if (Integer.parseInt(xiaoshu.substring(1, 2)) % 2 == 0) {
						return new BigDecimal(valueOf.substring(0,
								valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).longValue();
					} else {
						return new BigDecimal(valueOf.substring(0,
								valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).add(BigDecimal.ONE).longValue();
					}
				}
			} else {
				return new BigDecimal(valueOf).multiply(new BigDecimal(100)).longValue();
			}

		}
	}
}
