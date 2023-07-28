package com.candle.store.service;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.mapper.CandleMapper;
import com.candle.store.repository.CandleRepository;
import com.candle.store.util.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class CandleService {

    private final FileParser fileParser;
    private final CandleRepository candleRepository;
    private final CandleMapper candleMapper;

    @Autowired
    public CandleService(CandleRepository candleRepository, CandleMapper candleMapper, FileParser fileParser) {
        this.candleRepository = candleRepository;
        this.candleMapper = candleMapper;
        this.fileParser = fileParser;
    }

    public List<Candle> getAllCandles() {
        return candleRepository.findAll();
    }

    public void saveCandle(CandleDto candleDto, MultipartFile file) throws IOException {
        Candle candle = candleMapper.mapper(candleDto, file);
        candleRepository.save(candle);
    }

    public Candle findById(String candleId) {
        Optional<Candle> candle = candleRepository.findById(Integer.parseInt(candleId));
        if (candle.isPresent()) {
            return candle.get();
        }
        return null;
    }

    public void updateCandle(Candle candle, MultipartFile file) throws IOException {

        Optional<Candle> candle1 = candleRepository.findById(candle.getId());
        if (candle1.isPresent()) {
            candle1.get().setTitle(candle.getTitle());
            candle1.get().setDescription(candle.getDescription());
            candle1.get().setPrice(candle.getPrice());
            candle1.get().setQuantity(candle.getQuantity());
            candle1.get().setFileCover(fileParser.getFile(file));

            candleRepository.save(candle1.get());
        }
    }

}
