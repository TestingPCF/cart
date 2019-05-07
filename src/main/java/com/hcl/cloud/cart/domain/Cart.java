package com.hcl.cloud.cart.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

/**
 * Cart entity class to be persisted in the database.
 *
 * @author baghelp
 */
@Entity
@Table(name = "CART")
public class Cart {
    /**
     * Default Constructor.
     */
    public Cart() {
    }

    /**
     * Parameterized constructor.
     * @param idParam id.
     * @param userIdParam userId.
     * @param subTotalParam subTotal.
     * @param cartItemsParam cartItems.
     */
    public Cart(long idParam, String userIdParam, BigDecimal subTotalParam,
                List<CartItem> cartItemsParam) {
        this.id = idParam;
        this.userId = userIdParam;
        this.subTotal = subTotalParam;
        this.cartItems = cartItemsParam;
    }

    /**
     * Serial version UID for the serialization of the object.
     */
    private static final long serialVersionUID = 4L;

    /**
     * Primary key - Id, field for the Cart entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cart_sequence")
    @SequenceGenerator(name = "cart_sequence",
            sequenceName = "CART_SEQ")
    @Column(name = "cart_id", unique = true,
            nullable = false)
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
     * @return id id.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for id.
     * @param idParam idParam.
     */
    public void setId(final long idParam) {
        this.id = idParam;
    }

    /**
     * Getter method for userId.
     * @return userId userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     * @param userIdParam userIdParam
     */
    public void setUserId(final String userIdParam) {
        this.userId = userIdParam;
    }

    /**
     * Getter for subTotal.
     * @return subTotal subTotal
     */
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * Setter method for subTotal.
      * @param subTotalParam subTotalParam
     */
    public void setSubTotal(BigDecimal subTotalParam) {
        this.subTotal = subTotalParam;
    }

    /**
     * Getter method for cartItems.
     * @return cartItems cartItems
     */
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * Setter method for cartItems.
     * @param cartItemsParam cartItemsParam.
     */
    public void setCartItems(List<CartItem> cartItemsParam) {
        this.cartItems = cartItemsParam;
    }

}
