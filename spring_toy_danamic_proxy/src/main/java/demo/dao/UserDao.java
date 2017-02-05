package demo.dao;

import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import demo.annotation.ProxyInfo;
import demo.entity.User;

@Repository("userDao")
public class UserDao implements IUserDao {
	
	private static final Logger log = Logger.getLogger("UserDao");

	@Override
	public void save(User user) {
		log.info("save :" + user);
	}

	@Override
	public void update(User user) {
		log.info("update :" + user);
	}

	@Override
	public User load(Integer id) {
		log.info("load :" + id);
		return null;
	}

	@Override
	public void delete(Integer id) {
		log.info("delete:" +id);
	}

}
