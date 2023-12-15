package com.shopme.common.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="password_resets")
public class PasswordReset {

	public PasswordReset() {
		this.token = UUID.randomUUID();
	}
	
	@Id	
	@Email
	@NotBlank(message="Email could not empty!")
	private String email;
	
	private UUID token;
	
	@CreatedDate
	private LocalDateTime created_at=LocalDateTime.now();
	
	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public UUID getToken() {
		return token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		
}
