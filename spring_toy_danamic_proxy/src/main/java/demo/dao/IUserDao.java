package demo.dao;

import demo.annotation.ProxyInfo;
import demo.entity.User;

public interface IUserDao {


	@ProxyInfo("extra info: save user")
	public void save(User user);
	
	public void update(User user);
	
	public User load(Integer id);
	
	public void delete(Integer id);
	
	
}
