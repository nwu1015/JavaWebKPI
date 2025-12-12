package com.example.spacecatmarket.javawebkpi.repository;

import com.example.spacecatmarket.javawebkpi.repository.entity.ProductEntity;
import com.example.spacecatmarket.javawebkpi.repository.projection.TopProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByCategoryId(Long categoryId);

    @Query("SELECT p.name as productName, SUM(oi.quantity) as totalSold " +
            "FROM OrderItemEntity oi " +
            "JOIN oi.product p " +
            "GROUP BY p.id, p.name " +
            "ORDER BY totalSold DESC")
    List<TopProductProjection> findTopSellingProducts();
}