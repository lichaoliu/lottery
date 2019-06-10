/**
 * 
 */
package com.lottery.common.util;

import java.lang.reflect.Method;


public class CoreObjectUtils {
	public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws SecurityException, NoSuchMethodException {
		Method m;
		try {
			m = clazz.getDeclaredMethod(name, parameterTypes);
		} catch (SecurityException e) {
			m = null;
			throw e;
		} catch (NoSuchMethodException e) {
			m = null;
		}
		if (m == null) {
			if (clazz.equals(Object.class)) {
				throw new NoSuchMethodException("Method not found in any super class.");
			}
			return getMethod(clazz.getSuperclass(), name, parameterTypes);
		}
		return m;
	}
}
