package com.hcl.cloud.cart.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductResponse - API response for Product.
 * @author kumar_sanjay
 */
public class ProductResponse {
	/**
	 * productList - productList
	 */
	private List<ProductDto> productList = new ArrayList<>();
	/**
	 * statusCode - statusCode
	 */
	private String statusCode;
	/**
	 * status - status
	 */
	private String status;

	/**
	 * Getter method for productList.
	 * @return
	 */
	public List<ProductDto> getProductList() {
		return productList;
	}

	/**
	 * Setter method for productList.
	 * @param productListParam productListParam
	 */
	public void setProductList(List<ProductDto> productListParam) {
		this.productList = productListParam;
	}
	/**
	 * Getter method for statusCode.
	 * @return statusCode statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * Setter method for statusCode.
	 * @param statusCodeParam statusCodeParam
	 */
	public void setStatusCode(String statusCodeParam) {
		this.statusCode = statusCodeParam;
	}
	/**
	 * Getter method for status.
	 * @return status status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Setter method for status.
	 * @param statusParam statusParam
	 */
	public void setStatus(String statusParam) {
		this.status = statusParam;
	}

}
