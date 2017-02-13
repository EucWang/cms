package cn.wxn.demo.test;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.wxn.demo.dao.IRoleDao;
import cn.wxn.demo.entity.Role;
import cn.wxn.demo.exception.RoleException;

public class TestRoleDao extends AbstractDbunitTestCase {

	@Resource
	private IRoleDao roleDao;
	private IDataSet dataSetRole;

	@Before
	public void setUp() {
		try {
			backupAllTable();
			dataSetRole = createDataSet("t_role");
			DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dataSetRole);
		} catch (SQLException | IOException | DatabaseUnitException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAdd() {
		try {

			DatabaseOperation.TRUNCATE_TABLE.execute(dbunitCon, dataSetRole);

			try {
				roleDao.add(EntitiesHelper.baseRole);
			} catch (RoleException e) {
				e.printStackTrace();
			}

			Role load = roleDao.loadById(1L);
			EntitiesHelper.equalsRole(load);
		} catch (DatabaseUnitException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoadByName() {
//		fail("please add test code");
		EntitiesHelper.equalsRole(roleDao.loadByName("admin"));
	}

	@Test
	public void testLoadById() {
//		fail("please add test code");
		EntitiesHelper.equalsRole(roleDao.loadById(1L));
	}

	@Test
	public void testUpdate() {
		fail("please add test code");
	}

	@Test
	public void testDelete() {
		fail("please add test code");
	}

	@Test
	public void testList() {
		fail("please add test code");
	}

	@After
	public void tearDown() {
		try {
			resumeTable();
		} catch (FileNotFoundException | DatabaseUnitException | SQLException e) {
			e.printStackTrace();
		}
	}
}
