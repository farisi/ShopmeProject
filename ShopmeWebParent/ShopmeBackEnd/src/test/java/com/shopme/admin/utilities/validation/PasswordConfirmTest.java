package com.shopme.admin.utilities.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.shopme.admin.requests.UserRequest;
import com.shopme.admin.utilitas.validations.ConfirmPasswordValidation;

import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmTest {
	@InjectMocks
	private ConfirmPasswordValidation validator;
	
	@Mock
	private ConstraintValidatorContext context;
	
	@Test
	void testValidPasswords() {
		UserRequest usrReq = new UserRequest();
		usrReq.setPassword("123456");
		usrReq.setConfirmPassword("123456");
		boolean isValid = validator.isValid(usrReq, context);
		assertTrue(isValid);
	}
	
	@Test
	void testInvalidPasswords() {
		UserRequest usrReq = new UserRequest();
		usrReq.setPassword("password123");
		usrReq.setConfirmPassword("differentPassword");
		boolean isValid = validator.isValid(usrReq, context);
		assertFalse(isValid);
	}
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

	    System.out.println(" terjadi pada setUp " + context.buildConstraintViolationWithTemplate("Password and confirm password must be matched!"));

	    Mockito.lenient().when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
	            .then(invocation -> {
	                ConstraintValidatorContext.ConstraintViolationBuilder builder =
	                        Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class,
	                                Mockito.CALLS_REAL_METHODS);

	                // Override addPropertyNode() to return a non-null NodeBuilderCustomizableContext
	                Mockito.when(builder.addPropertyNode(Mockito.anyString()))
	                        .thenReturn(Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class));

	                return builder;
	            });
		
	}
}
