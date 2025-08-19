
package com.singh.ecommerceapp.processor;

public interface EntityProcessor<T> {
    void process(T entity);
}