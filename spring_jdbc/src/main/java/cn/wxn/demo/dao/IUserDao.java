package cn.wxn.demo.dao;

 
import java.util.List;

import cn.wxn.demo.entity.User;

public interface IUserDao {

	
	boolean add(User user);
	
	boolean update(User user);
	
	void delete(Integer id);
	
	User load(Integer id);
	
	List<User> list(String sql, List args);
	
}
