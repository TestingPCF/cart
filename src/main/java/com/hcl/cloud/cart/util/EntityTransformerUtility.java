package com.hcl.cloud.cart.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.cart.domain.Cart;

public class EntityTransformerUtility {

	//private static final Logger LOG = Logger.getLogger(EntityTransformerUtility.class);
	
	/**
	 * Convert Java object to Json.
	 * @param traitSet
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
	public static Cart convertJsonToJavaObject(final String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Cart cart = null;
		try {
			cart = objectMapper.readValue(jsonString, Cart.class);
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
		return cart;
	}
}
