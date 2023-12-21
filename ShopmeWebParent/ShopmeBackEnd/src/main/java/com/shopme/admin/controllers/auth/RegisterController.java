package com.shopme.admin.controllers.auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired(required = true)
	private UserService userSrv;

	@Autowired
	private RoleService roleSrv;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	DaoAuthenticationProvider authProvider;
	
		
	@GetMapping("")
	public String create(Model ui) {
		ui.addAttribute("user",new UserRequest());		
		ui.addAttribute("roles", roleSrv.all());
		return "registers/create";
	}
	
	@PostMapping("")
	public String store(@Valid  @ModelAttribute("user")  UserRequest user, BindingResult valid,Model ui,RedirectAttributes redirectAttributes) {
		if(valid.hasErrors()) {
			ui.addAttribute("user",user);
			ui.addAttribute("roles", roleSrv.all());
			return "users/create";
		}
		User changedUser = userSrv.save(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(changedUser.getEmail(), changedUser.getPassword());
		
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        authProvider.authenticate(authentication);
        System.out.println(" register tersimpan!");
		return "redirect:/";
	}
}
