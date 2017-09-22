package com.bart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
public final class ItemDto {
    private UUID id;
    private String sku;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;
    @Valid
    private SpecialOfferDto specialOffer;
}
