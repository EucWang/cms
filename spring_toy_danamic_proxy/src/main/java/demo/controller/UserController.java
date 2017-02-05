package demo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import demo.entity.User;
import demo.service.IUserService;

@Controller("userController")
public class UserController {
	
	
	private User user;
	
	private Integer id;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Resource
	private IUserService userService;
	
	public void saveUser(){
		userService.save(user);
	}
	
	public void loadUser(){
		userService.load(id);
	}
	
	public void updateUser(){
		userService.update(user);
	}
	
	public void deleteUser(){
		userService.delete(id);
	}

}
