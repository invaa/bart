package com.bart.service.impl;

import com.bart.dto.BalanceDto;
import com.bart.entity.*;
import com.bart.exception.CartException;
import com.bart.repository.CartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceImplTest {
    private static final UUID CART_ID = UUID.fromString("f1ab0350-0091-42bc-858b-611baafa5b6e");
    @InjectMocks
    private CartServiceImpl cartService;
    @Mock
    private CartRepository cartRepositoryMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCountBalance() throws CartException {
        //given
        List<CartItem> cartItems = Arrays.asList(
                getCartItem("7b7ccb89-eca5-49e5-924e-45d9d9dcd1bb", "A", 40, 70, 3,7),
                getCartItem("62a78cc7-af29-48e1-a94c-86a0d3b3817e", "B", 10, 15, 2, 6),
                getCartItemNoDiscount("6a18df93-a203-4b80-b967-b77a65076526", "C", 30, 2),
                getCartItemNoDiscount("6ebf6f07-3aed-4739-83f8-bd0ce447d6b9", "D", 25, 1)
        );

        Cart cart = new Cart();
        cart.setId(CART_ID);
        cart.setCartItems(cartItems);

        when(cartRepositoryMock.getOne(CART_ID)).thenReturn(cart);
        BigDecimal expectedPrice = new BigDecimal(310);
        BigDecimal expectedPriceOfDiscount = new BigDecimal(115);

        //when
        BalanceDto balanceDto = cartService.getBalance(CART_ID);

        //then
        assertEquals(expectedPrice, balanceDto.getPrice());
        assertEquals(expectedPriceOfDiscount, balanceDto.getPriceOfDiscount());
    }

    private CartItem getCartItem(String itemId, String sku, int pxAsInt, int pxDiscountedAsInt, int qtyForDiscount, int qty) {
        return new CartItem(
              new CartItemPK(
                      CART_ID,
                      new Item(
                              UUID.fromString(itemId),
                              sku,
                              new BigDecimal(pxAsInt),
                        new SpecialOffer(
                            qtyForDiscount,
                            new BigDecimal(pxDiscountedAsInt)
                                  )
                          )
                ),
                qty
        );
    }

    private CartItem getCartItemNoDiscount(String itemId, String sku, int pxAsInt, int qty) {
        return new CartItem(
                new CartItemPK(
                        CART_ID,
                        new Item(
                                UUID.fromString(itemId),
                                sku,
                                new BigDecimal(pxAsInt),
                                new SpecialOffer()
                        )
                ),
                qty
        );
    }
}