package com.hcl.cloud.cart.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
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
    private static String inventoryUri = "http://inventory.apps.sandbox.cflab01.in.hclcnlabs.com/";

    /**
     * productUri.
     */
    private static String productUri = "http://products.apps.sandbox.cflab01.in.hclcnlabs.com/";

    /**
     * uaa.
     */
    private static String uaa = "http://uaa.apps.sandbox.cflab01.in.hclcnlabs.com/tokenInfo";
    
    /**
     * logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RestClient.class);

    /**
     * getResponseFromMS.
     * @param serviceName Name of the service.
     * @param object Object
     * @param authorization Authorization
     * @param skuCode skuCode
     * @return ResponseEntity prepared response entity
     * @throws HttpClientErrorException exception
     */
    public static ResponseEntity<Object>
    getResponseFromMS(final String serviceName,
                      final Object object,
                      final String authorization,
                      final String skuCode)
            throws HttpClientErrorException {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(CartConstant.AUTHORIZATION_TOKEN, authorization);
            HttpEntity<String> entity = new HttpEntity<>("parameters",
                    headers);
            ResponseEntity<Object> response = null;
            switch (serviceName) {
                case CartConstant.PRODUCT:
                 LOGGER.info("Call API of product MS from cart");
                   response = restTemplate.exchange(productUri + skuCode,
                                    HttpMethod.GET,
                                    entity,
                                    Object.class);
                    return response;
                case CartConstant.INVERNTORY:
                 LOGGER.info("Call API of inventory MS from cart");
                 response = restTemplate.exchange(inventoryUri + skuCode,
                            HttpMethod.GET,
                            entity,
                            Object.class);
                 return response;
                 
                case CartConstant.TOKEN:
                 LOGGER.info("Call API of Token MS from cart");
                 response = restTemplate.exchange(uaa,
                            HttpMethod.GET,
                            entity,
                            Object.class);
                 return response;
                    default:
                        return new ResponseEntity<>(HttpStatus
                                .INTERNAL_SERVER_ERROR);

            }
    }

    /**
     * add Method Method.
     */
    public final int printApplication(final int a, final int b) {
        return a + b;
    }

}
