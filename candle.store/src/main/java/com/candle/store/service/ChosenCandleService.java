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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChosenCandleService {

    private final ChosenCandleRepository chosenCandleRepository;
    private final CandleRepository candleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChosenCandleService(ChosenCandleRepository chosenCandleRepository, CandleRepository candleRepository, UserRepository userRepository) {
        this.chosenCandleRepository = chosenCandleRepository;
        this.candleRepository = candleRepository;
        this.userRepository = userRepository;
    }

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

//        checkForDup(chosenCandle);
        return chosenCandle;
    }

    public void removeChosenCandle(String candleId) {
        Optional<ChosenCandle> chosenCandle = chosenCandleRepository.findById(Integer.parseInt(candleId));
        if (chosenCandle.isPresent()) {
            ChosenCandle chosenCandleFound = chosenCandle.get();
            chosenCandleRepository.delete(chosenCandleFound);
        }

    }

//    private void checkForDup(ChosenCandle chosenCandle) {
//        List<ChosenCandle> chosenCandles = chosenCandleRepository.findAll();
//        for (ChosenCandle candle : chosenCandles) {
//            if (candle.getCandle().equals(chosenCandle.getCandle())
//                    && candle.getShoppingCart().equals(chosenCandle.getShoppingCart())) {
//                chosenCandle.setId(candle.getId());
//            }
//        }
//    }

}
