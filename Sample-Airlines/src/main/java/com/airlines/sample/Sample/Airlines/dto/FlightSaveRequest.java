package com.airlines.sample.Sample.Airlines.dto;

public class FlightSaveRequest {

    private String flightNumber;
    private String origin;
    private String destination;
    private Integer duration;




    public FlightSaveRequest(String flightNumber, String origin, String destination, Integer duration) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.duration = duration;
    }
    public FlightSaveRequest() {

    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
