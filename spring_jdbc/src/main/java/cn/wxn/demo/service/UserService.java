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
	public User addUser(User user) {
		if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
			log.warning("addUser user.getUsername() is null or empty"); 
			return null;
		}
		
		return userDao.add(user);
	}

	@Override
	public User load(Long id) {
		
		return userDao.load(id);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public boolean delete(long id) {
		return userDao.delete(id);
	}

}
