package com.example.demo.repository;

import com.example.demo.model.TemporaryPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
public interface TemporaryPointRepository extends MongoRepository<TemporaryPoint, Long> {
}
