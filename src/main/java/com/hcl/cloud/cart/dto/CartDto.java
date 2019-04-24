package com.hcl.cloud.cart.dto;

import java.math.BigDecimal;

/**
 * CartDto class that represents the data transfer object to be persisted.
 * @author baghelp
 */
public class CartDto {

    /**
     * skuCode - represents the code of the sku( product item).
     */
    private String skuCode;

    /**
     * quantity - represents the quantity of the sku(product item).
     */
    private int quantity;
    
    /**
     * salePrice - represents the sale price of the item.
     */
    private BigDecimal salePrice = new BigDecimal(0.00);

    /**
     * listrice - represents the list price of the item.
     */
    private BigDecimal listrice = new BigDecimal(0.00);

    /**
     * Getter method for the skuCode.
     * @return
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * Setter method for the skuCode.
     * @param skuCode
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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
