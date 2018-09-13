package com.example.demo.repository;

import com.example.demo.model.AirplaneCharacteristics;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
public interface AirplaneCharacteristicsRepository extends MongoRepository<AirplaneCharacteristics, Long> {
}
