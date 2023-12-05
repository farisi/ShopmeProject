package com.shopme.admin.utilitas.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String>{
	
	private String password;
	private String confirmPassword;
	
	@Override
    public void initialize(final ValidPassword constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
		Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);
		return (passwordValue !=null) ? passwordValue.equals(confirmPasswordValue) : confirmPasswordValue==null;
	}

}
