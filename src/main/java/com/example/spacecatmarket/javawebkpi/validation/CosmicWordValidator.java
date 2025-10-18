package com.example.spacecatmarket.javawebkpi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {
    private final List<String> cosmicWords = List.of("star", "galaxy", "comet");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        String lowerValue = value.toLowerCase();
        return cosmicWords.stream().anyMatch(lowerValue::contains);
    }
}