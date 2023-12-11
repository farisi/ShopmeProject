package com.shopme.admin.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopme.common.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
