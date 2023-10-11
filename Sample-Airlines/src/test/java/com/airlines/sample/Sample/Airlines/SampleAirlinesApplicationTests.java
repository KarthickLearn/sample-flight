package com.airlines.sample.Sample.Airlines;

import com.sample.airlines.controller.FlightController;
import com.sample.airlines.dto.FlightResponse;
import com.sample.airlines.flightentity.Flight;
import com.sample.airlines.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FlightController.class)
@AutoConfigureMockMvc
class SampleAirlinesApplicationTests {

	@MockBean
	FlightService flightService;


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	Flight flight_1 = new Flight("A1", "CH","KL",30);
	Flight flight_2 = new Flight("B1", "TV","DL",50);


	@Test
	@DisplayName("verify save flight to database")
	 void saveFlightTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/saveFlight")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(flight_1)))
				.andExpect(status().isCreated());


	}

	@Test
	@DisplayName("verify getAll flights from database")
	 void getAllFlights() throws Exception {

		FlightResponse flightResponse_1 = new FlightResponse(null,"A1", "CH","KL",30 );
		FlightResponse flightResponse_2 = new FlightResponse(null,"B1", "TV","DL",50);
		Mockito.when(flightService.getAllFlight()).thenReturn(Arrays.asList(flightResponse_1,flightResponse_2));

		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/getAllFlightsDetails")


		).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].flightNumber", Matchers.is("A1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].origin", Matchers.is("CH")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].destination", Matchers.is("DL")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].duration", Matchers.is(50)));

		;

	}
}
