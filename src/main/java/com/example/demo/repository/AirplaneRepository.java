package com.example.demo.repository;

import com.example.demo.model.Airplane;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Gennadiy Usatenko
 * @version 12.09.2018
 */
public interface AirplaneRepository extends MongoRepository<Airplane, Long> {
}
