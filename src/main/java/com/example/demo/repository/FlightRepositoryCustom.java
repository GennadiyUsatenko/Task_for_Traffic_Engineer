package com.example.demo.repository;

import com.example.demo.model.Flight;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
public interface FlightRepositoryCustom {
    Flight saveFlight(Flight flight, boolean isTemporaryMode);
    void printPatsFlights();
}
