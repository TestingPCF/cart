package com.hcl.cloud.cart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Cart entity class to be persisted in the database.
 *
 * @author baghelp
 */
@Entity
@Table(name = "CART")
public class Cart {
    public Cart() {
    }

    public Cart(long id, String userId, BigDecimal subTotal, List<CartItem> cartItems) {
        this.id = id;
        this.userId = userId;
        this.subTotal = subTotal;
        this.cartItems = cartItems;
    }

    /**
     * Serial version UID for the serialization of the object.
     */
    private static final long serialVersionUID = 4L;

    /**
     * Primary key - Id, field for the Cart entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
    @SequenceGenerator(name = "cart_sequence", sequenceName = "CART_SEQ")
    @Column(name = "cart_id", unique = true, nullable = false)
    private long id;

    /**
     * user_id field of the database table "cart".
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * subTotal - represents the total amount of the shopping cart items.
     */
    private BigDecimal subTotal;

    /**
     * cartItems - represents the list of the items added in the cart.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    /**
     * Getter method for id.
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for id.
     * @param id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Getter method for userId.
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     * @param userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
