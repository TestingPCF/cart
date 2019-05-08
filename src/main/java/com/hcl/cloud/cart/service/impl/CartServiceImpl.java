package com.hcl.cloud.cart.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.hcl.cloud.cart.constant.CartConstant;
import com.hcl.cloud.cart.controller.CartController;
import com.hcl.cloud.cart.domain.Cart;
import com.hcl.cloud.cart.domain.CartItem;
import com.hcl.cloud.cart.dto.CartDto;
import com.hcl.cloud.cart.dto.InventoryResponse;
import com.hcl.cloud.cart.dto.ProductDto;
import com.hcl.cloud.cart.dto.ProductResponse;
import com.hcl.cloud.cart.dto.TokenInfo;
import com.hcl.cloud.cart.exception.BadRequestException;
import com.hcl.cloud.cart.exception.CustomException;
import com.hcl.cloud.cart.repository.CartRepository;
import com.hcl.cloud.cart.service.CartService;
import com.hcl.cloud.cart.util.EntityTransformerUtility;

/**
 * CartServiceImpl - implementation class for the cart service.
 * @author baghelp
 */
@Service
public class CartServiceImpl implements CartService {
    /**
     * Logger object.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(CartController.class);

    /**
     * Autowired object of the CartRepository to be able
     * to access the members of the JPA repository.
     */
    @Autowired
    private CartRepository cartRepository;
    /**
     * Constant for 204.
     */
    @Value("${cart.constant.nocontent}")
    private String noContent;

    /**
     * Method to add item in the cart.
     * @param authToken authToken
     * @param cartDto cartDto
     * @return boolean value.
     * @throws Exception
     */
    @Override
    public boolean addItemInCart(final String authToken,
                                 final CartDto cartDto) throws Exception {
        boolean notPreset = false;
        validate(cartDto);
        String userId = getUserIdByToken(authToken);
        Cart cart = getCart(userId);
        if (cart != null) {
            setCartQtyToDto(cartDto, cart);
        }

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }
        ProductResponse productResponse =
                getProductDetails(cartDto, authToken);
        setPrices(productResponse, cartDto);

