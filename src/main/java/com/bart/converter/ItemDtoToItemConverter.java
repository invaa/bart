package com.bart.converter;

import com.bart.dto.ItemDto;
import com.bart.dto.SpecialOfferDto;
import com.bart.entity.Item;
import com.bart.entity.SpecialOffer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemDtoToItemConverter
        implements Converter<ItemDto, Item> {
    @Override
    public Item convert(final ItemDto itemDto) {
        final SpecialOffer specialOffer = new SpecialOffer();
        if (Optional.ofNullable(itemDto.getSpecialOffer())
                .map(SpecialOfferDto::getPrice)
                .isPresent() && Optional.ofNullable(itemDto.getSpecialOffer())
                                    .map(SpecialOfferDto::getQty)
                                    .isPresent()) {
            specialOffer.setOfferPrice(itemDto.getSpecialOffer().getPrice());
            specialOffer.setOfferQty(itemDto.getSpecialOffer().getQty());
        }
        return new Item(
                itemDto.getId(),
                itemDto.getSku(),
                itemDto.getPrice(),
                specialOffer
        );
    }
}
