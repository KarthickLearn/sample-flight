package com.airlines.sample.Sample.Airlines;

import com.airlines.sample.Sample.Airlines.dto.FlightResponse;
import com.airlines.sample.Sample.Airlines.flightentity.Flight;
import com.airlines.sample.Sample.Airlines.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlightServiceTest {


    FlightService flightService = new FlightService();
    Flight flight_1 = new Flight("A1", "CH","KL",30);

    @Test
    public void mapToFlightResponseTest(){


      FlightResponse flightResponse = flightService.mapToFlightResponse(flight_1);

        Assertions.assertEquals(flight_1.getDuration(),flightResponse.getDuration());
    }

}
