package com.lottery.common;

import java.util.List;
/**
 * */
public class AdminPage<E> {

	private Integer start = 0;
	private Integer limit = 30;
	private List<E> list;
	private Integer totalResult;
	private String orderby = "";
	
	public AdminPage() {
		super();
	}

	public AdminPage(Integer start, Integer limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	public AdminPage(Integer start, Integer limit, String orderby) {
		super();
		this.start = start;
		this.limit = limit;
		this.orderby = orderby;
	}

	public AdminPage(List<E> list, Integer totalResult) {
		super();
		this.list = list;
		this.totalResult = totalResult;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setTotalResult(Integer totalResult) {
		this.totalResult = totalResult;
	}

	public List<E> getList() {
		return list;
	}
	
	public void setList(List<E> list) {
		this.list = list;
	}

	public Integer getTotalResult() {
		return totalResult;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
}
