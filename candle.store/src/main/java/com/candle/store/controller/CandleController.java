package com.candle.store.controller;

import com.candle.store.dto.CandleDto;
import com.candle.store.dto.ChosenCandleDto;
import com.candle.store.dto.ShoppingCartDto;
import com.candle.store.dto.UserDetailsDto;
import com.candle.store.entity.Candle;
import com.candle.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class CandleController {
    private final CandleService candleService;
    private final CandleValidator candleValidator;
    private final ChosenCandleService chosenCandleService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final CustomerOrderService customerOrderService;

    @Autowired
    public CandleController(CandleService candleService, CandleValidator candleValidator, ChosenCandleService chosenCandleService, ShoppingCartService shoppingCartService, UserService userService, CustomerOrderService customerOrderService) {
        this.candleService = candleService;
        this.candleValidator = candleValidator;
        this.chosenCandleService = chosenCandleService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/")
    public String viewTemplate(Model model) {

        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (authenticated) {
            List<Candle> candles = candleService.getAllCandles();
            model.addAttribute("candles", candles);
            ChosenCandleDto chosenCandleDto = ChosenCandleDto.builder().build();
            model.addAttribute("chosenCandleDto", chosenCandleDto);
            return "candles";
        } else {
            return "login";
        }
    }

    @GetMapping("/candles")
    public String viewCandles(Model model) {
        List<Candle> candles = candleService.getAllCandles();
        model.addAttribute("candles", candles);
        ChosenCandleDto chosenCandleDto = ChosenCandleDto.builder().build();
        model.addAttribute("chosenCandleDto", chosenCandleDto);

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
        ChosenCandleDto chosenCandleDto = ChosenCandleDto.builder().build();
        model.addAttribute("chosenCandleDto", chosenCandleDto);
        return "redirect:/candles";
    }

    @PostMapping("/candle/{candleId}")
    public String addToShoppingList(
            @PathVariable(value = "candleId") String candleId,
            @ModelAttribute ChosenCandleDto chosenCandleDto,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("chosenCandleDto", chosenCandleDto);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        chosenCandleService.saveChosenCandle(chosenCandleDto, candleId, email);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartByUserEmail(email);
        model.addAttribute("shoppingCartDto", shoppingCartDto);

        return "cart";
    }

    @GetMapping("/checkout")
    public String viewCheckout(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartByUserEmail(email);
        model.addAttribute("shoppingCartDto", shoppingCartDto);

        UserDetailsDto userDetailsDto = userService.getUserDto(email);
        model.addAttribute("userDetailsDto", userDetailsDto);

        return "checkout";
    }

    @PostMapping("/sendOrder")
    public String sendOrder(@ModelAttribute("userDetailsDto") UserDetailsDto userDetailsDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        customerOrderService.addCustomerOrder(email, userDetailsDto.getShippingAddress());

        return "confirmation";
    }

    @GetMapping("/candle/{candleId}")
    public String viewCandle(
            @PathVariable(value = "candleId") String candleId,
            Model model) {
        Candle foundCandle = candleService.findById(candleId);
        model.addAttribute(foundCandle);

        return "candle-update";
    }

    @PutMapping("/candle/update")
    public String updateCandle(
            @ModelAttribute("candle") Candle candle,
            Model model,
            BindingResult bindingResult,
            @RequestParam("coverImage") MultipartFile file) throws IOException {
        model.addAttribute("candle", candle);
        candleService.updateCandle(candle, file);
        List<Candle> candles = candleService.getAllCandles();
        model.addAttribute("candles", candles);
        ChosenCandleDto chosenCandleDto = ChosenCandleDto.builder().build();
        model.addAttribute("chosenCandleDto", chosenCandleDto);
        return "redirect:/candles";
    }


}
