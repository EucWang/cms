package cn.wxn.demo.cms_core.dao;

import org.springframework.stereotype.Repository;

import cn.wxn.demo.basic_hibernate.dao.impl.BaseDao;
import cn.wxn.demo.cms_core.entity.User;
 
@Repository("userDao")
public class UserDao  extends BaseDao<User> implements IUserDao{


}
