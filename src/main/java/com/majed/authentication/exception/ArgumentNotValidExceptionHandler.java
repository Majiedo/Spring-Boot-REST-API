package com.majed.authentication.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class ArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd - HH.mm.ss").format(new java.util.Date());

        HashMap<String, Object> response = new HashMap<>();

        HashMap<String, String> errors = new HashMap<>();

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();

        fieldErrors.forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        response.put("path", request.getRequestURI());
        response.put("timestamp", timeStamp);
        response.put("errors", errors);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
