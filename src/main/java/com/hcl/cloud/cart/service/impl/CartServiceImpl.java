package com.hcl.cloud.cart.service.impl;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.cart.client.RestClient;
import com.hcl.cloud.cart.constant.CartConstant;
import com.hcl.cloud.cart.controller.CartController;
import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.dto.InventoryResponse;
import com.hcl.cloud.cart.dto.ProductDto;
import com.hcl.cloud.cart.dto.ProductResponse;
import com.hcl.cloud.cart.dto.TokenInfo;
import com.hcl.cloud.cart.exception.BadRequestException;
import com.hcl.cloud.cart.exception.CustomException;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.service.CartService;
import com.hcl.cloud.cart.util.EntityTransformerUtility;

/**
 * CartServiceImpl - implementation class for the cart service.
 * 
 * @author baghelp
 */
@Service
public class CartServiceImpl implements CartService {

	private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

	/**
	 * userId - represents the id of the user, which is unique for all the users.
	 */
	private String userId = "123";

	/**
	 * Autowired object of the CartRepository to be able to access the members of
	 * the JPA repository.
	 */
	@Autowired
	private CartRepository cartRepository;

	/**
	 * Method to add item in the cart.
	 * 
	 * @param authToken
	 * @param cartDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean addItemInCart(final String authToken, final CartDto cartDto) throws Exception {
		boolean notPreset = false;
		validate(cartDto);
		String id = getUserIdByToken(authToken);
		if(id != null && !"".equals(id)) {
			userId = id;
		}
		ProductResponse productResponse = getProductDetails(cartDto, authToken);
		setPrices(productResponse, cartDto);
		Cart cart = getCart(authToken);
		ShoppingCart shoppingCart = null;

		if (cart != null) {
			shoppingCart = EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson());
		} else {
			cart = new Cart();
			cart.setUserId(userId);
		}

		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			shoppingCart.setUserId(userId);
			shoppingCart.getCartItems().add(transformCartItem(cartDto));

			LOG.info("Item adedd successfully in the shopping cart.");
			notPreset = true;

		} else if (!shoppingCart.getCartItems().isEmpty()) {
			for (CartItem cartItem : shoppingCart.getCartItems()) {
				if (cartItem.getItemCode().equalsIgnoreCase(cartDto.getSkuCode())) {
					int qty = cartItem.getQuantity() + cartDto.getQuantity();
					cartItem.setQuantity(qty);
					notPreset = true;
				}
			}
		}
		if (!notPreset) {
			shoppingCart.getCartItems().add(transformCartItem(cartDto));
		}
		try {
			String cartJson = EntityTransformerUtility.convertJavaToJsonString(shoppingCart);
			cart.setCartJson(cartJson);
			Cart persistCart = cartRepository.save(cart);
			if (persistCart != null) {
				LOG.info("Item persisted successfully into the database.");
				return true;
			} else {
				LOG.info("Item could not be saved into the database.");
				return false;
			}
		} catch (RuntimeException ex) {
			LOG.error("Item cannot be added into the cart. ", ex.getMessage());
			throw new RuntimeException(ex.getMessage());

		}

	}

	/**
	 * Private method to validate the cartDto object attributes.
	 * 
	 * @param cartDto
	 * @throws Exception
	 */
	private void validate(final CartDto cartDto) throws BadRequestException {
		if (cartDto.getSkuCode() == null || "".equals(cartDto.getSkuCode())) {
			LOG.info("Sku code is mandatory.");
			throw new BadRequestException("Sku code is mandatory");
		}
		if (cartDto.getQuantity() <= 0) {
			LOG.info("Quantity must be 1 or greater.");
			throw new BadRequestException("Quantity must be 1 or greater");
		}
	}

