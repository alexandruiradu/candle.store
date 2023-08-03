package com.candle.store.service;

import com.candle.store.dto.ChosenCandleDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ChosenCandleValidator {

    public void validate(ChosenCandleDto chosenCandleDto, BindingResult bindingResult) {
        if (chosenCandleDto.getQuantity().isEmpty()) {
            FieldError fieldError = new FieldError(
                    "chosenCandleDto",
                    "quantity",
                    "Please fill in mandatory fields!"
            );
            bindingResult.addError(fieldError);
        } else if (Integer.parseInt(chosenCandleDto.getQuantity()) <= 0) {
            FieldError fieldError = new FieldError(
                    "chosenCandleDto",
                    "quantity",
                    "Please insert a proper value!"
            );
            bindingResult.addError(fieldError);
        }
    }
}
