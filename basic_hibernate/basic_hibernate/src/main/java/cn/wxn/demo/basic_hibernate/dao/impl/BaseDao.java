package cn.wxn.demo.basic_hibernate.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Class<T> clazz;

	/**
	 * 获取泛型对象
	 * 
	 * @return
	 */
	public Class<T> getClazz() {
		if (clazz == null) {
			clazz = (Class<T>) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
					.getActualTypeArguments()[0];
		}
		return clazz;
	}

	public T add(T t) {
		getSession().save(t);
		return t;
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void delete(String id) {
		getSession().delete(this.load(id));
	} 
	
	
	public T load(String id) {
		return (T) getSession().load(getClazz(), id);
	}

	public List<T> list(String hql, Object[] args) {
		return list(hql, args, null);
	}

	public List<T> list(String hql, Object args) {
		return list(hql, new Object[]{args}, null);
	}

	public List<T> list(String hql) {
		return list(hql, null, null);
	}

	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
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

		Query query = getSession().createQuery(hql); //查询语句
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
		
		if (args != null && args.length > 0) {
			int index = 0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
		
		return query.list();
	}

	public List<T> list(String hql, Map<String, Object> alias) {
		return list(hql, null, alias);
	}

	public List<T> find(String hql, Object[] args) {

		return null;
	}

	public List<T> find(String hql, Object args) {

		return null;
	}

	public List<T> find(String hql) {

		return null;
	}

	public List<T> find(String hql, Object[] args, Map<String, Object> alias) {

		return null;
	}

	public List<T> find(String hql, Map<String, Object> alias) {

		return null;
	}

	public Object queryObject(String hql, Object[] args) {

		return null;
	}

	public Object queryObject(String hql, Object arg) {

		return null;
	}

	public Object queryObject(String hql) {

		return null;
	}

	public void updateByHql(String hql, Object[] args) {

	}

	public void updateByHql(String hql, Object arg) {

	}

	public void updateByHql(String hql) {

	}

	public List<T> listBySql(String sql, Object[] args, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public List<T> listBySql(String sql, Object arg, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public List<T> listBySql(String sql, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public List<T> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public List<T> listBySql(String sql, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public Pager<T> findBySql(String sql, Object[] args, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public Pager<T> findBySql(String sql, Object arg, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public Pager<T> findBySql(String sql, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public Pager<T> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

	public Pager<T> findBySql(String sql, Map<String, Object> alias, Class<T> clazz, boolean hasEntity) {

		return null;
	}

}
