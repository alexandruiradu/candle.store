package com.candle.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDetailsDto {

    private Integer id;
    private String fullName;
    private String email;
    private String address;
//    private String password;
//    private String confirmPassword;
}
