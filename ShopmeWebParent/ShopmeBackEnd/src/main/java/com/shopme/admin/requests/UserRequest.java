package com.shopme.admin.requests;

import java.util.HashSet;
import java.util.Set;

import com.shopme.admin.utilitas.validations.ConfirmPassword;
import com.shopme.admin.utilitas.validations.EmailUnique;
import com.shopme.common.entities.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@ConfirmPassword(message="Password and confirm password must be matched!")
public class UserRequest implements ConfirmInterface {
	
	private Integer id;

	@EmailUnique
	@NotBlank(message="Email can not empty!!")
	private String email;
	
	private String confirmPassword;
	
	@NotBlank(message = "password can not empty!!")
	private String password;
	
	@NotBlank(message="Firstname can not blank!")
	private String firstName;	
	
	private String lastName;
	
	@NotEmpty
	private Set<Role> roles=new HashSet<>();
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
}
