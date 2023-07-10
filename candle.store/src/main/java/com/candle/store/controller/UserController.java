package com.candle.store.controller;


import com.candle.store.dto.UserUpdateDetailsDto;
import com.candle.store.entity.User;
import com.candle.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/details")
    public String viewUserDetails(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserUpdateDetailsDto userUpdateDetailsDto = userService.getUserDetailsDto(email);
        model.addAttribute("userUpdateDetailsDto", userUpdateDetailsDto);

        return "user";
    }

    @GetMapping("/user/account")
    public String viewAccount(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user", user);

        return "account";
    }

    @PatchMapping("/user/update/details")
    public String userDetailsUpdate(
            @ModelAttribute("userUpdateDetailsDto") UserUpdateDetailsDto userUpdateDetailsDto,
            Model model,
            BindingResult bindingResult
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userUpdateDetailsDto", userUpdateDetailsDto);
        userService.updateUserDetails(userUpdateDetailsDto, email);

        return "redirect:/logout";
    }
}


