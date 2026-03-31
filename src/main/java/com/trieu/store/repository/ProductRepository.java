package com.trieu.store.repository;

import com.trieu.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Spring Data JPA sẽ tự động tạo các câu lệnh SQL cơ bản cho bảng products
}