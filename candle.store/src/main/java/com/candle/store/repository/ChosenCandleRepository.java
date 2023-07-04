package com.candle.store.repository;

import com.candle.store.entity.ChosenCandle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChosenCandleRepository extends JpaRepository<ChosenCandle, Integer> {
}
