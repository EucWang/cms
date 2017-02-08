package cn.wxn.demo.test;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wxn.demo.entity.Role;
import cn.wxn.demo.entity.User;
import cn.wxn.demo.service.IRoleService;
import cn.wxn.demo.service.IUserService; 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application_context.xml")
public class TestJdbc {
	@Resource
	private IUserService userService;

	@Resource
	private IRoleService roleService;

	private Logger log = Logger.getLogger("TestJdbc");

	@Test
	public void testAddRoleAndUser() {
//		Role role = new Role("二逼青年", null);
//		Role addRole = roleService.addRole(role);
		
		User user = new User("zuoshizi", "男", "左史兹", new Date(), roleService.loadById(22L));
		user = userService.addUser(user);
		log.info(user.toString());
	}

	@Test
	public void testAddRole() {
		Role role = new Role("二逼青年", "80后");
		Role addRole = roleService.addRole(role);
		org.junit.Assert.assertNotNull(addRole);
	}

	@Test
	public void testUpdateUser() {
		User user = userService.load(14L);
//		Role role = roleService.loadById(22L);
		user.setNickname("吕布");
		user.setRole(null);

		userService.update(user);
	}
	
	@Test
	public void testDeleteUser() {
		Assert.assertTrue(userService.delete(14L));
	}
	
	
	
}
