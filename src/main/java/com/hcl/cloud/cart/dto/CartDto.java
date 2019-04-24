package com.hcl.cloud.cart.dto;

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
}
