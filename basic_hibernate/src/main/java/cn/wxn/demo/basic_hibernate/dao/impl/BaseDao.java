package cn.wxn.demo.basic_hibernate.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import cn.wxn.demo.basic_hibernate.dao.IBaseDao;
import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.basic_hibernate.model.SystemContext;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @Inject是jsr330 引入的注解
	 * @param sessionFactory
	 */
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Class<T> clazz;

	/**
	 * 获取泛型对象 
	 * @return
	 */
	public Class<T> getClazz() {
		if (clazz == null) {
			clazz = (Class<T>) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
					.getActualTypeArguments()[0];
		}
		return clazz;
	}

	////////////////////////////////////////////////////////////////////////////////

	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}
	
	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public void delete(Integer id) {
		getSession().delete(this.load(id));
	} 
	

	@Override
	public T load(Integer id) {
		Session session = getSession();
		Class<T> clazz2 = getClazz();
		T t = (T) session.load(clazz2, id);
		return t;
	}
	
////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<T> list(String hql, Object[] args) {
		return list(hql, args, null);
	}

	@Override
	public List<T> list(String hql, Object args) {
		return list(hql, new Object[]{args}, null);
	}
 
	@Override
	public List<T> list(String hql) {
		return list(hql);
	}
	
	@Override
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = setOrderAndSort(hql);        //排序规则，顺序倒序
		Query query = getSession().createQuery(hql); //查询语句
		setAlias(alias, query);//别名查询
		setParameter(args, query);//普通查询
		return query.list();
	}

	/**
	 * 设置query的hql或者sql参数
	 * @param args
	 * @param query
	 */
	private void setParameter(Object[] args, Query query) {
		if (args != null && args.length > 0) {
			int index = 0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
	}

	/**
	 * 对于查询对象，设置其 基于别名的设置
	 * @param alias
	 * @param query
	 */
	private void setAlias(Map<String, Object> alias, Query query) {
		if (alias != null) {
			Set<String> keySet = alias.keySet();
			for (String key :keySet) {
				Object object = alias.get(key);
				if (object  instanceof Collection) {
					// 查询条件是列表
					query.setParameterList(key, (Collection)object);
				} else {
					query.setParameter(key, object);
				}
			}
		}
	}
	
	/**
	 * 向hql中填充 排序方式以及是倒序还是顺序
	 * @param hql
	 * @return
	 */
	private String setOrderAndSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort != null && !"".equals(sort.trim())) {
			hql += " order by " + sort;
			if (!"desc".equals(order)) {
				hql += " asc";
			} else {
				hql += " desc";
			}
		}
		return hql;
	}

	@Override
	public List<T> list(String hql, Map<String, Object> alias) {
		return list(hql, null, alias);
	}

////////////////////////////////////////////////////////////////////////////////

	@Override
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	@Override
	public Pager<T> find(String hql, Object args) {
		return this.find(hql, new Object[]{args});
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql, null,null);
	}
 
	/**
	 * 分页查找
	 */
	@Override
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		Pager<T> pager = new Pager<T>();

		String hqlCount = getCountHql(hql,true);
		
		hql = setOrderAndSort(hql);
		Query query = getSession().createQuery(hql);  //查询分页数据
		setPagers(query, pager);
		setAlias(alias, query);
		setParameter(args, query);
		List<T> list = query.list();
		
		//查询总条目数
		Query query2 = getSession().createQuery(hqlCount);
		setAlias(alias, query2);
		setParameter(args, query2);
		Long count  = (Long) query2.uniqueResult();

		//拼装最终结果
		pager.setDatas(list);
		pager.setCountOfItems(count);
		return pager;
	}
	
	/**
	 * 获取一个hql对应的查询条目数的hql
	 * @param hql
	 * @return
	 */
	private String getCountHql(String hql, boolean isHql){
		String retVal =  "select count(*) " + hql.substring(hql.indexOf("from"));
		if(isHql){
			retVal = retVal.replaceAll("fetch", "");
		}
		return retVal;
	}
	
	/**
	 * 对Query对象设置分页相关参数
	 * 同时对Pager对象也设置相应的数据
	 * @param query
	 */
	private void setPagers(Query query, Pager pager) {
		Integer itemCountOfAPage = SystemContext.getItemCountOfAPage();
		Integer pageOffset = SystemContext.getPageOffset();
		if(itemCountOfAPage == null || itemCountOfAPage < 0) itemCountOfAPage = 5;
		if(pageOffset == null || pageOffset < 0) pageOffset = 0;
		
		query.setFirstResult(pageOffset).setMaxResults(itemCountOfAPage);
		
		if(pager != null){
			pager.setIndexOfPage(pageOffset);
			pager.setItemsCountOfAPage(itemCountOfAPage);
		}
	}

	@Override
	public Pager<T> find(String hql, Map<String, Object> alias) {
		return this.find(hql,null, alias);
	}

