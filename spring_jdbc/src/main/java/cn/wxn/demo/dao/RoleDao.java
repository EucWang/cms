package cn.wxn.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import cn.wxn.demo.exception.RoleException;

@Repository("roleDao")
public class RoleDao implements IRoleDao {

	private static Logger log = Logger.getLogger("RoleDao");

	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public Role add(final Role role) throws RoleException{
		Role loadByName = loadByName(role.getName());
		if (loadByName != null) {
			throw new RoleException("数据库中已经存在相同的一条数据，不能插入名称相同的ROle");
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
		log.warning("插入数据库失败");
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

	@Override
	public boolean delete(Long id) {
		String sql = "delete from t_role where id=?";
		int update = jdbcTemplate.update(sql, id);
		return update>0?true:false;
	}

	@Override
	public boolean update(Role role) throws RoleException{
		Role load = loadById(role.getId());
		if (load == null) {
			throw new RoleException("不存在需要更新的角色");
		}
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("update t_role set ");
		List<String> toAppend = new ArrayList<>();
		if (!load.getName().equals(role.getName())) {
			toAppend.add("name='" + role.getName() + "' ");
		}
		
		if ((load.getDescription() != null && !load.getDescription().equals(role.getDescription()))
				|| (role.getDescription() != null && !role.getDescription().equals(load.getDescription()))) {
			toAppend.add("description='" + role.getDescription() + "' ");
		}
		
		if (toAppend.size() == 0) {
			throw new RoleException("角色信息相同，不需要更新");
		}

		for(int i=0; i<toAppend.size();i++){
			sBuffer.append(toAppend.get(i));
			if (i != toAppend.size() -1) {
				sBuffer.append(",");
			}
		}
		sBuffer.append(" where id=?");
		int update = jdbcTemplate.update(sBuffer.toString(), new Object[] {role.getId()});
		if (update > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Role> list() {
		String sql = "select * from t_role";
		List<Role> list = jdbcTemplate.query(sql, new RoleRowMapper());
		return list;
	}
}
