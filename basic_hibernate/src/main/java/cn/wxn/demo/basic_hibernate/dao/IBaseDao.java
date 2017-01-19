package cn.wxn.demo.basic_hibernate.dao;

import java.util.List;
import java.util.Map;

import cn.wxn.demo.basic_hibernate.model.Pager;

public interface IBaseDao<T> {

	
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * 删除对象
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 根据id加载对象
	 * @param id
	 * @return
	 */
	public T load(String id);
	
	/**
	 * 不分页 全部用list, 用hql来查询
	 * 第一种查询:
	 *   select user from User where user.username = ?
	 *    "?"通配符  就是通过args参数来设置值的
	 * 第二种查询:
	 *    select role from Role where role.user.id in (:ids) and username=:username
	 *    用多个别名查询
	 *    
	 *    session.createQuery(hql).setParameter(ids,[1,2,3]);
	 *    session.createQuery(hql).setParameter(usernames,["zhagnsan","abac","lisi"]);
	 *    
	 * 第三种查询:
	 * 		混合有 通配符 和别名   
	 *    
	 * @param hql
	 * @param objs
	 * @return  返回一组对象
	 */
	public List<T> list(String hql, Object[] args);
	public List<T> list(String hql, Object args);
	public List<T> list(String hql);
	
	public List<T> list(String hql, Object[] args, Map<String, Object> alias);   //既有别名,又有通配符
	public List<T> list(String hql, Map<String, Object> alias);   //只有别名,没有通配符

	
	/**
	 * 分页的查询 , 用hql来查询
	 * @param hql
	 * @param args
	 * @return
	 */
	public Pager<T> find(String hql, Object[] args);
	public Pager<T> find(String hql, Object args);
	public Pager<T> find(String hql);
	
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias);   //既有别名,又有通配符
	public Pager<T> find(String hql, Map<String, Object> alias);   //只有别名,没有通配符
	
	
	/**
	 * 通过hql查询一组对象
	 * @return
	 */
	public Object queryObject(String hql, Object[] args);
	public Object queryObject(String hql, Object arg);
	public Object queryObject(String hql);
 
	
	/**
	 * 通过hql来更新一组对象
	 * @param hql
	 * @param args
	 */
	public void updateByHql(String hql, Object[] args);
	public void updateByHql(String hql, Object arg);
	public void updateByHql(String hql);
	
 
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	///////////////////如下是为了以后系统优化///////////////////
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	
	
	/**
	 * 不分页查询
	 * 基于原始sql来查询,
	 * 不包含关联对象
	 * 当对象没有被hibernate管理时, 比如dto,
	 * 还需要调用方法
	 * @param sql  查询的sql语句
	 * @param args 查询的条件
	 * @param clazz  查询的实体对象
	 * @param hasEntity  该对象是否是一个hibernate所管理的实体, 如果不是则需要使用 setResultTransform查询
	 * @return  返回一组对象
	 * 
	 * 使用原生sql查询
	 * session.createSQLQuery("SELECT * FROM CATS").addEntity(Cat.class);
	 * session.createSQLQuery("SELECT ID, NAME, BIRTHDAY FROM CATS").addEntity(Cat.class);
	 * 
	 * 当DTO不是实体对象, 且没有放入到数据库中时,
	 * session.createSQLQuery("SELECT NAME, BIRTHDAY FROM CATS").setResultTransformer(Transformers.aliasToBean(CatDTO.class));
	 * 
	 */
	public List<T> listBySql(String sql, Object[] args, Class<T> clazz, boolean hasEntity);
	public List<T> listBySql(String sql, Object arg, Class<T> clazz, boolean hasEntity);
	public List<T> listBySql(String sql, Class<T> clazz, boolean hasEntity);
	
	public List<T> listBySql(String sql, Object[] args,Map<String, Object>  alias, Class<T> clazz, boolean hasEntity);
	public List<T> listBySql(String sql, Map<String, Object> alias, Class<T> clazz, boolean hasEntity);
	
	/**
	 * 分页查询
	 * @param sql
	 * @param args
	 * @param clazz
	 * @param hasEntity
	 * @return
	 */
	public Pager<T> findBySql(String sql, Object[] args, Class<T> clazz, boolean hasEntity);
	public Pager<T> findBySql(String sql, Object arg, Class<T> clazz, boolean hasEntity);
	public Pager<T> findBySql(String sql, Class<T> clazz, boolean hasEntity);
	public Pager<T> findBySql(String sql, Object[] args,Map<String, Object> alias, Class<T> clazz, boolean hasEntity);
	public Pager<T> findBySql(String sql, Map<String, Object> alias, Class<T> clazz, boolean hasEntity);
	

	
}
