package cn.wxn.demo.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.basic_hibernate.model.SystemContext;
import cn.wxn.demo.test.base.AbstractDbUnitTestCase;
import cn.wxn.demo.test.base.EntitiesHelper;
import cn.wxn.demo.test.dao.IUserDao;
import cn.wxn.demo.test.entity.User;
import junit.framework.Assert;

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
		
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		List<User> list = userDao.list("from User where id>? and id<?", new Object[]{1,4});
		
		Assert.assertNotNull(list);
		Assert.assertEquals(new Integer(list.size()), new Integer(2));
		Assert.assertEquals(list.get(0).getId(), new Integer(2));
		Assert.assertEquals(list.get(0).getName(),"admin2");
		Assert.assertEquals(list.get(1).getId(), new Integer(3));
		Assert.assertEquals(list.get(1).getName(),"admin3");
	}
	
	@Test
	public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		
		
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[]{3,5,6});
		alias.put("ids", list2);
		List<User> list = userDao.list("from User where id>? and id<? and id in (:ids)", new Object[]{2,7}, alias);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(new Integer(list.size()), new Integer(3));
		Assert.assertEquals(list.get(0).getId(), new Integer(3));
		Assert.assertEquals(list.get(0).getName(),"admin3");
		Assert.assertEquals(list.get(1).getId(), new Integer(5));
		Assert.assertEquals(list.get(1).getName(),"admin5");
		Assert.assertEquals(list.get(2).getId(), new Integer(6));
		Assert.assertEquals(list.get(2).getName(),"admin6");
	}
	
	@Test
	public void testFindByAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		

		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[]{3,5,6});
		alias.put("ids", list2);
		List<User> list = userDao.list("from User where id>? and id<? and id in (:ids)", new Object[]{2,7}, alias);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(new Integer(list.size()), new Integer(3));
		Assert.assertEquals(list.get(0).getId(), new Integer(3));
		Assert.assertEquals(list.get(0).getName(),"admin3");
		Assert.assertEquals(list.get(1).getId(), new Integer(5));
		Assert.assertEquals(list.get(1).getName(),"admin5");
		Assert.assertEquals(list.get(2).getId(), new Integer(6));
		Assert.assertEquals(list.get(2).getName(),"admin6");
	}
	
	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[]{3,5,6,7,8,9,11,12,13,});
		alias.put("ids", list2);
		Pager<User> find = userDao.find("from User where id>? and id<? and id in (:ids)", new Object[]{2,17}, alias);
		
		Assert.assertNotNull(find);
		Assert.assertEquals(find.getCountOfItems(), 9);
		Assert.assertEquals(find.getDatas().size(), 5);
	}
	
	@Test
	public void testListSQLByAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[]{3,5,6,7,8,9,11,12,13,});
		alias.put("ids", list2);
		Pager<User> find = userDao.find("from User where  id in (:ids)",  alias);
		
		Assert.assertNotNull(find);
		Assert.assertEquals(find.getCountOfItems(), 9);
		Assert.assertEquals(find.getDatas().size(), 5);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testListSQLByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
		

		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		String sql = "select * from t_user u where u.id > ? and u.id < ? and u.id in (:ids)";
		Object[] args  = new Object[]{2,10};
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[]{6,7,8,9,10, 11,12,13,14,15});
		alias.put("ids", list2);
		Class clazz = User.class;
		boolean hasEntity = true;
		 List<User> users = userDao.listBySql(sql, args, alias, clazz, hasEntity);
		
		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 4);
		Assert.assertEquals(users.size(), 4);
		Assert.assertEquals(users.get(0).getId().intValue(), 6);
		Assert.assertEquals(users.get(0).getName(), "admin6");
	}
}
