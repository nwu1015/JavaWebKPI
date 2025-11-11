package com.example.spacecatmarket.javawebkpi.service.impl;

import com.example.spacecatmarket.javawebkpi.featuretoggle.FeatureToggles;
import com.example.spacecatmarket.javawebkpi.featuretoggle.annotation.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatService {

    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public List<String> getCosmoCats() {
        return List.of("CosmoCat1", "CosmoCat2", "CosmoCat3");
    }

    @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    public List<String> getKittyProducts() {
        return List.of(
                "Space Catnip",
                "Cosmic Scratching Post",
                "Galactic Laser Toy"
        );
    }
}
