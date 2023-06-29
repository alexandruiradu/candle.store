package com.candle.store.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String address;
    private String userRole;
}
