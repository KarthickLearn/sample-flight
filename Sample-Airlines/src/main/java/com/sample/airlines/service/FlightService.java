package com.sample.airlines.service;

import com.sample.airlines.customexpection.FlightNumberNotFoundException;
import com.sample.airlines.flightentity.Flight;
import com.sample.airlines.flightrepo.FlightRepo;
import com.sample.airlines.dto.FlightResponse;
import com.sample.airlines.dto.FlightSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FlightService {


    @Autowired
    private FlightRepo flightRepo;

    public FlightService(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }


    //Save Flight info to database
    public String saveFlight(FlightSaveRequest flightSaveRequest) {

        Flight flight = new Flight(flightSaveRequest.getFlightNumber(),
                flightSaveRequest.getOrigin(),
                flightSaveRequest.getDestination(),
                flightSaveRequest.getDuration());

        flightRepo.save(flight);

        return "The Given Fight details successfully saved";

    }

    public FlightResponse getFlight(String flightNumber) throws FlightNumberNotFoundException {


        try {

            flightRepo.findByflightNumber(flightNumber);
        }catch (NoSuchElementException e){

            throw new FlightNumberNotFoundException("The Given Flight Number not present");
        }


        return mapToFlightResponse(flightRepo.findByflightNumber(flightNumber).get());

    }

    public FlightResponse mapToFlightResponse(Flight flight) {


        FlightResponse flightResponse = new FlightResponse();

        flightResponse.setId(flight.getId());
        flightResponse.setFlightNumber(flight.getFlightNumber());
        flightResponse.setOrigin(flight.getOrigin());
        flightResponse.setDestination(flight.getDestination());
        flightResponse.setDuration(flight.getDuration());

        return flightResponse;
    }





    public List<FlightResponse> getFlightsDetails(String origin, String destination) {

        List<Flight> flights = flightRepo.findAllByoriginAndDestination(origin,destination);

         return flights.stream()
                 .sorted((f1,f2)-> f1.getDuration() - f2.getDuration() ).map(this::mapToFlightResponse).toList();


    }

    public String deleteFlight(String flightNumber) throws FlightNumberNotFoundException {

        flightRepo.findByflightNumber(flightNumber).
                orElseThrow(() -> new FlightNumberNotFoundException("The Given Flight Number not present"));

        Optional<Flight>  flight = flightRepo.findByflightNumber(flightNumber);

        flightRepo.delete(flight.get());

        return "The Give Flight remove successfully";

    }


    public List<FlightResponse> getAllFlight() {

        List<Flight> flights = flightRepo.findAll();

        return flights.stream()
                .sorted((f1,f2)-> f1.getDuration() - f2.getDuration() ).map(this::mapToFlightResponse).toList();


    }

    public FlightResponse updateFlightRequest(FlightSaveRequest flightSaveRequest) {


       boolean result = flightRepo.findByflightNumber(flightSaveRequest.getFlightNumber()).isPresent();

       if (result){

          Flight saveFlight = flightRepo.findByflightNumber(flightSaveRequest.getFlightNumber()).get();
           saveFlight.setFlightNumber(flightSaveRequest.getFlightNumber());
           saveFlight.setOrigin(flightSaveRequest.getOrigin());
           saveFlight.setDestination(flightSaveRequest.getDestination());
           saveFlight.setDuration(flightSaveRequest.getDuration());

           flightRepo.save(saveFlight);


       }else {
           Flight flight = new Flight(flightSaveRequest.getFlightNumber(),
                   flightSaveRequest.getOrigin(),
                   flightSaveRequest.getDestination(),
                   flightSaveRequest.getDuration());

           flightRepo.save(flight);
       }

        Flight updatedFlight = flightRepo.findByflightNumber(flightSaveRequest.getFlightNumber()).get();

        return mapToFlightResponse(updatedFlight);
    }
}
