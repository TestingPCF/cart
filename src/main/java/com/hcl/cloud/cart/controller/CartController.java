package com.hcl.cloud.cart.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.cloud.cart.constant.CartConstant;
import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.dto.ResponseStatus;
import com.hcl.cloud.cart.dto.Status;
import com.hcl.cloud.cart.exception.BadRequestException;
import com.hcl.cloud.cart.exception.CustomException;
import com.hcl.cloud.cart.exception.ServiceUnavailableException;
import com.hcl.cloud.cart.service.CartService;

/**
 * RestController for the cart.
 * @author baghelp || sanjaykumar || shikhar.a
 */
@RestController
@RefreshScope
public class CartController {
    /**
     * Logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(CartController.class);

    /**
     * Autowired object of CartService variable to access
     * service api.
     */
    @Autowired
    private CartService cartService;

    /**
     * Constant for successRetrieve.
     */
    @Value("${cart.constant.retrieve.success.message}")
    private String successRetrieve;

    /**
     * Constant for successRetrieve.
     */
    @Value("${cart.constant.create.success.message}")
    private String successCreate;
    
    
    @Value("${cart.constant.updateItem.error.message}")
    private String updateItemFailure;
    
    

    /**
     * Method to add item in the cart.
     *
     * @param authToken authToken.
     * @param cartDto cartDto.
     * @return ResponseEntity response.
     */
    @PostMapping
    public ResponseEntity<ResponseStatus<String>> addItemInCart(
            @RequestHeader(value = "accessToken", required = true)
                    String authToken, @RequestBody CartDto cartDto) {
        ResponseStatus<String> response = null;
        Status messageStatus;
        try {
            boolean status = cartService.addItemInCart(authToken, cartDto);
            response = prepareOrderCreateResponse(status);
        } catch (BadRequestException ex) {
            response = prepareResponse(HttpStatus.BAD_REQUEST, ex.getMessage());           
            LOG.error("Item cannot be added into the cart. ", ex.getMessage());
        } catch (CustomException | ServiceUnavailableException ex) {
            response = prepareResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
            LOG.error(CartConstant.UNAUTHORIZED_ERROR_MESSAGE, ex.getMessage());
        }   catch (Exception ex) {
            response = prepareResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            LOG.error("Item cannot be added into the cart. ", ex.getMessage());
        }
        return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
    }

    /**
     * Method to get cart details.
     * @param authToken
     * @return ResponseStatus<String> ResponseStatus<String>
     */
    @GetMapping
    public ResponseEntity<ResponseStatus<?>> getCart(
            @RequestHeader(value = "accessToken", required = true)
                    String authToken) {
        ResponseStatus<Cart> response = null;
        Status messageStatus = null;
		try {
			Cart cart = cartService.getCartById(authToken);
			if(cart != null) {
    Status status = new Status(HttpStatus.OK, successRetrieve);
          response = new ResponseStatus.Builder<Cart>(status).setEntity(cart)
                        .build();
			} else {
				Status status = new Status(HttpStatus.OK, CartConstant.CART_EMPTY);
		        response = new ResponseStatus.Builder<Cart>(status).build();
			}
		} catch (CustomException | IOException ex) {
			 messageStatus = new Status(HttpStatus.UNAUTHORIZED, ex.getMessage());
            response = new ResponseStatus.Builder<Cart>(messageStatus).build();
            LOG.error(CartConstant.UNAUTHORIZED_ERROR_MESSAGE, ex.getMessage());
		} catch(Exception ex) {
    messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR,
                     ex.getMessage());
            response = new ResponseStatus.Builder<Cart>(messageStatus).build();
            LOG.error(ex.getMessage());
		}
        LOG.info(successRetrieve);
        return new ResponseEntity<ResponseStatus<?>>(response, response
                .getStatus().status());
    }
    
    /**
     * Update item into cart
     * @param authToken
     * @param cartDto
     * @return ResponseStatus<String> ResponseStatus<String>
     */
    @PutMapping
    public ResponseEntity<ResponseStatus<String>> updateItemInCart(
            @RequestHeader(value = "accessToken", required = true)
                    String authToken, @RequestBody CartDto cartDto) {
        ResponseStatus<String> response = null;
        Status messageStatus;
        try {
            boolean status = cartService.updateItemInCart(authToken, cartDto);
            if (status) {
                messageStatus = new Status(HttpStatus.CREATED,
                        successRetrieve);
                response = new ResponseStatus.Builder<String>(messageStatus)
                        .build();
                LOG.info("Item updated successfully into the cart.");
            } else {
                messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR,
                        updateItemFailure);
                response = new ResponseStatus.Builder<String>(messageStatus)
                        .build();
                LOG.info("Item cannot be updated successfully into the cart.");
            }
        }catch (BadRequestException ex) {
                messageStatus = new Status(HttpStatus.BAD_REQUEST, ex.getMessage());
                response = new ResponseStatus.Builder<String>(messageStatus).build();
                LOG.error("Item cannot be updated into the cart. ", ex.getMessage());
            } catch (CustomException | ServiceUnavailableException ex) {
                messageStatus = new Status(HttpStatus.UNAUTHORIZED, ex.getMessage());
                response = new ResponseStatus.Builder<String>(messageStatus).build();
                LOG.error(CartConstant.UNAUTHORIZED_ERROR_MESSAGE, ex.getMessage());
            }   catch (Exception ex) {
                messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
                response = new ResponseStatus.Builder<String>(messageStatus).build();
                LOG.error("Item cannot be updated into the cart. ", ex.getMessage());
            }
            return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
    }

    /**
     * This method sets the parameter response as per the status.
     * @param status status
     * @return  response response
     */
    private ResponseStatus<String> prepareOrderCreateResponse(boolean status) {
        ResponseStatus<String> response = null;
        if (status) {
            response = prepareResponse(HttpStatus.CREATED, successCreate);
            LOG.info(successCreate);
        } else {
            response = prepareResponse(HttpStatus.INTERNAL_SERVER_ERROR, CartConstant.FAIL);
            LOG.info("Item cannot be added successfully into the cart.");
        }
        return response;
    }

    /**
     * This method sets the parameter response as per the message and httpStatus.
     * @param httpStatus httpStatus
     * @param message message
     * @return ResponseStatus<String> ResponseStatus<String>
     */
    private ResponseStatus<String> prepareResponse(HttpStatus httpStatus, String message) {
        Status messageStatus;
        messageStatus = new Status(httpStatus,
                message);
        return new ResponseStatus.Builder<String>(messageStatus)
                .build();
    }
}
