package com.candle.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShoppingCartDto {

    private String subTotal;
    private String total;
    private List<ShoppingCartCandleDto> candles = new ArrayList<>();

    public void add(ShoppingCartCandleDto shoppingCartCandleDto) {
        candles.add(shoppingCartCandleDto);
    }
}
