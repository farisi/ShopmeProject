 package com.shopme.admin.controllers;

import java.util.ArrayList;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String index(
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int pagenum, 
			@RequestParam(defaultValue = "10") int pagesize, 
			@RequestParam(defaultValue = "firstName,asc") String[] sort,
			Model ui) {
		
		try {
		      List<User> users = new ArrayList<User>();
		      
		      String sortField = sort[0];
		      String sortDirection = sort[1];
		      
		      Direction direction;
			if (sortDirection.equals("desc"))
			{
				direction = Sort.Direction.DESC;
			}
			else
			{
				direction = Sort.Direction.ASC;
			}
		    Order order = new Order(direction, sortField);
		      
		    Pageable pageable = PageRequest.of(pagenum - 1, pagesize, Sort.by(order));

		      Page<User> pageUser;
		      if (keyword == null) {
		        pageUser = userSrv.all(pageable);
		      } else {
		    	pageUser = userSrv.findByKeywordLike(keyword, pageable);
		        ui.addAttribute("keyword", keyword);
		      }

		      users = pageUser.getContent();
		      
		      ui.addAttribute("users", users);
		      ui.addAttribute("currentPage", pageUser.getNumber() + 1);
		      ui.addAttribute("totalItems", pageUser.getTotalElements());
		      ui.addAttribute("totalPages", pageUser.getTotalPages());
		      ui.addAttribute("pageSize", pagesize);
		      ui.addAttribute("sortField", sortField);
		      ui.addAttribute("sortDirection", sortDirection);
		      ui.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		    } catch (Exception e) {
		      ui.addAttribute("message", e.getMessage());
		    }

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
