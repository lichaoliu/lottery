/**
 * 
 */
package com.lottery.common;


public class IntegerBeanLabelItem extends IntegerBeanLabel {
	
	private static final long serialVersionUID = -769945821003341103L;


	protected IntegerBeanLabelItem(String className, String name, int value) {
		super(className, name, value);
	}

	public static IntegerBeanLabelItem getResult(String className, int value) {
		IntegerBeanLabelItem result = (IntegerBeanLabelItem)get(className, value);
		if (result == null) {
			throw new RuntimeException("Result for Value = " + value + " not defind");
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[name: ").append(this.getName()).append(", value: ").append(this.getValue()).append("]");
		return sb.toString();
	}

	
}
