package com.storm.eunice.rest.api.repository;

import com.storm.eunice.rest.api.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeatherSensorRepository extends JpaRepository<Sensor, String> {
}

