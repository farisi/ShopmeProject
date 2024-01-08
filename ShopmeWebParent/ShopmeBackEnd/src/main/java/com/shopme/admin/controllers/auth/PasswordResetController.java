package com.shopme.admin.controllers.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.exceptions.PageNotFoundException;
import com.shopme.admin.requests.PasswordResetRequest;
import com.shopme.admin.services.AuthenticationService;
import com.shopme.admin.services.PasswordResetService;
import com.shopme.admin.user.UserService;
import com.shopme.common.entities.PasswordReset;
import com.shopme.common.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth/reset_password")
public class PasswordResetController {

	@Autowired
	private PasswordResetService prSrv;
	
	@Autowired
	UserService usrSrv;
	
	@Autowired
	DaoAuthenticationProvider authProvider;
	
	@GetMapping("")
	public String create(Model ui) {
		ui.addAttribute("pr",new PasswordReset());
		return "auths/reset_password";
	}
	
	@PostMapping("")
	public String store(@Valid @ModelAttribute("pr") PasswordReset pr,
			BindingResult valid,
			Model ui, 
			RedirectAttributes redirect) {
		if(valid.hasErrors()) {
			ui.addAttribute("pr",pr);
			return "auths/reset_password";
		}
		if(usrSrv.findByEmail(pr.getEmail()).isPresent()) {
			prSrv.save(pr);
			redirect.addFlashAttribute("success", "Email confirmation sent successful!");
		}
		else {
			throw new PageNotFoundException("Your email could not found");
		}
		return "redirect:/auth/reset_password";
	}
	
	@GetMapping("/{token}")
	public String edit(@PathVariable UUID token,Model ui) {
		Optional<PasswordReset> pr = prSrv.findByToken(token);
		if(pr.isPresent()) {
			if(prSrv.isTokenValid(pr.get(),Duration.ofHours(1))) {
				Optional<User> user = usrSrv.findByEmail(pr.get().getEmail());
				if(user.isPresent()) {
					PasswordResetRequest prr = new PasswordResetRequest();
					prr.setEmail(user.get().getEmail());
					prr.setToken(token);
					ui.addAttribute("user",prr);
				}
				else {
					throw new PageNotFoundException("Invalid Email");
				}
			}
			else {
				throw new PageNotFoundException("Invalid or expired token");
			}
		}
		else {
			throw new PageNotFoundException("Invalid Token");
		}
		return "auths/form_reset_password";
	}
	
	@PatchMapping("/{token}/{email}")
	public String update(
			@PathVariable String email, 
			@PathVariable UUID token,
			@Valid @ModelAttribute("user") PasswordResetRequest prr,
			BindingResult valid,
			Model ui, 
			RedirectAttributes redirect) {
		if(valid.hasErrors()) {
			ui.addAttribute("user",prr);
			return "auths/form_reset_password";
		}
		
		Optional<User> getUser = usrSrv.findByEmail(prr.getEmail());
		if(getUser.isPresent()) {
			User changedUser = usrSrv.updateUserPassword(prr.getPassword(), getUser.get());
			return "redirect:/";
		}
		else {
			throw new PageNotFoundException("Your email could not be found");
		}
	}
}