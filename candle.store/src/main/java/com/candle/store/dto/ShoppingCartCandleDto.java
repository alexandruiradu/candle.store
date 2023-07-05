package com.candle.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoppingCartCandleDto {

    private String name;
    private String price;
    private String quantity;
    private String total;
}
