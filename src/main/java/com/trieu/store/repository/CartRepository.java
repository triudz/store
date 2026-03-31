package com.trieu.store.repository;

import com.trieu.store.entity.Cart;
import com.trieu.store.entity.User;
import com.trieu.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
}