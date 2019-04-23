package com.hcl.cloud.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.cloud.cart.domain.ShoppingCart;

public interface CartRepository extends JpaRepository<ShoppingCart, String> {

    ShoppingCart findByUserId(String userId);

}
