package cn.wxn.demo.cms_core.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.basic_hibernate.model.SystemContext;
import cn.wxn.demo.cms_core.dao.base.AbstractDbUnitTestCase;
import cn.wxn.demo.cms_core.dao.base.EntitiesHelper;
import cn.wxn.demo.cms_core.entity.Group;
import cn.wxn.demo.cms_core.entity.Role;
import cn.wxn.demo.cms_core.entity.User;
import cn.wxn.demo.cms_core.entity.UserGroup;
import cn.wxn.demo.cms_core.entity.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase {

	@Inject
	private IUserDao userDao;

	@Inject
	private IRoleDao roleDao;

	@Inject
	private IGroupDao groupDao;

	@Inject
	private SessionFactory sessionFactory;

	private Session s;

	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));

		this.backupAllTable();
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
	}

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		s = sessionHolder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);

		this.resumeTable();
	}

	@Test
	public void testLoad() {
		User user = userDao.load(1);
		EntitiesHelper.assertUser(user);
	}

	@Test
	public void testfindRolesOfUser() {
		List<Role> findRolesOfUser = userDao.findRolesOfUser(1);
		Assert.assertNotNull(findRolesOfUser);
		Assert.assertEquals(findRolesOfUser.size(), 2);
		Assert.assertEquals(findRolesOfUser.get(0).getId(), new Integer(1));
		Assert.assertEquals(findRolesOfUser.get(1).getId(), new Integer(2));
	}

	@Test
	public void testfindRoleIdsOfUser() {
		List<Integer> findRolesOfUser = userDao.findRoleIdsOfUser(1);
		Assert.assertNotNull(findRolesOfUser);
		Assert.assertEquals(findRolesOfUser.size(), 2);
		Assert.assertEquals(findRolesOfUser.get(0), new Integer(1));
		Assert.assertEquals(findRolesOfUser.get(1), new Integer(2));
	}

	@Test
	public void testfindGroupOfUser() {
		List<Group> groups = userDao.findGroupOfUser(1);
		Assert.assertNotNull(groups);
		Assert.assertEquals(groups.size(), 2);
		Assert.assertEquals(groups.get(0).getId(), new Integer(2));
		Assert.assertEquals(groups.get(1).getId(), new Integer(3));
	}

	@Test
	public void testfindGroupIdsOfUser() {
		List<Group> groups = userDao.findGroupOfUser(1);
		Assert.assertNotNull(groups);
		Assert.assertEquals(groups.size(), 2);
		Assert.assertEquals(groups.get(0).getId(), new Integer(2));
		Assert.assertEquals(groups.get(1).getId(), new Integer(3));
	}

	@Test
	public void testfindUserRole() {
		UserRole userRole = userDao.findUserRole(1, 2);
		Assert.assertNotNull(userRole);
		Assert.assertEquals(userRole.getId(), new Integer(2));
	}

	@Test
	public void testfindUserGroup() {
		UserGroup userGroup = userDao.findUserGroup(1, 3);
		Assert.assertNotNull(userGroup);
		Assert.assertEquals(userGroup.getId(), new Integer(2));
	}

	@Test
	public void testfindUserByRole() {
		List<User> users = userDao.findUserByRole(2);
		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 10);
	}

	@Test
	public void testfindUsersByGroup() {
		List<User> users = userDao.findUsersByGroup(2);
		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 3);

	}

	@Test
	public void testfindUserByRoleTye() {
		List<User> users = userDao.findUserByRoleTye(0);
		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 1);
	}

	@Test
	public void testAddUserRole() {
		User user = userDao.load(10);
		Role role = roleDao.load(3);
		userDao.addUserRole(user, role);

		UserRole userRole = userDao.findUserRole(10, 3);
		Assert.assertNotNull(userRole);
		Assert.assertEquals(userRole.getUser().getId(), new Integer(10));
		Assert.assertEquals(userRole.getRole().getId(), new Integer(3));
	}

	@Test
	public void testAddUserGroup() {
		User user = userDao.load(10);
		Group group = groupDao.load(3);
		userDao.addUserGroup(user, group);

		UserGroup userGroup = userDao.findUserGroup(10, 3);
		Assert.assertNotNull(userGroup);
		Assert.assertEquals(userGroup.getUser().getId(), new Integer(10));
		Assert.assertEquals(userGroup.getGroup().getId(), new Integer(3));
	}

	@Test
	public void testLoadByUsername() {
		User user = userDao.loadByUsername("admin7");
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getId(), new Integer(7));
		Assert.assertEquals(user.getNickname(), "a7");
	}

	@Test
	public void testDeleteUserRole() {
		userDao.deleteUserRole(1, 1);
		UserRole userRole = userDao.findUserRole(1, 1);
		Assert.assertNull(userRole);
	}

	@Test
	public void testDeleteUserGroup() {
		userDao.deleteUserGroup(10, 1);
		UserGroup userGroup = userDao.findUserGroup(10, 1);
		Assert.assertNull(userGroup);
	}

	@Test
	public void testDeleteUserRoles() {
		userDao.deleteUserRoles(1);
		UserRole userRole = userDao.findUserRole(1, 1);
		UserRole userRole2 = userDao.findUserRole(1, 2);
		Assert.assertNull(userRole);
		Assert.assertNull(userRole2);
	}

	@Test
	public void testDeleteUserGroups() {
		userDao.deleteUserGroups(1);
		UserGroup userGroup = userDao.findUserGroup(1, 2);
		UserGroup userGroup2 = userDao.findUserGroup(1, 3);
		Assert.assertNull(userGroup);
		Assert.assertNull(userGroup2);
	}

	@Test
	public void testFindUsers() {
		SystemContext.setPageOffset(0);
		SystemContext.setItemCountOfAPage(5);
		Pager<User> users = userDao.findUsers();
		Assert.assertNotNull(users);
		Assert.assertEquals(users.getCountOfItems(), 10);
		Assert.assertEquals(users.getDatas().size(), 5);
	}
}
