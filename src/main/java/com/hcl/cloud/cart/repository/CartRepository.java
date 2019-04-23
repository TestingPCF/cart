package com.hcl.cloud.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.cloud.cart.domain.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.userId = userId")
    Cart findByUserId(@Param("userId") String userId);

}
