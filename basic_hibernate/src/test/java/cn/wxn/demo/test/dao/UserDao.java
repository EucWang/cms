package cn.wxn.demo.test.dao;

import org.springframework.stereotype.Repository;

import cn.wxn.demo.basic_hibernate.dao.impl.BaseDao;
import cn.wxn.demo.test.entity.User;

@Repository("userDao")
public class UserDao  extends BaseDao<User> implements IUserDao{

}
 