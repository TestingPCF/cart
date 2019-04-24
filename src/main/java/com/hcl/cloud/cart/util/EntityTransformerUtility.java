package com.hcl.cloud.cart.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.ShoppingCart;

/**
 * EntityTransformerUtility - Utility class to convert java object to Json and vice-versa.
 * @author kumar_sanjay
 */
public class EntityTransformerUtility {

	//private static final Logger LOG = Logger.getLogger(EntityTransformerUtility.class);
	
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
		} catch (JsonParseException e) {
			e.printStackTrace();
			//LOG.error(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			//LOG.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			//LOG.error(e.getMessage());
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
		ShoppingCart ShoppingCart = null;
		try {
			ShoppingCart = objectMapper.readValue(jsonString, ShoppingCart.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
			//LOG.error(e.getMessage());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			//LOG.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			//LOG.error(e.getMessage());
		}
		return ShoppingCart;
	}
}
