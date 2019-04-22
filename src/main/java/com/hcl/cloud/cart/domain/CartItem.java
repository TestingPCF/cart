/**
 * Copyright (c) HCL.
 */
package com.hcl.cloud.cart.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author baghelp
 */
@Entity
@Table(name = "item")
public class CartItem {

    /**
     * itemCode.
     */

    @Id
    @Column(name="skuCode")
    private String itemCode;

    @Column(name="quantity")
    private int quantity;

    @Column(name="salePrice")
    private BigDecimal salePrice = new BigDecimal(10.00);

    @Column(name="listPrice")
    private BigDecimal listPrice = new BigDecimal(12.00);

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

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
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
