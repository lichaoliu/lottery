package com.lottery.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.lottery.common.AdminPage;
import com.lottery.common.PageBean;

public interface IGenericDAO<T extends Serializable, PK extends Serializable>
		extends Serializable {
	
	/**
	 * 

http://opensource.atlassian.com/projects/hibernate/browse/HHH-1538 

COUNT changed from Hibernate.INTEGER to Hibernate.LONG 
MAX/MIN no changes 
AVG changed from being dependent on the field to only return Hibernate.DOUBLE 
SUM changed from always just return the type of the field to return according to the above rules. 

hibernate 3.2的时候，AVG返回值类型是Hibernate.DOUBLE，你坚持一下你用的plya框架的

	 * 
	 * */
	
	/**
	 * Deadlock found when trying to get lock; try restarting transaction
     *出现这个原因要记住一点就是：innodb的行锁 和解锁都是针对主键索引的。如果查询时根据索引锁表，但更新时却不是通过主键更新
	 * */

	/**根据主键查询
	 * @param id 主键
	 * @return T
	 * */
	public T find(PK id) ;
	/**是否加锁根据主键查询
	 * @param id 主键
	 * @param lock  ture or false
	 * @return T
	 * */
	public T findWithLock(PK id, boolean lock);
	/**
	 * 无分页返回全部
	 * */
	public List<T> findAll() ;
	/**
	 * 更新实体
	 * @param entity 实体类
	 * */
	public void update(T entity) ;

	/**
	 * 保存实体
	 * @param entity 实体类
	 * */
	public void insert(T entity);
	
	/**
	 * 更新实体
	 * @param entity 实体类
	 * */
	public T merge(T entity);
	/**
	 * 清除缓存
	 * */
	public void clear();
	/**
	 * 刷新缓存
	 * */
	public void fush();

 

	/**
	 * 删除实体
	 * @param entity 实体类
	 * */
	public void remove(T entity);

	
	public void removeAll(Collection<T> entities);
	/**
	 * 使用原生sql语句查询
	 * @param queryString sql语句
	 * @return List<T>
	 * */
	public List<T> findByQueryString(String queryString);
	/**
	 * 使用原生sql语句查询
	 * @param queryString sql语句
	 * @param max 最大显示条数
	 * @return List<T>
	 * */
	public List<T> findByQueryString(int max,String queryString);
	/**
	 * 使用原生sql语句查询
	 * @param queryString sql语句
	 * @param page 分页
	 * @param  orderByString 排序条件
	 * @return List<T>
	 * */
	public List<T> findPageByQueryString(String queryString,PageBean<T> page,String orderByString);
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @return 
	 * */
	public List<T> findByCondition(Map<String,Object> map);
	
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param whereString 查询条件
	 * @return 
	 * */
	public List<T> findByCondition(String whereString,Map<String,Object> map);
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param whereString 查询条件
	 * @param max 每次最大数
	 * @return 
	 * */
	public List<T> findByCondition(int max,String whereString,Map<String,Object> map);
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param whereString 查询条件
	 * @param orderByString 排序字段
	 * @param max 每次最大数
	 * @return 
	 * */
	public List<T> findByCondition(int max,String whereString,Map<String,Object> map,String orderByString);
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param whereString 查询条件
	 * @param orderByString 排序字段
	 * @return 
	 * */
	public List<T> findByCondition(String whereString,Map<String,Object> map,String orderByString);
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param whereString 查询条件
	 * @param orderByString 排序
	 * @param page 分页
	 * @return 
	 * */
	public List<T> findPageByCondition(String whereString,Map<String,Object> map,PageBean<T> page,String orderByString);
	public List<T> findPageByCondition(String whereString,Map<String,Object> map,PageBean<T> page);
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param max 最大数
	 * @param map 
	 * @return 
	 * */
	public List<T> findByCondition(int max, Map<String,Object> map);
	
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param max 最大数
	 * @param map 
	 * @param orderByString 排序
	 * @return 
	 * */
	public List<T> findByCondition(int max, Map<String,Object> map,String orderByString) ;
	
	
	
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param page 分页
	 * @param orderByString 排序
	 * @return 
	 * */
	public List<T> findPageByCondition(Map<String,Object> map, PageBean<T> page,String orderByString);
	public List<T> findPageByCondition(Map<String,Object> map, PageBean<T> page);
	
	/**根据条件查询，String的值 一定是和类中属性的名称相同
	 * @param map 
	 * @param orderByString 排序
	 * @return 
	 * */
	public List<T> findByCondition(Map<String,Object> map, String orderByString);
	/**根据条件查询,数字占位
	 * 
	 * @param queryString  条件  如id=? and userName=?
	 * @param condition 值  new Object[1l,"keven"] 一定要和queryString的顺序一样
	 * @return 
	 * */
	public List<T> findByCondition(String queryString,Object[] condition);
	/**根据条件查询,数字占位
	 * 
	 * @param queryString  条件  如id=? and userName=?
	 * @param condition 值  new Object[1l,"keven"] 一定要和queryString的顺序一样
	 * @param max 一次最多取的条数
	 * @return 
	 * */
	public List<T> findByCondition(int max,String queryString,Object[] condition);
	/**根据条件查询,数字占位
	 * 
	 * @param queryString  条件  如id=? and userName=?
	 * @param condition 值  new Object[1l,"keven"] 一定要和queryString的顺序一样
	 * @param orderByString 排序
	 * @param  page 排序
	 * @return 
	 * */
	public List<T> findPageByCondition(String queryString,Object[] condition,PageBean<T> page,String orderByString);
	public List<T> findPageByCondition(String queryString,Object[] condition,PageBean<T> page);
	/**
	 * 查询条件condition，格式为json字符串。
		使用键值对的形式出现，下面以KEY和VALUE代表。例如
		condition={"EQG_displayState":"1","EQS_lotteryType":"1001"}
		VALUE中，为查询的值。
		KEY中，分为三部分：
		下划线前标示“匹配类型标识”+“字段类型标识”，下划线后标示“字段名称”。
		匹配类型有：EQ(=),LIKE(%%),LT(<),GT(>),LE(<=),GE(>=)
		匹配类型有：S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), G(
						BigDecimal.class)
		另外还提供了一种对于OR条件的查询 ，使用_OR_分隔字段（注意是大写）：
		例如查询有效战绩或无效战绩大于0的数据：
		condition={"GTG_a!effectiveAchievement_OR_a!ineffectiveAchievement", "0"}
		说明：“a!”，标示查询表的别名（需要和开发人员确认），另外使用“!”号作为而不是“.”。
	 * @param conditionMap
	 * @param page
	 */
	public void findPageByCondition(Map<String, Object> conditionMap, AdminPage<T> page);
	/**
	 * 根据条件修改  map的key一定要和类中属性名称相同
	 * @param contentMap 修改内容   String 修改项  Object 修改值
	 * @param whereMap 修改条件
	 * */
	public int update(Map<String,Object> contentMap,Map<String,Object> whereMap);
	/**
	 * 根据条件修改  map的key一定要和类中属性名称相同
	 * @param contentMap 修改内容   String 修改项  Object 修改值
	 * @param whereString 修改条件
	 * @param whereMap 条件内容
	 * */
	public int update(Map<String,Object> contentMap,String whereString,Map<String,Object> whereMap);
	
	/**
	 * 根据条件修改  map的key一定要和类中属性名称相同
	 * @param contentMap 修改内容   String 修改项  Object 修改值
	 * @param whereString 修改条件
	 * @param condition 修改值
	 * */
	public int update(Map<String,Object> contentMap,String whereString,Object[] condition);
	
	/**
	 * @param whereString 修改语句
	 * @param condition 条件内容
	 * */
	public int update(String whereString,Object[] condition);
	/**
	 * 根据条件删除，map的key一定要和类中属性名称相同
	 * @param whereMap 条件 
	 * */
	public int delete(Map<String,Object> whereMap);
	/**
	 * 根据条件删除，map的key一定要和类中属性名称相同
	 * @param whereString 条件
	 * @param whereMap 条件 内容
	 * */
	public int delete(String whereString,Map<String,Object> whereMap);
	/**根据条件查询一条数据
	 * service 方法里一定要抛出异常，不然会报javax.persistence.NoResultException: No entity found for query 
	 * */
	public T findByUnique(Map<String,Object> map);
	/**
	 * 查询总数
	 * @param map 参数
	 * */
	public Long getCountByCondition(Map<String,Object> map);
	
	/**
	 * 查询总数
	 * @param whereString 条件
	 * @param map 参数
	 * */
	public Long getCountByCondition(String whereString,Map<String,Object> map);
	/**
	 * 实体类是否受容器管理
	 * @param entity 实体类
	 * */
	public boolean isContains(T entity);
	
}
