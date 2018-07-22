/**
 * 
 */
package com.lottery.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class PropertiesAdvancedFactoryBean {
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private Properties propertiesHolder;
	
	protected static final String DEFAULT_DELIMITER = ",";
	
	public List<Long> readToLongList(String key) {
		return readToLongList(key, DEFAULT_DELIMITER);
	}
	
	public List<String> readToStringList(String key) {
		return readToStringList(key, DEFAULT_DELIMITER);
	}
	
	public List<Long> readToLongList(String key, String delimiter) {
		List<Long> result = new ArrayList<Long>();
		String originalValue = propertiesHolder.getProperty(key);
		if (originalValue == null) {
			return result;
		}
		String[] splitedValues = StringUtils.split(originalValue, delimiter);
		for (String value : splitedValues) {
			try {
				Long longValue = Long.valueOf(value);
				result.add(longValue);
			} catch (NumberFormatException e) {
				logger.error("转换成Long类型出错, value={}", value);
			}
		}
		return result;
	}
	
	public List<String> readToStringList(String key, String delimiter) {
        List<String> result = new ArrayList<String>();
        String originalValue = propertiesHolder.getProperty(key);
        if (originalValue == null) {
            return result;
        }

        String propertyValue = CoreStringUtils.substringBetween(originalValue, "(", ")");
        if (StringUtils.isBlank(propertyValue)) {
            propertyValue = originalValue;
        }
        String[] splitedValues = StringUtils.split(propertyValue, delimiter);
        for (String value : splitedValues) {
            result.add(value);
        }
        return result;
	}

	public Properties getPropertiesHolder() {
		return propertiesHolder;
	}

	public void setPropertiesHolder(Properties propertiesHolder) {
		this.propertiesHolder = propertiesHolder;
	}
}
