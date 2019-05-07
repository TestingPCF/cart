/**
 * Copyright (c) HCL.
 */
package com.hcl.cloud.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

/**
 * CartItem entity that represents the cart-item properties.
 *
 * @author baghelp
 */
@Entity
@Table(name = "cart_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {
    /**
     * DEfautl constructor.
     */
    public CartItem() {
    }

    /**
     * Parameterized constructor.
     * @param cartItemIdParam cartItemId
     * @param itemCodeParam itemCode
     * @param quantityParam quantity
     * @param salePriceParam salePrice
     * @param listPriceParam listPrice
     * @param productNameParam productName
     * @param cartParam cart
     */
    public CartItem(long cartItemIdParam, String itemCodeParam,
                    int quantityParam, BigDecimal salePriceParam,
                    BigDecimal listPriceParam, String productNameParam,
                    Cart cartParam) {
        this.cartItemId = cartItemIdParam;
        this.itemCode = itemCodeParam;
        this.quantity = quantityParam;
        this.salePrice = salePriceParam;
        this.listPrice = listPriceParam;
        this.productName = productNameParam;
        this.cart = cartParam;
    }

    /**
     * cartItemId - represents the id of the shopping cart.
     */
    @Id
    @Column(name = "cart_item_id", unique = true,
            nullable = false)
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
    private BigDecimal salePrice = BigDecimal.ZERO;

    /**
     * listrice - represents the list price of the item.
     */
    private BigDecimal listPrice = BigDecimal.ZERO;

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
     * @return itemCode itemCode.
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Setter method for the itemCode.
     *
     * @param itemCodeParam itemCodeParam
     */
    public void setItemCode(final String itemCodeParam) {
        this.itemCode = itemCodeParam;
    }

    /**
     * Getter method for the quantity.
     *
     * @return quantity quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for the quantity.
     *
     * @param quantityParam quantityParam
     */
    public void setQuantity(final int quantityParam) {
        this.quantity = quantityParam;
    }

    /**
     * Getter method for the salePrice.
     *
     * @return salePrice salePrice
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Setter method for the salePrice.
     *
     * @param salePriceParam salePriceParam
     */
    public void setSalePrice(final BigDecimal salePriceParam) {
        this.salePrice = salePriceParam;
    }

    /**
     * Getter method for the listPrice.
     *
     * @return listPrice listPrice
     */
    public BigDecimal getListPrice() {
        return listPrice;
    }

    /**
     * Setter method for the listPrice.
     *
     * @param listPriceParam listPriceParam
     */
    public void setListPrice(final BigDecimal listPriceParam) {
        this.listPrice = listPriceParam;
    }

    /**
     * Getter method for the productName.
     *
     * @return productName productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Setter method for the productName.
     *
     * @param productNameParam productNameParam
     */
    public void setProductName(final String productNameParam) {
        this.productName = productNameParam;
    }

    /**
     * Getter method for cartItemID.
     * @return cartItemId cartItemId
     */
    public long getCartItemId() {
        return cartItemId;
    }

    /**
     * Setter method for cartItemId.
     * @param cartItemIdParam cartItemIdParam
     */
    public void setCartItemId(long cartItemIdParam) {
        this.cartItemId = cartItemIdParam;
    }

    /**
     * Getter method for cart.
     * @return cart cart.
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Setter method for cart.
     * @param cartParam cartParam
     */
    public void setCart(Cart cartParam) {
        this.cart = cartParam;
    }
}
