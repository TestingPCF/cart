/**
 *
 */
package com.hcl.cloud.cart.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for ShoppingCart. This class includes getter and setter methods
 * for properties of the ShoppingCart.
 * 
 * @author baghelp
 *
 */
@Document(collection = "cart")
public class ShoppingCart {

	/**
	 * cartId.
	 */
	@Id
	private String cartId;

	/**
	 * subTotal.
	 */
	private BigDecimal subTotal = new BigDecimal(0.00);

	/**
	 * userId.
	 */
	private String userId;

	/**
	 * userId.
	 */
	private List<CartItem> cartItems = new ArrayList<>();

	public String getCartId() {
		return cartId;
	}

	public void setCartId(final String cartId) {
		this.cartId = cartId;
	}

	public BigDecimal getSubTotal() {
		this.subTotal = calculateSubtotal();
		return subTotal;
	}

	public void setSubTotal(final BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(final List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	private BigDecimal calculateSubtotal() {
		BigDecimal total = new BigDecimal(0.00);
		for(CartItem cartItem : cartItems) {
			total = total.add(cartItem.getSalePrice().multiply(new BigDecimal(cartItem.getQuantity())));
		}
		return total;
	}
}
