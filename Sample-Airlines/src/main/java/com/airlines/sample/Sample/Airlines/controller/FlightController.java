package com.airlines.sample.Sample.Airlines.controller;

import com.airlines.sample.Sample.Airlines.service.FlightService;
import com.airlines.sample.Sample.Airlines.dto.FlightSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    private FlightService flightService;


    //SAVE FLIGHT DETAILS
    @PostMapping("/saveFlight")
    public ResponseEntity saveFlightDetails(@RequestBody FlightSaveRequest flightSaveRequest){

        return  ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(flightService.saveFlight(flightSaveRequest));
    }



    //GET THE FLIGHT INFO BY FLIGHT NUMBER

    @GetMapping("/getFlight")
    public ResponseEntity getFlightInfoByFlightNumber(@RequestParam String flightNumber) throws Throwable {

        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.getFlight(flightNumber));
    }


    // GET ALL FLIGHT BASED ORIGIN AND DESTINATION COMBINATION
    @GetMapping("/getflightsdetails")
    public ResponseEntity getFlightsdetailsByOriginandDestination(@RequestParam String origin, @RequestParam String destination){

        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.getFlightsDetails(origin,destination));
    }



    //DELETE FLIGHT
    @DeleteMapping("/deleteflight")
    public ResponseEntity deleteFlight(@RequestParam String flightNumber){

        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.deleteFlight(flightNumber));

    }


    // GET ALL FLIGHT
    @GetMapping("/getallflightsdetails")
    public ResponseEntity getAllFlightsDetails(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.getAllFlight());
    }


    //UPDATE FLIGHT
    @PutMapping("/updateflight")
    public ResponseEntity updateFlight(@RequestBody FlightSaveRequest flightSaveRequest){

        return ResponseEntity.status(HttpStatus.OK)
                .body(flightService.updateFlightRequest(flightSaveRequest));

    }






}
