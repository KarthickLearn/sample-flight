package com.airlines.sample.Sample.Airlines;

import com.sample.airlines.customexpection.FlightNumberNotFoundException;
import com.sample.airlines.dto.FlightResponse;
import com.sample.airlines.dto.FlightSaveRequest;
import com.sample.airlines.flightentity.Flight;
import com.sample.airlines.flightrepo.FlightRepo;
import com.sample.airlines.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
 class FlightServiceTest {

    FlightService flightService;
    @Mock
    private FlightRepo flightRepo;

    @BeforeEach
     void setup() {
        flightService = new FlightService(flightRepo);
    }


    @Test
    @DisplayName("save flight detail to database")
     void saveFlightTest(){

        FlightSaveRequest flightSaveRequest =
                new FlightSaveRequest("A1","TV","CH",30);

        flightService.saveFlight(flightSaveRequest);

        Mockito.verify(flightRepo, Mockito.times(1))
                .save(ArgumentMatchers.any(Flight.class));


    }


    @Test
    @DisplayName("Get flight information based on flight-number")
     void getFlight() throws Throwable {
        Flight flight = new Flight("A1","TV","CH",30);
        Mockito.when(flightRepo.findByflightNumber("A1"))
                .thenReturn(Optional.of(flight));
        FlightResponse expectResponse = new FlightResponse(null,"A1","TV","CH",30);
        FlightResponse actualResponse = flightService.getFlight("A1");
        Assertions.assertEquals(expectResponse.getOrigin(),actualResponse.getOrigin());

    }
    @Test
    @DisplayName(" flight information not found based on flight-number")
     void notFoundFlightInfo() throws Throwable {


        FlightNumberNotFoundException thrown = Assertions.assertThrows(FlightNumberNotFoundException.class, () -> {
            flightRepo.findByflightNumber("A1");
        }, "The Given Flight Number not present");

        Assertions.assertEquals("The Given Flight Number not present", thrown.getMessage());
    }








    @Test
    @DisplayName("To get all fight details")
    void getAllFlightTest(){
        Flight flight_1 = new Flight("A1","TV","CH",30);
        Flight flight_2 = new Flight("B1","TV","CH",10);

        Mockito.when(flightRepo.findAll()).thenReturn(Arrays.asList(flight_1,flight_2));
        List<FlightResponse> flightList = flightService.getAllFlight();

        Assertions.assertEquals(flight_2.getDuration(),flightList.get(0).getDuration());

    }


    @Test
    @DisplayName("To get list of flight based on origin and destination")
    void getFlightBasedOriginAndDestination(){
        Flight flight_1 = new Flight("A1","TV","CH",30);
        Flight flight_2 = new Flight("B1","BN","DL",10);
        Flight flight_3 = new Flight("C1","TV","CH",20);

        Mockito.when(flightRepo.findAllByoriginAndDestination("TV","CH"))
                .thenReturn(Arrays.asList(flight_1,flight_3));


        List<FlightResponse> actualResponse = flightService.getFlightsDetails("TV","CH");

        Assertions.assertEquals(flight_3.getDuration(), actualResponse.get(0).getDuration());

    }






    @Test
     void mapToFlightResponseTest(){

        Flight flight_1 = new Flight("A1","TV","CH",30);
      FlightResponse flightResponse = flightService.mapToFlightResponse(flight_1);

        Assertions.assertEquals(flight_1.getDuration(),flightResponse.getDuration());
    }

}
