package com.singh.ecommerceapp.reader;

import java.io.IOException;
import java.util.List;

public interface FileReaderStrategy {

    boolean canRead(String filePath);

    <T> List<T> read(String filePath, Class<T> entityClass) throws IOException;
}