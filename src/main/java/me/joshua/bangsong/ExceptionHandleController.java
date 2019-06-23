package me.joshua.bangsong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionHandleController {
//    @ExceptionHandler(BangsongException.class)
    public ResponseEntity<String> handlerException(BangsongException e){
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
