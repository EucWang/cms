package cn.wxn.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping("/index")
	public String index(){
		System.out.println("ADMIN_CONTROLLER");
		return "admin";
	}

}
