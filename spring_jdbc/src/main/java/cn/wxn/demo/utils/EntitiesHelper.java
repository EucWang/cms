package cn.wxn.demo.utils;

import org.junit.Assert;

import cn.wxn.demo.entity.Role;
import cn.wxn.demo.entity.User;

public class EntitiesHelper {
	
	public static void equals(User expected, User actual){
		Assert.assertNotNull(expected);
		
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getGender(), actual.getGender());
		Assert.assertEquals(expected.getNickname(), actual.getNickname());
		
		if (expected.getBirthday() != null && actual.getBirthday() != null) {
			Assert.assertEquals(expected.getBirthday().getYear(), actual.getBirthday().getYear());
			Assert.assertEquals(expected.getBirthday().getMonth(), actual.getBirthday().getMonth());
			Assert.assertEquals(expected.getBirthday().getDate(), actual.getBirthday().getDate());
		}
		
//		Assert.assertEquals(expected.getRole(), actual.getRole());
	}

	

	public static void equals(Role expected, Role actual){
		Assert.assertNotNull(expected);
		
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
	}
}
