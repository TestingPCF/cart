/*package com.hcl.cloud.cart.util;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(value = PowerMockRunner.class)
@PrepareForTest({ EntityTransformerUtility.class })
public class EntityTransformerUtilityTest {

	*//**
	 * EntityTransformerUtilityTest Mock.
	 *//*
	@InjectMocks
	private EntityTransformerUtilityTest entityTransformerUtilityTest;
	
	*//**
	 * This Method is called before the test is executed.
	 * 
	 * @throws Exception
	 **//*

	@Before
	public void setUp() throws Exception {
		this.entityTransformerUtilityTest = PowerMockito.spy(new EntityTransformerUtilityTest());
		MockitoAnnotations.initMocks(this);
	}
	
	
	*//**
	 * Success Test for getCartById Method.
	 **//*

	@Test
	public final void testGetCartByIdSuccess() {
		PowerMockito.mockStatic(EntityTransformerUtility.class);
		ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
		Cart cart = Mockito.mock(Cart.class);
		
		Mockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
		Mockito.when(EntityTransformerUtility.convertJsonToJavaObject(cart.getCartJson())).thenReturn(shoppingCart);
		cartServiceImpl.getCartById(authToken);
	}
}
*/