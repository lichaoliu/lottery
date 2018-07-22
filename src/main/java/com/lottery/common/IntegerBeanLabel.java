package com.lottery.common;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class IntegerBeanLabel implements Serializable {

	private static final long serialVersionUID = -1573317468532371095L;
	protected static Map<String, Map<String, IntegerBeanLabel>> nameMap = new HashMap<String, Map<String, IntegerBeanLabel>>(16);
	protected static Map<String, Map<Integer, IntegerBeanLabel>> valueMap = new HashMap<String, Map<Integer, IntegerBeanLabel>>(16);
    
    protected int value;
    protected transient String name;
    protected String className;

    protected IntegerBeanLabel(String className,String name, int value) {
    	this.className = className;
        this.value = value;
        this.name = name;
        add();
    }

    protected void add() {
    	Map<String, IntegerBeanLabel> nameMaps = null;
    	Map<Integer, IntegerBeanLabel> valueMaps = null;
    	if (!nameMap.containsKey(this.className)) {
    		nameMaps = new HashMap<String, IntegerBeanLabel>();
			valueMaps = new HashMap<Integer, IntegerBeanLabel>();
			nameMap.put(this.className, nameMaps);
			valueMap.put(this.className, valueMaps);
    	} else {
    		nameMaps = nameMap.get(this.className);
    		valueMaps = valueMap.get(this.className);
    	}
    	
    	if (valueMaps.containsKey(new Integer(this.value))) {
    		throw new RuntimeException("存在已重复的值，请检查，value=" + this.value);
    	}
    	nameMaps.put(this.name, this);
    	valueMaps.put(new Integer(this.value), this);
    }

	public static IntegerBeanLabel get(String className, String name) {
		return nameMap.get(className).get(name);
	}

	public static IntegerBeanLabel get(String className, int value) {
		return valueMap.get(className).get(new Integer(value));
	}

	public static boolean contain(String className, int value) {
		return valueMap.get(className).containsKey(new Integer(value));
	}
	
	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	protected Object readResolve() throws ObjectStreamException {
		return get(this.className, this.value);
	}

}
