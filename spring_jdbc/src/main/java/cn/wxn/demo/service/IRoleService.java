package cn.wxn.demo.service;

import javax.annotation.Resource;

import cn.wxn.demo.entity.Role;

public interface IRoleService {

	Role addRole(Role role);

	Role loadByName(String name);

	Role loadById(Long id);
}
