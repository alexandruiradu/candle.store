package com.candle.store.mapper;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.util.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Component
public class CandleMapper {

    @Autowired
    private FileParser fileParser;

    public Candle mapper(CandleDto candleDto, MultipartFile file) throws IOException {
        Candle candle = new Candle();
        candle.setTitle(candleDto.getTitle());
        candle.setDescription(candleDto.getDescription());
        candle.setPrice(Double.parseDouble(candleDto.getPrice()));
        candle.setQuantity(Integer.parseInt(candleDto.getQuantity()));
        candle.setFileCover(fileParser.getFile(file));

        return candle;
    }


}
