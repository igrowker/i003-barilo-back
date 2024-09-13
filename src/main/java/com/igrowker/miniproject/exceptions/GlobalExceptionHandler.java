package com.igrowker.miniproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handlerConflictException(ConflictException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .error("CONFLICT")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            BadRequestException.class,
            org.springframework.dao.DuplicateKeyException.class,
            org.springframework.web.HttpRequestMethodNotSupportedException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.web.bind.MissingRequestHeaderException.class,
            org.springframework.web.bind.MissingServletRequestParameterException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class
    })
    public ResponseEntity<ErrorResponse> handlerBadRequestException(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("BAD_REQUEST")
                .message(exception.getMessage()) // Captura el mensaje de la excepción genérica
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handlerForbiddenException(ForbiddenException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .error("FORBIDDEN")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalException(Exception exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("INTERNAL_SERVER_ERROR")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
