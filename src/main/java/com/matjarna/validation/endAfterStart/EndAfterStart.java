package com.matjarna.validation.endAfterStart;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EndAfterStartValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndAfterStart {

	String message() default "End date must be after start date";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
