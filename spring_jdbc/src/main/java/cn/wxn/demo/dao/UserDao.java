package cn.wxn.demo.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.wxn.demo.entity.User;

@Repository("userDao")
public class UserDao implements IUserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	public void setDataSource(DataSource ds){
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public boolean add(User user) {
		int update = jdbcTemplate.update("insert into t_user (username, gender, nickname) values (?, ?, ?)", 
				user.getUsername(), 
				user.getGender(),
				user.getNickname());
		
		if (update == 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean update(User user) {

		return true;
	}

	@Override
	public void delete(Integer id) {

	}

	@Override
	public User load(Integer id) {
		return null;
	}

	@Override
	public List<User> list(String sql, List args) {
		return null;
	}

}
