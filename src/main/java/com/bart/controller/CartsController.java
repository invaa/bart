package com.bart.controller;

import com.bart.dto.BalanceDto;
import com.bart.dto.CartItemDto;
import com.bart.entity.Cart;
import com.bart.entity.CartItem;
import com.bart.exception.CartException;
import com.bart.repository.CartRepository;
import com.bart.service.CartItemService;
import com.bart.service.CartService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@EnableAutoConfiguration
@RestController
@RequestMapping("carts")
@Log4j
public class CartsController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<Cart> createCart() {
        final Cart newCart = cartRepository.save(new Cart());
        log.info("Cart saved: " + newCart.getId());
        return new ResponseEntity<>(newCart, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Cart> getCart(@PathVariable("cartId") final UUID cartId) {
        return new ResponseEntity<>(cartRepository.getOne(cartId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/items/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> removeItemFromCart(
            @PathVariable("cartId") final UUID cartId,
            @PathVariable("itemId") final UUID itemId
            ) throws CartException {
        cartItemService.remove(cartId, itemId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/items", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<CartItem> addItemToCart(
            @PathVariable("cartId") final UUID cartId,
            @Valid @RequestBody final CartItemDto cartItemDto
    ) throws CartException {
        final CartItem cartItem = cartItemService.put(cartId, cartItemDto);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> getCarts() {
        final List<Cart> cartList = cartRepository.findAll();
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/balance", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<BalanceDto> getCartBalance(@PathVariable("cartId") final UUID cartId) throws CartException {
        return new ResponseEntity<>(cartService.getBalance(cartId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/checkOut", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<Cart> checkOut(@PathVariable("cartId") final UUID cartId) throws CartException {
        return new ResponseEntity<>(cartService.checkOut(cartId), HttpStatus.OK);
    }
}
