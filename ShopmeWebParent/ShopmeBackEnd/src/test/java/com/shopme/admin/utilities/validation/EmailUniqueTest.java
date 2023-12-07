package com.shopme.admin.utilities.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import com.shopme.admin.user.UserService;
import com.shopme.admin.utilitas.validations.EmailUniqueValidator;

import jakarta.validation.ConstraintValidatorContext;

public class EmailUniqueTest {

	@InjectMocks
	private EmailUniqueValidator validator;
	
	@Mock
	private ConstraintValidatorContext context;
	
	@Mock
	private UserService usrSrv;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	
	@Test
	public void emailIsNotExistTest() {
		
		Mockito.when(usrSrv.isEmailExist(anyString()))
		.thenReturn(true);
		
		boolean status = validator.isValid("rizal.firdaus@gmail.com", context);
		assertFalse(status);
	}
	
	@Test
	public void emailIsExistTest() {

		Mockito.when(usrSrv.isEmailExist(anyString()))
		.thenReturn(false);
		
		boolean status = validator.isValid("rizal.firdaus@gmail.com", context);
		assertTrue(status);
	}
}
