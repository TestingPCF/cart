/**
 * Copyright (c) HCL.
 */
package com.hcl.cloud.cart.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * CartItem entity that represents the cart-item properties.
 *
 * @author baghelp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {

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
    private BigDecimal salePrice = new BigDecimal(10.00);

    /**
     * listrice - represents the list price of the item.
     */
    private BigDecimal listrice = new BigDecimal(12.00);

    /**
     * Getter method for the itemCode.
     * @return
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Setter method for the itemCode.
     * @param itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Getter method for the quantity.
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for the quantity.
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method for the salePrice.
     * @return
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Setter method for the salePrice.
     * @param salePrice
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * Getter method for the listrice.
     * @return
     */
    public BigDecimal getListrice() {
        return listrice;
    }

    /**
     * Setter method for the listrice.
     * @param listrice
     */
    public void setListrice(BigDecimal listrice) {
        this.listrice = listrice;
    }
}