////////////////////////////////////////////////////////////////////////////////

	/**
	 * 查询一个对象
	 */
	@Override
	public Object queryObject(String hql, Object[] args, Map<String, Object> alias) {
		Query createQuery = getSession().createQuery(hql);
		setAlias(alias,createQuery);
		setParameter(args, createQuery);
		return createQuery.uniqueResult();
	}

	@Override
	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql, args, null);
	}

	@Override
	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql,new Object[]{arg},null);
	}

	@Override
	public Object queryObject(String hql) {
		return this.queryObject(hql,null,null);
	}

	@Override
	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql,null,alias);
	}
 
////////////////////////////////////////////////////////////////////////////////

	/**
	 * 通过hql进行更新
	 */
	@Override
	public void updateByHql(String hql, Object[] args) {
		Query createQuery = getSession().createQuery(hql);
		setParameter(args, createQuery);
		createQuery.executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[]{arg});
	}
	
	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}

////////////////////////////////////////////////////////////////////////////////

	@Override
	public   <N extends Object> List<N>  listBySql(String sql, Object[] args, Map<String, Object> alias, Class<? extends Object> clazz, boolean hasEntity) {
		sql = setOrderAndSort(sql);
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);

		setAlias(alias, sqlQuery);
		setParameter(args, sqlQuery);
		if(hasEntity){
			sqlQuery.addEntity(clazz);
		}else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		return sqlQuery.list();
	}

	@Override
	public   <N extends Object> List<N>  listBySql(String sql, Object[] args, Class<? extends Object> clazz, boolean hasEntity) {
		return this.listBySql(sql, args, null, clazz, hasEntity);
	}

	@Override
	public   <N extends Object> List<N>  listBySql(String sql, Object arg, Class<? extends Object> clazz, boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, null, clazz, hasEntity);	
	}

	@Override
	public   <N extends Object> List<N>  listBySql(String sql, Class<? extends Object> clazz, boolean hasEntity) {
		return this.listBySql(sql, null, null, clazz, hasEntity);
	}

	@Override
	public   <N extends Object> List<N>  listBySql(String sql, Map<String, Object> alias, Class<? extends Object> clazz, boolean hasEntity) {
		return this.listBySql(sql,null, alias, clazz, hasEntity);
	}

////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("rawtypes")
	@Override
	public  <N extends Object> Pager<N> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<? extends Object> clazz, boolean hasEntity) {
		Pager<N> retVal = new Pager<>();
		
		sql = setOrderAndSort(sql);
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		setParameter(args, sqlQuery);
		setAlias(alias, sqlQuery);
		setPagers(sqlQuery, retVal);
		if (hasEntity) {
			sqlQuery.addEntity(clazz);
		}else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		
		List data = sqlQuery.list();
		
		String sqlCount = getCountHql(sql,false);
		SQLQuery sqlQueryCount = getSession().createSQLQuery(sqlCount);
		setParameter(args, sqlQueryCount);
		setAlias(alias, sqlQueryCount);
		Long count = ((BigInteger)sqlQueryCount.uniqueResult()).longValue();
		
		retVal.setDatas(data);
		retVal.setCountOfItems(count);
		
		return retVal;
	}
	
	@Override
	public  <N extends Object> Pager<N>  findBySql(String sql, Object[] args, Class<? extends Object> clazz, boolean hasEntity) {
		return this.findBySql(sql, args, null, clazz, hasEntity);
	}

	@Override
	public   <N extends Object> Pager<N>  findBySql(String sql, Object arg, Class<? extends Object> clazz, boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, clazz, hasEntity);
	}

	@Override
	public   <N extends Object> Pager<N>  findBySql(String sql, Class<? extends Object> clazz, boolean hasEntity) {
		return this.findBySql(sql, null, null, clazz, hasEntity);
	}

	@Override
	public   <N extends Object> Pager<N>  findBySql(String sql, Map<String, Object> alias, Class<? extends Object> clazz, boolean hasEntity) {
		return findBySql(sql, null, alias, clazz, hasEntity);
	}
}
