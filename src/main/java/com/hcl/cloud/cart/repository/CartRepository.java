package com.hcl.cloud.cart.repository;

import com.hcl.cloud.cart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<ShoppingCart, String> {

    @Query(value="{'userId' : ?0}")
    ShoppingCart findByUserId(String userId);


}
