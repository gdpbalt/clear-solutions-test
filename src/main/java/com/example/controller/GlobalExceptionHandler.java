package com.example.controller;

import com.example.dto.response.ErrorMessageDto;
import com.example.exception.EntityNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final static HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final static HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> entityNotFoundHandler(RuntimeException exception) {
        ErrorMessageDto body = new ErrorMessageDto();
        body.setStatusCode(NOT_FOUND.value());
        body.setTimestamp(new Date());
        body.setMessages(List.of(exception.getMessage()));
        return new ResponseEntity<>(body, NOT_FOUND);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageDto> constraintParametersHandler(
            ConstraintViolationException exception) {
        ErrorMessageDto body = new ErrorMessageDto();
        body.setStatusCode(BAD_REQUEST.value());
        body.setTimestamp(new Date());
        List<String> messages;
        try {
            messages = exception.getConstraintViolations().stream().
                    map(c ->
                            String.format("%s value '%s' %s",
                                    c.getPropertyPath(), c.getInvalidValue(), c.getMessage()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            messages = Collections.singletonList(exception.getMessage());
        }
        body.setMessages(messages);
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorMessageDto body = new ErrorMessageDto();
        body.setTimestamp(new Date());
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(this::getErrorMessage)
                .toList();
        body.setMessages(errors);
        return new ResponseEntity<>(body, status);
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError) {
            String field = ((FieldError) error).getField();
            return field + ": " + error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }
}
