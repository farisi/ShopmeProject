package com.shopme.admin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.user.EmailService;
import com.shopme.admin.user.RoleService;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired(required = true)
	private UserService userSrv;

	@Autowired
	private RoleService roleSrv;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping("")
	public String index(Model ui) {
		List<User> users =userSrv.all();
		ui.addAttribute("users",users);
		return "users/index";
	}
	
	@GetMapping("/create")
	public String create(Model ui) {
		ui.addAttribute("user",new UserRequest());		
		ui.addAttribute("roles", roleSrv.all());
		return "users/create";
	}
	
	@PostMapping("")
	public String store(@Valid  @ModelAttribute("user")  UserRequest user, BindingResult valid,Model ui,RedirectAttributes redirectAttributes) {
		if(valid.hasErrors()) {
			ui.addAttribute("user",user);
			ui.addAttribute("roles", roleSrv.all());
			return "users/create";
		}
		userSrv.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Integer id, Model ui) {
		User user = userSrv.findUserById(id);
		ui.addAttribute("user",user);
		return "/users/profile";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Integer id, Model ui) {
		UserRequest user = userSrv.findById(id);
		ui.addAttribute("user",user);
		ui.addAttribute("roles",roleSrv.all());
		
		return "/users/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@PathVariable Integer id, @ModelAttribute("user") UserRequest user) {
		userSrv.save(user);
		String to = user.getEmail();
        String subject = "Test Email";
        String body = "Hello, this is a test email from Spring Boot.";

        emailService.sendEmail(to, subject, body);
		return "redirect:/users";
	}
	
	@DeleteMapping("/{id}")
	public String remove(@PathVariable Integer id) {
		User user = userSrv.findUserById(id);
		userSrv.remove(user);
		return "redirect:/users";
	}
	
	@PatchMapping("/{id}/enabled")
	public String user_enable(@PathVariable Integer id, @ModelAttribute("user") User user) {
		return "redirect:/users";
	}
}
