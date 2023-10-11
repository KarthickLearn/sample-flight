package com.sample.airlines.customexpection;

public class FlightNumberNotFoundException extends Exception{
    public FlightNumberNotFoundException(String s) {
        super(s);
    }
}
