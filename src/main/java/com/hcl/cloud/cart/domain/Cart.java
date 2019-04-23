package com.hcl.cloud.cart.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CART")
public class Cart implements Serializable {

	private static final long serialVersionUID = 4L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
	@SequenceGenerator(name = "order_sequence", sequenceName = "ORDER_SEQ")
	@Column(name = "cart_id", unique = true, nullable = false)
	private long id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "CART_OBJECT")
	private String cartJson;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getCartJson() {
		return cartJson;
	}

	public void setCartJson(final String cartJson) {
		this.cartJson = cartJson;
	}

}
