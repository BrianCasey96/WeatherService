package com.storm.eunice.rest.api.service;

import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.model.SensorData;
import com.storm.eunice.rest.api.repository.CustomisedWeatherSensorRepository;
import com.storm.eunice.rest.api.repository.SensorId;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<SensorId> findAllBy(){
        return customisedWeatherSensorRepository.findAllBy();
    }

    public Sensor addSensorData(final Sensor sensor, final SensorData data){
       sensor.addSensorData(data);
       return customisedWeatherSensorRepository.saveSensor(sensor);
    }

    public List<SensorData> getValuesOverPeriod(final String sensorId, final LocalDate fromDate, final LocalDate toDate){
        return customisedWeatherSensorRepository.findSensor(sensorId).map(sensor -> sensor.getSensorData().stream()
                .filter(data -> data.getDate().toLocalDate().isAfter(fromDate) && data.getDate().toLocalDate().isBefore(toDate))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }
}
