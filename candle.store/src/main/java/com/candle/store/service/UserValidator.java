package com.candle.store.service;

import com.candle.store.dto.UserDto;
import com.candle.store.entity.User;
import com.candle.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public void validate(UserDto userDto, BindingResult bindingResult) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            FieldError fieldError = new FieldError(
                    "userDto",
                    "confirmPassword",
                    "Passwords don't match!");
            bindingResult.addError(fieldError);
        }

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            FieldError fieldError = new FieldError(
                    "userDto",
                    "email",
                    "Email is already in use!"
            );
            bindingResult.addError(fieldError);
        }
    }
}
