package com.lottery.common;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author fengqinyun 
 * */
public class PageBean<E> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -727738056175078953L;
	/** 开始记录行数FirstResult */
	private Integer pageIndex=1;
	/** 每页显示记录数maxResult */
	private Integer maxResult=15;
	/** 总记录数 */
	private Integer totalResult = 0;
	/** 查询结果集 */
	private List<E> list;
	/**
	 * 是否分页 ,默认为true分页 ,等于false时不分页
	 * */
	private boolean pageFlag=true;
	/**
	 * 分页是否需要 totalResult
	 * */
	private boolean totalFlag=true;


	public PageBean() {
		super();
	}
	
    
	public PageBean(Integer pageIndex, Integer maxResult, String orderBy,
			String orderDir) {
		super();
		this.pageIndex = pageIndex;
		this.maxResult = maxResult;
		this.orderBy = orderBy;
		this.orderDir = orderDir;
	}


	private Integer currentPage;
	public PageBean(int pageIndex,int maxResult) {
		super();
		this.pageIndex = pageIndex;
		this.maxResult = maxResult;
	}



	public PageBean(Integer pageIndex, Integer maxResult, Integer totalResult, List<E> list) {
		super();
		this.pageIndex = pageIndex;
		this.maxResult = maxResult;
		this.totalResult = totalResult;
	
		this.list = list;
	}
	
	public PageBean(Integer pageIndex, Integer maxResult, Integer totalResult, String orderBy, String orderDir,
			List<E> list) {
		super();
		this.pageIndex = pageIndex;
		this.maxResult = maxResult;
		this.totalResult = totalResult;
		this.list = list;
		this.orderBy = orderBy;
		this.orderDir = orderDir;
	}

    public String toString(){
    	StringBuffer sb=new StringBuffer();
    	sb.append("[pageIndex="+pageIndex);
    	sb.append(",maxResult="+maxResult);
    	sb.append(",totalResult="+totalResult);
    	sb.append(",currentPage="+currentPage);
    	sb.append("]");
    	return sb.toString();
    }
	/**o'r
	 * 当前页数
	 */
	public Integer getCurrentPageNo() {
		if (pageIndex == null || maxResult == null || maxResult == 0) {
			return 1;
		}
		return (pageIndex-1)/maxResult+1;
	}

	public Integer getPageIndex() {

		return null == pageIndex ? 1 : pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getTotalPage() {
		
		return totalResult % getMaxResult() == 0 ? totalResult / getMaxResult() : totalResult / getMaxResult() + 1;
	}

	public Integer getMaxResult() {
		return null == maxResult ? 15 : maxResult; 
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
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

	public void setTotalResult(Integer totalResult) {
		this.totalResult = totalResult;
	}

	
	public Integer getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		if (pageIndex == null || maxResult == null || maxResult == 0) {
			this.currentPage= 1;
		}else{
			this.currentPage=(pageIndex-1)/maxResult+1;
		}
	}

	public boolean isPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(boolean pageFlag) {
		this.pageFlag = pageFlag;
	}
	
	public boolean isTotalFlag() {
		return totalFlag;
	}


	public void setTotalFlag(boolean totalFlag) {
		this.totalFlag = totalFlag;
	}
	
	
	/**
	 * 
	 * 以下非通用
	 * */


	/** 排序字段名称 */
	private String orderBy = null;
	/** 排序顺序 */
	private String orderDir = null;
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderDir() {
		return orderDir;
	}
	public void setOrderDir(String orderDir) {
		String lowcaseOrderDir = StringUtils.lowerCase(orderDir);
		String[] orderDirs = StringUtils.split(lowcaseOrderDir, ',');
		for (String orderDirStr : orderDirs) {
			if (!Sort.DESC.equalsIgnoreCase(orderDirStr) && !Sort.ASC.equalsIgnoreCase(orderDirStr)) {
				throw new LotteryException(ErrorCode.Faile,"");
			}
		}
		this.orderDir = lowcaseOrderDir;
	}
	
	
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	public List<Sort> fetchSort() {
		List<Sort> orders = new ArrayList<Sort>();
		if(StringUtils.isBlank(orderBy) || StringUtils.isBlank(orderDir)){
			return orders;
		}
		String[] orderBys = StringUtils.split(orderBy, ',');
		String[] orderDirs = StringUtils.split(orderDir, ',');
		if (orderBys.length != orderDirs.length) {
			throw new LotteryException(ErrorCode.Faile,"");
		}

		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new Sort(orderBys[i], orderDirs[i]));
		}
		return orders;
	}

	public static class Sort {
		public static final String ASC = "asc";
		public static final String DESC = "desc";

		private final String property;
		private final String dir;

		public Sort(String property, String dir) {
			this.property = property;
			this.dir = dir;
		}

		public String getProperty() {
			return property;
		}

		public String getDir() {
			return dir;
		}
	}
	
	
}
