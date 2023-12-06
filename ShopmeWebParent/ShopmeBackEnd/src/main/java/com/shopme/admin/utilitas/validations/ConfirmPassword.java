package com.shopme.admin.utilitas.validations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Payload;
import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = ConfirmPasswordValidation.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ConfirmPassword {

	String message() default "Passwords must match!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}