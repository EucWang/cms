package cn.wxn.demo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wxn.demo.dao.IRoleDao;
import cn.wxn.demo.entity.Role;
import cn.wxn.demo.exception.RoleException;

@Service("roleService")
public class RoleService implements IRoleService{
	
	@Resource
	private IRoleDao roleDao;

	@Override
	public Role addRole(Role role) throws RoleException {
		return roleDao.add(role);
	}

	@Override
	public Role loadByName(String name) {
		if (name == null || "".equals(name.trim())) {
			return null;
		}
		return roleDao.loadByName(name);
	}

	@Override
	public Role loadById(Long id) {
		return roleDao.loadById(id);
	}

	@Override
	public boolean delete(Long id) {
		return roleDao.delete(id);
	}

}
