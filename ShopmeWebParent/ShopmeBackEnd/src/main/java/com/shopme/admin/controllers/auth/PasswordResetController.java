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
		prSrv.save(pr);
		redirect.addFlashAttribute("success", "Email confirmation sent successful!");
		return "redirect:/auth/reset_password";
	}
	
	@GetMapping("/{token}")
	public String edit(@PathVariable UUID token,Model ui) {
		Optional<PasswordReset> pr = prSrv.findByToken(token);
		if(isTokenValid(pr,Duration.ofHours(1))) {
			Optional<User> user = usrSrv.findByEmail(pr.get().getEmail());
			PasswordResetRequest prr = new PasswordResetRequest();
			prr.setEmail(user.get().getEmail());
			prr.setToken(token);
			ui.addAttribute("user",prr);
		}
		else {
			//throw new PageNotFoundException("Invalid or expired token");
		}
		return "auths/form_reset_password";
	}
	
	@PatchMapping("/{token}/{email}")
	public String update(
			@Valid @ModelAttribute("user") PasswordResetRequest prr,
			@PathVariable String email, 
			@PathVariable UUID token,
			BindingResult valid,
			Model ui, 
			HttpServletRequest req,
			RedirectAttributes redirect) {
		System.out.println(" sebelum pengujian validasi!");
		if(valid.hasErrors()) {
			System.out.println(" error tapi sebelum print error terjadi ");
			ui.addAttribute("user",new PasswordResetRequest());
			//ui.addAttribute("user",prr);
			return "auths/form_reset_password";
		}
		Optional<User> getUser = usrSrv.findByEmail(prr.getEmail());
		User changedUser = usrSrv.updateUserPassword(prr.getPassword(), getUser.get());
		System.out.println("user setelah diganti password " + changedUser.getEmail());
		Authentication authentication = new UsernamePasswordAuthenticationToken(changedUser.getEmail(), changedUser.getPassword());
		System.out.println(authentication);
        authProvider.authenticate(authentication);
        System.out.println("akan redirect");
		return "redirect:/";
	}
	
	private boolean isTokenValid(Optional<PasswordReset> pr, Duration expirationTime) {
		if(pr.isPresent()) {
			LocalDateTime createdAt = pr.get().getCreated_at();
            LocalDateTime expiration = createdAt.plus(expirationTime);
            return LocalDateTime.now().isBefore(expiration);
		}
		return false;
	}
}