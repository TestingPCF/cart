package com.hcl.cloud.cart.service;

import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;

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
    ShoppingCart getCartById(String authToken);

    /**
     * This method updates the item details in the cart.
     * @param authToken
     * @param skuCode
     * @param quantity
     * @return
     */
    boolean updateItemInCart(String authToken, CartDto cartDto) throws Exception;

    /**
     * This method deletes an item from the cart.
     * @param userId
     * @param skuCode
     * @return SUCCESS if deleted successfully, else FAILED
     */
    boolean deleteItemFromCart(String userId, String skuCode);

}
