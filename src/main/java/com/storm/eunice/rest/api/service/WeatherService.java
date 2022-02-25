package com.storm.eunice.rest.api.service;

import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.model.SensorData;
import com.storm.eunice.rest.api.repository.CustomisedWeatherSensorRepository;

import java.util.Optional;

public class WeatherService {

    private final CustomisedWeatherSensorRepository customisedWeatherSensorRepository;

    public WeatherService(final CustomisedWeatherSensorRepository customisedWeatherSensorRepository){
        this.customisedWeatherSensorRepository = customisedWeatherSensorRepository;
    }

    public Boolean existsById(final String id){
        return customisedWeatherSensorRepository.existsById(id);
    }

    public Sensor registerSensor(final String id, final SensorMetadata sensorMetadata){
        Sensor sensor = (sensorMetadata != null) ? new Sensor(id, sensorMetadata) : new Sensor(id);
        return customisedWeatherSensorRepository.saveSensor(sensor);
    }

    public Optional<Sensor> findSensor(final String id){
        return customisedWeatherSensorRepository.findSensor(id);
    }

    public Sensor addSensorData(final Sensor sensor, final SensorData data){
       sensor.addSensorData(data);
       return customisedWeatherSensorRepository.saveSensor(sensor);
    }

    public void getValuesForSensor(final String sensorId, final Optional<String> from, final Optional<String> to){
        //Not implemented
        if(!from.isPresent() && !to.isPresent()) {
            customisedWeatherSensorRepository.findSensor(sensorId);
        }
    }
}
