package cn.wxn.demo.service;

import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wxn.demo.dao.IUserDao;
import cn.wxn.demo.entity.User;

@Service("userService")
public class UserService implements IUserService {

	@Resource
	private IUserDao userDao;
	
	private static final Logger log = Logger.getLogger("UserService");
	
	@Override
	public void addUser(User user) {
		userDao.add(user);
	}

}
