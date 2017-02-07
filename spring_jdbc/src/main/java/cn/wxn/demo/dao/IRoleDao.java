package cn.wxn.demo.dao;

import cn.wxn.demo.entity.Role;

public interface IRoleDao {

	Role add(Role role);

	Role loadByName(String name);

	Role loadById(Long id);
}
