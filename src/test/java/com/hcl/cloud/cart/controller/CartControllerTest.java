package com.hcl.cloud.cart.controller;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;

import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.exception.BadRequestException;
import com.hcl.cloud.cart.exception.CustomException;
import com.hcl.cloud.cart.service.CartService;

/**
 * This mock class of CartController.
 * @author kumar_sanjay.
 *
 */
@RunWith(value = PowerMockRunner.class)
@PrepareForTest({HttpStatus.class})
public class CartControllerTest {

	/**
	 * cartController.
	 */
	@InjectMocks
	private CartController cartController;
	
	@Mock
	private CartService cartService;
	
	/**
     * ACCESS_TOKEN.
     */
    private static final String AUTH_TOKEN = "TOKEN";
	
	
	 /**
     * This is a method for preprocessing tasks.
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
    	cartController = PowerMockito.spy(new CartController());
        MockitoAnnotations.initMocks(this);
    }
	
    /**
     * Test Success for get cart.
     * @throws IOException 
     * @throws CustomException 
     */

    @Test
    public final void testGetCartSuccess() throws CustomException, IOException {
    	ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
    	Mockito.when(cartService.getCartById(AUTH_TOKEN)).thenReturn(shoppingCart);
        Assert.assertEquals(HttpStatus.OK, cartController.getCart(AUTH_TOKEN).getStatusCode());
    }
    
    /**
     * Test for failed case testGetCart.
     * @throws IOException 
     * @throws CustomException 
     */

    @Test
    public final void testGetCartFailed() throws CustomException, IOException {
    	Mockito.when(cartService.getCartById(AUTH_TOKEN)).thenReturn(null);
        Assert.assertEquals(HttpStatus.OK, cartController.getCart(AUTH_TOKEN).getStatusCode());
    }
    
    
    /**
     * Test for testGetCartWhenCustomExceptionOccur for CustomException.
     * @throws Exception 
     */
    @Test
    public final void testGetCartWhenCustomExceptionOccur() throws Exception {
    	Mockito.when(cartService.getCartById(AUTH_TOKEN)).thenThrow(CustomException.class);
    	Assert.assertEquals(HttpStatus.UNAUTHORIZED, cartController.getCart(AUTH_TOKEN).getStatusCode());
    }
    
    
    /**
     * Test for testGetCartWhenInternalServerError for Exception.
     * @throws Exception 
     */
    @Test
    public final void testGetCartWhenInternalServerError() throws Exception {
    	Mockito.when(cartService.getCartById(AUTH_TOKEN)).thenThrow(RuntimeException.class);
    	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cartController.getCart(AUTH_TOKEN).getStatusCode());
    }
    
    
    /**
     * Test Success for testAddItemInCartSuccess.
     * @throws Exception 
     */

    @Test
    public final void testAddItemInCartSuccess() throws Exception {
    	CartDto cartDto = Mockito.mock(CartDto.class);
    	Mockito.when(cartService.addItemInCart(AUTH_TOKEN, cartDto)).thenReturn(Boolean.TRUE);
    	Assert.assertEquals(HttpStatus.CREATED, cartController.addItemInCart(AUTH_TOKEN, cartDto).getStatusCode());
    }
	
    /**
     * Test Success for testAddItemInCartFalied.
     * @throws Exception 
     */
    @Test
    public final void testAddItemInCartFalied() throws Exception {
    	CartDto cartDto = Mockito.mock(CartDto.class);
    	Mockito.when(cartService.addItemInCart(AUTH_TOKEN, cartDto)).thenReturn(Boolean.FALSE);
    	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cartController.addItemInCart(AUTH_TOKEN, cartDto).getStatusCode());
    }
    
    /**
     * Test Success for testAddItemInCartFailure.
     * @throws Exception 
     */
    @Test
    public final void testAddItemInCartFailure() throws Exception {
    	CartDto cartDto = Mockito.mock(CartDto.class);
    	Mockito.when(cartService.addItemInCart(AUTH_TOKEN, cartDto)).thenThrow(RuntimeException.class);
    	Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, cartController.addItemInCart(AUTH_TOKEN, cartDto).getStatusCode());
    }
    
    /**
     * Test Success for testAddItemInCartFailure for BadRequestException.
     * @throws Exception 
     */
    @Test
    public final void testAddItemInCartWhenBadRequestException() throws Exception {
    	CartDto cartDto = Mockito.mock(CartDto.class);
    	Mockito.when(cartService.addItemInCart(AUTH_TOKEN, cartDto)).thenThrow(BadRequestException.class);
    	Assert.assertEquals(HttpStatus.BAD_REQUEST, cartController.addItemInCart(AUTH_TOKEN, cartDto).getStatusCode());
    }
    
    /**
     * Test Success for testAddItemInCartFailure for CustomException.
     * @throws Exception 
     */
    @Test
    public final void testAddItemInCartWhenCustomExceptionOccur() throws Exception {
    	CartDto cartDto = Mockito.mock(CartDto.class);
    	Mockito.when(cartService.addItemInCart(AUTH_TOKEN, cartDto)).thenThrow(CustomException.class);
    	Assert.assertEquals(HttpStatus.UNAUTHORIZED, cartController.addItemInCart(AUTH_TOKEN, cartDto).getStatusCode());
    }
}
