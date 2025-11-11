package com.example.spacecatmarket.javawebkpi.featuretoggle.aspect;

import com.example.spacecatmarket.javawebkpi.featuretoggle.FeatureToggleService;
import com.example.spacecatmarket.javawebkpi.featuretoggle.FeatureToggles;
import com.example.spacecatmarket.javawebkpi.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatmarket.javawebkpi.featuretoggle.exception.FeatureNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggleAnnotation(ProceedingJoinPoint joinPoint,
                                               FeatureToggle featureToggle) throws Throwable {

        return checkFlag(joinPoint, featureToggle);
    }

    public Object checkFlag(ProceedingJoinPoint joinPoint,
                            FeatureToggle featureToggle) throws Throwable{

        FeatureToggles toggleEnum = featureToggle.value();
        String featureName = toggleEnum.getName();

        if (featureToggleService.check(featureName)) {
            return joinPoint.proceed();
        } else {
            throw new FeatureNotAvailableException(featureName);
        }
    }
}