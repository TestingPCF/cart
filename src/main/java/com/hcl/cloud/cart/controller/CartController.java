package com.hcl.cloud.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.cloud.cart.constant.CartConstant;
import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.dto.ResponseStatus;
import com.hcl.cloud.cart.dto.Status;
import com.hcl.cloud.cart.service.CartService;

@RestController
@RequestMapping("/user")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/cart")
	public ResponseEntity<ResponseStatus<String>> addItemInCart(@RequestHeader(value = "Authorization", required = true) String authToken, @RequestBody CartDto cartDto) {
		ResponseStatus<String> response = null;
		Status messageStatus;
		try {
			boolean status = cartService.addItemInCart(authToken, cartDto);
			if(status) {
				messageStatus = new Status(HttpStatus.CREATED, CartConstant.RETRIEVE_SUCCESS);
				response = new ResponseStatus.Builder<String>(messageStatus).build();
			} else {
				messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, CartConstant.FAIL);
				response = new ResponseStatus.Builder<String>(messageStatus).build();
			}
			
		} catch(Exception ex) {
			messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
			response = new ResponseStatus.Builder<String>(messageStatus).build();
		}
		return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
	}

	@GetMapping("/cart")
	public ResponseEntity<ResponseStatus<ShoppingCart>> getCart(@RequestHeader(value = "Authorization", required = true) String authToken) {
		ResponseStatus<ShoppingCart> response;
		ShoppingCart shoppingCart = cartService.getCartById(authToken);
		Status status = new Status(HttpStatus.OK, CartConstant.RETRIEVE_SUCCESS);
		response = new ResponseStatus.Builder<ShoppingCart>(status).setEntity(shoppingCart).build();
		return new ResponseEntity<ResponseStatus<ShoppingCart>>(response, response.getStatus().status());
	}
	
	@PutMapping("/cart")
	public ResponseEntity<ResponseStatus<String>> updateSkuQuantityInCart(@RequestHeader(value = "Authorization", required = true) String authToken, @RequestBody CartDto cartDto) {
		ResponseStatus<String> response = null;
		Status messageStatus;
		try {
			boolean status = cartService.updateItemInCart(authToken, cartDto);
			if(status) {
				messageStatus = new Status(HttpStatus.OK, CartConstant.UPDATE_SUCCESS);
				response = new ResponseStatus.Builder<String>(messageStatus).build();
			} else {
				messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, CartConstant.FAIL);
				response = new ResponseStatus.Builder<String>(messageStatus).build();
			}
			
		} catch(Exception ex) {
			messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
			response = new ResponseStatus.Builder<String>(messageStatus).build();
		}
		return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
	}
	
	@DeleteMapping("/cart/sku/{skuCode}")
	public ResponseEntity<ResponseStatus<String>> removeItemFromCart(@RequestHeader(value = "Authorization", required = true) String authToken, @PathVariable String skuCode) {
		ResponseStatus<String> response = null;
		Status messageStatus;
		boolean status = cartService.deleteItemFromCart(authToken, skuCode);
		if(status) {
			messageStatus = new Status(HttpStatus.OK, CartConstant.DELETED);
			response = new ResponseStatus.Builder<String>(messageStatus).build();
		} else {
			messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, CartConstant.FAIL);
			response = new ResponseStatus.Builder<String>(messageStatus).build();
		}
		return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
	}
	

}
