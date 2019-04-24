package com.hcl.cloud.cart.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.cart.controller.CartController;
import com.hcl.cloud.cart.domain.ShoppingCart;

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
	 * Convert Json to Java object.
	 * @param jsonString
	 * @return Cart
	 */
	public static ShoppingCart convertJsonToJavaObject(final String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ShoppingCart shoppingCart = null;
		try {
			shoppingCart = objectMapper.readValue(jsonString, ShoppingCart.class);
			LOG.info("succesfully convertJsonToJavaObject.");
		} catch (JsonParseException ex) {
			LOG.error(ex.getMessage());
		} catch (JsonMappingException ex) {
			LOG.error(ex.getMessage());
		} catch (IOException ex) {
			LOG.error(ex.getMessage());
		}
		return shoppingCart;
	}
}
