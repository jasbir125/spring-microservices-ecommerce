package com.singh.ecommerceapp.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TextFileReader implements FileReaderStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public boolean canRead(String filePath) {
        return filePath.endsWith(".txt");
    }

    @Override
    public <T> List<T> read(String filePath, Class<T> entityClass) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(line -> parseLine(line, entityClass))
                    .collect(Collectors.toList());
        }
    }

    private <T> T parseLine(String line, Class<T> entityClass) {
        try {
            return objectMapper.readValue(line, entityClass);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing line: " + line, e);
        }
    }
}