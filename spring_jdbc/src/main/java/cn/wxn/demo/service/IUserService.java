package cn.wxn.demo.service;

import cn.wxn.demo.entity.User;
import cn.wxn.demo.exception.UserException;

public interface IUserService {

	User addUser(User user);
	
	User load(Long id);

	void update(User user)throws UserException;

	boolean delete(long l);
}
