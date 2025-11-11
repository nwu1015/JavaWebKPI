package com.example.spacecatmarket.javawebkpi.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {
    COSMO_CATS("cosmoCats"),
    KITTY_PRODUCTS("kittyProducts");

    private final String name;

    FeatureToggles(String name) {
      this.name = name;
    }
}
