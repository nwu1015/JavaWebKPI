package com.example.spacecatmarket.javawebkpi.featuretoggle;

import com.example.spacecatmarket.javawebkpi.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.spacecatmarket.javawebkpi.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class FeatureToggleExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(testMethod -> {
            FeatureToggleService featureToggleService = getFeatureToggleService(context);

            if (testMethod.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggle = testMethod.getAnnotation(EnabledFeatureToggle.class);
                featureToggleService.enable(enabledFeatureToggle.value().getName());

            } else if (testMethod.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggle = testMethod.getAnnotation(DisabledFeatureToggle.class);
                featureToggleService.disable(disabledFeatureToggle.value().getName());
            }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(testMethod -> {
            String featureName = null;
            if (testMethod.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggle = testMethod.getAnnotation(EnabledFeatureToggle.class);
                featureName = enabledFeatureToggle.value().getName();

            } else if (testMethod.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggle = testMethod.getAnnotation(DisabledFeatureToggle.class);
                featureName = disabledFeatureToggle.value().getName();
            }

            if (featureName != null) {
                FeatureToggleService featureToggleService = getFeatureToggleService(context);
                if (getFeatureNameFromPropertiesAsBoolean(context, featureName)) {
                    featureToggleService.enable(featureName);
                } else {
                    featureToggleService.disable(featureName);
                }
            }
        });
    }

    private boolean getFeatureNameFromPropertiesAsBoolean(ExtensionContext context, String featureName) {
        Environment environment = SpringExtension.getApplicationContext(context).getEnvironment();
        String propertyKey = "feature.toggles." + featureName;
        return environment.getProperty(propertyKey, Boolean.class, false);
    }

    private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
        ApplicationContext appContext = SpringExtension.getApplicationContext(context);
        return appContext.getBean(FeatureToggleService.class);
    }
}