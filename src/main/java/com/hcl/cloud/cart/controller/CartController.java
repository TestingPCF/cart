package com.hcl.cloud.cart.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author baghelp
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
     * UNAUTHORIZED_ERROR_MESSAGE.
     */
    private static final String UNAUTHORIZED_ERROR_MESSAGE = "UNAUTHORIZED User or Invalid token. ";

    /**
     * Autowired object of CartService variable to access
     * service api.
     */
    @Autowired
    private CartService cartService;

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
            if (status) {
                messageStatus = new Status(HttpStatus.CREATED,
                        CartConstant.RETRIEVE_SUCCESS);
                response = new ResponseStatus.Builder<String>(messageStatus)
                        .build();
                LOG.info("Item added successfully into the cart.");
            } else {
                messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR,
                        CartConstant.FAIL);
                response = new ResponseStatus.Builder<String>(messageStatus)
                        .build();
                LOG.info("Item cannot be added successfully into the cart.");
            }

        } catch (BadRequestException ex) {
            messageStatus = new Status(HttpStatus.BAD_REQUEST, ex.getMessage());
            response = new ResponseStatus.Builder<String>(messageStatus).build();
            LOG.error("Item cannot be added into the cart. ", ex.getMessage());
        } catch (CustomException | ServiceUnavailableException ex) {
            messageStatus = new Status(HttpStatus.UNAUTHORIZED, ex.getMessage());
            response = new ResponseStatus.Builder<String>(messageStatus).build();
            LOG.error(UNAUTHORIZED_ERROR_MESSAGE, ex.getMessage());
        }   catch (Exception ex) {
            messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            response = new ResponseStatus.Builder<String>(messageStatus).build();
            LOG.error("Item cannot be added into the cart. ", ex.getMessage());
        }
        return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
    }

    /**
     * Method to get cart details.
     * @param authToken
     * @return
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
    Status status = new Status(HttpStatus.OK, CartConstant
                        .RETRIEVE_SUCCESS);
          response = new ResponseStatus.Builder<Cart>(status).setEntity(cart)
                        .build();
			} else {
				Status status = new Status(HttpStatus.OK, CartConstant.CART_EMPTY);
		        response = new ResponseStatus.Builder<Cart>(status).build();
			}
		} catch (CustomException | IOException ex) {
			 messageStatus = new Status(HttpStatus.UNAUTHORIZED, ex.getMessage());
            response = new ResponseStatus.Builder<Cart>(messageStatus).build();
            LOG.error(UNAUTHORIZED_ERROR_MESSAGE, ex.getMessage());
		} catch(Exception ex) {
    messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR,
                     ex.getMessage());
            response = new ResponseStatus.Builder<Cart>(messageStatus).build();
            LOG.error(ex.getMessage());
		}
        LOG.info("Item retrieved successfully into the cart.");
        return new ResponseEntity<ResponseStatus<?>>(response, response
                .getStatus().status());
    }
    
    /**
     * Update item into cart
     * @param authToken
     * @param cartDto
     * @return
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
                        CartConstant.RETRIEVE_SUCCESS);
                response = new ResponseStatus.Builder<String>(messageStatus)
                        .build();
                LOG.info("Item updated successfully into the cart.");
            } else {
                messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR,
                        CartConstant.FAIL);
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
                LOG.error(UNAUTHORIZED_ERROR_MESSAGE, ex.getMessage());
            }   catch (Exception ex) {
                messageStatus = new Status(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
                response = new ResponseStatus.Builder<String>(messageStatus).build();
                LOG.error("Item cannot be updated into the cart. ", ex.getMessage());
            }
            return new ResponseEntity<ResponseStatus<String>>(response, response.getStatus().status());
    }

}
