package com.singh.ecommerceapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataImportUtility {

    private final ObjectMapper objectMapper;

    /**
     * Imports a list of entities from a JSON file and processes each entity.
     *
     * @param jsonFilePath     Path to the JSON file (relative to classpath)
     * @param entityClass      Class type of the entities to be deserialized
     * @param entityProcessor  Consumer to process each entity (e.g. save to DB)
     * @param <T>              Type of entity
     */
    public <T> void importEntitiesFromJson(String jsonFilePath, Class<T> entityClass, Consumer<T> entityProcessor) {
        log.info("Starting import of entities from file: {}", jsonFilePath);

        try (InputStream inputStream = new ClassPathResource(jsonFilePath).getInputStream()) {
            List<T> entities = objectMapper.readValue(
                    inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, entityClass)
            );

            log.info("Parsed {} entities of type {} from JSON file: {}", entities.size(), entityClass.getSimpleName(), jsonFilePath);

            for (int i = 0; i < entities.size(); i++) {
                T entity = entities.get(i);
                log.debug("Processing entity #{}: {}", i + 1, entity);
                try {
                    entityProcessor.accept(entity);
                } catch (Exception e) {
                    log.warn("Failed to process entity #{}: {} - continuing with next", i + 1, e.getMessage());
                }
            }

            log.info("Successfully imported and processed {} entities from: {}", entities.size(), jsonFilePath);
        } catch (IOException e) {
            log.error("Failed to read or parse JSON file: {}", jsonFilePath, e);
        } catch (Exception e) {
            log.error("Unexpected error during data import from file: {}", jsonFilePath, e);
        }
    }
}