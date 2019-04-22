package com.hcl.cloud.cart.service.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	private static String userId = "123";
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public boolean addItemInCart(final String authToken, final CartDto cartDto) throws Exception {
		boolean notPreset = false;
		validate(cartDto);
		ShoppingCart shoppingCart = getCartById(userId);
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
			ShoppingCart cart = cartRepository.save(shoppingCart);
			if (cart != null) {
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
		ShoppingCart shoppingCart = cartRepository.findByUserId(userId);
		return shoppingCart;
	}

	@Override
	public boolean updateItemInCart(final String authToken, final CartDto cartDto) throws Exception {
		validate(cartDto);
		boolean found = false;
		ShoppingCart shoppingCart = getCartById(userId);
		if (shoppingCart != null) {
			if (shoppingCart.getCartItems().isEmpty()) {
				throw new Exception("no sku found for update");
			} else {
				for (CartItem cartItem : shoppingCart.getCartItems()) {
					if (cartItem.getItemCode().equalsIgnoreCase(cartDto.getSkuCode())) {
						cartItem.setQuantity(cartDto.getQuantity());
						found = true;
					}
				}
			}
			if (!found) {
				throw new Exception("no sku found for update");
			}
			cartRepository.save(shoppingCart);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteItemFromCart(final String authToken, String skuCode) {
		ShoppingCart shoppingCart = getCartById(userId);
		if (shoppingCart != null) {
			updateShoppingCart(shoppingCart, skuCode);
			cartRepository.save(shoppingCart);
			return true;
		}
		return false;
	}

	private boolean updateShoppingCart(final ShoppingCart shoppingCart, String skuCode) {
		boolean updateStatus = false;
		Iterator<CartItem> cartItems = shoppingCart.getCartItems().iterator();
		CartItem cartItem = null;
		while (cartItems.hasNext()) {
			cartItem = (CartItem) cartItems.next();
			if (cartItem.getItemCode().equalsIgnoreCase(skuCode)) {
				cartItems.remove();
				updateStatus = true;
			}
		}
		return updateStatus;
	}

}
