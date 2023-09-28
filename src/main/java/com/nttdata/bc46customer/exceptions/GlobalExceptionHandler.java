package com.nttdata.bc46customer.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("code", "CUSTOMER_NOT_FOUND");
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CustomerTypeBadRequestException.class)
  public ResponseEntity<Object> handleCustomerTypeBadRequestException(CustomerTypeBadRequestException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("code", "CUSTOMER_TYPE_BAD_REQUEST");
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}