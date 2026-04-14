package com.trieu.store.repository;

import com.trieu.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    // ĐÃ SỬA: Đổi từ List sang Page và thêm tham số Pageable ở cuối
    @Query("SELECT p FROM Product p WHERE " +
           "(p.name LIKE %?1% OR CAST(p.id as string) LIKE %?1%) " +
           "AND (p.price >= ?2 AND p.price <= ?3) " +
           "AND (?4 = -1 OR p.category.id = ?4)")
    Page<Product> filterProducts(String keyword, BigDecimal minPrice, BigDecimal maxPrice, Integer categoryId, Pageable pageable);
}