	/**
	 * Method to retrieve the details of the shopping cart by userId.
	 * @param authToken string type.
	 * @return shoppingCart {@link ShoppingCart}
	 * @throws IOException 
	 * @throws CustomException 
	 */
	@Override
	public ShoppingCart getCartById(final String authToken) throws CustomException, IOException {
		Cart cart = getCart(authToken);
		ShoppingCart shoppingCart = null;
		if (cart != null) {
			shoppingCart = EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson());
		}
		return shoppingCart;

	}

	/**
	 * Private method to get cart details by userId.
	 * @param authToken
	 * @return cart object Cart type.
	 * @throws IOException 
	 * @throws CustomException 
	 */
	private Cart getCart(final String authToken) throws CustomException, IOException {
		String id = getUserIdByToken(authToken);
		if(id != null && !"".equals(id)) {
			userId = id;
		}
		return cartRepository.findByUserId(userId);
	}

	/**
	 * @param cartDto
	 * @param authToken
	 * @throws IOException
	 * @throws BadRequestException
	 */
	private void checkInventory(final CartDto cartDto, final String authToken) throws IOException, BadRequestException {
		ResponseEntity<Object> response = RestClient.getResponseFromMS(CartConstant.INVERNTORY, null, authToken,
				cartDto.getSkuCode());
		JsonNode jsonNode = new ObjectMapper().valueToTree(response.getBody());
		String json = new ObjectMapper().writeValueAsString(jsonNode);
		InventoryResponse inventoryResponse = new ObjectMapper().readValue(json, InventoryResponse.class);
		if (!inventoryResponse.isInStock() || (inventoryResponse.isInStock() && cartDto.getQuantity() > inventoryResponse.getQuantity())) {
			throw new BadRequestException("Item out of stock");
		}
	}
	
	/**
	 * @param cartDto
	 * @param authToken
	 * @throws IOException
	 * @throws BadRequestException
	 */
	private ProductResponse getProductDetails(final CartDto cartDto, final String authToken) throws IOException, BadRequestException {
		ResponseEntity<Object> response = RestClient.getResponseFromMS(CartConstant.PRODUCT, null, authToken,
				cartDto.getSkuCode());
		JsonNode jsonNode = new ObjectMapper().valueToTree(response.getBody());
		String json = new ObjectMapper().writeValueAsString(jsonNode);
		ProductResponse productResponse = new ObjectMapper().readValue(json, ProductResponse.class);
		if(productResponse != null && CartConstant.NO_CONTENT.equals(productResponse.getStatusCode())) {
			throw new BadRequestException(productResponse.getStatus());
		} else if(productResponse != null && !CartConstant.NO_CONTENT.equals(productResponse.getStatusCode())) {
			checkInventory(cartDto, authToken);
		}
		return productResponse;
	}

	/**
	 * This method return userId based on Authorization token value.
	 * @param authToken {@link String}
	 * @return userId {@link String} based on token.
	 * @throws IOException 
	 * @throws CustomException
	 */
	private String getUserIdByToken(final String authToken) throws IOException, CustomException {
		try {
			ResponseEntity<Object> response = RestClient.getResponseFromMS(CartConstant.TOKEN, null, authToken, null); 
			JsonNode jsonNode = new ObjectMapper().valueToTree(response.getBody());
			String json = new ObjectMapper().writeValueAsString(jsonNode);
			TokenInfo tokenInfo = new ObjectMapper().readValue(json, TokenInfo.class);
			if(tokenInfo != null && tokenInfo.getStatus() == 0) {
				return tokenInfo.getUserId();
			} else if(tokenInfo != null && tokenInfo.getStatus() == 401) {
				throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
			}
		} catch(HttpClientErrorException ex) {
			throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
		}
		return null;
	}
	
	private void setPrices(final ProductResponse productResponse, final CartDto cartDto) {
		if(productResponse != null) {
			ProductDto productDto = productResponse.getProductList().get(0);
			if(productDto != null) {
				cartDto.setListrice(new BigDecimal(productDto.getListPrice()));
				cartDto.setSalePrice(new BigDecimal(productDto.getSalePrice()));
			}
		}
	}
	
	/**
	 * @param cartDto.
	 * @return cartItem {@link CartItem} 
	 */
	private CartItem transformCartItem(final CartDto cartDto) {
		CartItem cartItem = new CartItem();
		cartItem.setItemCode(cartDto.getSkuCode());
		cartItem.setQuantity(cartDto.getQuantity());
		cartItem.setListPrice(cartDto.getListrice());
		cartItem.setSalePrice(cartDto.getSalePrice());
		return cartItem;
	}
}
