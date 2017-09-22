package com.bart.service;

import com.bart.dto.BalanceDto;
import com.bart.entity.Cart;
import com.bart.exception.CartException;

import java.util.UUID;

public interface CartService {
    Cart checkOut(final UUID cartId) throws CartException;
    BalanceDto getBalance(final UUID cartId) throws CartException;
    Cart getSafely(final UUID cartId) throws CartException;
    void cartIsNotCheckedOut(final Cart cart) throws CartException;
}
