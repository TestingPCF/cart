package com.hcl.cloud.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.powermock.reflect.Whitebox;

import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.domain.ShoppingCart;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.util.EntityTransformerUtility;

/**
 * CartServiceImplTest - Test class for the CartServiceImpl class.
 * @author kumar_sanjay
 */
@RunWith(value = PowerMockRunner.class)
@PrepareForTest({ EntityTransformerUtility.class })
public class CartServiceImplTest {

	/**
	 * userId.
	 */
	private static String userId = "123";

	/**
	 * authToken.
	 */
	private final static String authToken = "1212bjhsds6";

	/**
	 * skuCode.
	 */
	private final static String SKU_CODE = "iphone6Red";

	/**
	 * quantity.
	 */
	private final static int quantity = 2;

	/**
	 * LIST_PRICE.
	 */
	private final static BigDecimal LIST_PRICE = new BigDecimal(12.00);

	/**
	 * SALE_PRICE.
	 */
	private final static BigDecimal SALE_PRICE = new BigDecimal(10.00);

	/**
	 * cartServiceImpl Mock.
	 */
	@InjectMocks
	private CartServiceImpl cartServiceImpl;

	/**
	 * cartRepository.
	 */
	@Mock
	private CartRepository cartRepository;

	/**
	 * This Method is called before the test is executed.
	 * 
	 * @throws Exception
	 **/

	@Before
	public void setUp() throws Exception {
		this.cartServiceImpl = PowerMockito.spy(new CartServiceImpl());
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Success Test for getCartById Method.
	 **/

	/**
	 * Success test method for GetCartById. 
	 */
	@Test
	public final void testGetCartByIdSuccess() {
		PowerMockito.mockStatic(EntityTransformerUtility.class);
		ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
		Cart cart = Mockito.mock(Cart.class);
		Mockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
		Mockito.when(EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson())).thenReturn(shoppingCart);
		cartServiceImpl.getCartById(authToken);
	}

	/**
	 * Success Test for testGetCartByIdWhenCartIsNull Method.
	 **/
	@Test
	public final void testGetCartByIdWhenCartIsNull() {
		Mockito.when(cartRepository.findByUserId(userId)).thenReturn(null);
		cartServiceImpl.getCartById(authToken);
	}

	/**
	 * Success Test for testaddItemInCartSuccess Method.
	 * @throws Exception
	 **/
	@Test
	public final void testaddItemInCartSuccess() throws Exception {
		CartDto cartDto = Mockito.mock(CartDto.class);
		Mockito.when(cartDto.getSkuCode()).thenReturn(SKU_CODE);
		Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
		PowerMockito.mockStatic(EntityTransformerUtility.class);
		ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
		Cart cart = Mockito.mock(Cart.class);
		Mockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
		Mockito.when(EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson())).thenReturn(shoppingCart);
		List<CartItem> cartItems = new ArrayList<>();
		CartItem cartItem = Mockito.mock(CartItem.class);
		Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
		Mockito.when(cartItem.getListrice()).thenReturn(LIST_PRICE);
		Mockito.when(cartItem.getSalePrice()).thenReturn(SALE_PRICE);
		Mockito.when(cartItem.getQuantity()).thenReturn(quantity);
		cartItems.add(cartItem);
		Mockito.when(shoppingCart.getCartItems()).thenReturn(cartItems);
		Cart persistCart = Mockito.mock(Cart.class);
		Mockito.when(cartRepository.save(cart)).thenReturn(persistCart);
		cartServiceImpl.addItemInCart(authToken, cartDto);
	}

	/**
	 * This method test for Validate when SKU is null in request.
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testValidateWhenSkuIsNull() throws Exception {
		CartDto cartDto = Mockito.mock(CartDto.class);
		Mockito.when(cartDto.getSkuCode()).thenReturn(null);
		Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
		Whitebox.invokeMethod(cartServiceImpl, "validate", cartDto);
	}
	
	@Test
	public final void testaddItemInCartSuccessWhenCartIsNull() throws Exception {
		CartDto cartDto = Mockito.mock(CartDto.class);
		Mockito.when(cartDto.getSkuCode()).thenReturn(SKU_CODE);
		Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
		PowerMockito.mockStatic(EntityTransformerUtility.class);
		ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
		List<CartItem> cartItems = new ArrayList<>();
		Mockito.when(shoppingCart.getCartItems()).thenReturn(cartItems);
		cartServiceImpl.addItemInCart(authToken, cartDto);
	}
	
	/**
	 * @throws Exception
	 */
	@Test(expected = RuntimeException.class)
	public final void testaddItemInCartFailure() throws Exception {
		CartDto cartDto = Mockito.mock(CartDto.class);
		Mockito.when(cartDto.getSkuCode()).thenReturn(SKU_CODE);
		Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
		PowerMockito.mockStatic(EntityTransformerUtility.class);
		ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
		Cart cart = Mockito.mock(Cart.class);
		Mockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
		Mockito.when(EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson())).thenReturn(shoppingCart);
		List<CartItem> cartItems = new ArrayList<>();
		Mockito.when(shoppingCart.getCartItems()).thenReturn(cartItems);
		Mockito.when(cartRepository.save(cart)).thenThrow(RuntimeException.class);
		cartServiceImpl.addItemInCart(authToken, cartDto);
	}

}
