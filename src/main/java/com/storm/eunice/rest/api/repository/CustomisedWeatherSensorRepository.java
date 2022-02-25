package com.storm.eunice.rest.api.repository;

import com.storm.eunice.rest.api.model.Sensor;

import java.util.Date;
import java.util.Optional;

public interface CustomisedWeatherSensorRepository {

    Boolean existsById(String id);
    Optional<Sensor> findSensor(final String id);
    Sensor saveSensor(Sensor sensor);
    Boolean updateSensorValues();
    Object getValues(final String sensorId, final Date from, final Date to);


}
