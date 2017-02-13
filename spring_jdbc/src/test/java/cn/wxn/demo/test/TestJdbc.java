package cn.wxn.demo.test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import cn.wxn.demo.entity.Role;
import cn.wxn.demo.entity.User;
import cn.wxn.demo.exception.RoleException;
import cn.wxn.demo.exception.UserException;
import cn.wxn.demo.service.IRoleService;
import cn.wxn.demo.service.IUserService;
import cn.wxn.demo.utils.EntitiesHelper;

public class TestJdbc extends AbstractDbunitTestCase {

	@Resource
	private IUserService userService;

	@Resource
	private IRoleService roleService;

	private Logger log = Logger.getLogger("TestJdbc");
	
	private IDataSet dataSetRole;
	private IDataSet dataSetUser;
	
	@org.junit.Before
	public void setUp() {
		try {
			backupAllTable();   //备份原有数据

			dataSetRole = createDataSet("t_role");   //插入给定的测试数据到数据库中
			dataSetUser = createDataSet("t_user");
			DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dataSetRole);
			DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dataSetUser);

		} catch (DataSetException e) {
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		try {
			resumeTable();
		} catch (FileNotFoundException | DatabaseUnitException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试加载测试数据是否成功
	 */
	@Test
	public void testLoadTestData() {
		User user = userService.load(1L);
		log.info(user.toString());
		
		Role role = roleService.loadById(1L);
		log.info(role.toString());
	}


	@Test
	public void testAddRoleAndUser() {
		try {
			DatabaseOperation.TRUNCATE_TABLE.execute(dbunitCon, dataSetRole);
			DatabaseOperation.TRUNCATE_TABLE.execute(dbunitCon, dataSetUser);
		} catch (DatabaseUnitException | SQLException e) {
			e.printStackTrace();
		}

		Role addRole = null;
		try {
			addRole = roleService.addRole(new Role("二逼青年", null));
		} catch (RoleException e) {
			e.printStackTrace();
		}
		User user = userService.addUser(new User("zuoshizi", "男", "左史兹", new Date(), roleService.loadById(1L)));
		
		log.info(user.toString());
		log.info(addRole.toString());
		
		Role actualRole = roleService.loadById(1L);
		User actualUser = userService.load(1L);
		
		EntitiesHelper.equals(actualUser, user);
		EntitiesHelper.equals(actualRole, addRole);
	}

	@Test
	public void testUpdateUser() {
		User user = userService.load(1L);
		user.setNickname("吕布");
		user.setRole(null);

		try {
			userService.update(user);
		} catch (UserException e) {
			e.printStackTrace();
		}
		User user2 = userService.load(1L);
		Assert.assertNotNull(user2);
		Assert.assertEquals(user2.getNickname(), "吕布");
		Assert.assertNull(user2.getRole());
	}

	/**
	 * 测试删除一条用户数据
	 */
	@Test
	public void testDeleteUser() {
		Assert.assertTrue(userService.delete(1L));
		Assert.assertNull(userService.load(1L));
	}

	/**
	 * 测试删除一条角色数据
	 */
	@Test
	public void testDeleteRole(){
		Assert.assertTrue(roleService.delete(1L));
		Assert.assertNull(roleService.loadById(1L));

	}
}
