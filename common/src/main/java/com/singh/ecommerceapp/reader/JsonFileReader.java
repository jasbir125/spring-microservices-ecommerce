package com.singh.ecommerceapp.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonFileReader implements FileReaderStrategy {

    private final ObjectMapper objectMapper;

    @Override
    public boolean canRead(String filePath) {
        return filePath.endsWith(".json");
    }

    @Override
    public <T> List<T> read(String filePath, Class<T> entityClass) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        return objectMapper.readValue(inputStream,
                objectMapper.getTypeFactory().constructCollectionType(List.class, entityClass));
    }
}