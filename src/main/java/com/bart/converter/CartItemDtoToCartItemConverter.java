package com.bart.converter;

import com.bart.dto.CartItemDto;
import com.bart.entity.Cart;
import com.bart.entity.CartItem;
import com.bart.entity.CartItemPK;
import com.bart.entity.Item;
import com.bart.exception.CartException;
import com.bart.exception.ConversionException;
import com.bart.repository.ItemRepository;
import com.bart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemDtoToCartItemConverter
        implements Converter<Pair<UUID, CartItemDto>, CartItem> {
    @Autowired
    private CartService cartService;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public CartItem convert(final Pair<UUID, CartItemDto> pair) {
        final Cart cart;
        try {
            cart = cartService.getSafely(pair.getFirst());
        } catch (final CartException ex) {
            throw new ConversionException("Can't find cart: " + pair.getFirst(), ex);
        }
        final Item item = itemRepository.findOne(pair.getSecond().getId());
        if (!Optional.ofNullable(item).isPresent())
            throw new ConversionException("Can't find item: " + pair.getSecond().getId());

        return new CartItem(
                new CartItemPK(cart.getId(), item),
                pair.getSecond().getQty()
        );
    }
}
