package com.storm.eunice.rest.api.repository;

import com.storm.eunice.rest.api.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherSensorRepository extends JpaRepository<Sensor, String> {

    //https://github.com/spring-projects/spring-data-jpa/issues/2408
    //Upgraded to springboot 2.6.4 due to bug when query result is projection-based interface
    List<SensorId> findAllBy();
}

