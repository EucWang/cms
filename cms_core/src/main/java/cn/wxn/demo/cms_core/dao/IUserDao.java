package cn.wxn.demo.cms_core.dao;

import java.util.List;

import cn.wxn.demo.basic_hibernate.dao.IBaseDao;
import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.cms_core.entity.Group;
import cn.wxn.demo.cms_core.entity.Role;
import cn.wxn.demo.cms_core.entity.User;
import cn.wxn.demo.cms_core.entity.UserGroup;
import cn.wxn.demo.cms_core.entity.UserRole;

public interface IUserDao extends IBaseDao<User> {

	User loadByUsername(String username);
	
	/**
	 * 获取一个用户的所有角色
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> findRolesOfUser(Integer userId);

	/**
	 * 获取一个用户的所有角色的id集合
	 * 
	 * @param userId
	 * @return
	 */
	List<Integer> findRoleIdsOfUser(Integer userId);

	List<Group> findGroupOfUser(Integer userId);

	List<Integer> findGroupIdsOfUser(Integer userId);

	UserRole findUserRole(Integer userId, Integer roleId);

	UserGroup findUserGroup(Integer userId, Integer groupId);

	List<User> findUserByRole(Integer roleId);

	List<User> findUserByRoleTye(int roleType);

	List<User> findUsersByGroup(Integer groupId);

	void addUserRole(User user, Role role);

	void addUserGroup(User user, Group group);
	
	void deleteUserRoles(Integer userId);
	
	void deleteUserGroups(Integer userId);
	
	void deleteUserRole(Integer userId, Integer roleId);
	
	void deleteUserGroup(Integer userId, Integer groupId);

	Pager<User> findUsers();
}
