package cn.wxn.demo.cms_core.dao.base;

import java.util.Date;

import cn.wxn.demo.cms_core.entity.User;
import junit.framework.Assert;

public class EntitiesHelper {
	private static User baseUser = new User(1, "admin1","a1","123","110","abc1@gmail.com",1, new Date());

	public static void assertUser(User expected, User actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getNickname(), actual.getNickname());
		Assert.assertEquals(expected.getPassword(), actual.getPassword());
		Assert.assertEquals(expected.getPhone(), actual.getPhone());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getStatus(), actual.getStatus());
	}
	
	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}
}
