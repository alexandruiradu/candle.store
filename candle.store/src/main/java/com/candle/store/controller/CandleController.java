package com.candle.store.controller;

import com.candle.store.entity.Candle;
import com.candle.store.service.CandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CandleController {

    @Autowired
    private CandleService candleService;


    @GetMapping("/")
    public String viewTemplate(Model model) {

        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (authenticated) {
            List<Candle> candles = candleService.getAllCandles();
            model.addAttribute("candles", candles);
//            ChosenItemDto chosenItemDto = new ChosenItemDto();
//            model.addAttribute("chosenItemDto", chosenItemDto);
            return "candles";
        } else {
            return "login";
        }
    }

    @GetMapping("/candles")
    public String viewCandles(Model model) {
        List<Candle> candles = candleService.getAllCandles();
        model.addAttribute("candles", candles);

        return "candles";
    }


}
