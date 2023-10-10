package com.airlines.sample.Sample.Airlines.service;

import com.airlines.sample.Sample.Airlines.Expection.FlightNumberNotFoundException;
import com.airlines.sample.Sample.Airlines.flightentity.Flight;
import com.airlines.sample.Sample.Airlines.flightrepo.FlightRepo;
import com.airlines.sample.Sample.Airlines.dto.FlightResponse;
import com.airlines.sample.Sample.Airlines.dto.FlightSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {


    @Autowired
    private FlightRepo flightRepo;

    
    
    //Save Flight info to database
    public String saveFlight(FlightSaveRequest flightSaveRequest) {

        Flight flight = new Flight(flightSaveRequest.getFlightNumber(),
                flightSaveRequest.getOrigin(),
                flightSaveRequest.getDestination(),
                flightSaveRequest.getDuration());

        flightRepo.save(flight);

        return "The Given Fight details successfully saved";

    }

    public FlightResponse getFlight(String flightNumber) throws Throwable {


          flightRepo.findByflightNumber(flightNumber).
          orElseThrow(() -> new FlightNumberNotFoundException("The Given Flight Number not present"));

        FlightResponse flightResponse= mapToFlightResponse(flightRepo.findByflightNumber(flightNumber).get());


        return flightResponse;
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

         List<FlightResponse> sorterFlightResponse = flights.stream()
                 .sorted((f1,f2)-> f1.getDuration() - f2.getDuration() ).map(this::mapToFlightResponse).toList();

        return sorterFlightResponse;
    }

    public String deleteFlight(String flightNumber) {

        flightRepo.findByflightNumber(flightNumber).
                orElseThrow(() -> new FlightNumberNotFoundException("The Given Flight Number not present"));

        Flight flight = flightRepo.findByflightNumber(flightNumber).get();

        flightRepo.delete(flight);

        return "The Give Flight remove successfully";

    }


    public List<FlightResponse> getAllFlight() {

        List<Flight> flights = flightRepo.findAll();

        List<FlightResponse> sorterFlightResponse = flights.stream()
                .sorted((f1,f2)-> f1.getDuration() - f2.getDuration() ).map(this::mapToFlightResponse).toList();

        return sorterFlightResponse;
    }

    public FlightResponse updateFlightRequest(FlightSaveRequest flightSaveRequest) {


       Boolean result = flightRepo.findByflightNumber(flightSaveRequest.getFlightNumber()).isPresent();

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
