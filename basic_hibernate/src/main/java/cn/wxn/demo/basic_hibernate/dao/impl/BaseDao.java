package cn.wxn.demo.basic_hibernate.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.xml.stream.events.EndDocument;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.HqlASTFactory;

import cn.wxn.demo.basic_hibernate.dao.IBaseDao;
import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.basic_hibernate.model.SystemContext;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {

	protected Session getSession() {
		return sessionFactory.openSession();
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
	public void delete(String id) {
		getSession().delete(this.load(id));
	} 
	

	@Override
	public T load(String id) {
		return (T) getSession().load(getClazz(), id);
	}

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

	@Override
	public Pager<T> find(String hql, Object[] args) {

		return null;
	}

	@Override
	public Pager<T> find(String hql, Object args) {

		return null;
	}

	@Override
	public Pager<T> find(String hql) {

		return null;
	}

	
	/**
	 * 分页查找
	 */
	@Override
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		Pager<T> pager = new Pager<T>();

		String hqlCount = getCountHql(hql);
		
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
		Long count  = (Long) query.uniqueResult();

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
	private String getCountHql(String hql){
		String retVal =  "select count(*) " + hql.substring(hql.indexOf("from"));
		int end = hql.indexOf("fetch");
		if(end > 0 ){
			retVal = retVal.substring(0, end);
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
		if(itemCountOfAPage == null || itemCountOfAPage < 0) itemCountOfAPage = 20;
		if(pageOffset == null || pageOffset < 0) pageOffset = 0;
		
		query.setFirstResult(pageOffset).setMaxResults(itemCountOfAPage);
		
		if(pager != null){
			pager.setIndexOfPage(pageOffset);
			pager.setItemsCountOfAPage(itemCountOfAPage);
		}
	}


	@Override
	public Pager<T> find(String hql, Map<String, Object> alias) {

		return null;
	}
	



	@Override
	public Object queryObject(String hql, Object[] args, Map<String, Object> alias) {
		
		Query createQuery = getSession().createQuery(hql);
		setAliasParameter(createQuery , alias);
		setParameter(createQuery, args);
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
 
	

	@Override
	public void updateByHql(String hql, Object[] args) {
		Query createQuery = getSession().createQuery(hql);
		setParameter(createQuery,args);
		createQuery.executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object arg) {

	}

	@Override
	public void updateByHql(String hql) {

	}

	@Override
	public List<T> listBySql(String sql, Object[] args, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public List<T> listBySql(String sql, Object arg, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public List<T> listBySql(String sql, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public List<T> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public List<T> listBySql(String sql, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public Pager<T> findBySql(String sql, Object[] args, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public Pager<T> findBySql(String sql, Object arg, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public Pager<T> findBySql(String sql, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public Pager<T> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	@Override
	public Pager<T> findBySql(String sql, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	
	
	private void setParameter(Query createQuery, Object[] args) {
		
	}

	private void setAliasParameter(Query createQuery, Map<String, Object> alias) {
		
	}

}
