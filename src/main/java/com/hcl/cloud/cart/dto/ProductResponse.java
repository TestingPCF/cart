package com.hcl.cloud.cart.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductResponse {

	private List<ProductDto> productList = new ArrayList<>();

	private String statusCode;

	private String status;

	public List<ProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDto> productList) {
		this.productList = productList;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
