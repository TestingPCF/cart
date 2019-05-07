package com.hcl.cloud.cart.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.cart.client.RestClient;
import com.hcl.cloud.cart.constant.CartConstant;
import com.hcl.cloud.cart.controller.CartController;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.dto.InventoryResponse;
import com.hcl.cloud.cart.dto.ProductResponse;
import com.hcl.cloud.cart.dto.TokenInfo;

/**
 * EntityTransformerUtility - Utility class to convert java object to Json and vice-versa.
 * @author kumar_sanjay
 */
public class EntityTransformerUtility {

	private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
	
	/**
	 * Convert Java object to Json.
	 * @param object
	 * @return String
	 */
	public static String convertJavaToJsonString(final Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(object);
		} catch (JsonParseException ex) {
			LOG.error(ex.getMessage());
		} catch (JsonMappingException ex) {
			LOG.error(ex.getMessage());
		} catch (IOException ex) {
			LOG.error(ex.getMessage());
		}
		return jsonString;
	}

	/**
	 * @param authToken
	 * @return
	 * @throws IOException
	 */
	public static TokenInfo getTokenInfo(final String authToken) throws IOException {
		ResponseEntity<Object> response = RestClient.getResponseFromMS(CartConstant.TOKEN, null, authToken, null); 
		JsonNode jsonNode = new ObjectMapper().valueToTree(response.getBody());
		String json = new ObjectMapper().writeValueAsString(jsonNode);
		return new ObjectMapper().readValue(json, TokenInfo.class);
	}

	/**
	 * @param cartDto
	 * @param authToken
	 * @return
	 * @throws IOException
	 */
	public static ProductResponse getProductResponse(final CartDto cartDto, final String authToken) throws IOException {
		ResponseEntity<Object> response = RestClient.getResponseFromMS(CartConstant.PRODUCT, null, authToken,
				cartDto.getSkuCode());
		JsonNode jsonNode = new ObjectMapper().valueToTree(response.getBody());
		String json = new ObjectMapper().writeValueAsString(jsonNode);
		return new ObjectMapper().readValue(json, ProductResponse.class);
	}
	
	/**
	 * @param cartDto
	 * @param authToken
	 * @return
	 * @throws IOException
	 */
	public static InventoryResponse getInventoryResponse(final CartDto cartDto, final String authToken) throws IOException {
		ResponseEntity<Object> response = RestClient.getResponseFromMS(CartConstant.INVERNTORY, null, authToken,
				cartDto.getSkuCode());
		JsonNode jsonNode = new ObjectMapper().valueToTree(response.getBody());
		String json = new ObjectMapper().writeValueAsString(jsonNode);
		return new ObjectMapper().readValue(json, InventoryResponse.class);
	}
	
}
