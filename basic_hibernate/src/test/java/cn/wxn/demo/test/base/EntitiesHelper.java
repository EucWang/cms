package cn.wxn.demo.test.base;

import java.util.Date;

import cn.wxn.demo.test.entity.User;
import junit.framework.Assert;

public class EntitiesHelper {

	private static User baseUser = new User(1, "admin1");

	public static void assertUser(User expected, User actual) {
		Assert.assertNotNull(expected);
		
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
	}
	
	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}
}
