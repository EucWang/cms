package cn.wxn.demo.test.dao;

import java.util.List;
import java.util.Map;

import cn.wxn.demo.basic_hibernate.dao.IBaseDao;
import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.test.entity.User;

public interface IUserDao extends IBaseDao<User>{

	List<User> listUser(String string, Object[] objects);

	List<User> listUser(String string, Object[] objects, Map<String, Object> alias);

	Pager<User> findUser(String string, Map<String, Object> alias);

	Pager<User> findUser(String string, Object[] objects, Map<String, Object> alias);

	List<User> listUserBySql(String sql, Map<String, Object> alias, Class<User> class1, boolean b);

	List<User> listUserBySql(String sql, Object[] args, Map<String, Object> alias, Class<User> class1, boolean b);

	Pager<User> findUserBySql(String sql, Object[] args, Map<String, Object> alias, Class<User> class1, boolean b);

	void updateUserByHql(String hql, Object[] args);

	Object queryUserObject(String hql, Object arg);

}
