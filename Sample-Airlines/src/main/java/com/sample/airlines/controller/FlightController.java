package com.sample.airlines.controller;
import com.sample.airlines.customexpection.FlightNumberNotFoundException;
import com.sample.airlines.dto.FlightResponse;
import com.sample.airlines.service.FlightService;
import com.sample.airlines.dto.FlightSaveRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    private FlightService flightService;


    //SAVE FLIGHT DETAILS
    @PostMapping("/saveFlight")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveFlightDetails(@RequestBody @Valid FlightSaveRequest flightSaveRequest){

        return  flightService.saveFlight(flightSaveRequest);
    }



    //GET THE FLIGHT INFO BY FLIGHT NUMBER

    @GetMapping("/getFlight")
    @ResponseStatus(HttpStatus.OK)
    public FlightResponse getFlightInfoByFlightNumber(@RequestParam String flightNumber) throws FlightNumberNotFoundException {

        return flightService.getFlight(flightNumber);
    }


    // GET ALL FLIGHT BASED ORIGIN AND DESTINATION COMBINATION
    @GetMapping("/getFlightsDetails")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getFlightsDetailsByOriginAndDestination(@RequestParam String origin, @RequestParam String destination){

        return flightService.getFlightsDetails(origin,destination);
    }



    //DELETE FLIGHT
    @DeleteMapping("/deleteFlight")
    @ResponseStatus(HttpStatus.OK)
    public String deleteFlight(@RequestParam String flightNumber) throws FlightNumberNotFoundException {

        return flightService.
                deleteFlight(flightNumber);

    }


    // GET ALL FLIGHT
    @GetMapping("/getAllFlightsDetails")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllFlightsDetails(){

        return flightService.getAllFlight();
    }


    //UPDATE FLIGHT
    @PutMapping("/updateFlight")
    @ResponseStatus(HttpStatus.OK)
    public FlightResponse updateFlight(@RequestBody FlightSaveRequest flightSaveRequest){

        return flightService.updateFlightRequest(flightSaveRequest);

    }






}
