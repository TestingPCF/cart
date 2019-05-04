package com.hcl.cloud.cart.service;

import java.io.IOException;

import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.exception.CustomException;

public interface CartService {

    /**
     * This method adds the passed product item in the cart and saves the cart.
     * @param authToken
     * @param cartDto
     * @return
     */
    boolean addItemInCart(String authToken, CartDto cartDto) throws Exception;

    /**
     * This method retrieves the cart details for the given user.
     * @param authToken
     * @return
     */
    Cart getCartById(String authToken) throws CustomException, IOException;
    
    /**
     * This method is used to update the cart details.
     * @param authToken
     * @param cartDto
     * @return
     * @throws Exception
     */

    boolean updateItemInCart(String authToken, CartDto cartDto) throws Exception;
}
