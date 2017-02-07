package cn.wxn.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cn.wxn.demo.entity.Role;
import cn.wxn.demo.entity.User;

@Repository("userDao")
public class UserDao implements IUserDao {

	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public User add(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int update = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into t_user (username, gender, nickname, birthday, gid) values (?, ?, ?, ?, ?)";
				Long rid = null;
				if (user.getRole() != null && user.getRole().getId() != null) {
					rid = user.getRole().getId();
				}
				java.sql.Date date = null;
				if (user.getBirthday() != null) {
					date = new java.sql.Date(((Date) user.getBirthday()).getTime());
				}

				PreparedStatement statement = con.prepareStatement(sql, new String[] { "id" });
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getGender());
				statement.setString(3, user.getNickname());
				statement.setDate(4, date);
				statement.setLong(5, rid);

				return statement;
			}
		}, keyHolder);

		if (update > 0) {
			user.setId((Long) keyHolder.getKey());
			return user;
		}

		return null;
	}

	@Override
	public boolean update(User user) {
		User load = load(user.getId());

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("update t_user set ");
		List<String> toAppend = new ArrayList<>();

		if (!load.getUsername().equals(user.getUsername())) {
			toAppend.add("username='" + user.getUsername() + "' ");
		}
		if ((load.getGender() != null && !load.getGender().equals(user.getGender()))
				|| (user.getGender() != null && !user.getGender().equals(load.getGender()))) {
			toAppend.add("gender='" + user.getGender() + "' ");
		}
		if ((load.getNickname() != null && !load.getNickname().equals(user.getNickname()))
				|| (user.getNickname() != null && !user.getNickname().equals(load.getNickname()))) {
			toAppend.add("nickname='" + user.getNickname() + "' ");
		}
		if ((load.getBirthday() != null && !load.getBirthday().equals(user.getBirthday()))
				|| (user.getBirthday() != null && !user.getBirthday().equals(load.getBirthday()))) {
			toAppend.add("birthday=" + user.getBirthday() + " ");
		}
		if ((load.getRole() != null && !load.getRole().getId().equals(user.getRole().getId()))
				|| (user.getRole() != null && !user.getRole().getId().equals(load.getRole().getId()))) {
			toAppend.add("gid='" + user.getRole().getId() + "' ");
		}
		for(int i=0; i<toAppend.size();i++){
			sBuffer.append(toAppend.get(i));
			if (i != toAppend.size() -1) {
				sBuffer.append(",");
			}
		}
		sBuffer.append(" where id=?");
		
		Object[] args = new Object[] {user.getId()};
		int update = jdbcTemplate.update(sBuffer.toString(), args);
		if (update > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public User load(Long id) {
		String sql = "select u.id uid, u.*, r.* from t_user u left join t_role r on (u.gid=r.id) where u.id=?";
		Object[] args = new Object[] { id };
		User result = jdbcTemplate.queryForObject(sql, args, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				User user = new User();

				user.setId(rs.getLong("uid"));
				user.setUsername(rs.getString("username"));
				user.setGender(rs.getString("gender"));
				user.setNickname(rs.getString("nickname"));
				user.setBirthday(rs.getDate("birthday"));

				role.setId(rs.getLong("gid"));
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));

				user.setRole(role);
				return user;
			}
		});
		return result;
	}

	@Override
	public List<User> list(String sql, List args) {
		return null;
	}

}
