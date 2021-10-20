package com.eleks.academy.pharmagator.exception.handler;

import com.eleks.academy.pharmagator.exception.AbstractException;
import com.eleks.academy.pharmagator.exception.dto.GenericExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AbstractException.class})
    public ResponseEntity<GenericExceptionResponse> handleCourseNotFoundException(AbstractException exception) {

        GenericExceptionResponse dto = GenericExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .error(exception.getClass().getSimpleName())
                .build();

        log.info("Global Exception Handler invoke: {}", exception.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

}
