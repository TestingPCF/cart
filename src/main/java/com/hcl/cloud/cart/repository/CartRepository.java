package com.hcl.cloud.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.cloud.cart.domain.Cart;
import org.springframework.data.jpa.repository.Query;

/**
 * CartRepository class that is responsible for the database operations.
 * @author baghelp
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Method to find cart by userId.
     * @param userId userId
     * @return cart cart.
     */
    @Query("select c from Cart c where c.userId = ?1")
    Cart findByUserId(String userId);

}
