package com.singh.ecommerceapp.product.exception.handler;

import com.singh.ecommerceapp.product.exception.DuplicateRecordException;
import com.singh.ecommerceapp.product.exception.InvalidInputException;
import com.singh.ecommerceapp.product.exception.ProductServiceCustomException;
import com.singh.ecommerceapp.product.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse<String>> handleException(InvalidInputException e) {
		log.warn("InvalidInputException: {}", e.getMessage());
		return new ResponseEntity<>(new ErrorResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse<String>> handleException(DuplicateRecordException e) {
		log.warn("DuplicateRecordException: {}", e.getMessage());
		return new ResponseEntity<>(new ErrorResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse<String>> handleException(Exception e) {
		return new ResponseEntity<>(new ErrorResponse<>(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();

		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList();

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductServiceCustomException.class)
	public ResponseEntity<com.singh.ecommerceapp.product.exception.ErrorResponse> handleProductServiceException(ProductServiceCustomException exception) {
		return new ResponseEntity<>(com.singh.ecommerceapp.product.exception.ErrorResponse.builder()
				.errorMessage(exception.getMessage())
				.errorCode(exception.getErrorCode())
				.build(), HttpStatus.NOT_FOUND);
	}
	//@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
