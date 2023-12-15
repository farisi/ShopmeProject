package com.shopme.admin.utilitas.validations;

import com.shopme.admin.requests.PasswordResetRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordResetValidation implements ConstraintValidator<ConfirmPassword, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		PasswordResetRequest user = (PasswordResetRequest) value; 
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
