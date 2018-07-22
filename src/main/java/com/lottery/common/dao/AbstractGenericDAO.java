package com.lottery.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang.StringUtils;

import com.lottery.common.AdminPage;
import com.lottery.common.PageBean;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.PropertyFilter;

/**
 * 公用数据库操作类
 * 
 * @author fengqinyun
 * */
public abstract class AbstractGenericDAO<T extends Serializable, PK extends Serializable>  implements IGenericDAO<T, PK> {
	@PersistenceContext
	protected EntityManager entityManager;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2392675958611907389L;

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	@SuppressWarnings({ "unchecked" })
	public AbstractGenericDAO() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
		if (this.entityClass == null) {
			throw new LotteryException("类初始化失败");
		}
		
	}

	@Override
	public T find(PK id) {
		T t=entityManager.find(entityClass, id);
		return t;
	}

	@Override
	public T findWithLock(PK id, boolean lock) {

		T t=entityManager.find(entityClass, id, lock ? LockModeType.PESSIMISTIC_WRITE : LockModeType.NONE);
		if(t!=null)
			entityManager.flush();
		return t;
	}

	@Override
	public List<T> findAll() {
         
		return entityManager.createQuery("from " + this.entityClass.getName(), this.entityClass).getResultList();
	}

	/**
	 * clear(),调用Clear() 方法，可以强制清除Session缓存。
	 * 在处理大量实体的时候，如果你不把已经处理过的实体从EntityManager中分离出来，将会消耗你大量的内存。 调用EntityManager
	 * 的clear()方法后，所有正在被管理的实体将会从持久化内容中分离出来。有一点需要说明下，
	 * 在事务没有提交前（事务默认在调用堆栈的最后提交，如：方法的返回），如果调用clear()方法，
	 * 之前对实体所作的任何改变将会掉失，所以建议你在调用clear()方法之前先调用flush()方法保存更改。
	 * */
	@Override
	public void update(T entity) {
		entityManager.merge(entity);
		entityManager.flush();

	}

	@Override
	public void insert(T entity) {
		entityManager.persist(entity);
		entityManager.flush();

	}

	// 存储实体
	@Override
	public T merge(T entity) {
		T t = entityManager.merge(entity);
		entityManager.flush();
		return t;
	}

	// 删除指定的实体
	@Override
	public void remove(T entity) {
		entityManager.remove(entity);
		entityManager.flush();

	}

	// 删除集合中的全部实体
	@Override
	public void removeAll(Collection<T> entities) {
		for(T t:entities){
			this.remove(t);
		}
	}


	@Override
	public List<T> findByQueryString(String queryString) {
		
		return findByQueryString(0,queryString);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByQueryString(int max, String queryString) {
		if (StringUtils.isBlank(queryString)) {
			return new ArrayList<T>();
		}
		Query query= entityManager.createNativeQuery(queryString, this.entityClass);
		if(max>0){
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findPageByQueryString(String queryString, PageBean<T> page, String orderByString) {
		List<T> list = new ArrayList<T>();
		if (StringUtils.isBlank(queryString)) {
			return list;
		}
        if (page.isTotalFlag()){
			String countStr = queryString.replace("*", "count(*)");
			Query querycount = entityManager.createNativeQuery(countStr);
			BigDecimal count = (BigDecimal) querycount.getSingleResult();
			page.setTotalResult(count.intValue());
		}

		if (StringUtils.isNotBlank(orderByString)) {
			queryString += " " + orderByString;
		}
		Query query = entityManager.createNativeQuery(queryString, this.entityClass);
		if (page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		list = query.getResultList();
		page.setList(list);
		return list;
	}

	public List<T> findByCondition(int max, String whereString, Map<String, Object> map) {
		return this.findByCondition(max, whereString, map, null);
	}

	protected String getWhereSql(Map<String, Object> map, String orderByString) {
		StringBuffer condition = new StringBuffer();
		condition.append("from ").append(this.entityClass.getName());
		if (map == null)
			return condition.toString();
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null)
				sb.append(" and ").append(entry.getKey()).append("=:").append(entry.getKey());
		}
		String str = sb.toString().trim();
		if (str.length() > 3) {
			str = str.trim().substring(3);
			condition.append(" where").append(str);
		}
		if (StringUtils.isNotBlank(orderByString)) {
			condition.append(" ").append(orderByString);
		}
		return condition.toString();
	}

	protected String getWhereSql(String whereString, String orderByString) {
		StringBuffer condition = new StringBuffer();
		condition.append("from ").append(this.entityClass.getName());

		if (StringUtils.isNotBlank(whereString))
			condition.append(" where ").append(whereString);

		if (StringUtils.isNotBlank(orderByString))
			condition.append(" ").append(orderByString);

		return condition.toString();
	}

	protected void typedParam(Query query, Map<String, Object> map) {
		if (map == null)
			return;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null)
				query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	protected void typedParam(Query query, Object[] condition) {
		if (condition == null)
			return;
		for (int i = 0; i < condition.length; i++) {
			query.setParameter(i + 1, condition[i]);
		}
	}

	public List<T> findByCondition(int max, String whereString, Map<String, Object> map, String orderByString) {
		String whereSql = getWhereSql(whereString, orderByString);
		TypedQuery<T> query = entityManager.createQuery(whereSql, this.entityClass);
		typedParam(query, map);
		if (max > 0) {
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	public List<T> findByCondition(String whereString, Map<String, Object> map, String orderByStrinng) {
		return this.findByCondition(0, whereString, map, orderByStrinng);
	}

	public List<T> findByCondition(String whereString, Map<String, Object> map) {

		return findByCondition(0, whereString, map);
	}

	@Override
	public List<T> findByCondition(int max, Map<String, Object> map, String orderByString) {
		String queryString = getWhereSql(map, orderByString);

		TypedQuery<T> query = entityManager.createQuery(queryString, this.entityClass);

		typedParam(query, map);

		if (max > 0)
			query.setMaxResults(max);

		return query.getResultList();
	}

	@Override
	public List<T> findByCondition(int max, Map<String, Object> map) {
		return this.findByCondition(max, map, null);
	}

	@Override
	public List<T> findByCondition(Map<String, Object> map) {
		return this.findByCondition(0, map, null);
	}

	@Override
	public T findByUnique(Map<String, Object> map) {
		String queryString = getWhereSql(map, null);
		TypedQuery<T> query = entityManager.createQuery(queryString, this.entityClass);
		typedParam(query, map);
		return query.getSingleResult();
	}

	@Override
	public List<T> findByCondition(Map<String, Object> map, String orderByString) {
		return this.findByCondition(0, map, orderByString);
	}

	@Override
	public List<T> findPageByCondition(String whereString, Map<String, Object> map, PageBean<T> page, String orderByString) {

		if (page != null && page.isTotalFlag()) {
			page.setTotalResult(getTotal(whereString, map).intValue());

		}

		String whereSql = getWhereSql(whereString, orderByString);

		TypedQuery<T> query = entityManager.createQuery(whereSql, this.entityClass);
		if (StringUtils.isNotBlank(whereString)) {
			typedParam(query, map);
		}

		if (page != null && page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());

		List<T> list = query.getResultList();

		if (page != null)
			page.setList(list);
		return list;
	}

	@Override
	public List<T> findPageByCondition(Map<String, Object> map, PageBean<T> page, String orderByString) {

		if (page != null && page.isTotalFlag()) {

			Long total = getCountByCondition(map);
			page.setTotalResult(total.intValue());
		}
		String whereSql = getWhereSql(map, orderByString);

		TypedQuery<T> query = entityManager.createQuery(whereSql, this.entityClass);
		typedParam(query, map);

		if (page != null && page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		List<T> list = query.getResultList();
		if (page != null)
			page.setList(list);
		return list;
	}

	@Override
	public List<T> findByCondition(String queryString, Object[] condition) {
		return findByCondition(0, queryString, condition);
	}

	public List<T> findByCondition(int max, String queryString, Object[] condition) {

		String whereSql = getWhereSql(queryString, null);
		TypedQuery<T> query = entityManager.createQuery(whereSql, this.entityClass);
		typedParam(query, condition);
		if (max > 0) {
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findPageByCondition(String queryString, Object[] condition, PageBean<T> page, String orderByString) {

		if (page != null && page.isTotalFlag()) {
			page.setTotalResult(getTotal(queryString, condition).intValue());
		}

		String whereSql = getWhereSql(queryString, orderByString);

		TypedQuery<T> query = entityManager.createQuery(whereSql, this.entityClass);
		if (StringUtils.isNotBlank(queryString)) {
			typedParam(query, condition);
		}

		if (page != null && page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		List<T> list = query.getResultList();
		if (page != null)
			page.setList(list);
		return list;
	}

	protected Long getTotal(String queryString, Object[] condition) {
		StringBuffer str = new StringBuffer();
		str.append("select count(o) from ").append(this.entityClass.getName()).append(" o ");
		if (StringUtils.isNotBlank(queryString)) {
			str.append(" where ").append(queryString);
		}
		TypedQuery<Long> totalResult = entityManager.createQuery(str.toString(), Long.class);
		if (StringUtils.isNotBlank(queryString) && condition != null) {
			for (int i = 0; i < condition.length; i++) {
				totalResult.setParameter(i + 1, condition[i]);
			}
		}
		return totalResult.getSingleResult();
	}

	protected Long getTotal(String queryString, Map<String, Object> map) {

		StringBuffer str = new StringBuffer();
		str.append("select count(o) from ").append(this.entityClass.getName()).append(" o ");
		if (StringUtils.isNotBlank(queryString)) {
			str.append(" where ").append(queryString);
		}
		TypedQuery<Long> totalResult = entityManager.createQuery(str.toString(), Long.class);

		if (StringUtils.isNotBlank(queryString) && map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null)
					totalResult.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return totalResult.getSingleResult();
	}

	@Override
	public int update(Map<String, Object> conditionMap, Map<String, Object> whereMap) {
		if (conditionMap == null) {
			return 0;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(getUpdateSetString(conditionMap));
		sb.append(getUpdateWhereString(whereMap));

		Query query = entityManager.createQuery(sb.toString());

		typedParam(query, conditionMap);
		typedParam(query, whereMap);

		int i = query.executeUpdate();

		entityManager.flush();

		return i;
	}

	@Override
	public int update(String whereString, Object[] condition) {
		Query query = entityManager.createQuery(whereString);
		typedParam(query, condition);
		int i = query.executeUpdate();

		entityManager.flush();

		return i;
	}

	protected String getUpdateWhereString(Map<String, Object> whereMap) {
		StringBuffer whereSb = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : whereMap.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null)
				sb.append(" and ").append(entry.getKey()).append("=:").append(entry.getKey());
		}
		String str = sb.toString().trim();
		if (str.length() > 3) {
			str = str.trim().substring(3);
			whereSb.append(" where").append(str);
		}

		return whereSb.toString();
	}

	protected String getUpdateSetString(Map<String, Object> conditionMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("update ").append(this.entityClass.getName()).append(" set ");
		StringBuffer str = new StringBuffer();
		for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null)
				str.append(entry.getKey()).append("=:").append(entry.getKey()).append(",");
		}
		if (str.charAt(str.length() - 1) == ',') {
			str.deleteCharAt(str.length() - 1);
		}
		sb.append(str.toString());
		return sb.toString();
	}

	@Override
	public int update(Map<String, Object> conditionMap, String whereString, Map<String, Object> whereMap) {
		if (conditionMap == null) {
			return 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getUpdateSetString(conditionMap));

		if (StringUtils.isNotBlank(whereString)) {
			sb.append(" where ").append(whereString);
		}
		Query query = entityManager.createQuery(sb.toString());

		typedParam(query, conditionMap);

		typedParam(query, whereMap);

		int i = query.executeUpdate();
		entityManager.flush();

		return i;
	}

	@Override
	public int update(Map<String, Object> conditionMap, String whereString, Object[] condition) {
		if (conditionMap == null) {
			return 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getUpdateSetString(conditionMap));

		if (StringUtils.isNotBlank(whereString)) {
			sb.append(" where ").append(whereString);
		}
		Query query = entityManager.createQuery(sb.toString());

		typedParam(query, conditionMap);

		typedParam(query, condition);

		int i = query.executeUpdate();
		entityManager.flush();

		return i;
	}

	@Override
	public int delete(Map<String, Object> whereMap) {

		StringBuffer str = new StringBuffer();
		str.append("delete  ").append(this.entityClass.getName());

		StringBuffer inStr = new StringBuffer();
		for (Map.Entry<String, Object> entry : whereMap.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null)
				inStr.append(" and ").append(entry.getKey()).append("=:").append(entry.getKey());
		}
		String strS = inStr.toString().trim();

		if (str.length() > 3) {
			strS = strS.trim().substring(3);
			str.append(" where").append(strS);
		}

		Query query = entityManager.createQuery(str.toString());

		typedParam(query, whereMap);
		int i = query.executeUpdate();
		entityManager.flush();
		return i;
	}

	@Override
	public int delete(String whereString, Map<String, Object> whereMap) {
		StringBuffer str = new StringBuffer();
		str.append("delete  ").append(this.entityClass.getName());
		if (StringUtils.isNotBlank(whereString)) {
			str.append(" where ").append(whereString);
		}
		Query query = entityManager.createQuery(str.toString());

		typedParam(query, whereMap);
		int i = query.executeUpdate();
		entityManager.flush();
		return i;
	}

	@Override
	public boolean isContains(T entity) {
		return entityManager.contains(entity);
	}

	/**
	 * order by distance CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
	 * Root<Pet> pet = cq.from(Pet.class); cq.where(cb.equal(pet.get(Pet_.name),
	 * "Fido")); cq.where(cb.gt(pet.get(Pet_.birthday), date));
	 * cq.where(cb.between(pet.get(Pet_.birthday), firstDate, secondDate));
	 * cq.where(cb.equal(pet.get(Pet_.name),
	 * "Fido").and(cb.equal(pet.get(Pet_.color), "brown")));
	 * cq.groupBy(pet.get(Pet_.color));
	 * cq.having(cb.in(pet.get(Pet_.color)).value("brown").value("blonde"));
	 * 参考:http://my.oschina.net/zhaoqian/blog/133500
	 * */
	protected CriteriaBuilder getCriteriaBuilder() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		/*
		 * CriteriaQuery<T> cq=cb.createQuery(entityClass); Root<T>
		 * root=cq.from(entityClass); Expression
		 * e=null;//employee.get(Employee_.age)
		 */
		return cb;
	}

	protected EntityType<T> getEntityType() {
		return entityManager.getMetamodel().entity(this.entityClass);
	}

	public Long getCountByCondition(Map<String, Object> map) {

		if (map == null || map.isEmpty()) {
			return 0L;
		}

		StringBuffer sb = new StringBuffer();

		StringBuffer condition = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()))
				condition.append(" and ").append(entry.getKey()).append("=:").append(entry.getKey());
		}
		String str = condition.toString();

		if (str.length() > 3) {
			str = str.trim().substring(3);
			sb.append(" where").append(str);
		}

		String queryString = "select count(o) from " + this.entityClass.getName() + " o " + sb.toString();

		TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null)
				query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getSingleResult();
	}

	public Long getCountByCondition(String whereString, Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return 0L;
		}
		if (StringUtils.isBlank(whereString)) {
			return 0L;
		}

		StringBuffer count = new StringBuffer();
		count.append("select count(o) from ").append(this.entityClass.getName());
		count.append(" o where ").append(whereString);
		TypedQuery<Long> queryCount = entityManager.createQuery(count.toString(), Long.class);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			queryCount.setParameter(entry.getKey(), entry.getValue());
		}
		return queryCount.getSingleResult();
	}

	@Override
	public List<T> findPageByCondition(String whereString, Map<String, Object> map, PageBean<T> page) {
		return this.findPageByCondition(whereString, map, page, null);
	}

	@Override
	public List<T> findPageByCondition(Map<String, Object> map, PageBean<T> page) {
		return this.findPageByCondition(map, page, null);
	}

	@Override
	public List<T> findPageByCondition(String queryString, Object[] condition, PageBean<T> page) {
		return this.findPageByCondition(queryString, condition, page, null);
	}

	@Override
	public void clear() {
		/**
		 * 调用此方法注意：
		 * 在事务没有提交前（事务默认在调用堆栈的最后提交，如：方法的返回），如果调用clear()方法，之前对实体所作的任何改变将会掉失
		 * 会出现错误：java.lang.IllegalArgumentException: Removing a detached
		 * instance
		 * 
		 * */
		this.entityManager.clear();
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void fush() {
		this.entityManager.flush();
	}

	@Override
	public void findPageByCondition(Map<String, Object> conditionMap, AdminPage<T> page) {
		String sql = "SELECT o FROM " + this.entityClass.getName() + " o ";
		String countSql = "SELECT count(*) FROM " + this.entityClass.getName() + " o ";
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
		List<PropertyFilter> pfList = null;
		if (conditionMap != null && conditionMap.size() > 0) {
			pfList = PropertyFilter.buildFromMap(conditionMap);
			whereSql.append(PropertyFilter.transfer2Sql(pfList, "o"));
		}
		TypedQuery<T> q = this.entityManager.createQuery(sql + whereSql.toString() + page.getOrderby(), this.entityClass);
		TypedQuery<Long> total = this.entityManager.createQuery(countSql + whereSql.toString(), Long.class);
		if (conditionMap != null && conditionMap.size() > 0) {
			PropertyFilter.setMatchValue2Query(q, pfList);
			PropertyFilter.setMatchValue2Query(total, pfList);
		}
		List<T> resultList = q.setFirstResult(page.getStart()).setMaxResults(page.getLimit()).getResultList();
		page.setList(resultList);
		page.setTotalResult(total.getSingleResult().intValue());
	}
}
