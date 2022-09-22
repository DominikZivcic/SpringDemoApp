package com.demo.demoApp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = UserExceptionReq.class)
    public ResponseEntity<Object> handleUserException(UserExceptionReq e){
        UserException request = new UserException(
                e.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountExceptionReq.class)
    public ResponseEntity<Object> handleAccountException (AccountExceptionReq e){
        AccountException request = new AccountException(
                e.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(request, HttpStatus.BAD_REQUEST);
    }

}
