package com.shopme.admin.utilitas.validations;

import com.shopme.admin.requests.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ConfirmPasswordValidation implements ConstraintValidator<ConfirmPassword, UserRequest>{
	
	@Override
    public void initialize(final ConfirmPassword cp) {}

	@Override
	public boolean isValid(UserRequest value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
	    return value.getPassword().equals(value.getConfirmPassword());
	}
}
