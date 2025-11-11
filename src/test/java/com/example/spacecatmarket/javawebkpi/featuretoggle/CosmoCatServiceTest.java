package com.example.spacecatmarket.javawebkpi.featuretoggle;

import com.example.spacecatmarket.javawebkpi.featuretoggle.exception.FeatureNotAvailableException;
import com.example.spacecatmarket.javawebkpi.service.impl.CosmoCatService;

import com.example.spacecatmarket.javawebkpi.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.spacecatmarket.javawebkpi.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(FeatureToggleExtension.class)
class CosmoCatServiceTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void testGetCosmoCatsWhenFeatureIsEnabled() {
        assertDoesNotThrow(() -> {
            List<String> cats = cosmoCatService.getCosmoCats();
            assertFalse(cats.isEmpty());
            assertTrue(cats.contains("CosmoCat1"));
        });
    }

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void testGetCosmoCatsWhenFeatureIsDisabled() {
        assertThrows(FeatureNotAvailableException.class, () -> cosmoCatService.getCosmoCats());
    }

    @Test
    void testGetKittyProductsWhenDisabledByDefault() {
        assertThrows(FeatureNotAvailableException.class, () -> cosmoCatService.getKittyProducts());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void testGetKittyProductsWhenEnabled() {
        assertDoesNotThrow(() -> {
            var products = cosmoCatService.getKittyProducts();
            assertFalse(products.isEmpty());
        });
    }
}