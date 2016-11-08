package cn.wxn.demo.basic_hibernate.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {

	
	public T add(T t);
	
	public void update(T t);
	
	public void delete(String id);
	
	public T load(String id);
	
	
	
	public List<T> list(String hql, Object[] objs);
	public List<T> list(String hql, Object obj);
	public List<T> list(String hql);
	
	public List<T> list(String hql, Object[] objs, Map<String, Object> alias);
	public List<T> list(String hql, Map<String, Object> alias);
	
	
	
}
