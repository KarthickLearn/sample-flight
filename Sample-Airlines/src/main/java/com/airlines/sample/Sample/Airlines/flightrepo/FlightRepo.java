package com.airlines.sample.Sample.Airlines.flightrepo;

import com.airlines.sample.Sample.Airlines.flightentity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepo extends JpaRepository<Flight,Long> {

    Optional<Flight> findByflightNumber(String flightNumber);

    List<Flight> findAllByoriginAndDestination(String origin, String destination);
}
