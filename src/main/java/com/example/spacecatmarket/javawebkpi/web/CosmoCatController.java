package com.example.spacecatmarket.javawebkpi.web;

import com.example.spacecatmarket.javawebkpi.service.impl.CosmoCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/cats")
@RequiredArgsConstructor
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    @GetMapping
    public ResponseEntity<List<String>> getCosmoCats() {
        List<String> cats = cosmoCatService.getCosmoCats();
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/products")
    public ResponseEntity<List<String>> getProducts() {
        List<String> products = cosmoCatService.getKittyProducts();
        return ResponseEntity.ok(products);
    }
}
