package demo.test;

import java.util.Date;
import java.util.logging.Logger;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.controller.UserController;
import demo.entity.User;

public class TestUserDanamicProxy {

	
	private BeanFactory factory = new ClassPathXmlApplicationContext("application_context.xml");
	
	private static final Logger log = Logger.getLogger("TestUserDanamicProxy");
	
	@Test
	public void test(){
		
		UserController userController = factory.getBean("userController", UserController.class);
		userController.setId(1);
		userController.setUser(new User(1, "张飞", new Date()));
		
		log.info("================================================");
		userController.saveUser();
		log.info("================================================");
		userController.updateUser();
		log.info("================================================");
		userController.loadUser();
		log.info("================================================");
		userController.deleteUser();
		log.info("================================================");
		
	}
}
