package cn.wxn.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@Repository("roleDao")
public class RoleDao implements IRoleDao {

	private static Logger log = Logger.getLogger("RoleDao");

	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public Role add(final Role role) {
		Role loadByName = loadByName(role.getName());
		if (loadByName != null) {
			log.info("数据库中已经存在相同的一条数据，不能插入名称相同的ROle");
			return null;
		}

		KeyHolder keyHolder = new GeneratedKeyHolder();

		String sql = "insert into t_role (name, description) values (?, ?)";

		int update = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement prepareStatement = con.prepareStatement(sql, new String[] { "id" });
				prepareStatement.setString(1, role.getName());
				prepareStatement.setString(2, role.getDescription());
				return prepareStatement;
			}
		}, keyHolder);

		if (update > 0) {
			role.setId((Long) keyHolder.getKey());
			return role;
		}
		return null;
	}

	@Override
	public Role loadByName(String name) {
		String sql = "select * from t_role where name=?";
		Object[] args = new Object[] { name };

		List<Role> query = jdbcTemplate.query(sql, args, new RoleRowMapper());

		if (query != null && query.size() > 0) {
			return query.get(0);
		}
		return null;
	}

	@Override
	public Role loadById(Long id) {
		String sql = "select * from t_role where id=?";
		Object[] args = new Object[] { id };

		List<Role> query = jdbcTemplate.query(sql, args, new RoleRowMapper());

		if (query != null && query.size() > 0) {
			return query.get(0);
		}
		return null;
	}

	public class RoleRowMapper implements RowMapper<Role> {
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();

			role.setId(rs.getLong(1));
			role.setName(rs.getString(2));
			role.setDescription(rs.getString(3));
			return role;
		}
	}
}
