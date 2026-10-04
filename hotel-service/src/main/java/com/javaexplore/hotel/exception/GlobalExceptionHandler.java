package com.javaexplore.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handlerResourceNotFoundException(ResourceNotFoundException re) {
        Map map = new HashMap();
        map.put("message", re.getMessage());
        map.put("success", false);
        map.put("statusCode", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
