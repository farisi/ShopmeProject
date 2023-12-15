package com.shopme.admin.utilitas.validations;

import com.shopme.admin.requests.ConfirmInterface;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ConfirmPasswordValidation implements ConstraintValidator<ConfirmPassword, ConfirmInterface>{
	
	@Override
    public void initialize(final ConfirmPassword cp) {}

	@Override
	public boolean isValid(ConfirmInterface user, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		 boolean isValid = user.getPassword() != null && user.getConfirmPassword() != null &&  user.getPassword().equals(user.getConfirmPassword());
	
		 if (!isValid) {

	         context.disableDefaultConstraintViolation();
			 //System.out.println("getDefaultConstraintMessageTemplate " + context.getDefaultConstraintMessageTemplate());
	         context.buildConstraintViolationWithTemplate("Password and confirm password must be matched!")
	         .addPropertyNode("confirmPassword")
	         .addConstraintViolation();
	         
		 }
	    return isValid;
	}
}
