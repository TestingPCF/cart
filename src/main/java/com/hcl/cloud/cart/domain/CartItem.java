/**
 * Copyright (c) HCL.
 */
package com.hcl.cloud.cart.domain;

import java.math.BigDecimal;

/**
 * @author baghelp
 */
public class CartItem {

    /**
     * itemCode.
     */
    private String itemCode;
    private int quantity;
    private BigDecimal salePrice = new BigDecimal(10.00);
    
    private BigDecimal listrice = new BigDecimal(12.00);

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getListrice() {
		return listrice;
	}

	public void setListrice(BigDecimal listrice) {
		this.listrice = listrice;
	}

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return itemCode.equals(cartItem.itemCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode);
    }*/
}
