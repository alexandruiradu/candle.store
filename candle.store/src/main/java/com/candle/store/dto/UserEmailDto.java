package com.candle.store.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserEmailDto {

    private String email;
    private String password;
    private String confirmPassword;
}
