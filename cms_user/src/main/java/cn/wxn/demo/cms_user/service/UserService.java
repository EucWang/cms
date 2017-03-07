package cn.wxn.demo.cms_user.service;

import java.util.List;
import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import cn.wxn.demo.basic_hibernate.model.Pager; 
import cn.wxn.demo.cms_core.dao.IGroupDao;
import cn.wxn.demo.cms_core.dao.IRoleDao;
import cn.wxn.demo.cms_core.dao.IUserDao;
import cn.wxn.demo.cms_core.entity.Group;
import cn.wxn.demo.cms_core.entity.Role;
import cn.wxn.demo.cms_core.entity.User; 
import cn.wxn.demo.cms_user.exception.CmsException;

@Service("userService")
public class UserService implements IUserService {

	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;
	
	@Inject
	public void setUserDao(IUserDao userDao){
		this.userDao = userDao;
	}
	
	@Inject
	public void setRoleDao(IRoleDao roleDao){
		this.roleDao = roleDao;
	}
	
	@Inject
	public void setGroupDao(IGroupDao groupDao){
		this.groupDao = groupDao;
	}
	
	///////////////////////////////////////////////////////
	///////////////////////////////////////////////////////
	///////////////////////////////////////////////////////

	@Override
	public void add(User user, List<Integer> roleIds, List<Integer> groupIds) throws CmsException {
		// 判断用户名称是否已经存在
		if (user.getUsername() == null || user.getUsername().trim() == "") {
			throw new CmsException("添加的用户名称已经存在,不能添加");
		}
		userDao.add(user);

		// 添加到角色的关系
		if (roleIds != null && roleIds.size() > 0) {
			for (Integer roleId : roleIds) {
				addUserRole(user, roleId);
			}
		}

		// 添加和组的关系
		if (groupIds != null && groupIds.size() > 0) {
			for (Integer groupId : groupIds) {
				addUserGroup(user, groupId);
			}
		}
	}

	@Override
	public void delete(Integer userId) {
		userDao.deleteUserRoles(userId);// 删除到角色的关系
		userDao.deleteUserGroups(userId);// 删除和组的关系
		userDao.delete(userId);//删除用户信息
	}

	@Override
	public void update(User user, Integer[] roleIds, Integer[]  groupIds) throws CmsException {
		Integer userId= user.getId();
		List<Integer> currentRoleIds = userDao.findRoleIdsOfUser(userId);
		List<Integer> currentGroupIds = userDao.findGroupIdsOfUser(userId);
		
		//先处理需要添加的
		if(roleIds != null && roleIds.length > 0){
			for(Integer roleId : roleIds){
				if (currentRoleIds != null && currentRoleIds.size() > 0){
						if(!currentRoleIds.contains(roleId)) {
							addUserRole(user, roleId);
						}
				}else {
					addUserRole(user, roleId);
				}
			}
		}
		if(groupIds != null && groupIds.length > 0){
			for(Integer groupId : groupIds){
				if (currentGroupIds != null && currentGroupIds.size() > 0){
					if (!currentGroupIds.contains(groupId)) {
						addUserGroup(user, groupId);
					}
				}else{
					addUserGroup(user, groupId);
				}
			}
		}
		//然后处理需要删除的
		if(currentRoleIds != null && currentRoleIds.size() > 0){
			for(Integer roleId : currentRoleIds){
				if(roleIds != null && roleIds.length > 0){
					if (!ArrayUtils.contains(roleIds, roleId)) {
						userDao.deleteUserRole(userId, roleId);
					}
				}else {
					userDao.deleteUserRole(userId, roleId);
				}
			}
		}
		if(currentGroupIds != null && currentGroupIds.size() > 0){
			for(Integer groupId : currentGroupIds){
				if(roleIds != null && roleIds.length > 0){
					if (!ArrayUtils.contains(groupIds, groupId)) {
						userDao.deleteUserGroup(userId, groupId);
					}
				}else{
					userDao.deleteUserGroup(userId, groupId);
				}
			}
		}
		userDao.update(user);
	}

	@Override
	public Pager<User> findUsers() {
		return userDao.findUsers();
	}

	@Override
	public User load(Integer id) {
		return userDao.load(id);
	}

	@Override
	public List<Role> listUserRoles(Integer userId) {
		return userDao.findRolesOfUser(userId);
	}

	@Override
	public List<Group> listUserGroup(Integer userId) {
		return userDao.findGroupOfUser(userId);
	}
	
	/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	
	/**
	 * 添加用户到组的关系
	 * @param user
	 * @param groupId
	 * @throws CmsException
	 */
	private void addUserGroup(User user, Integer groupId) throws CmsException {
		Group group = groupDao.load(groupId);
		if (group == null) {
			throw new CmsException("添加的用户组信息不存在,不能添加");
		}
		userDao.addUserGroup(user, group);
	}

	/**
	 * 添加用户到角色的关系
	 * @param user
	 * @param roleId
	 * @throws CmsException
	 */
	private void addUserRole(User user, Integer roleId) throws CmsException {
		Role role = roleDao.load(roleId);
		if (role == null) {
			throw new CmsException("添加的用户角色信息不存在,不能添加");
		}
		userDao.addUserRole(user, role);
	}

}
