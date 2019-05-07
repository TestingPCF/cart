package com.hcl.cloud.cart.service.impl;

import java.io.IOException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.cart.client.RestClient;
import com.hcl.cloud.cart.constant.CartConstant;
import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.dto.InventoryResponse;
import com.hcl.cloud.cart.dto.ProductDto;
import com.hcl.cloud.cart.dto.ProductResponse;
import com.hcl.cloud.cart.dto.TokenInfo;
import com.hcl.cloud.cart.exception.BadRequestException;
import com.hcl.cloud.cart.exception.CustomException;
import com.hcl.cloud.cart.exception.ServiceUnavailableException;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.util.EntityTransformerUtility;

/**
 * CartServiceImplTest - Test class for the CartServiceImpl class.
 * 
 * @author kumar_sanjay
 */
@RunWith(value = PowerMockRunner.class)
@PrepareForTest({ EntityTransformerUtility.class, RestClient.class })
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
    private final static BigDecimal LIST_PRICE = BigDecimal.TEN;

    /**
     * SALE_PRICE.
     */
    private final static BigDecimal SALE_PRICE = BigDecimal.ONE;

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
     * responseBody.
     */
    @Mock
    private Object responseBody;

    /**
     * salePrice - represents the sale price of the item.
     */
    private double salePrice = 10.0;

    /**
     * listrice - represents the list price of the item.
     */
    private double listPrice = 12.4;

    /**
     * response.
     */
    @Mock
    private ResponseEntity<Object> response;

    @Mock
    private ObjectMapper objectMapper;

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
     * 
     * @throws IOException
     * @throws CustomException
     */
    @Test
    public final void testGetCartByIdSuccess() throws CustomException, IOException {
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        TokenInfo tokenInfo = Mockito.mock(TokenInfo.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        Mockito.when(tokenInfo.getUserId()).thenReturn(userId);
        Cart cart = Mockito.mock(Cart.class);
        Mockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
        cartServiceImpl.getCartById(authToken);
    }

    /**
     * Success Test for testGetCartByIdWhenCartIsNull Method.
     * 
     * @throws IOException
     * @throws CustomException
     **/
    @Test(expected = CustomException.class)
    public final void testGetCartByIdWhenCartIsNull() throws CustomException, IOException {
        Mockito.when(cartRepository.findByUserId(userId)).thenReturn(null);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setStatus(401);
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        cartServiceImpl.getCartById(authToken);
    }

    /**
     * Success Test for testGetCartByIdWhenCartIsNull Method for
     * HttpClientErrorException.
     * 
     * @throws IOException
     * @throws HttpClientErrorException
     **/
    @Test(expected = CustomException.class)
    public final void testGetCartByIdHttpClientErrorException() throws HttpClientErrorException, IOException {
        Mockito.when(cartRepository.findByUserId(userId)).thenReturn(null);
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenThrow(HttpClientErrorException.class);
        cartServiceImpl.getCartById(authToken);
    }

    /**
     * Success Test for testGetCartByIdWhenCartIsNull Method.
     * 
     * @throws IOException
     * @throws CustomException
     **/
    @Test(expected = CustomException.class)
    public final void testGetCartByIdWhenCustomExceptionOccurs() throws CustomException, IOException {
        Mockito.when(cartRepository.findByUserId(userId)).thenReturn(null);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setStatus(201);
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenThrow(CustomException.class);
        cartServiceImpl.getCartById(authToken);
    }

    /**
     * Success Test for testaddItemInCartSuccess Method.
     * 
     * @throws Exception
     **/
    @Test
    public final void testaddItemInCartEmptyCartSuccess() throws Exception {
        CartDto cartDto = new CartDto();
        List<ProductDto> productList = new ArrayList<>();

        PowerMockito.mockStatic(EntityTransformerUtility.class);
        PowerMockito.mockStatic(RestClient.class);
        TokenInfo tokenInfo = new TokenInfo();
        cartDto.setQuantity(1);
        cartDto.setSkuCode(SKU_CODE);

        ResponseEntity response = Mockito.mock(ResponseEntity.class);
        ProductDto product = new ProductDto();
        productList.add(product);
        ProductResponse productResponse = new ProductResponse();
        PowerMockito.when(EntityTransformerUtility.getProductResponse(cartDto, authToken)).thenReturn(productResponse);
        productResponse.setProductList(productList);
        productResponse.setStatusCode(SKU_CODE);
        CartItem cartItem = Mockito.mock(CartItem.class);
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        cartDto.setSkuCode(SKU_CODE);

        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        tokenInfo.setUserId(userId);
        cartServiceImpl.addItemInCart(authToken, cartDto);
    }

    /**
     * Success Test for testaddItemInCartSuccess Method.
     * 
     * @throws Exception
     **/
    @Test
    public final void testAddItemInCartSuccess() throws Exception {
        CartDto cartDto = new CartDto();
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        PowerMockito.mockStatic(RestClient.class);
        TokenInfo tokenInfo = new TokenInfo();
        List<ProductDto> productList = new ArrayList<>();
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        tokenInfo.setUserId(userId);

        cartDto.setQuantity(1);
        cartDto.setSkuCode(SKU_CODE);
        Cart cart = Mockito.mock(Cart.class);
        PowerMockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);

        ResponseEntity response = Mockito.mock(ResponseEntity.class);
        ProductDto product = new ProductDto();
        productList.add(product);
        ProductResponse productResponse = new ProductResponse();
        InventoryResponse inventoryResponse = new InventoryResponse();
        PowerMockito.when(EntityTransformerUtility.getProductResponse(cartDto, authToken)).thenReturn(productResponse);
        PowerMockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken))
                .thenReturn(inventoryResponse);
        productResponse.setProductList(productList);
        productResponse.setStatusCode(SKU_CODE);
        inventoryResponse.setInStock(true);
        inventoryResponse.setActiveStatus(true);
        inventoryResponse.setQuantity(4);
        CartItem cartItem = Mockito.mock(CartItem.class);
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        cartDto.setSkuCode(SKU_CODE);
        List<CartItem> cartItems = new ArrayList<>();
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        Mockito.when(cartItem.getListPrice()).thenReturn(LIST_PRICE);
        Mockito.when(cartItem.getSalePrice()).thenReturn(SALE_PRICE);
        Mockito.when(cartItem.getQuantity()).thenReturn(quantity);
        cartItems.add(cartItem);

        Mockito.when(cart.getCartItems()).thenReturn(cartItems);
        PowerMockito.when(cartRepository.save(cart)).thenReturn(cart);

        cartServiceImpl.addItemInCart(authToken, cartDto);
    }

    /**
     * This method test for Validate when SKU is null in request.
     * 
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void testValidateWhenSkuIsNull() throws Exception {
        CartDto cartDto = Mockito.mock(CartDto.class);
        Mockito.when(cartDto.getSkuCode()).thenReturn(null);
        Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
        Whitebox.invokeMethod(cartServiceImpl, "validate", cartDto);
    }

    /**
     * This method test for Validate when SKU is null in request.
     * 
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void testValidateForQuantity() throws Exception {
        CartDto cartDto = Mockito.mock(CartDto.class);
        Mockito.when(cartDto.getSkuCode()).thenReturn(SKU_CODE);
        Mockito.when(cartDto.getQuantity()).thenReturn(-1);
        Whitebox.invokeMethod(cartServiceImpl, "validate", cartDto);
    }

    /**
     * This method test for testcheckInventoryBadRequestException.
     * 
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void testcheckInventoryBadRequestException() throws Exception {
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        CartDto cartDto = Mockito.mock(CartDto.class);
        Mockito.when(cartDto.getSkuCode()).thenReturn(null);
        Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
        InventoryResponse inventoryResponse = Mockito.mock(InventoryResponse.class);
        Mockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken)).thenReturn(inventoryResponse);
        Mockito.when(inventoryResponse.isInStock()).thenReturn(Boolean.TRUE);
        Whitebox.invokeMethod(cartServiceImpl, "checkInventory", cartDto, authToken);

    }

    /**
     * This method test for testcheckInventoryHttpClientErrorException.
     * 
     * @throws Exception
     */
    @Test(expected = BadRequestException.class)
    public void testcheckInventoryHttpClientErrorException() throws Exception {
        CartDto cartDto = Mockito.mock(CartDto.class);
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(cartDto.getSkuCode()).thenReturn(null);
        Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
        InventoryResponse inventoryResponse = Mockito.mock(InventoryResponse.class);
        Mockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken)).thenReturn(inventoryResponse);
        Mockito.when(inventoryResponse.isInStock()).thenReturn(Boolean.TRUE);
        Whitebox.invokeMethod(cartServiceImpl, "checkInventory", cartDto, authToken);
    }

    /**
     * This method test for testcheckInventoryHttpClientErrorException.
     * 
     * @throws Exception
     */
    @Test(expected = ServiceUnavailableException.class)
    public void testcheckInventoryServiceUnavailableException() throws Exception {
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        CartDto cartDto = Mockito.mock(CartDto.class);
        Mockito.when(cartDto.getSkuCode()).thenReturn(null);
        Mockito.when(cartDto.getQuantity()).thenReturn(quantity);
        Mockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken))
                .thenThrow(HttpClientErrorException.class);
        Whitebox.invokeMethod(cartServiceImpl, "checkInventory", cartDto, authToken);
    }

