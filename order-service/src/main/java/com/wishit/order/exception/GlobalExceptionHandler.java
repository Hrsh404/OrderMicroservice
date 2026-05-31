package com.wishit.order.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wishit.order.dto.ResponseWrapper;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseWrapper<?> handleInvalidInput(HttpMessageNotReadableException ex) {
        return new ResponseWrapper<>("Err06", "Invalid input format (wrong data type)");
    }
}
