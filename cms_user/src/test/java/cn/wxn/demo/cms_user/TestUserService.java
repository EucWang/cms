package cn.wxn.demo.cms_user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.cms_core.dao.IGroupDao;
import cn.wxn.demo.cms_core.dao.IRoleDao;
import cn.wxn.demo.cms_core.dao.IUserDao;
import cn.wxn.demo.cms_core.entity.Group;
import cn.wxn.demo.cms_core.entity.Role;
import cn.wxn.demo.cms_core.entity.User;
import cn.wxn.demo.cms_user.exception.CmsException;
import cn.wxn.demo.cms_user.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestUserService {

	@Inject
	private IUserService userService;

	@Inject
	private IUserDao userDao;

	@Inject
	private IRoleDao roleDao;

	@Inject
	private IGroupDao groupDao;
	
	User baseUser = new User(1, "admin1", "a1", "123", "123", "a1@a1.cn", 1, new Date());

	@Test
	public void testDelete() {
		int userId = 1;

		EasyMock.reset(userDao);

		userDao.deleteUserRoles(userId);
		EasyMock.expectLastCall();

		userDao.deleteUserGroups(userId);
		EasyMock.expectLastCall();

		userDao.delete(userId);
		EasyMock.expectLastCall();

		EasyMock.replay(userDao);
		userService.delete(userId);

		EasyMock.verify(userDao);
	}

	@Test
	public void testFindUsers() {
		EasyMock.reset(userDao);

		EasyMock.expect(userDao.findUsers()).andReturn(new Pager<User>());
		EasyMock.replay(userDao);
		userService.findUsers();

		EasyMock.verify(userDao);
	}

	@Test
	public void testLoad() {
		int id = 1;
		
		EasyMock.reset(userDao);

		EasyMock.expect(userDao.load(id)).andReturn(baseUser);
		EasyMock.replay(userDao);
		userService.load(id);

		EasyMock.verify(userDao);
	}

	@Test
	public void testListUserRoles() {
		Integer userId = 1;
		EasyMock.reset(userDao);

		List<Role> roles = new ArrayList<Role>();
		EasyMock.expect(userDao.findRolesOfUser(userId)).andReturn(roles);
		EasyMock.replay(userDao);
		userService.listUserRoles(userId);

		EasyMock.verify(userDao);
	}

	@Test
	public void testListUserGroup() {
		Integer userId = 1;
		EasyMock.reset(userDao);

		List<Group> groups = new ArrayList<Group>();
		EasyMock.expect(userDao.findGroupOfUser(userId)).andReturn(groups);
		EasyMock.replay(userDao);
		userService.listUserGroup(userId);

		EasyMock.verify(userDao);
	}
	
	@Test(expected=CmsException.class)
	public void testAddWithNoUserName() throws CmsException{
		
		User user = new User();
		List<Integer> roleIds = new ArrayList<Integer>();
		List<Integer> groupIds = new ArrayList<Integer>();
		
		EasyMock.reset(userDao,roleDao, groupDao);

		EasyMock.expect(userDao.add(user)).andReturn(user);
		EasyMock.replay(userDao,roleDao, groupDao);
		userService.add(user, roleIds, groupIds);

		EasyMock.verify(userDao,roleDao, groupDao);
	}
	
	@Test
	public void testAdd() throws CmsException{
		
		User user = new User();
		user.setUsername("zhangsan");
		List<Integer> roleIds = Arrays.asList(new Integer[]{1,2,3});
		List<Integer> groupIds = Arrays.asList(new Integer[]{1,2,3});
		Role role = new Role();
		Group group = new Group();
		
		EasyMock.reset(userDao,roleDao, groupDao);
		
		EasyMock.expect(userDao.add(user)).andReturn(user);
		
		EasyMock.expect(roleDao.load(EasyMock.gt(0))).andReturn(role).times(3);
		userDao.addUserRole(user, role);
		EasyMock.expectLastCall().times(3);
		
		EasyMock.expect(groupDao.load(EasyMock.gt(0))).andReturn(group).times(3);
		userDao.addUserGroup(user, group);
		EasyMock.expectLastCall().times(3);
		
		EasyMock.replay(userDao,roleDao, groupDao);
		userService.add(user, roleIds, groupIds);
		
		EasyMock.verify(userDao,roleDao, groupDao);
	}
	
	
	@Test
	public void testUpdate() throws CmsException{

		Integer[] roleIds = new Integer[]{1,2}; 
		Integer[]  groupIds = new Integer[]{1,2}; 
		List<Integer> roleIdList = Arrays.asList(new Integer[]{2,3});
		List<Integer> groupIdList = Arrays.asList(new Integer[]{2,3});
		Role role = new Role();
		role.setId(1);
		Group group = new Group();
		group.setId(1);
		
		EasyMock.reset(userDao,roleDao, groupDao);

		EasyMock.expect(userDao.findRoleIdsOfUser(baseUser.getId())).andReturn(roleIdList);
		EasyMock.expect(userDao.findGroupIdsOfUser(baseUser.getId())).andReturn(groupIdList);
		
		EasyMock.expect(roleDao.load(1)).andReturn(role);
		userDao.addUserRole(baseUser, role);
		EasyMock.expectLastCall();
		
		EasyMock.expect(groupDao.load(1)).andReturn(group);
		userDao.addUserGroup(baseUser, group);
		EasyMock.expectLastCall();
		
		userDao.deleteUserRole(baseUser.getId(), 3);
		EasyMock.expectLastCall();
		userDao.deleteUserGroup(baseUser.getId(), 3);
		EasyMock.expectLastCall();
		
		userDao.update(baseUser);
		
		EasyMock.replay(userDao,roleDao, groupDao);
		userService.update(baseUser, roleIds, groupIds);

		EasyMock.verify(userDao,roleDao, groupDao);
		
	}

}
