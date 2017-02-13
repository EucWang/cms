package cn.wxn.demo.dao;

import java.util.List;

import cn.wxn.demo.entity.Role;
import cn.wxn.demo.exception.RoleException;

public interface IRoleDao {

	Role add(Role role) throws RoleException;

	Role loadByName(String name);

	Role loadById(Long id);
	
	boolean update(Role role) throws RoleException;

	boolean delete(Long id);
	
	List<Role> list();
}
