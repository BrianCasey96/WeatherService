package com.storm.eunice.rest.api.repository;

import com.storm.eunice.rest.api.model.Sensor;

import java.util.List;
import java.util.Optional;

public class CustomisedWeatherSensorRepositoryImpl implements CustomisedWeatherSensorRepository {

    private final WeatherSensorRepository weatherSensorRepository;

    public CustomisedWeatherSensorRepositoryImpl(final WeatherSensorRepository weatherSensorRepository){
        this.weatherSensorRepository = weatherSensorRepository;
    }

    public Boolean existsById(final String id) {
        return weatherSensorRepository.existsById(id);
    }

    public List<SensorId> findAllBy(){
        return weatherSensorRepository.findAllBy();
    }

    public Optional<Sensor> findSensor(String id) {
        return weatherSensorRepository.findById(id);
    }

    public Sensor saveSensor(final Sensor sensor) {
        return weatherSensorRepository.save(sensor);
    }
}
