package com.shopme.admin;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("")
	public String viewHomePage(Authentication authentication) {
		String test=authentication.getPrincipal().toString();
		System.out.println(" test : " + test);
		return "index";
	}
}