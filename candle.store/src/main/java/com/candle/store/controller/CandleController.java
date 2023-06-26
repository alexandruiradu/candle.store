package com.candle.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CandleController {


    @GetMapping("/")
    public String viewTemplate() {

        return "login";
    }

}
