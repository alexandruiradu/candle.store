package com.candle.store.service;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.entity.FileCover;
import com.candle.store.mapper.CandleMapper;
import com.candle.store.repository.CandleRepository;
import com.candle.store.repository.FileCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class CandleService {

    private static final String DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    private CandleRepository candleRepository;

    @Autowired
    private CandleMapper candleMapper;

    @Autowired
    private FileCoverRepository fileCoverRepository;

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
        Path fileName = Paths.get(DIRECTORY);
        FileCover fileCover = new FileCover();
        fileCover.setPath(fileName.toFile().getPath());
        FileCover fileSaved = fileCoverRepository.save(fileCover);

        final String fileExtension = Optional.ofNullable(file.getOriginalFilename())
                .flatMap(CandleService::getFileExtension)
                .orElse("");

        final String targetFileName = fileSaved.getId() + "." + fileExtension;
        final Path targetPath = fileName.resolve(targetFileName);

        File f = new File(String.valueOf(targetPath));
        file.transferTo(f);

        Optional<Candle> candle1 = candleRepository.findById(candle.getId());
        if (candle1.isPresent()) {
            candle1.get().setTitle(candle.getTitle());
            candle1.get().setDescription(candle.getDescription());
            candle1.get().setPrice(candle.getPrice());
            candle1.get().setQuantity(candle.getQuantity());
            candle1.get().setFileCover(fileSaved);

            candleRepository.save(candle1.get());
        }
    }

    private static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }

}
