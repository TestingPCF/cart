package com.hcl.cloud.cart.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for ShoppingCart. This class includes getter and setter methods
 * for properties of the ShoppingCart.
 * @author baghelp
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCart {

	/**
	 * cartId - represents the id of the shopping cart.
	 */
	private String cartId;

	/**
	 * subTotal - represents the total amount of the shopping cart items.
	 */
	private BigDecimal subTotal = new BigDecimal(0.00);

	/**
	 * userId - represents the userId.
	 */
	private String userId;

	/**
	 * cartItems - represents the list of the items added in the cart.
	 */
	private List<CartItem> cartItems = new ArrayList<>();

	/**
	 * Getter method for the cartId
	 * @return
	 */
	public String getCartId() {
		return cartId;
	}

	/**
	 * Setter method for the cartId.
	 * @param cartId
	 */
	public void setCartId(final String cartId) {
		this.cartId = cartId;
	}

	/**
	 * Getter method for the subTotal
	 * @return
	 */
	public BigDecimal getSubTotal() {
		this.subTotal = calculateSubtotal();
		return subTotal;
	}

	/**
	 * Setter method for the subTotal.
	 * @param subTotal
	 */
	public void setSubTotal(final BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * Getter method for the userId
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setter method for the userId.
	 * @param userId
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * Getter method for the cartItems
	 * @return
	 */
	public List<CartItem> getCartItems() {
		return cartItems;
	}

	/**
	 * Setter method for the cartItems.
	 * @param cartItems
	 */
	public void setCartItems(final List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * Method to calculate the total amount of the items added in the shopping cart.
	 * @return
	 */
	private BigDecimal calculateSubtotal() {
		BigDecimal total = new BigDecimal(0.00);
		for(CartItem cartItem : cartItems) {
			total = total.add(cartItem.getSalePrice().multiply(new BigDecimal(cartItem.getQuantity())));
		}
		return total;
	}
}
