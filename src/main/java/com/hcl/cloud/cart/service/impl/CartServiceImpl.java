package com.hcl.cloud.cart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.service.CartService;
import com.hcl.cloud.cart.util.EntityTransformerUtility;

@Service
public class CartServiceImpl implements CartService {
	
	private static String userId = "123";
	
	@Autowired
	private CartRepository cartRepository;

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
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}


	private void validate(final CartDto cartDto) throws Exception {
		if (cartDto.getSkuCode() == null || "".equals(cartDto.getSkuCode())) {
			throw new Exception("Sku code is mandatory");
		}
		if (cartDto.getQuantity() <= 0) {
			throw new Exception("Quantity must be 1 or greater");
		}
	}

	@Override
	public ShoppingCart getCartById(final String authToken) {
		Cart cart = getCart(authToken);
		ShoppingCart shoppingCart = null;
		if(cart != null){
			shoppingCart = EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson());
		}
		return shoppingCart;

	}

	private Cart getCart(final String authToken){
		return cartRepository.findByUserId(userId);
	}

	

}
