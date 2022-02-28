package com.storm.eunice.rest.api.repository;

import com.storm.eunice.rest.api.model.Sensor;

import java.util.List;
import java.util.Optional;

public interface CustomisedWeatherSensorRepository {

    Boolean existsById(String id);
    Optional<Sensor> findSensor(final String id);
    List<SensorId> findAllBy();
    Sensor saveSensor(Sensor sensor);


}
