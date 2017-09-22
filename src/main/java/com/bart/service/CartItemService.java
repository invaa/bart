package com.bart.service;

import com.bart.dto.CartItemDto;
import com.bart.entity.CartItem;
import com.bart.exception.CartException;

import java.util.UUID;

public interface CartItemService {
    void remove(final UUID cartId, final UUID itemId) throws CartException;
    CartItem put(final UUID cartId, final CartItemDto cartItemDto) throws CartException;
}
