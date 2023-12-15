package com.shopme.admin.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import com.shopme.common.entities.PasswordReset;


public interface PasswordResetRepository extends CrudRepository<PasswordReset, String>, ListCrudRepository<PasswordReset, String>{
	Optional<PasswordReset> findByToken(UUID token);
}
