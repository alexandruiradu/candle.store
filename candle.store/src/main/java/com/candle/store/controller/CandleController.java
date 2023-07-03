package com.candle.store.controller;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.service.CandleService;
import com.candle.store.service.CandleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class CandleController {

    @Autowired
    private CandleService candleService;

    @Autowired
    private CandleValidator candleValidator;


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

    @GetMapping("/candle")
    public String viewCandleForm(Model model) {
        CandleDto candleDto = new CandleDto();
        model.addAttribute("candle", candleDto);

        return "candle";
    }

    @PostMapping("/candle/save")
    public String saveCandle(
            @ModelAttribute("candle") CandleDto candleDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam("coverImage") MultipartFile file
    ) throws IOException {
        candleValidator.validate(candleDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("candle", candleDto);
            return "candle";
        }
        candleService.saveCandle(candleDto, file);
        List<Candle> candles = candleService.getAllCandles();
        model.addAttribute("candles", candles);
        return "redirect:/candles";
    }


}
