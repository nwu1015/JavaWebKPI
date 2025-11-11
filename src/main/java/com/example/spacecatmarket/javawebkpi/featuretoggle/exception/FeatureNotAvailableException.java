package com.example.spacecatmarket.javawebkpi.featuretoggle.exception;

public class FeatureNotAvailableException extends RuntimeException {
    private static final String FEATURE_NOT_AVAILABLE_MESSAGE = "Feature %s not available";


    public FeatureNotAvailableException(String message) {
        super(String.format(FEATURE_NOT_AVAILABLE_MESSAGE, message));
    }
}
