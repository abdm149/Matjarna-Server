package com.matjarna.validation.endAfterStart;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.matjarna.constants.Constants;
import com.matjarna.exception.ValidationException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndAfterStartValidator implements ConstraintValidator<EndAfterStart, Durationable> {

	@Override
	public void initialize(EndAfterStart constraintAnnotation) {
	}

	@Override
	public boolean isValid(Durationable dto, ConstraintValidatorContext context) {
		if (dto == null || dto.getEndDate() == null) {
			return true;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		try {
			return formatter.parse(dto.getEndDate()).after(formatter.parse(dto.getStartDate()));
		} catch (ParseException e) {
			throw new ValidationException(e.getMessage());
		}
	}
}
