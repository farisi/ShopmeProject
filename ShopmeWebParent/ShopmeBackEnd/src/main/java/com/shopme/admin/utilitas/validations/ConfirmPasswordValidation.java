package com.shopme.admin.utilitas.validations;

import com.shopme.admin.requests.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ConfirmPasswordValidation implements ConstraintValidator<ConfirmPassword, UserRequest>{
	
	@Override
    public void initialize(final ConfirmPassword cp) {}

	@Override
	public boolean isValid(UserRequest user, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		 boolean isValid = user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword());
		 System.out.println("sebelum test");
		 if (!isValid) {
			 System.out.println("sebelum penambahan ");
	         context.disableDefaultConstraintViolation();
			 System.out.println("getDefaultConstraintMessageTemplate " + context.getDefaultConstraintMessageTemplate());
	         context.buildConstraintViolationWithTemplate("Password and confirm password must be matched!")
	         .addPropertyNode("confirmPassword")
	         .addConstraintViolation();
	         System.out.println("Sesudah pemabahan violation");
		 }
	    return isValid;
	}
}
