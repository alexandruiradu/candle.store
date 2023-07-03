package com.candle.store.service;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.mapper.CandleMapper;
import com.candle.store.repository.CandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CandleService {

    @Autowired
    private CandleRepository candleRepository;

    @Autowired
    private CandleMapper candleMapper;

    public List<Candle> getAllCandles() {
        return candleRepository.findAll();
    }

    public void saveCandle(CandleDto candleDto, MultipartFile file) throws IOException {
        Candle candle = candleMapper.mapper(candleDto, file);
        candleRepository.save(candle);
    }
}
