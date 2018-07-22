package com.lottery.common.dao;

import com.lottery.common.PageBean;
import com.lottery.common.exception.LotteryException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractLotteryCommonDao<T extends Serializable, PK extends Serializable> implements ILotteryCommonDao<T, PK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SessionFactory sessionFactory;

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	@SuppressWarnings("unchecked")
	public AbstractLotteryCommonDao() {
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

	protected Session getSession() {
		if (getSessionFactory() == null) {
			throw new LotteryException("sessionFactory is null");
		}
		return getSessionFactory().getCurrentSession();
	}

	protected StatelessSession getStatelessSession() {
		if (getSessionFactory() == null) {
			throw new LotteryException("sessionFactory is null");
		}
		return sessionFactory.openStatelessSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// @Resource(name="sessionFactory")
	// public void setSessionFactory(SessionFactory sessionFactory) {
	// this.sessionFactory = sessionFactory;
	// }

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getWithLock(PK id, LockOptions lock) {

		T t = (T) getSession().get(entityClass, id, lock);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(PK id) {

		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T getWithLock(PK id) {
		T t = (T) getSession().get(entityClass, id, LockOptions.UPGRADE);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T loadWithLock(PK id, LockOptions lock) {
		T t = (T) getSession().load(entityClass, id, lock);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T save(T entity) {
		T t = (T) getSession().save(entity);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		getSession().flush();
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
		getSession().flush();
		getSession().clear();

	}

	@SuppressWarnings("unchecked")
	public T merge(T entity) {
		T t = (T) getSession().merge(entity);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	public List<T> findByQueryString(int max, String queryString){
		Query query=getSession().createSQLQuery(queryString);
		if (max>0){
            query.setMaxResults(max);
		}
		return  query.list();
	}
	public List<T> findByQueryString(String queryString){
		return this.findByQueryString(0, queryString);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findPageByQueryString(String queryString, PageBean<T> page, String orderByString) {
		List<T> list = new ArrayList<T>();
		if (StringUtils.isBlank(queryString)) {
			return list;
		}
		String countStr = queryString.replace("*", "count(*)");
		Query querycount = getSession().createSQLQuery(countStr);

		page.setTotalResult((Integer) querycount.uniqueResult());
		queryString= queryString+" "+ orderByString;
		Query query = getSession().createSQLQuery(queryString);
		if (page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		list = query.list();
		page.setList(list);
		return list;
	}

	@Override
	public List<T> findByCondition(Map<String, Object> map) {
		return this.findByCondition(0, map, null);
	}

	@Override
	public List<T> findByCondition(String whereString, Map<String, Object> map) {
		return this.findByCondition(0, whereString, map,null);
	}

	@Override
	public List<T> findByCondition(int max, String whereString, Map<String, Object> map) {
		return this.findByCondition(max,whereString,map,null);
	}

	@Override
	public List<T> findByCondition(int max, String whereString, Map<String, Object> map, String orderByString) {
		String whereSql = getWhereSql(whereString, orderByString);
		Query query = getSession().createQuery(whereSql);
		typedParam(query, map);
		if (max > 0) {
			query.setMaxResults(max);
		}
		return query.list();
	}

	@Override
	public List<T> findByCondition(String whereString, Map<String, Object> map, String orderByStrinng) {
		return this.findByCondition(0, whereString, map, orderByStrinng);
	}

	@Override
	public List<T> findPageByCondition(String whereString, Map<String, Object> map, PageBean<T> page, String orderByString) {

		if (page != null && page.isTotalFlag()) {
			page.setTotalResult(getTotal(whereString, map).intValue());

		}

		String whereSql = getWhereSql(whereString, orderByString);

		Query query = getSession().createQuery(whereSql);
		if (StringUtils.isNotBlank(whereString)) {
			typedParam(query, map);
		}

		if (page != null && page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());

		List<T> list = query.list();

		if (page != null)
			page.setList(list);
		return list;
	}

	@Override
	public List<T> findPageByCondition(String whereString, Map<String, Object> map, PageBean<T> page) {
		return this.findPageByCondition(whereString, map, page, null);
	}

	@Override
	public List<T> findByCondition(int max, Map<String, Object> map) {
		return this.findByCondition(max,map,null);
	}

	@Override
	public List<T> findByCondition(int max, Map<String, Object> map, String orderByString) {
		String queryString = getWhereSql(map, orderByString);

		Query query = getSession().createQuery(queryString);

		typedParam(query, map);

		if (max > 0)
			query.setMaxResults(max);

		return query.list();
	}

	@Override
	public List<T> findPageByCondition(Map<String, Object> map, PageBean<T> page, String orderByString) {
		if (page != null && page.isTotalFlag()) {

			Long total = getCountByCondition(map);
			page.setTotalResult(total.intValue());
		}
		String whereSql = getWhereSql(map, orderByString);

		Query query = getSession().createQuery(whereSql);
		typedParam(query, map);

		if (page != null && page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		List<T> list = query.list();
		if (page != null)
			page.setList(list);
		return list;
	}

	@Override
	public List<T> findPageByCondition(Map<String, Object> map, PageBean<T> page) {
		return this.findPageByCondition(map,page,null);
	}

	@Override
	public List<T> findByCondition(Map<String, Object> map, String orderByString) {
		return this.findByCondition(0, map, orderByString);
	}

	@Override
	public List<T> findByCondition(String queryString, Object[] condition) {
		return findByCondition(0, queryString, condition);
	}

	@Override
	public List<T> findByCondition(int max, String queryString, Object[] condition) {
		String whereSql = getWhereSql(queryString, null);
		Query query = getSession().createQuery(whereSql);
		typedParam(query, condition);
		if (max > 0) {
			query.setMaxResults(max);
		}
		return query.list();
	}

	@Override
	public List<T> findPageByCondition(String queryString, Object[] condition, PageBean<T> page, String orderByString) {
		if (page != null && page.isTotalFlag()) {
			page.setTotalResult(getTotal(queryString, condition).intValue());
		}

		String whereSql = getWhereSql(queryString, orderByString);

		Query query = getSession().createQuery(whereSql);
		if (StringUtils.isNotBlank(queryString)) {
			typedParam(query, condition);
		}

		if (page != null && page.isPageFlag())
			query.setFirstResult((page.getPageIndex() - 1) * page.getMaxResult()).setMaxResults(page.getMaxResult());
		List<T> list = query.list();
		if (page != null)
			page.setList(list);
		return list;
	}

	@Override
	public List<T> findPageByCondition(String queryString, Object[] condition, PageBean<T> page) {
		return this.findPageByCondition(queryString,condition,page,null);
	}

	@Override
	public int update(Map<String, Object> conditionMap, Map<String, Object> whereMap) {
		if (conditionMap == null) {
			return 0;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(getUpdateSetString(conditionMap));
		sb.append(getUpdateWhereString(whereMap));

		Query query = getSession().createQuery(sb.toString());

		typedParam(query, conditionMap);
		typedParam(query, whereMap);

		int i = query.executeUpdate();

		getSession().flush();

		return i;
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
		Query query = getSession().createQuery(sb.toString());

		typedParam(query, conditionMap);

		typedParam(query, whereMap);

		int i = query.executeUpdate();
		getSession().flush();

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
		Query query = getSession().createQuery(sb.toString());

		typedParam(query, conditionMap);

		typedParam(query, condition);

		int i = query.executeUpdate();
		getSession().flush();

		return i;
	}

	@Override
	public int update(String whereString, Object[] condition) {
		Query query = getSession().createQuery(whereString);
		typedParam(query, condition);
		int i = query.executeUpdate();

		getSession().flush();

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

		Query query = getSession().createQuery(str.toString());

		typedParam(query, whereMap);
		int i = query.executeUpdate();
		getSession().flush();
		return i;
	}

	@Override
	public int delete(String whereString, Map<String, Object> whereMap) {
		StringBuffer str = new StringBuffer();
		str.append("delete  ").append(this.entityClass.getName());
		if (StringUtils.isNotBlank(whereString)) {
			str.append(" where ").append(whereString);
		}
		Query query = getSession().createQuery(str.toString());

		typedParam(query, whereMap);
		int i = query.executeUpdate();
		getSession().flush();
		return i;
	}

	@Override
	public T findByUnique(Map<String, Object> map) {
		String queryString = getWhereSql(map, null);

		Query query = getSession().createQuery(queryString);

		typedParam(query, map);
		return (T) query.uniqueResult();
	}

	@Override
	public Long getCountByCondition(Map<String, Object> map) {
		return null;
	}

	@Override
	public Long getCountByCondition(String whereString, Map<String, Object> map) {
		return null;
	}

	@Override
	public boolean isContains(T entity) {
		return false;
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
	protected Long getTotal(String queryString, Map<String, Object> map) {

		StringBuffer str = new StringBuffer();
		str.append("select count(o) from ").append(this.entityClass.getName()).append(" o ");
		if (StringUtils.isNotBlank(queryString)) {
			str.append(" where ").append(queryString);
		}
		Query totalResult = getSession().createQuery(str.toString());

		if (StringUtils.isNotBlank(queryString) && map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null)
					totalResult.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return (Long) totalResult.uniqueResult();
	}
	protected Long getTotal(String queryString, Object[] condition) {
		StringBuffer str = new StringBuffer();
		str.append("select count(o) from ").append(this.entityClass.getName()).append(" o ");
		if (StringUtils.isNotBlank(queryString)) {
			str.append(" where ").append(queryString);
		}
		Query totalResult = getSession().createQuery(str.toString());
		if (StringUtils.isNotBlank(queryString) && condition != null) {
			for (int i = 0; i < condition.length; i++) {
				totalResult.setParameter(i + 1, condition[i]);
			}
		}
		return (Long) totalResult.uniqueResult();
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

}
