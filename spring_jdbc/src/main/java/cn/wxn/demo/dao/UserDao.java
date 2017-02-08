package cn.wxn.demo.dao;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
	
	private static final Logger log = Logger.getLogger("UserDao");

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
		
		if ((load.getRole() != null && user.getRole() != null && !load.getRole().getId().equals(user.getRole().getId()))
				|| (user.getRole() != null && user.getRole().getId() != null && load.getRole() == null)) {
			toAppend.add("gid='" + user.getRole().getId() + "' ");
		}else if (load.getRole() != null && load.getRole().getId() != null && user.getRole() == null){
			toAppend.add("gid=null ");			
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
	public User load(Long id) {
		String sql = "select u.id uid, u.*, r.* from t_user u left join t_role r on (u.gid=r.id) where u.id=?";
		Object[] args = new Object[] { id };
		User result = jdbcTemplate.queryForObject(sql, args, new UserRowMapper ());
		return result;
	}
	
	@Override
	public boolean delete(Long id) {
		User load = load(id);
		if (load == null) {
			log.warning("the user[id="+id+"] cannot be deleted,no user in db.");
			return false;
		}
		
		String sql = "delete from t_user where id=?";
		Object[] args = new Object[]{id};
		int update = jdbcTemplate.update(sql, args);
		if (update > 0) {
			
			return true;
		}
		log.warning("the user[id="+id+"] cannot be deleted,delete fail.");
		return false;
	}

	@Override
	public List<User> list(String sql, List args) {
//		String sql = " select u.id uid, r.id rid , u.*, r.* from t_user u left join t_role r on (u.gid = r.id) "
//				+ "group by uid order by username asc limit 0,10;";
		return null;
	}

	public class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("uid"));
			user.setUsername(rs.getString("username"));
			user.setGender(rs.getString("gender"));
			user.setNickname(rs.getString("nickname"));
			user.setBirthday(rs.getDate("birthday"));

			Long rid = rs.getLong("gid");
			if (rid != null && rid > 0) {
				Role role = new Role();
				role.setId(rid);
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
				user.setRole(role);
			}
			return user;
		}
	}
}
