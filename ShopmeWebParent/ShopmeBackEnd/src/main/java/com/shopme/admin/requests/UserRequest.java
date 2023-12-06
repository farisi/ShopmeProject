package com.shopme.admin.requests;

import com.shopme.admin.utilitas.validations.ConfirmPassword;

import jakarta.validation.constraints.NotBlank;

@ConfirmPassword(message="Password and confirm password must be matched!")
public class UserRequest {

	@NotBlank(message="Email can not empty!!")
	private String email;
	
	private String confirmPassword;
	
	@NotBlank(message = "password can not empty!!")
	private String password;
	
	@NotBlank(message="Firstname can not blank!")
	private String firstName;
	
	
	private String lastName;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
