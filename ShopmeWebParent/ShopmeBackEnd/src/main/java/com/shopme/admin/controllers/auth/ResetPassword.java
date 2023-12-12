package com.shopme.admin.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/reset_password")
public class ResetPassword {

	@GetMapping("")
	public String create() {
		return "auths/reset_password";
	}
	
	@PostMapping("")
	public String store() {
		return "redirect:/login";
	}
}
