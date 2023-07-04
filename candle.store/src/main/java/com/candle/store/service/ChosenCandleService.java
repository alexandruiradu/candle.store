package com.candle.store.service;

import com.candle.store.dto.ChosenCandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.entity.ChosenCandle;
import com.candle.store.entity.User;
import com.candle.store.repository.CandleRepository;
import com.candle.store.repository.ChosenCandleRepository;
import com.candle.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChosenCandleService {

    @Autowired
    private ChosenCandleRepository chosenCandleRepository;

    @Autowired
    private CandleRepository candleRepository;

    @Autowired
    private UserRepository userRepository;


    public void saveChosenCandle(ChosenCandleDto chosenCandleDto, String candleId, String email) {
        ChosenCandle chosenCandle = buildProduct(chosenCandleDto, candleId, email);
        chosenCandleRepository.save(chosenCandle);
    }

    private ChosenCandle buildProduct(ChosenCandleDto chosenCandleDto, String candleId, String email) {
        ChosenCandle chosenCandle = new ChosenCandle();
        chosenCandle.setChosenQuantity(Integer.parseInt(chosenCandleDto.getQuantity()));

        Optional<Candle> candle = candleRepository.findById(Integer.parseInt(candleId));
        candle.ifPresent(chosenCandle::setCandle);

        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(value -> chosenCandle.setShoppingCart(value.getShoppingCart()));
        return chosenCandle;
    }
}
