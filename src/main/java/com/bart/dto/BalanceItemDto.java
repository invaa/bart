package com.bart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class BalanceItemDto {
    private ItemDto item;
    private int qty;
}
