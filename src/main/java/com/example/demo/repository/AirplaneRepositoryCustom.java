package com.example.demo.repository;

import com.example.demo.model.Airplane;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
public interface AirplaneRepositoryCustom {
    Airplane saveAirplane(Airplane airplane, boolean isTemporaryMode);
}
