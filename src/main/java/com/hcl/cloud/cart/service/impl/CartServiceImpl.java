package com.hcl.cloud.cart.service.impl;

import com.hcl.cloud.cart.controller.CartController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.service.CartService;
import com.hcl.cloud.cart.util.EntityTransformerUtility;

/**
 * CartServiceImpl - implementation class for the cart service.
 * @author baghelp
 */
@Service
public class CartServiceImpl implements CartService {

	private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

	/**
	 * userId - represents the id of the user, which is unique for all the users.
	 */
	private static String userId = "123";

	/**
	 * Autowired object of the CartRepository to be able to access the members of the JPA repository.
	 */
	@Autowired
	private CartRepository cartRepository;

	/**
	 * Method to add item in the cart.
	 * @param authToken
	 * @param cartDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean addItemInCart(final String authToken, final CartDto cartDto) throws Exception {
		boolean notPreset = false;
		validate(cartDto);
		Cart cart = getCart(authToken);
		ShoppingCart shoppingCart = null;

		if(cart != null){
			shoppingCart = EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson());
		} else {
			cart = new Cart();
			cart.setUserId(userId);
		}

		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			shoppingCart.setUserId(userId);
			CartItem cartItem = new CartItem();
			cartItem.setItemCode(cartDto.getSkuCode());
			cartItem.setQuantity(cartDto.getQuantity());
			shoppingCart.getCartItems().add(cartItem);

			LOG.info("Item adedd successfully in the shopping cart.");
			notPreset = true;

		} else if (!shoppingCart.getCartItems().isEmpty()) {
			for (CartItem cartItem : shoppingCart.getCartItems()) {
				if (cartItem.getItemCode().equalsIgnoreCase(cartDto.getSkuCode())) {
					int qty = cartItem.getQuantity() + cartDto.getQuantity();
					cartItem.setQuantity(qty);
					notPreset = true;
				}
			}
		}
		if(!notPreset) {
			CartItem cartItem = new CartItem();
			cartItem.setItemCode(cartDto.getSkuCode());
			cartItem.setQuantity(cartDto.getQuantity());
			shoppingCart.getCartItems().add(cartItem);
		}
		try {
			String cartJson = EntityTransformerUtility.convertJavaToJsonString(shoppingCart);
			cart.setCartJson(cartJson);
			Cart persistCart =  cartRepository.save(cart);
			if(persistCart != null) {
				LOG.info("Item persisted successfully into the database.");
				return true;
			} else {
				LOG.info("Item could not be saved into the database.");
				return false;
			}
		} catch (RuntimeException ex) {
			LOG.error("Item cannot be added into the cart. ", ex.getMessage());
			throw new RuntimeException(ex.getMessage());

		}

	}

	/**
	 * Private method to validate the cartDto object attributes.
	 * @param cartDto
	 * @throws Exception
	 */
	private void validate(final CartDto cartDto) throws Exception {
		if (cartDto.getSkuCode() == null || "".equals(cartDto.getSkuCode())) {
			LOG.info("Sku code is mandatory.");
			throw new Exception("Sku code is mandatory");
		}
		if (cartDto.getQuantity() <= 0) {
			LOG.info("Quantity must be 1 or greater.");
			throw new Exception("Quantity must be 1 or greater");
		}
	}

	/**
	 * Method to retrieve the details of the shopping cart by userId.
	 * @param authToken
	 * @return
	 */
	@Override
	public ShoppingCart getCartById(final String authToken) {
		Cart cart = getCart(authToken);
		ShoppingCart shoppingCart = null;
		if(cart != null){
			shoppingCart = EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson());
		}
		return shoppingCart;

	}

	/**
	 * Private method to get cart details by userId.
	 * @param authToken
	 * @return
	 */
	private Cart getCart(final String authToken){
		return cartRepository.findByUserId(userId);
	}

	

}
