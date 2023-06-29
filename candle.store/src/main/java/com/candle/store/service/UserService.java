package com.candle.store.service;

import com.candle.store.dto.UserDto;
import com.candle.store.entity.User;
import com.candle.store.mapper.UserMapper;
import com.candle.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    public void saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }
}
