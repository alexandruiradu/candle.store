package com.candle.store.service;

import com.candle.store.dto.*;
import com.candle.store.entity.User;
import com.candle.store.mapper.UserMapper;
import com.candle.store.mapper.UserUpdateDetailsMapper;
import com.candle.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserUpdateDetailsMapper userUpdateDetailsMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper, UserUpdateDetailsMapper userUpdateDetailsMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userUpdateDetailsMapper = userUpdateDetailsMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }

    public UserDetailsDto getUserDto(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setFullName(user.get().getFullName());
        userDetailsDto.setAddress(user.get().getAddress());

        return userDetailsDto;
    }

    public void updateUserDetails(UserUpdateDetailsDto userUpdateDetailsDto, String email) {
        User user = userUpdateDetailsMapper.map(userUpdateDetailsDto, email);
        userRepository.save(user);
    }

    public UserUpdateDetailsDto getUserDetailsDto(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        UserUpdateDetailsDto userUpdateDetailsDto = new UserUpdateDetailsDto();
        userUpdateDetailsDto.setEmail(user.get().getEmail());
        userUpdateDetailsDto.setFullName(user.get().getFullName());
        userUpdateDetailsDto.setAddress(user.get().getAddress());
        userUpdateDetailsDto.setId(user.get().getId());

        return userUpdateDetailsDto;
    }

    public User findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);

    }

    public void updateUserPassword(User user, UserEmailDto userEmailDto) {
        user.setPassword(passwordEncoder.encode(userEmailDto.getPassword()));
        userRepository.save(user);
    }
}
