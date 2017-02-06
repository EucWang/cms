package cn.wxn.demo.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wxn.demo.entity.User;
import cn.wxn.demo.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application_context.xml")
public class TestUser {
	
	@Resource
	private IUserService userService;

	
	@Test
	public void testAddUser(){
		User user = new User("zhangfei","man","zhangdafei");
		userService.addUser(user);
	}
}
