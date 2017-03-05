package cn.wxn.demo.cms_core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import cn.wxn.demo.basic_hibernate.dao.impl.BaseDao;
import cn.wxn.demo.cms_core.entity.Group;
import cn.wxn.demo.cms_core.entity.Role; 
import cn.wxn.demo.cms_core.entity.User;
import cn.wxn.demo.cms_core.entity.UserGroup;
import cn.wxn.demo.cms_core.entity.UserRole;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findRolesOfUser(Integer userId) {
		String hql = "select ur.role from UserRole ur where ur.user.id=?";
		List<Role> list = getSession().createQuery(hql).setParameter(0, userId).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findRoleIdsOfUser(Integer userId) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id=?";
		List<Integer> list = getSession().createQuery(hql).setParameter(0, userId).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> findGroupOfUser(Integer userId) {
		String hql = "select ug.group from UserGroup ug where ug.user.id=?";
		List<Group> groups = getSession().createQuery(hql).setParameter(0, userId).list();
		return groups;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findGroupIdsOfUser(Integer userId) {
		String hql = "select ug.group.id from UserGroup ug where ug.user.id=?";
		List<Integer> groupIds = getSession().createQuery(hql).setParameter(0, userId).list();
		return groupIds;
	}

	@Override
	public UserRole findUserRole(Integer userId, Integer roleId) {
		String hql = "from UserRole ur where ur.user.id=? and ur.role.id=?";
		UserRole userRole = (UserRole)getSession().createQuery(hql).setParameter(0, userId).setParameter(1, roleId).uniqueResult();
		return userRole;
	}

	@Override
	public UserGroup findUserGroup(Integer userId, Integer groupId) {
		String hql = "from UserGroup ug where ug.user.id=? and ug.group.id=?";
		UserGroup userGroup= (UserGroup)getSession().createQuery(hql).setParameter(0, userId).setParameter(1, groupId).uniqueResult();
		return userGroup;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByRole(Integer roleId) {
		String hql = "select ur.user from UserRole ur where ur.role.id=?";
		List<User> users = getSession().createQuery(hql).setParameter(0, roleId).list();
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByGroup(Integer groupId) {
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		List<User> users = getSession().createQuery(hql).setParameter(0, groupId).list();
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByRoleTye(int roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		List<User> users = getSession().createQuery(hql).setParameter(0, roleType).list();
		return users;
	}
}
