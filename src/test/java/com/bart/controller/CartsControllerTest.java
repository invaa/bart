package com.bart.controller;

import com.bart.TestContext;
import com.bart.dto.BalanceDto;
import com.bart.dto.BalanceItemDto;
import com.bart.dto.ItemDto;
import com.bart.dto.SpecialOfferDto;
import com.bart.entity.Cart;
import com.bart.repository.CartRepository;
import com.bart.service.CartItemService;
import com.bart.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static com.bart.JsonHelper.toJSONString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestContext.class)
public class CartsControllerTest {
    private static final String ENDPOINT = "/carts";
    private static final String ENDPOINT_GET_CART = "/carts/{cartId}";
    private static final String ENDPOINT_GET_BALANCE = "/carts/{cartId}/balance";
    private static final String CART_ID = "f1ab0350-0091-42bc-858b-611baafa5b6e";
    private MockMvc mockMvc;

    @InjectMocks
    private CartsController cartsController;
    @Mock
    private CartRepository cartRepositoryMock;
    @Mock
    private CartItemService cartItemServiceMock;
    @Mock
    private CartService cartServiceMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartsController).build();
    }

    @Test
    public void shouldGetCartList() throws Exception {
        //given when then
        mockMvc.perform(get(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldGetCart() throws Exception {
        //given
        when(cartRepositoryMock.getOne(UUID.fromString(CART_ID))).thenReturn(new Cart());

        //when
        MvcResult mvcResult = mockMvc.perform(get(ENDPOINT_GET_CART, CART_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        //then
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(toJSONString(new Cart()), content);
    }

    @Test
    public void shouldCalculateCartBalance() throws Exception {
        //given
        BalanceDto balanceDto = new BalanceDto(
                Collections.singletonList(
                        new BalanceItemDto(
                                new ItemDto(
                                        UUID.fromString("caf2dd00-7d7c-4269-9eee-5c00ecf097b4"),
                                        "ART-123",
                                        new BigDecimal(100),
                                        new SpecialOfferDto(
                                                1,
                                                new BigDecimal(90)
                                        )
                                ),
                                10
                        )
                ),
                new BigDecimal(200),
                new BigDecimal(100),
                true
        );

        when(cartServiceMock.getBalance(UUID.fromString(CART_ID))).thenReturn(balanceDto);

        //when
        MvcResult mvcResult = mockMvc.perform(get(ENDPOINT_GET_BALANCE, CART_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

        //then
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(toJSONString(balanceDto), content);
    }
}