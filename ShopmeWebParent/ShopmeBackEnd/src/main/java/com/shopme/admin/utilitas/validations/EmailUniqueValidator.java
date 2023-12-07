package com.shopme.admin.utilitas.validations;

import org.springframework.beans.factory.annotation.Autowired;

import com.shopme.admin.user.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailUniqueValidator  implements ConstraintValidator<EmailUnique,String> {
	
	@Autowired
	private UserService usrSrv;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		System.out.println("value : " + value);
		boolean isExist = usrSrv.isEmailExist(value);
		return !isExist;
	}

}
