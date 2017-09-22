package com.bart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Getter
public final class CartItemDto {
    @NotNull
    private UUID id;
    @Min(0)
    private int qty;
}
