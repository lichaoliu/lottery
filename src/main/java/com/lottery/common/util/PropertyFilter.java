package com.lottery.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;

/**
 * 查询条件工具类
 */
public class PropertyFilter {

	/** 多个属性间OR关系的分隔符. */
	public static final String OR_SEPARATOR = "_OR_";

	public static final String TABLE_SUFFIX = "@";

	/**
	 * 属性比较类型. EQ(=),LIKE(%%),LT(<),GT(>),LE(<=),GE(>=)
	 */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}

	/** 属性数据类型. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), G(
				BigDecimal.class);

		private Class<?> clazz;

		private PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private MatchType matchType = null;
	private Object matchValue = null;

	private Class<?> propertyClass = null;
	private String[] propertyNames = null;

	public PropertyFilter() {
	}

	/**
	 * @param filterName
	 *            比较属性字符串,含待比较的比较类型、属性值类型及属性列表. eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value
	 *            待比较的值.
	 */
	public PropertyFilter(final String filterName, final String value) {
		String firstPart = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
		String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		try {
			propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
		}
		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
		propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);
		this.matchValue = PropertyFilter.convertToObject(value, propertyClass);
	}

	public static String buildSqlFromMap(Map<String, Object> conditionMap) {
		List<PropertyFilter> list = buildFromMap(conditionMap);
		String sql = transfer2Sql(list, null);
		return sql;
	}

	public static List<PropertyFilter> buildFromMap(Map<String, Object> conditionMap) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		if (conditionMap.size() > 0) {
			// 分析参数Map,构造PropertyFilter列表
			for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
				String filterName = entry.getKey();
				String value = String.valueOf(entry.getValue());
				// 如果value值为空,则忽略此filter.
				if (StringUtils.isNotBlank(value)) {
					PropertyFilter filter = new PropertyFilter(filterName, value);
					filterList.add(filter);
				}
			}
		}
		return filterList;
	}

	public static String transfer2Sql(final List<PropertyFilter> filters, String model) {
		StringBuffer sql = new StringBuffer("");
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { // 只有一个属性需要比较的情况.
				String s = compose2Sql(filter.getPropertyName(), filter.getMatchType(), model);
				sql.append(" AND " + s);
			} else {// 包含多个属性需要比较的情况,进行or处理.
				sql.append(" AND (");
				for (String param : filter.getPropertyNames()) {
					sql.append(compose2Sql(param, filter.getMatchType(), model) + " OR ");
				}
				sql.delete(sql.length() - 3, sql.length());
				sql.append(") ");
			}
		}
		return sql.toString();
	}

	private static String compose2Sql(final String propertyName, final MatchType matchType, final String model) {
		String result = "";
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("The propertyName argument is required");
		}
		if (sql_inj(propertyName)) {
			throw new LotteryException(ErrorCode.Faile,"参数错误");
		}
		String pName = propertyName;
		String modelStr = " ";
		if (pName.contains("!")) {
			pName = pName.replace("!", ".");
		} else {
			if (!pName.contains(".")) {
				if (StringUtils.isNotBlank(model)) {
					modelStr = " " + model + ".";
				}
			}
		}
		String pNameAlias = pName;
		if (pNameAlias.indexOf(".") > -1) {
			pNameAlias = StringUtils.substringAfter(pNameAlias, ".");
		}
		switch (matchType) {
		case EQ:
			result = modelStr + pName + " = :" + pNameAlias + matchType.hashCode() + " ";
			break;
		case LIKE:
			result = modelStr + pName + " like :" + pNameAlias + matchType.hashCode() + " ";
			break;
		case LE:
			result = modelStr + pName + " <= :" + pNameAlias + matchType.hashCode() + " ";
			break;
		case LT:
			result = modelStr + pName + " < :" + pNameAlias + matchType.hashCode() + " ";
			break;
		case GE:
			result = modelStr + pName + " >= :" + pNameAlias + matchType.hashCode() + " ";
			break;
		case GT:
			result = " " + pName + " > :" + pNameAlias + matchType.hashCode() + " ";
			break;
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static TypedQuery setMatchValue2Query(TypedQuery query, List<PropertyFilter> filters) {
		if (filters != null) {
			for (PropertyFilter filter : filters) {
				if (!filter.hasMultiProperties()) { // 只有一个属性需要比较的情况.
					String pNameAlias = filter.getPropertyName();
					if (pNameAlias.indexOf("!") > -1) {
						pNameAlias = StringUtils.substringAfter(pNameAlias, "!");
					}
					if (filter.getMatchType().equals(PropertyFilter.MatchType.LIKE)) {
						String value = filter.getMatchValue().toString();
						if (value.charAt(0) != '%') {
							value = "%" + value;
						}
						if (value.charAt(value.length() - 1) != '%') {
							value = value + "%";
						}
						query.setParameter(pNameAlias + filter.getMatchType().hashCode(), value);
					} else {
						query.setParameter(pNameAlias + filter.getMatchType().hashCode(), filter.getMatchValue());
					}
				} else {// 包含多个属性需要比较的情况,进行or处理.
					for (String param : filter.getPropertyNames()) {
						String pNameAlias = param;
						if (pNameAlias.indexOf("!") > -1) {
							pNameAlias = StringUtils.substringAfter(pNameAlias, "!");
						}
						if (filter.getMatchType().equals(PropertyFilter.MatchType.LIKE)) {
							String value = filter.getMatchValue().toString();
							if (value.charAt(0) != '%') {
								value = "%" + value;
							}
							if (value.charAt(value.length() - 1) != '%') {
								value = value + "%";
							}
							query.setParameter(pNameAlias + filter.getMatchType().hashCode(), value);
						} else {
							query.setParameter(pNameAlias + filter.getMatchType().hashCode(), filter.getMatchValue());
						}
					}
				}
			}
		}
		return query;
	}

	public static Query setMatchValue2Query(Query query, List<PropertyFilter> filters) {
		if (filters != null) {
			for (PropertyFilter filter : filters) {
				if (!filter.hasMultiProperties()) { // 只有一个属性需要比较的情况.
					String pNameAlias = filter.getPropertyName();
					if (pNameAlias.indexOf("!") > -1) {
						pNameAlias = StringUtils.substringAfter(pNameAlias, "!");
					}
					if (filter.getMatchType().equals(PropertyFilter.MatchType.LIKE)) {
						String value = filter.getMatchValue().toString();
						if (value.charAt(0) != '%') {
							value = "%" + value;
						}
						if (value.charAt(value.length() - 1) != '%') {
							value = value + "%";
						}
						query.setParameter(pNameAlias + filter.getMatchType().hashCode(), value);
					} else {
						query.setParameter(pNameAlias + filter.getMatchType().hashCode(), filter.getMatchValue());
					}
				} else {// 包含多个属性需要比较的情况,进行or处理.
					for (String param : filter.getPropertyNames()) {
						String pNameAlias = param;
						if (pNameAlias.indexOf("!") > -1) {
							pNameAlias = StringUtils.substringAfter(pNameAlias, "!");
						}
						if (filter.getMatchType().equals(PropertyFilter.MatchType.LIKE)) {
							String value = filter.getMatchValue().toString();
							if (value.charAt(0) != '%') {
								value = "%" + value;
							}
							if (value.charAt(value.length() - 1) != '%') {
								value = value + "%";
							}
							query.setParameter(pNameAlias + filter.getMatchType().hashCode(), value);
						} else {
							query.setParameter(pNameAlias + filter.getMatchType().hashCode(), filter.getMatchValue());
						}
					}
				}
			}
		}
		return query;
	}

	/**
	 * Sql是否被注入非法字符
	 * 
	 * @param str
	 * @return true：被注入，false：没被注入
	 */
	public static boolean sql_inj(String str) {
		// TODO 防止注入验证
		return false;
	}

	/**
	 * 基于Apache BeanUtils转换字符串到相应类型.
	 * 
	 * @param value
	 *            待转换的字符串.
	 * @param toType
	 *            转换目标类型.
	 */
	public static Object convertToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw new LotteryException(ErrorCode.Faile,"转换错误");
		}
	}

	static {
		// 初始化日期格式为yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
		registerDateConverter("yyyy-MM-dd,yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 定义Apache BeanUtils日期Converter的格式,可注册多个格式,以','分隔
	 */
	public static void registerDateConverter(String patterns) {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(StringUtils.split(patterns, ","));
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	/**
	 * 获取比较方式.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * 获取比较值.
	 */
	public Object getMatchValue() {
		return matchValue;
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * 获取唯一的比较属性名称.
	 */
	public String getPropertyName() {
		Assert.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
		return propertyNames[0];
	}

	/**
	 * 是否比较多个属性.
	 */
	public boolean hasMultiProperties() {
		return (propertyNames.length > 1);
	}

}
