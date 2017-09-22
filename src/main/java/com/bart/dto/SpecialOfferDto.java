package com.bart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public final class SpecialOfferDto {
    @Min(1)
    private int qty;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;
}
