package com.candle.store.service;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class CandleValidator {

    public void validateCandleDto(CandleDto candleDto, BindingResult bindingResult) {
        if (candleDto.getTitle().isEmpty()) {
            FieldError fieldError = new FieldError(
                    "candleDto",
                    "title",
                    "Pleas fill mandatory fields!"
            );
            bindingResult.addError(fieldError);
        }

        if (candleDto.getPrice().isEmpty()) {
            FieldError fieldError = new FieldError(
                    "candleDto",
                    "price",
                    "Please fill mandatory fields!"
            );
            bindingResult.addError(fieldError);
        } else if (Double.parseDouble(candleDto.getPrice()) <= 0) {
            FieldError fieldError = new FieldError(
                    "candleDto",
                    "price",
                    "Please insert a positive value!"
            );
            bindingResult.addError(fieldError);
        }
        if (candleDto.getQuantity().isEmpty()) {
            FieldError fieldError = new FieldError(
                    "candleDto",
                    "quantity",
                    "Please fill mandatory fields!"
            );
            bindingResult.addError(fieldError);
        } else if (Double.parseDouble(candleDto.getQuantity()) <= 0) {
            FieldError fieldError = new FieldError(
                    "candleDto",
                    "quantity",
                    "Please insert a positive value!"
            );
            bindingResult.addError(fieldError);
        }

    }

    public void validateCandle(Candle candle, BindingResult bindingResult){
        if (candle.getTitle().isEmpty()){
            FieldError fieldError = new FieldError(
                    "candle",
                    "title",
                    "Please fill mandatory fields!"
            );
            bindingResult.addError(fieldError);
        }

        if (candle.getPrice() <= 0){
            FieldError fieldError = new FieldError(
                    "candle",
                    "price",
                    "Please insert a proper value!"
            );
            bindingResult.addError(fieldError);
        }

        if (candle.getQuantity() <= 0){
            FieldError fieldError = new FieldError(
                    "candle",
                    "quantity",
                    "Please insert a proper value!"
            );
            bindingResult.addError(fieldError);
        }
    }

}
