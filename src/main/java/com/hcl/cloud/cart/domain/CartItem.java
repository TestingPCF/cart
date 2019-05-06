/**
 * Copyright (c) HCL.
 */
package com.hcl.cloud.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * CartItem entity that represents the cart-item properties.
 *
 * @author baghelp
 */
@Entity
@Table(name = "cart_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {

    public CartItem() {
    }

    public CartItem(long cartItemId, String itemCode, int quantity, BigDecimal salePrice
            , BigDecimal listPrice, String productName, Cart cart) {
        this.cartItemId = cartItemId;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.listPrice = listPrice;
        this.productName = productName;
        this.cart = cart;
    }

    /**
     * cartItemId - represents the id of the shopping cart.
     */
    @Id
    @Column(name = "cart_item_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cart_item_sequence")
    @SequenceGenerator(name = "cart_item_sequence",
            sequenceName = "CART_ITEM_SEQ")
    private long cartItemId;

    /**
     * itemCode - represents the code of the item.
     */
    private String itemCode;

    /**
     * quantity - represents the quantity of the item.
     */
    private int quantity;

    /**
     * salePrice - represents the sale price of the item.
     */
    private BigDecimal salePrice = new BigDecimal(0.00);

    /**
     * listrice - represents the list price of the item.
     */
    private BigDecimal listPrice = new BigDecimal(0.00);

    /**
     * productName.
     */
    private String productName;

    /**
     * cart.
     */
    @JoinColumn(name = "fk_cart")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;

    /**
     * Getter method for the itemCode.
     *
     * @return
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Setter method for the itemCode.
     *
     * @param itemCode
     */
    public void setItemCode(final String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Getter method for the quantity.
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for the quantity.
     *
     * @param quantity
     */
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method for the salePrice.
     *
     * @return
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Setter method for the salePrice.
     *
     * @param salePrice
     */
    public void setSalePrice(final BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * Getter method for the listPrice.
     *
     * @return
     */
    public BigDecimal getListPrice() {
        return listPrice;
    }

    /**
     * Setter method for the listPrice.
     *
     * @param listPrice
     */
    public void setListPrice(final BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * Getter method for the productName.
     *
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Setter method for the productName.
     *
     * @param productName
     */
    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
