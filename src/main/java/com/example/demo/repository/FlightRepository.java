package com.example.demo.repository;

import com.example.demo.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
public interface FlightRepository extends MongoRepository<Flight, Long> {
}
