package com.singh.ecommerceapp.product.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(value = Include.NON_NULL)
public class ErrorResponse<T> {

    private final boolean success;
    private final String message;
    private T data;

    public ErrorResponse(boolean success, String message, T data) {
        super();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ErrorResponse(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

}
