package com.candle.store.mapper;

import com.candle.store.dto.CandleDto;
import com.candle.store.entity.Candle;
import com.candle.store.entity.FileCover;
import com.candle.store.repository.FileCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class CandleMapper {

    private static final String DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    private FileCoverRepository fileCoverRepository;

    public Candle mapper(CandleDto candleDto, MultipartFile file) throws IOException {
        Path fileName = Paths.get(DIRECTORY);
        FileCover fileCover = new FileCover();
        fileCover.setPath(fileName.toFile().getPath());
        FileCover fileSaved = fileCoverRepository.save(fileCover);

        final String fileExtension = Optional.ofNullable(file.getOriginalFilename())
                .flatMap(CandleMapper::getFileExtension)
                .orElse("");

        final String targetFileName = fileSaved.getId() + "." + fileExtension;
        final Path targetPath = fileName.resolve(targetFileName);

        File f = new File(String.valueOf(targetPath));
        file.transferTo(f);

        Candle candle = new Candle();
        candle.setTitle(candleDto.getTitle());
        candle.setDescription(candleDto.getDescription());
        candle.setPrice(Double.parseDouble(candleDto.getPrice()));
        candle.setQuantity(Integer.parseInt(candleDto.getQuantity()));
        candle.setFileCover(fileSaved);

        return candle;
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
