package demo.service;

import demo.entity.User;

public interface IUserService {

	
	void save(User user);
	
	void update(User user);
	
	User load(Integer id);
	
	void delete(Integer id);
	
	
}
