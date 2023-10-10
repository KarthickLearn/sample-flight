package com.airlines.sample.Sample.Airlines;

import com.airlines.sample.Sample.Airlines.Expection.FlightNumberNotFoundException;
import com.airlines.sample.Sample.Airlines.dto.FlightResponse;
import com.airlines.sample.Sample.Airlines.dto.FlightSaveRequest;
import com.airlines.sample.Sample.Airlines.flightentity.Flight;
import com.airlines.sample.Sample.Airlines.flightrepo.FlightRepo;
import com.airlines.sample.Sample.Airlines.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepo flightRepo;


    FlightService flightService;


    @BeforeEach
    public void setup() {
        flightService = new FlightService(flightRepo);
    }





    @Test
    @DisplayName("save flight detail to database")
    public void saveFlightTest(){

        FlightSaveRequest flightSaveRequest =
                new FlightSaveRequest("A1","TV","CH",30);

        flightService.saveFlight(flightSaveRequest);

        Mockito.verify(flightRepo, Mockito.times(1))
                .save(ArgumentMatchers.any(Flight.class));


    }



    @Test
    @DisplayName("Get flight information based on flight-number")
    public void getFlight() throws Throwable {
        Flight flight = new Flight("A1","TV","CH",30);
        Mockito.when(flightRepo.findByflightNumber("A1"))
                .thenReturn(Optional.of(flight));
        FlightResponse expectResponse = new FlightResponse(null,"A1","TV","CH",30);
        FlightResponse actualResponse = flightService.getFlight("A1");
        Assertions.assertEquals(expectResponse.getOrigin(),actualResponse.getOrigin());

    }
    @Test
    @DisplayName(" flight information not found based on flight-number")
    public void notFoundFlightInfo() throws Throwable {
       /* Flight flight = new Flight("A1","TV","CH",30);
        Mockito.when(flightRepo.findByflightNumber("A1").isPresent())
                .thenReturn(null);*/

        FlightNumberNotFoundException thrown = Assertions.assertThrows(FlightNumberNotFoundException.class, () -> {
            flightRepo.findByflightNumber("A1");
        }, "The Given Flight Number not present");

        Assertions.assertEquals("he Given Flight Number not present", thrown.getMessage());
    }








    @Test
    @DisplayName("To get all fight details")
    public void getAllFlightTest(){
        Flight flight_1 = new Flight("A1","TV","CH",30);
        Flight flight_2 = new Flight("B1","TV","CH",10);

        Mockito.when(flightRepo.findAll()).thenReturn(Arrays.asList(flight_1,flight_2));
        List<FlightResponse> flightList = flightService.getAllFlight();

        Assertions.assertEquals(flight_2.getDuration(),flightList.get(0).getDuration());

    }


    @Test
    @DisplayName("To get list of flight based on origin and destination")
    public void getFlightBasedOriginAndDestination(){
        Flight flight_1 = new Flight("A1","TV","CH",30);
        Flight flight_2 = new Flight("B1","BN","DL",10);
        Flight flight_3 = new Flight("C1","TV","CH",20);

        Mockito.when(flightRepo.findAllByoriginAndDestination("TV","CH"))
                .thenReturn(Arrays.asList(flight_1,flight_3));


        List<FlightResponse> actualResponse = flightService.getFlightsDetails("TV","CH");

        Assertions.assertEquals(flight_3.getDuration(), actualResponse.get(0).getDuration());

    }






    @Test
    public void mapToFlightResponseTest(){

        Flight flight_1 = new Flight("A1","TV","CH",30);
      FlightResponse flightResponse = flightService.mapToFlightResponse(flight_1);

        Assertions.assertEquals(flight_1.getDuration(),flightResponse.getDuration());
    }

}
