package cn.wxn.demo.cms_user.service;

import java.util.List;

import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.cms_core.entity.Group;
import cn.wxn.demo.cms_core.entity.Role;
import cn.wxn.demo.cms_core.entity.User;
import cn.wxn.demo.cms_user.exception.CmsException;

public interface IUserService {
	
	void add(User user, List<Integer> roleIds, List<Integer> groupIds) throws CmsException;
	
	void delete(Integer userId);
	
	void update(User user, Integer[]  roleIds,Integer[]  groupIds) throws CmsException;
	
	Pager<User> findUsers();
	
	User load(Integer id);
	
	List<Role> listUserRoles(Integer userId);
	
	List<Group> listUserGroup(Integer userId);
}
