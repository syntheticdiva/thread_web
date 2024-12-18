package com.smp.thread.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessageServiceException.class)
    public ResponseEntity<ErrorResponse> handleMessageServiceException(MessageServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCause("Ошибка при работе с сообщениями.");
        errorResponse.setResolution("Проверьте, что база данных доступна и корректно настроена. ");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}