package cn.wxn.demo.service;

import cn.wxn.demo.entity.User;

public interface IUserService {

	User addUser(User user);
	
	User load(Long id);

	void update(User user);
}
