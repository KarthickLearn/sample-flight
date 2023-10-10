package com.airlines.sample.Sample.Airlines.configuration;


import com.airlines.sample.Sample.Airlines.Expection.FlightNumberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FlightException {



    @ExceptionHandler(FlightNumberNotFoundException.class)
    public ResponseEntity handleConflict(FlightNumberNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());

    }

}
