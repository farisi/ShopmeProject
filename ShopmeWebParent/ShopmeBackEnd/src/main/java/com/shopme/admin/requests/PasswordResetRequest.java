package com.shopme.admin.requests;

import java.util.UUID;

import com.shopme.admin.utilitas.validations.ConfirmPassword;


import jakarta.validation.constraints.NotBlank;

@ConfirmPassword(message="Password and confirm password must be matched!")
public class PasswordResetRequest implements ConfirmInterface {
	
	private String email;
	private UUID token;
	
	@NotBlank(message = "password can not empty!")
	private String password;
	
	@NotBlank(message="Password confirmation can not empty!")
	private String confirmPassword;
	
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
	@Override
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public void setToken(UUID token) {
		this.token=token;
	}
	
	public UUID getToken() {
		return token;
	}
}
