package com.shopme.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopme.admin.user.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userSrv;

	@GetMapping("")
	public String index() {
		return "users/index";
	}
	
	@GetMapping("/create")
	public String create() {
		return "users/create";
	}
	
	@PostMapping("/")
	public String store() {
		return "redirect:/users";
	}
}
