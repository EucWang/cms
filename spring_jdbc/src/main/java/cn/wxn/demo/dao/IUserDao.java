package cn.wxn.demo.dao;

 
import java.util.List;

import cn.wxn.demo.entity.User;
import cn.wxn.demo.exception.UserException;

public interface IUserDao {

	
	User add(User user);
	
	boolean update(User user) throws UserException;
	
	boolean delete(Long id);
	
	User load(Long id);
	
	List<User> list(String sql, List args);
	
}
