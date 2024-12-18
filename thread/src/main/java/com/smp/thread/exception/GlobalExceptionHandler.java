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
    @ExceptionHandler(InsertMessageException.class)
    public ResponseEntity<ErrorResponse> handleInsertMessageException(InsertMessageException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCause("Ошибка при вставке сообщений.");
        errorResponse.setResolution("Убедитесь, что данные корректны и база данных доступна.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(CountMessageException.class)
    public ResponseEntity<ErrorResponse> handleCountMessageException(CountMessageException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCause("Ошибка при подсчете сообщений.");
        errorResponse.setResolution("Проверьте логику подсчета и доступность базы данных.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}