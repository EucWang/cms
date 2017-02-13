package cn.wxn.demo.test;

import org.junit.Assert;

import cn.wxn.demo.entity.Role;

public class EntitiesHelper {

	public static Role baseRole = new Role(1L, "admin", "超级管理员");

	public static void equalsRole(Role expect, Role actual) {
		Assert.assertNotNull(expect);
		Assert.assertEquals(expect.getId(), actual.getId());
		Assert.assertEquals(expect.getName(), actual.getName());
		Assert.assertEquals(expect.getDescription(), actual.getDescription());
	}
	
	public static void equalsRole(Role expect){
		equalsRole(expect, baseRole);
	}
}
