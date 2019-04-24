package com.hcl.cloud.cart.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hcl.cloud.cart.constant.CartConstant;

/**
 * This class will treat as a client for rest apis.
 * @author kumar_sanjay || Pankaj
 */
public class RestClient {

    /**
     * inventoryUri.
     */
    public static String inventoryUri = "http://inventory.apps.cnpsandbox.dryice01.in.hclcnlabs.com/"; //"http://localhost:8181/inventory/";

    /**
     * cartUri.
     */
    public static String productUri = "http://products.apps.cnpsandbox.dryice01.in.hclcnlabs.com/product/";

    /**
     * logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RestClient.class);

    /**
     * getResponseFromMS.
     * @param serviceName Name of the service
     * @param object Object
     * @param authorization Authorization
     * @return ResponseEntity prepared response entity
     */
    public static ResponseEntity<Object> getResponseFromMS(
            final String serviceName,
            final Object object,
           final String authorization, final String skuCode) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(CartConstant.AUTHORIZATION_TOKEN, authorization);
            HttpEntity<String> entity = new HttpEntity<>("parameters",
                    headers);
            ResponseEntity<Object> response = null;
            switch (serviceName) {
                case "product":
                	productUri = productUri + skuCode;
                   response = restTemplate.exchange(productUri,
                                    HttpMethod.GET,
                                    entity,
                                    Object.class);
                    return response;
                case "inventory":
                	inventoryUri = inventoryUri+skuCode;
                	response = restTemplate.exchange(inventoryUri,
                            HttpMethod.GET,
                            entity,
                            Object.class);
                	return response;
                    default:
                        return new ResponseEntity<Object>(HttpStatus
                                .INTERNAL_SERVER_ERROR);

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }

   
}
