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
    private BigDecimal salePrice = BigDecimal.ZERO;

    /**
     * listrice - represents the list price of the item.
     */
    private BigDecimal listPrice = BigDecimal.ZERO;

    /**
     * quantityInCart.
     */
    private int quantityInCart;

    /**
     * productName.
     */
    private String productName;

    /**
     * Getter method for the skuCode.
     * @return skuCode skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * Setter method for the skuCode.
     * @param skuCodeParam skuCodeParam
     */
    public void setSkuCode(final String skuCodeParam) {
        this.skuCode = skuCodeParam;
    }

    /**
     * Getter method for the quantity.
     * @return quantity quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for the quantity.
     * @param quantityParam quantityParam
     */
    public void setQuantity(final int quantityParam) {
        this.quantity = quantityParam;
    }
    
    /**
     * Getter method for the salePrice.
     * @return salePrice salePrice
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Setter method for the salePrice.
     * @param salePriceParam salePriceParam
     */
    public void setSalePrice(final BigDecimal salePriceParam) {
        this.salePrice = salePriceParam;
    }

    /**
     * Getter method for the listPrice.
     * @return listPrice listPrice
     */
    public BigDecimal getListPrice() {
  return listPrice;
 }

    /**
     * Setter method for the listPrice.
     * @param listPriceParam listPriceParam
     */
 public void setListPrice(BigDecimal listPriceParam) {
  this.listPrice = listPriceParam;
 }

 /**
     * Getter method for the quantityInCart.
     * @return quantityInCart quantityInCart
     */
 public int getQuantityInCart() {
  return quantityInCart;
 }

 /**
     * Setter method for the quantityInCart.
     * @param quantityInCartParam quantityInCartParam
     */
 public void setQuantityInCart(int quantityInCartParam) {
  this.quantityInCart = quantityInCartParam;
 }

  /**
     * Getter method for the productName.
     * @return productName productName
     */
 public String getProductName() {
  return productName;
 }

 /**
     * Setter method for the productName.
     * @param productNameParam productNameParam
     */
 public void setProductName(final String productNameParam) {
  this.productName = productNameParam;
 }
}
