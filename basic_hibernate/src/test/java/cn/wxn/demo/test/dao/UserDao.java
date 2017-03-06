package cn.wxn.demo.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.wxn.demo.basic_hibernate.dao.impl.BaseDao;
import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.test.entity.User;

@Repository("userDao")
public class UserDao  extends BaseDao<User> implements IUserDao{

	@Override
	public List<User> listUser(String hql, Object[] args) {
		return super.list(hql, args);
	}

	@Override
	public List<User> listUser(String hql, Object[] args, Map<String, Object> alias) {
		return super.list(hql, args, alias);
	}

	@Override
	public Pager<User> findUser(String hql, Map<String, Object> alias) {
		return super.find(hql, alias);
	}

	@Override
	public Pager<User> findUser(String hql, Object[] args, Map<String, Object> alias) {
		return super.find(hql, args, alias);
	}

	@Override
	public List<User> listUserBySql(String sql, Map<String, Object> alias, Class<User> class1, boolean hasEntity) {
		return super.listBySql(sql, alias, class1, hasEntity);
	}

	@Override
	public List<User> listUserBySql(String sql, Object[] args, Map<String, Object> alias, Class<User> class1,
			boolean hasEntity) {
		return super.listBySql(sql,args, alias, class1, hasEntity);
	}

	@Override
	public Pager<User> findUserBySql(String sql, Object[] args, Map<String, Object> alias, Class<User> class1,
			boolean hasEntity) {
		return super.findBySql(sql,args, alias, class1, hasEntity);
	}

	@Override
	public void updateUserByHql(String hql, Object[] args) {
		super.updateByHql(hql, args);
	}

	@Override
	public Object queryUserObject(String hql, Object arg) {
		return super.queryObject(hql, arg);
	}

}
 