package com.bart.service.impl;

import com.bart.converter.CartItemDtoToCartItemConverter;
import com.bart.dto.CartItemDto;
import com.bart.entity.Cart;
import com.bart.entity.CartItem;
import com.bart.exception.CartException;
import com.bart.repository.CartItemRepository;
import com.bart.service.CartItemService;
import com.bart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.bart.constants.Constants.INTEGER_ZERO;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDtoToCartItemConverter cartItemConverter;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void remove(final UUID cartId, final UUID itemId) throws CartException {
        cartService.cartIsNotCheckedOut(cartService.getSafely(cartId));
        final CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartItem put(final UUID cartId, final CartItemDto cartItemDto) throws CartException {
        final Cart cart = cartService.getSafely(cartId);
        cartService.cartIsNotCheckedOut(cart);

        final CartItem cartItem = cart.getCartItems().stream().filter(c -> c.getCartItemPK()
                .getItem().getId().equals(cartItemDto.getId())).findFirst()
                .orElseGet(
                        () -> cartItemConverter.convert(Pair.of(cartId, new CartItemDto(cartItemDto.getId(), INTEGER_ZERO)))
                );
        if (cartItem.getQty() + cartItemDto.getQty() < INTEGER_ZERO)
            throw new CartException("Can't set negative quantity for "+ cartItemDto.getId());

        cartItem.setQty(cartItem.getQty() + cartItemDto.getQty());
        return cartItemRepository.save(cartItem);
    }
}
