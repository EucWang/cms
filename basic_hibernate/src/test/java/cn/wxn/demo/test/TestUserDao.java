package cn.wxn.demo.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.wxn.demo.test.base.AbstractDbUnitTestCase;
import cn.wxn.demo.test.base.EntitiesHelper;
import cn.wxn.demo.test.dao.IUserDao;
import cn.wxn.demo.test.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase {

	@Inject
	private IUserDao userDao;

	@Inject
	private SessionFactory sessionFactory;

	private Session s;
	
	@Before
	public void setUp() throws DataSetException, SQLException, IOException {
		s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
	}

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
		
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		s= sessionHolder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		
		this.resumeTable();
	}

	@Test
	public void testLoad() throws DatabaseUnitException, SQLException {
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);

		User user = userDao.load(1);
		EntitiesHelper.assertUser(user);
		System.out.println("user:" + user);
		System.out.println("testLoad() over");
	}
	
	@Test(expected=ObjectNotFoundException.class)
	public void testDelete() throws DatabaseUnitException, SQLException {
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		
		userDao.delete(1);
		s.flush();                   //强制session刷新缓存, 让语句执行到数据库,不然delete语句会留到最后才执行
		User user = userDao.load(1);
		System.out.println("abc : " + user.getName());
		System.out.println("testDelete over");
	}
	
	
	@Test
	public void testListByArgs() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		//TODO
	}
	
	@Test
	public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		//TODO
	}
	
	@Test
	public void testFindByAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		//TODO
	}
	
	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		//TODO
	}
	
	@Test
	public void testListSQLByAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		//TODO
	}
	
	@Test
	public void testListSQLByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		//TODO
	}
}
