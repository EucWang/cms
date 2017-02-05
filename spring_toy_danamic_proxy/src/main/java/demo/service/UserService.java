package demo.service;

import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import demo.dao.IUserDao;
import demo.entity.User;

@Service("userService")
public class UserService implements IUserService {
	
	private static final Logger log = Logger.getLogger("UserService");

//	@Resource
//	private IUserDao userProxy;

	@Resource
	private IUserDao userDao;

	@Override
	public void save(User user) {
//		userProxy.save(user);
		userDao.save(user);
		log.info("save :" + user);
	}

	@Override
	public void update(User user) {
//		userProxy.update(user);
		userDao.update(user);
		log.info("update:" + user);
	}

	@Override
	public User load(Integer id) {
//		userProxy.load(id);
		userDao.load(id);
		log.info("load :" + id);
		return null;
	}

	@Override
	public void delete(Integer id) {
//		userProxy.delete(id);
		userDao.delete(id);
		log.info("delete :" + id);
	}

}
