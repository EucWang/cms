package cn.wxn.demo.cms_core.dao;

import org.springframework.stereotype.Repository;

import cn.wxn.demo.basic_hibernate.dao.impl.BaseDao;
import cn.wxn.demo.cms_core.entity.Role;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {
}
