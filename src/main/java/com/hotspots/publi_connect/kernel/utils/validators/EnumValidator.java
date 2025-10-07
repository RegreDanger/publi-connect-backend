package com.hotspots.publi_connect.kernel.utils.validators;

import java.util.Arrays;

import com.hotspots.publi_connect.kernel.utils.annotations.ValidEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

	private String[] acceptedValues;

	@Override
	public void initialize(ValidEnum annotation) {
		acceptedValues = Arrays.stream(annotation.enumClass().getEnumConstants())
							   .map(Enum::name)
							   .toArray(String[]::new);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return true; // @NotNull si lo necesitas aparte
		return Arrays.asList(acceptedValues).contains(value);
	}
}
