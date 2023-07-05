package com.candle.store.repository;

import com.candle.store.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    ShoppingCart findByUserEmail(String email);
}
