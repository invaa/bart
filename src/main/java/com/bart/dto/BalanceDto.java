package com.bart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public final class BalanceDto {
    private List<BalanceItemDto> items;
    private BigDecimal price;
    private BigDecimal priceOfDiscount;
    private boolean checkedOut;
}
