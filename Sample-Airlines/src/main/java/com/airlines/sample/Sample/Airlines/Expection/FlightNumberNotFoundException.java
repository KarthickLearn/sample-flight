package com.airlines.sample.Sample.Airlines.Expection;

public class FlightNumberNotFoundException extends RuntimeException{
    public FlightNumberNotFoundException(String s) {
        super(s);
    }
}
