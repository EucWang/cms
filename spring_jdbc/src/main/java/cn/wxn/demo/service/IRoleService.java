package cn.wxn.demo.service;

import cn.wxn.demo.entity.Role;

public interface IRoleService {

	Role addRole(Role role);

	Role loadByName(String name);

	Role loadById(Long id);

	boolean delete(Long id);
}
