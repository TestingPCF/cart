package com.hcl.cloud.cart.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hcl.cloud.cart.constant.CartConstant;

/**
 * This class will treat as a client for rest apis.
 * @author kumar_sanjay || Pankaj
 */
@Component
public class RestClient {

   /**
     * inventoryUri.
     */
    private static String inventoryUri;

    /**
     * productUri this URI is of the Query part of Product API.
     */
    private static String productUri;
    /**
     * uaa.
     */
    private static String uaa;

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
                 LOGGER.info("Call API of product MS from cart, uri is : "
                 + productUri);
                   response = restTemplate.exchange(productUri + skuCode,
                                    HttpMethod.GET,
                                    entity,
                                    Object.class);
                    return response;
                case CartConstant.INVERNTORY:
                 LOGGER.info("Call API of inventory MS from cart, uri is : "
                 + inventoryUri);
                 response = restTemplate.exchange(inventoryUri + skuCode,
                            HttpMethod.GET,
                            entity,
                            Object.class);
                 return response;
                 
                case CartConstant.TOKEN:
                 LOGGER.info("Call API of Token MS from cart, uri is : "+uaa);
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

    @Value("${inventory.uri}")
    public final void setInventoryUri(String inventoryUri) {
        RestClient.inventoryUri = inventoryUri;
    }

    @Value("${product.uri}")
    public final void setProductUri(String productUri) {
        RestClient.productUri = productUri;
    }

    @Value("${uaa.uri}")
    public final void setUaa(String uaa) {
        RestClient.uaa = uaa;
    }
}
