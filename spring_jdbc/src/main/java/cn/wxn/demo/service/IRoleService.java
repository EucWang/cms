package cn.wxn.demo.service;

import cn.wxn.demo.entity.Role;
import cn.wxn.demo.exception.RoleException;

public interface IRoleService {

	Role addRole(Role role)  throws RoleException ;

	Role loadByName(String name);

	Role loadById(Long id);

	boolean delete(Long id);
}
