package com.shopme.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired(required = true)
	private UserService userSrv;

	
	@GetMapping("")
	public String index(Model ui) {
		List<User> users =userSrv.all();
		ui.addAttribute("users",users);
		return "users/index";
	}
	
	@GetMapping("/create")
	public String create(Model ui) {
		ui.addAttribute("user",new UserRequest());		
		return "users/create";
	}
	
	@PostMapping("")
	public String store(@Valid  @ModelAttribute("user")  UserRequest user, BindingResult valid,Model ui,RedirectAttributes redirectAttributes) {
		if(valid.hasErrors()) {
			for(FieldError e : valid.getFieldErrors()) {
				System.out.println( " field " + e.getField() + " messages " + e.getDefaultMessage());
			}
			ui.addAttribute("user",user);
			return "users/create";
		}
		userSrv.save(user);
		return "redirect:/users";
	}
}
