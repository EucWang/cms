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

import com.mysql.jdbc.log.Log;

import cn.wxn.demo.basic_hibernate.model.Pager;
import cn.wxn.demo.basic_hibernate.model.SystemContext;
import cn.wxn.demo.test.base.AbstractDbUnitTestCase;
import cn.wxn.demo.test.base.EntitiesHelper;
import cn.wxn.demo.test.dao.IUserDao;
import cn.wxn.demo.test.entity.User;
import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase {

	@Inject
	private IUserDao userDao;

	@Inject
	private SessionFactory sessionFactory;

	private Session s;

	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));

		this.backupAllTable();
		IDataSet dateSet = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dateSet);
	}

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		s = sessionHolder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);

		this.resumeTable();
	}

	@Test
	public void testLoad() throws DatabaseUnitException, SQLException {
		User user = userDao.load(1);
		EntitiesHelper.assertUser(user);
		System.out.println("user:" + user.toString());
		System.out.println("testLoad() over");
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testDelete() throws DatabaseUnitException, SQLException {
		userDao.delete(1);
		s.flush(); // 强制session刷新缓存, 让语句执行到数据库,不然delete语句会留到最后才执行
		User user = userDao.load(1);
		System.out.println("abc : " + user.getUsername());
		System.out.println("testDelete over");
	}

	@Test
	public void testListByArgs() throws DatabaseUnitException, SQLException {
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		List<User> list = userDao.list("from User where id>? and id<?", new Object[] { 1, 4 });

		Assert.assertNotNull(list);
		Assert.assertEquals(new Integer(list.size()), new Integer(2));
		Assert.assertEquals(list.get(0).getId(), new Integer(2));
		Assert.assertEquals(list.get(0).getUsername(), "admin2");
		Assert.assertEquals(list.get(1).getId(), new Integer(3));
		Assert.assertEquals(list.get(1).getUsername(), "admin3");
	}

	@Test
	public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException {
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[] { 3, 5, 6 });
		alias.put("ids", list2);
		List<User> list = userDao.list("from User where id>? and id<? and id in (:ids)", new Object[] { 2, 7 }, alias);

		Assert.assertNotNull(list);
		Assert.assertEquals(new Integer(list.size()), new Integer(3));
		Assert.assertEquals(list.get(0).getId(), new Integer(3));
		Assert.assertEquals(list.get(0).getUsername(), "admin3");
		Assert.assertEquals(list.get(1).getId(), new Integer(5));
		Assert.assertEquals(list.get(1).getUsername(), "admin5");
		Assert.assertEquals(list.get(2).getId(), new Integer(6));
		Assert.assertEquals(list.get(2).getUsername(), "admin6");
	}

	@Test
	public void testFindByAlias() throws DatabaseUnitException, SQLException {
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setItemCountOfAPage(3);
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[] { 3, 5, 6 });
		alias.put("ids", list2);
		Pager<User> list = userDao.find("from User where id in (:ids)", alias);

		Assert.assertNotNull(list);
		Assert.assertEquals(new Integer(list.getItemsCountOAPage()), new Integer(3));
		Assert.assertEquals(list.getDatas().get(0).getId(), new Integer(3));
		Assert.assertEquals(list.getDatas().get(0).getUsername(), "admin3");
		Assert.assertEquals(list.getDatas().get(1).getId(), new Integer(5));
		Assert.assertEquals(list.getDatas().get(1).getUsername(), "admin5");
		Assert.assertEquals(list.getDatas().get(2).getId(), new Integer(6));
		Assert.assertEquals(list.getDatas().get(2).getUsername(), "admin6");
	}

	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException {

		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		SystemContext.setItemCountOfAPage(3);
		SystemContext.setPageOffset(3);
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[] { 3, 5, 6, 7, 8, 9, 11, 12, 13, });
		alias.put("ids", list2);
		Pager<User> find = userDao.find("from User where id>? and id<? and id in (:ids)", new Object[] { 2, 17 },
				alias);

		Assert.assertNotNull(find);
		Assert.assertEquals(find.getCountOfItems(), 9);
		Assert.assertEquals(find.getDatas().size(), 3);
		EntitiesHelper.assertUser(find.getDatas().get(0), new User(7, "admin7"));
		EntitiesHelper.assertUser(find.getDatas().get(1), new User(8, "admin8"));
		EntitiesHelper.assertUser(find.getDatas().get(2), new User(9, "admin9"));
	}

	@Test
	public void testListSQLByAlias() throws DatabaseUnitException, SQLException {
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		SystemContext.removeItemCountOfAPage();
		SystemContext.removePageOffset();
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[] { 3, 5, 6, 7, 8, 9, 11, 12, 13 });
		alias.put("ids", list2);
		String sql = "select * from t_user where id in (:ids)";
		List<User> list = userDao.listBySql(sql, alias, User.class, true);

		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 9);
		EntitiesHelper.assertUser(list.get(0), new User(13, "admin13"));
		EntitiesHelper.assertUser(list.get(1), new User(12, "admin12"));
		EntitiesHelper.assertUser(list.get(2), new User(11, "admin11"));
	}

	@Test
	public void testListSQLByArgsAndAlias() throws DatabaseUnitException, SQLException {
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		String sql = "select * from t_user u where u.id > ? and u.id < ? and u.id in (:ids)";
		Object[] args = new Object[] { 2, 10 };
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[] { 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
		alias.put("ids", list2);
		List<User> users = userDao.listBySql(sql, args, alias, User.class, true);

		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 4); 
		Assert.assertEquals(users.get(0).getId().intValue(), 6);
		Assert.assertEquals(users.get(0).getUsername(), "admin6");
	}
	
	@Test
	public void testFindBySql(){
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		SystemContext.setItemCountOfAPage(3);
		SystemContext.setPageOffset(0);
		String sql = "select * from t_user u where u.id > ? and u.id < ? and u.id in (:ids)";
		Object[] args = new Object[] { 2, 10 };
		Map<String, Object> alias = new HashMap<>();
		List<Integer> list2 = Arrays.asList(new Integer[] { 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
		alias.put("ids", list2);
		Pager<User> users = userDao.findBySql(sql, args, alias, User.class, true);

		Assert.assertNotNull(users);
		Assert.assertEquals(users.getDatas().size(), 3); 
		Assert.assertEquals(users.getCountOfItems(), 4);
		EntitiesHelper.assertUser(users.getDatas().get(0), new User(9,"admin9"));
		EntitiesHelper.assertUser(users.getDatas().get(1), new User(8,"admin8"));
		EntitiesHelper.assertUser(users.getDatas().get(2), new User(7,"admin7"));
	}


	@Test
	public void testupdateByHql(){
		String hql = "update User s set s.username=? where s.id=?";
		Object[] args = new Object[]{"zhangsan", 1};
		userDao.updateByHql(hql, args);
		s.flush();
		
		User user = userDao.load(1);
		EntitiesHelper.assertUser(user, new User(1, "zhangsan"));
		
	}

	@Test
	public void testQueryObject(){
		String hql = "select u.username from User u where id=?";
//		Map<String, Object> alias = new HashMap<String, Object>();
//		alias.put("id", 1);
		Object obj =  userDao.queryObject(hql, 1);
//		System.out.println(obj);
		Assert.assertEquals((String)obj, "admin1");
	}

	@Test
	public void testUpdate(){
		User user = userDao.load(1);
		user.setUsername("zhangsan");
		userDao.update(user);
		s.flush();
		User user2 = userDao.load(1);
		Assert.assertEquals(user2.getUsername(), "zhangsan");
	}

	@Test
	public void testAdd(){
		User user  = new User(21, "admin21");
		userDao.add(user);
		s.flush();
		User user2 = userDao.load(21);
		EntitiesHelper.assertUser(user2, user);
	}


}
