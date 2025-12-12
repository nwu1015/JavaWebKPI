package com.example.spacecatmarket.javawebkpi.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(
            name = "category_seq",
            sequenceName = "category_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

     @OneToMany(mappedBy = "category")
     private List<ProductEntity> products;
}