        if (cart != null && (cart.getCartItems() == null
                || cart.getCartItems().isEmpty())) {
            CartItem item = transformCartItem(cartDto);
            item.setCart(cart);
            cart.getCartItems().add(item);
            LOG.info("Item adedd successfully in the shopping cart.");
            notPreset = true;
        }
        if (!cart.getCartItems().isEmpty() && !notPreset) {
            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.getItemCode()
                        .equalsIgnoreCase(cartDto.getSkuCode())) {
                    int qty = cartItem.getQuantity() + cartDto.getQuantity();
                    cartItem.setQuantity(qty);
                    notPreset = true;
                }
            }
        }
        if (!notPreset) {
            CartItem item = transformCartItem(cartDto);
            item.setCart(cart);
            cart.getCartItems().add(item);
        }
        cart.setSubTotal(calculateSubtotal(cart.getCartItems()));
        try {
            Cart persistCart = cartRepository.save(cart);
            if (persistCart != null) {
                LOG.info("Item persisted successfully into the database.");
                return true;
            } else {
                LOG.info("Item could not be saved into the database.");
                return false;
            }
        } catch (RuntimeException ex) {
            LOG.error("Item cannot be added into the cart. ", ex.getMessage());
            throw new RuntimeException(ex.getMessage());

        }

    }

    /**
     * Private method to validate the cartDto object attributes.
     * @param cartDto cartDto
     * @throws Exception Exception
     */
    private void validate(final CartDto cartDto) throws BadRequestException {
        if (cartDto.getSkuCode() == null || "".equals(cartDto.getSkuCode())) {
            LOG.info("Sku code is mandatory.");
            throw new BadRequestException("Sku code is mandatory");
        }
        if (cartDto.getQuantity() <= 0) {
            LOG.info("Quantity must be 1 or greater.");
            throw new BadRequestException("Quantity must be 1 or greater");
        }
    }

    /**
     * Method to retrieve the details of the shopping cart by userId.
     * 
     * @param authToken string type.
     * @return cart {@link Cart}
     * @throws IOException
     * @throws CustomException
     */
    @Override
    public Cart getCartById(final String authToken) throws CustomException, IOException {
        String userId = getUserIdByToken(authToken);
        Cart cart = getCart(userId);
        return cart;

    }

    /**
     * Private method to get cart details by userId.
     * 
     * @return cart object Cart type.
     * @throws IOException
     * @throws CustomException
     */
    private Cart getCart(final String userId) throws CustomException, IOException {
        return cartRepository.findByUserId(userId);
    }

    /**
     * @param cartDto
     * @param authToken
     * @throws IOException
     * @throws BadRequestException
     * @throws ServiceUnavailableException
     */
    private void checkInventory(final CartDto cartDto, final String authToken)
            throws IOException, BadRequestException, ServiceUnavailableException {
        try {
            InventoryResponse inventoryResponse = EntityTransformerUtility.getInventoryResponse(cartDto, authToken);
            int totalQuantity = cartDto.getQuantity() + cartDto.getQuantityInCart();
            if (!inventoryResponse.isInStock()
                    || (inventoryResponse.isInStock() && totalQuantity > inventoryResponse.getQuantity())) {
                throw new BadRequestException("Item out of stock");
            }
        } catch (HttpClientErrorException ex) {
            LOG.error("Inventory Service not available", ex.getMessage());
            throw new com.hcl.cloud.cart.exception.ServiceUnavailableException("Inventory Service not available",
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * @param cartDto
     * @param authToken
     * @throws IOException
     * @throws BadRequestException
     * @throws ServiceUnavailableException
     */
    private ProductResponse getProductDetails(final CartDto cartDto, final String authToken)
            throws IOException, BadRequestException, ServiceUnavailableException {
        ProductResponse productResponse = EntityTransformerUtility.getProductResponse(cartDto, authToken);
        if (productResponse != null && noContent.equals(productResponse.getStatusCode())) {
            throw new BadRequestException(productResponse.getStatus());
        } else if (productResponse != null && !noContent.equals(productResponse.getStatusCode())) {
            checkInventory(cartDto, authToken);
        }
        return productResponse;
    }

    /**
     * This method return userId based on Authorization token value.
     * 
     * @param authToken {@link String}
     * @return userId {@link String} based on token.
     * @throws IOException
     * @throws CustomException
     */
    private String getUserIdByToken(final String authToken) throws IOException, CustomException {
        try {
            TokenInfo tokenInfo = EntityTransformerUtility.getTokenInfo(authToken);
            if (tokenInfo != null && tokenInfo.getStatus() == 0) {
                return tokenInfo.getUserId();
            } else if (tokenInfo != null && tokenInfo.getStatus() == 401) {
                throw new CustomException("Unauthorized User", HttpStatus.UNAUTHORIZED);
            }
        } catch (HttpClientErrorException ex) {
            throw new CustomException("Unauthorized User", HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    /**
     * @param productResponse
     * @param cartDto
     */
    private void setPrices(final ProductResponse productResponse, final CartDto cartDto) {
        if (productResponse != null && !productResponse.getProductList().isEmpty()) {
            ProductDto productDto = productResponse.getProductList().get(0);
            if (productDto != null) {
                cartDto.setListPrice(new BigDecimal(productDto.getListPrice()));
                cartDto.setSalePrice(new BigDecimal(productDto.getSalePrice()));
                cartDto.setProductName(productDto.getProductName());
            }
        }
    }

    /**
     * @param cartDto
     * @return cartItem {@link CartItem}
     */
    private CartItem transformCartItem(final CartDto cartDto) {
        CartItem cartItem = new CartItem();
        if (!StringUtils.isEmpty(cartDto.getSkuCode())) {
            cartItem.setItemCode(cartDto.getSkuCode());
        }
        if (!StringUtils.isEmpty(cartDto.getQuantity())) {
            cartItem.setQuantity(cartDto.getQuantity());
        }
        if (!StringUtils.isEmpty(cartDto.getListPrice())) {
            cartItem.setListPrice(cartDto.getListPrice());
        }
        if (!StringUtils.isEmpty(cartDto.getSalePrice())) {
            cartItem.setSalePrice(cartDto.getSalePrice());
        }
        if (!StringUtils.isEmpty(cartDto.getProductName())) {
            cartItem.setProductName(cartDto.getProductName());
        }
        return cartItem;
    }

    /**
     * This method to set current quantity in cart to dto for inventory check.
     * 
     * @param cartDto {@link CartDto}
     * @param cart    {@link Cart}
     */
    private void setCartQtyToDto(final CartDto cartDto, final Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getItemCode().equalsIgnoreCase(cartDto.getSkuCode())) {
                cartDto.setQuantityInCart(cartItem.getQuantity());
            }
        }

    }

    /**
     * Method to calculate the total amount of the items added in the shopping cart.
     * 
     * @param cartItems of cartItems
     * @return
     */
    private BigDecimal calculateSubtotal(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            total = total.add(cartItem.getSalePrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }
        return total;
    }

    /**
     * Method to update item in the cart.
     * 
     * @param authToken
     * @param cartDto
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateItemInCart(String authToken, CartDto cartDto) throws Exception {

        List<CartItem> itemList = new ArrayList<CartItem>();
        validate(cartDto);
        String userId = getUserIdByToken(authToken);
        Cart cart = getCart(userId);
        if (!StringUtils.isEmpty(cart)) {
            setCartQtyToDto(cartDto, cart);
        }

        if (StringUtils.isEmpty(cart)) {
            cart = new Cart();
            cart.setUserId(userId);
        }

        try {
            Cart updateCart = null;
            ProductResponse productResponse = getProductDetails(cartDto, authToken);
            setPrices(productResponse, cartDto);

            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.getItemCode().equalsIgnoreCase(cartDto.getSkuCode())) {
                    CartItem item = transformCartItem(cartDto);
                    itemList.add(item);
                    cart.setCartItems(itemList);
                    cart.setSubTotal(calculateSubtotal(cart.getCartItems()));
                    updateCart = cartRepository.save(cart);
                    LOG.info("Item  updated into the cart and database.");
                    break;

                }
            }

            if (updateCart != null) {
                LOG.info("Item persisted successfully into the database.");
                return true;
            } else {
                LOG.info("Item could not be updated into the database.");
                return false;
            }

        } catch (RuntimeException ex) {
            LOG.error("Item cannot be updated into the cart. ", ex.getMessage());
            throw new RuntimeException(ex.getMessage());

        }

    }

}
