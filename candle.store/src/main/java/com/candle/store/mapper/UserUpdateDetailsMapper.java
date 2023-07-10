package com.candle.store.mapper;

import com.candle.store.dto.UserUpdateDetailsDto;
import com.candle.store.entity.User;
import com.candle.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUpdateDetailsMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User map(UserUpdateDetailsDto userUpdateDetailsDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            user.get().setFullName(userUpdateDetailsDto.getFullName());
            user.get().setEmail(userUpdateDetailsDto.getEmail());
            user.get().setAddress(userUpdateDetailsDto.getAddress());

            return user.get();
        }
        return null;
    }
}
