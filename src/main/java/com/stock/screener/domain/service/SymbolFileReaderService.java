package com.stock.screener.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SymbolFileReaderService {

    @Value("${symbols.file-path}")
    private String symbolsFilePath;

    public List<String> readSymbolsFromFile() {
        try {
            return Files.readAllLines(Paths.get(symbolsFilePath))
                    .stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .filter(line -> !line.startsWith("//"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to read symbols file: {}", symbolsFilePath, e);
            return Collections.emptyList();
        }
    }
}