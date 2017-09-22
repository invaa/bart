package com.bart.service.impl;

import com.bart.dto.BalanceDto;
import com.bart.dto.BalanceItemDto;
import com.bart.dto.ItemDto;
import com.bart.dto.SpecialOfferDto;
import com.bart.entity.Cart;
import com.bart.entity.CartItem;
import com.bart.entity.SpecialOffer;
import com.bart.exception.CartException;
import com.bart.repository.CartRepository;
import com.bart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.bart.constants.Constants.INTEGER_ZERO;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart checkOut(final UUID cartId) throws CartException {
        final Cart cart = getSafely(cartId);
        if (cart.getCheckedOut())
            throw new CartException("Cart " + cartId + " is already checked out.");
        cart.setCheckedOut(true);
        return cartRepository.save(cart);
    }

    @Override
    public BalanceDto getBalance(final UUID cartId) throws CartException {
        final Cart cart = getSafely(cartId);
        return getBalanceDto(cart,
                calculatePrice(cart),
                calculatePriceNoDiscount(cart)
        );
    }

    private BigDecimal calculatePrice(final Cart cart) {
        return cart.getCartItems().stream().map(item -> {
                final SpecialOffer specialOffer = item.getCartItemPK().getItem().getSpecialOffer();
            if (Optional.ofNullable(specialOffer).map(SpecialOffer::getOfferPrice).isPresent()
                    && Optional.ofNullable(specialOffer).map(SpecialOffer::getOfferQty).isPresent()) {
                int qtyNormalPrice = item.getQty() % specialOffer.getOfferQty();
                int offerCycles = (item.getQty() - qtyNormalPrice) / specialOffer.getOfferQty();

                return specialOffer.getOfferPrice().multiply(new BigDecimal(offerCycles))
                        .add(item.getCartItemPK().getItem().getPrice().multiply(new BigDecimal(qtyNormalPrice)));
            } else {
                return item.getCartItemPK().getItem().getPrice().multiply(new BigDecimal(item.getQty()));
            }
        }).reduce(BigDecimal::add).orElse(new BigDecimal(INTEGER_ZERO));
    }

    private BigDecimal calculatePriceNoDiscount(final Cart cart) {
        return cart.getCartItems()
                .stream().map(item ->
                        item.getCartItemPK().getItem().getPrice().multiply(new BigDecimal(item.getQty())))
                .reduce(BigDecimal::add).orElse(new BigDecimal(INTEGER_ZERO));
    }

    private BalanceDto getBalanceDto(final Cart cart, final BigDecimal totalPrice, final BigDecimal totalPriceNoDiscount) {
        return new BalanceDto(
                cart.getCartItems().stream()
                        .map(this::getBalanceItemDto).collect(toList()),
                totalPrice,
                totalPriceNoDiscount.subtract(totalPrice),
                cart.getCheckedOut()
        );
    }

    private BalanceItemDto getBalanceItemDto(final CartItem item) {
        return new BalanceItemDto(
                getItemDto(item),
                item.getQty()
        );
    }

    private ItemDto getItemDto(final CartItem item) {
            return new ItemDto(
                    item.getCartItemPK().getItem().getId(),
                    item.getCartItemPK().getItem().getSku(),
                    item.getCartItemPK().getItem().getPrice(),
                    getSafeSpecialOfferDto(item)
            );
    }

    private SpecialOfferDto getSafeSpecialOfferDto(CartItem item) {
        return Optional.ofNullable(item.getCartItemPK().getItem().getSpecialOffer()).map(SpecialOffer::getOfferPrice).isPresent()
                && Optional.ofNullable(item.getCartItemPK().getItem().getSpecialOffer()).map(SpecialOffer::getOfferQty).isPresent()
        ? new SpecialOfferDto(
                item.getCartItemPK().getItem().getSpecialOffer().getOfferQty(),
                item.getCartItemPK().getItem().getSpecialOffer().getOfferPrice()
        ) : null;
    }

    @Override
    public Cart getSafely(final UUID cartId) throws CartException {
        final Cart cart = cartRepository.getOne(cartId);
        if (!Optional.ofNullable(cart).isPresent())
            throw new CartException("Cart " + cartId + " not found.");
        return cart;
    }

    @Override
    public void cartIsNotCheckedOut(final Cart cart) throws CartException {
        if (cart.getCheckedOut())
            throw new CartException("Cart " + cart.getId().toString() + " is already checked out.");
    }
}
