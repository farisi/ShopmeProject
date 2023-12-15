package com.shopme.admin.services;

import java.util.Optional;
import java.util.UUID;

import com.shopme.common.entities.PasswordReset;

public interface PasswordResetService {
	public PasswordReset findById(String email);
	public PasswordReset save(PasswordReset rp);
	public Optional<PasswordReset> findByToken(UUID token);
}
