package com.example.demo.repository;

import com.example.demo.model.Airplane;
import org.springframework.beans.factory.annotation.Autowired;

public class AirplaneRepositoryImpl implements AirplaneRepositoryCustom {
    @Autowired
    AirplaneRepository airplaneRepository;
    @Autowired
    AirplaneCharacteristicsRepository airplaneCharacteristicsRepository;
    @Autowired
    FlightRepositoryImpl flightRepository;
    @Autowired
    TemporaryPointRepository temporaryPointRepository;

    @Override
    public Airplane saveAirplane(Airplane airplane, boolean isTemporaryMode) {
        if(!isTemporaryMode){
            System.out.println(airplane.toString());
            airplaneCharacteristicsRepository.save(airplane.getAirplaneCharacteristics());
        }
        System.out.println(airplane.getPosition().toString());
        temporaryPointRepository.save(airplane.getPosition());
        flightRepository.saveFlight(airplane.getFlight(), isTemporaryMode);

        return airplaneRepository.save(airplane);
    }
}
