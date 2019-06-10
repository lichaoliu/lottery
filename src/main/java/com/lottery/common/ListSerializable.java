package com.lottery.common;

import java.io.Serializable;
import java.util.List;

public class ListSerializable<T> implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = -2272933449302899970L;

    private List<T> list;

	public List<T> getList() {
	    return list;
    }

	public void setList(List<T> list) {
	    this.list = list;
    }
    
}
