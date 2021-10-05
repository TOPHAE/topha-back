package com.make.projects.exception;

import com.make.projects.exception.valiationexception.ValidErrorResponse;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ValidErrorResponse response = ValidErrorResponse.of(httpStatus.value(),LocalDateTime.now(), e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ValidErrorResponse> handleBindException(BindException e) {
        log.error("handleBindExceptions={}", e.getFieldErrors());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidErrorResponse response = ValidErrorResponse.of(httpStatus.value(), LocalDateTime.now(), e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> processError(Exception e) {
        CustomErrorResponse response = new CustomErrorResponse();
        HttpStatus status = null;

        if (e instanceof DuplicateKeyException) {
            status = HttpStatus.BAD_REQUEST;
            response.setStatusCode(status.value());
            response.setMessage(e.getMessage());
        } else if (e instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            response.setStatusCode(status.value());
            response.setMessage(e.getMessage());
        } else if (e instanceof IllegalArgumentException){
            status = HttpStatus.BAD_REQUEST;
            response.setStatusCode(status.value());
            response.setMessage(e.getMessage());
        } else if(e instanceof NullPointerException){
            status = HttpStatus.NOT_FOUND;
            response.setStatusCode(status.value());
            response.setMessage(e.getMessage());
        }else{
            status = HttpStatus.BAD_REQUEST;
            response.setStatusCode(status.value());
            response.setMessage(e.getMessage());
        }
        response.setCreateErrorTime(LocalDateTime.now());
        return new ResponseEntity<>(response, status);
    }



}
