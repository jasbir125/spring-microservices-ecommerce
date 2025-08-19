package com.singh.ecommerceapp.service;

import com.singh.ecommerceapp.processor.EntityProcessor;
import com.singh.ecommerceapp.reader.FileReaderStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoaderService {

    private final List<FileReaderStrategy> fileReaders;

    public <T> void importData(String filePath, EntityProcessor<T> entityProcessor) throws IOException {
        //log.info("Starting data import from file: {}", filePath);
        // Find the appropriate file reader for the given file type
        FileReaderStrategy fileReader = fileReaders.stream()
                .filter(reader -> reader.canRead(filePath))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported file format for: " + filePath));

        // Parse the file into entities
        ParameterizedType type = (ParameterizedType) entityProcessor.getClass().getGenericInterfaces()[0];
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
        List<T> entities = fileReader.read(filePath, clazz);
        //process each entity
        entities.forEach(entityProcessor::process);
        //log.info("Data import completed for file: {}", filePath);
    }
}