//    /**
//     * This method test for testGetProductDetails BadRequestException.
//     *
//     * @throws Exception
//     */
//    @Test(expected = BadRequestException.class)
//    public void testGetProductDetailsBadRequestException() throws Exception {
//        PowerMockito.mockStatic(EntityTransformerUtility.class);
//        ProductResponse productResponse = Mockito.mock(ProductResponse.class);
//        CartDto cartDto = Mockito.mock(CartDto.class);
//        Mockito.when(EntityTransformerUtility.getProductResponse(cartDto, authToken)).thenReturn(productResponse);
//        Mockito.when(productResponse.getStatusCode()).thenReturn("204");
//        Whitebox.invokeMethod(cartServiceImpl, "getProductDetails", cartDto, authToken);
//    }

    /**
     * This method test for testSetCartQtyToDto.
     * 
     * @throws Exception
     */
    @Test
    public void testSetCartQtyToDto() throws Exception {
        CartDto cartDto = Mockito.mock(CartDto.class);
        CartItem cartItem = Mockito.mock(CartItem.class);
        Cart cart = Mockito.mock(Cart.class);
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        Mockito.when(cartDto.getSkuCode()).thenReturn(SKU_CODE);
        Mockito.when(cartItem.getQuantity()).thenReturn(quantity);
        Whitebox.invokeMethod(cartServiceImpl, "setCartQtyToDto", cartDto, cart);
    }

    /**
     * Failure Test for testaddItemInCartSuccess Method.
     * 
     * @throws Exception
     **/
    @Test(expected = RuntimeException.class)
    public final void testAddItemInCartFailure() throws Exception {
        CartDto cartDto = new CartDto();
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        PowerMockito.mockStatic(RestClient.class);
        TokenInfo tokenInfo = new TokenInfo();

        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        tokenInfo.setUserId(userId);

        cartDto.setQuantity(1);
        cartDto.setSkuCode(SKU_CODE);
        Cart cart = Mockito.mock(Cart.class);
        PowerMockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);

        ResponseEntity response = Mockito.mock(ResponseEntity.class);

        ProductResponse productResponse = new ProductResponse();
        InventoryResponse inventoryResponse = new InventoryResponse();
        PowerMockito.when(EntityTransformerUtility.getProductResponse(cartDto, authToken)).thenReturn(productResponse);
        PowerMockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken))
                .thenReturn(inventoryResponse);
        inventoryResponse.setInStock(true);
        inventoryResponse.setActiveStatus(true);
        inventoryResponse.setQuantity(4);
        CartItem cartItem = Mockito.mock(CartItem.class);
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        cartDto.setSkuCode(SKU_CODE);
        List<CartItem> cartItems = new ArrayList<>();
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        Mockito.when(cartItem.getListPrice()).thenReturn(LIST_PRICE);
        Mockito.when(cartItem.getSalePrice()).thenReturn(SALE_PRICE);
        Mockito.when(cartItem.getQuantity()).thenReturn(quantity);
        cartItems.add(cartItem);

        Mockito.when(cart.getCartItems()).thenReturn(cartItems);
        PowerMockito.when(cartRepository.save(cart)).thenThrow(RuntimeException.class);

        cartServiceImpl.addItemInCart(authToken, cartDto);
    }

    /**
     * Updateitem in cart success.
     * 
     * @throws Exception
     */

    @Test
    public final void testUpdateItemInCartSuccess() throws Exception {
        CartDto cartDto = new CartDto();
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        PowerMockito.mockStatic(RestClient.class);
        TokenInfo tokenInfo = new TokenInfo();
        List<ProductDto> productList = new ArrayList<>();
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        tokenInfo.setUserId(userId);
        cartDto.setQuantity(1);
        cartDto.setSkuCode(SKU_CODE);
        Cart cart = Mockito.mock(Cart.class);
        PowerMockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
        ProductDto product = new ProductDto();
        productList.add(product);
        ProductResponse productResponse = new ProductResponse();
        InventoryResponse inventoryResponse = new InventoryResponse();
        PowerMockito.when(EntityTransformerUtility.getProductResponse(cartDto, authToken)).thenReturn(productResponse);
        PowerMockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken))
                .thenReturn(inventoryResponse);
        productResponse.setProductList(productList);
        productResponse.setStatusCode(SKU_CODE);
        inventoryResponse.setInStock(true);
        inventoryResponse.setActiveStatus(true);
        inventoryResponse.setQuantity(4);
        CartItem cartItem = Mockito.mock(CartItem.class);
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        cartDto.setSkuCode(SKU_CODE);
        List<CartItem> cartItems = new ArrayList<>();
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        Mockito.when(cartItem.getListPrice()).thenReturn(LIST_PRICE);
        Mockito.when(cartItem.getSalePrice()).thenReturn(SALE_PRICE);
        Mockito.when(cartItem.getQuantity()).thenReturn(quantity);
        cartItems.add(cartItem);

        Mockito.when(cart.getCartItems()).thenReturn(cartItems);
        PowerMockito.when(cartRepository.save(cart)).thenReturn(cart);

        cartServiceImpl.updateItemInCart(authToken, cartDto);
    }

    /**
     * Update item in cart failure.
     * 
     * @throws Exception
     */

    @Test(expected = RuntimeException.class)
    public final void testUpdateItemInCartFailure() throws Exception {
        CartDto cartDto = new CartDto();
        PowerMockito.mockStatic(EntityTransformerUtility.class);
        PowerMockito.mockStatic(RestClient.class);
        TokenInfo tokenInfo = new TokenInfo();

        PowerMockito.mockStatic(EntityTransformerUtility.class);
        Mockito.when(EntityTransformerUtility.getTokenInfo(authToken)).thenReturn(tokenInfo);
        tokenInfo.setUserId(userId);

        cartDto.setQuantity(1);
        cartDto.setSkuCode(SKU_CODE);
        Cart cart = Mockito.mock(Cart.class);
        PowerMockito.when(cartRepository.findByUserId(userId)).thenReturn(cart);
        ProductResponse productResponse = new ProductResponse();
        InventoryResponse inventoryResponse = new InventoryResponse();
        PowerMockito.when(EntityTransformerUtility.getProductResponse(cartDto, authToken)).thenReturn(productResponse);
        PowerMockito.when(EntityTransformerUtility.getInventoryResponse(cartDto, authToken))
                .thenReturn(inventoryResponse);
        inventoryResponse.setInStock(true);
        inventoryResponse.setActiveStatus(true);
        inventoryResponse.setQuantity(4);
        CartItem cartItem = Mockito.mock(CartItem.class);
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        cartDto.setSkuCode(SKU_CODE);
        List<CartItem> cartItems = new ArrayList<>();
        Mockito.when(cartItem.getItemCode()).thenReturn(SKU_CODE);
        Mockito.when(cartItem.getListPrice()).thenReturn(LIST_PRICE);
        Mockito.when(cartItem.getSalePrice()).thenReturn(SALE_PRICE);
        Mockito.when(cartItem.getQuantity()).thenReturn(quantity);
        cartItems.add(cartItem);

        Mockito.when(cart.getCartItems()).thenReturn(cartItems);
        PowerMockito.when(cartRepository.save(cart)).thenThrow(RuntimeException.class);

        cartServiceImpl.updateItemInCart(authToken, cartDto);
    }

}
