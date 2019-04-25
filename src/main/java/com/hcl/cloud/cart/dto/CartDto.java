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
    private BigDecimal listPrice = new BigDecimal(0.00);
    
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
     * @return
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * Setter method for the skuCode.
     * @param skuCode
     */
    public void setSkuCode(final String skuCode) {
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
    public void setQuantity(final int quantity) {
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
    public void setSalePrice(final BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * Getter method for the listPrice.
     * @return
     */
    public BigDecimal getListPrice() {
		return listPrice;
	}

    /**
     * Setter method for the listPrice.
     * @param listPrice
     */
	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	/**
     * Getter method for the quantityInCart.
     * @return
     */
	public int getQuantityInCart() {
		return quantityInCart;
	}

	/**
     * Setter method for the quantityInCart.
     * @param quantityInCart
     */
	public void setQuantityInCart(int quantityInCart) {
		this.quantityInCart = quantityInCart;
	}

	 /**
     * Getter method for the productName.
     * @return
     */
	public String getProductName() {
		return productName;
	}

	/**
     * Setter method for the productName.
     * @param productName
     */
	public void setProductName(final String productName) {
		this.productName = productName;
	}
}
