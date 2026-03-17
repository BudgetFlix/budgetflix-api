package hu.budgetflix.api.controller;

import hu.budgetflix.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException (NotFoundException ex){
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
