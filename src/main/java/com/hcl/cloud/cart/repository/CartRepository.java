package com.hcl.cloud.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.cloud.cart.domain.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * CartRepository class that is responsible for the database operations.
 * @author baghelp
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Method to find cart by userId.
     * @param userId
     * @return
     */
    @Query("select c from Cart c where c.userId = userId")
    Cart findByUserId(@Param("userId") String userId);

}
