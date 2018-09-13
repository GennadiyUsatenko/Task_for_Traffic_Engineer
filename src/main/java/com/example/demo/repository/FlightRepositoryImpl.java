package com.example.demo.repository;

import com.example.demo.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
public class FlightRepositoryImpl implements FlightRepositoryCustom {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    WayPointRepository wayPointRepository;
    @Autowired
    TemporaryPointRepository temporaryPointRepository;

    @Override
    public Flight saveFlight(Flight flight, boolean isTemporaryMode) {
        if(!isTemporaryMode){
            wayPointRepository.saveAll(flight.getWayPoints());
        }
        return flightRepository.save(flight);
    }

    @Override
    public void printPatsFlights() {
        System.out.println("\n-------------------------------------------------------------------------------------\n");
        System.out.println("Past flights:");
        flightRepository.findAll().forEach(
                f -> System.out.println(f.getFlightInfo())
        );
    }
}
