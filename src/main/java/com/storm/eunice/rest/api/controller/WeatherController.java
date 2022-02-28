package com.storm.eunice.rest.api.controller;

import com.storm.eunice.rest.api.exception.SensorAlreadyExistsException;
import com.storm.eunice.rest.api.exception.SensorNotFoundException;

import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.model.SensorData;
import com.storm.eunice.rest.api.repository.SensorId;
import com.storm.eunice.rest.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/registerSensor/{id}")
    public ResponseEntity<?> registerSensor(@PathVariable final String id,
                                            @RequestBody final Optional<SensorMetadata> sensorMetadata) {
        if(weatherService.existsById(id)){
            throw new SensorAlreadyExistsException();
        }

        Sensor sensor = weatherService.registerSensor(id, sensorMetadata.orElse(null));
        return ResponseEntity.ok(sensor);
    }

    @GetMapping("/getSensor/{sensorId}")
    public ResponseEntity<?> getSensor(@PathVariable final String sensorId) {
        return weatherService.findSensor(sensorId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addSensorData/{id}")
    public ResponseEntity<?> addSensorData(@PathVariable final String id,
                                           @RequestBody final SensorData data) {
        Optional<Sensor> sensor = weatherService.findSensor(id);
        sensor.orElseThrow(SensorNotFoundException::new);

        if(data.getDate() == null){
            data.setDate(LocalDateTime.now());
        }

        Sensor sensorWithData =  weatherService.addSensorData(sensor.get(), data);
        return ResponseEntity.ok(sensorWithData);
    }

    @GetMapping("/getSensors")
    public ResponseEntity<?> getSensors(@RequestParam List<String> ids,
                                        @RequestParam Optional<String> from,
                                        @RequestParam Optional<String> to) {
        Map<String, SensorData> sensorValuesMap = new HashMap<>();
        LocalDate fromDate, toDate;
        log.info("Sensors Id's to process are {}", ids);

        if (ids.get(0).equals("all")) {
            //remove string 'all' from the List
            ids.clear();
            ids.addAll(weatherService.findAllBy().stream().map(SensorId::getId).collect(Collectors.toList()));
        }

        if (from.isPresent() && to.isPresent()) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            fromDate = LocalDate.parse(from.get(), formatter);
            toDate = LocalDate.parse(to.get(), formatter);

            if (fromDate == null || toDate == null || fromDate.isAfter(toDate)) {
                //TODO: add note, or DateException class
                return ResponseEntity.badRequest().build();
            }

            //TODO: check if timeframe is less than 30days
            log.info("From Date: {}. To Date: {}", fromDate, toDate);

            for (String sensorId : ids) {
                //this should really be a custom JPA Query for the specific dates instead of getting all values back for the sensor
                List<SensorData> values = weatherService.getValuesOverPeriod(sensorId, fromDate, toDate);
                Double tempAvg = values.stream().mapToDouble(SensorData::getTemperature).average().orElse(0);
                Double humidityAvg = values.stream().mapToDouble(SensorData::getHumidity).average().orElse(0);
                sensorValuesMap.put(sensorId, new SensorData(tempAvg, humidityAvg));
            }
        }else{
            //get most recent sensorData
            for (String sensorId : ids) {
                //Again this should really be a custom JPA Query instead of getting all values back for the sensor
              Optional<SensorData> data = weatherService.findSensor(sensorId).map(sensor -> sensor.getSensorData().get(sensor.getSensorData().size() - 1));
              data.ifPresent(sensorData -> sensorValuesMap.put(sensorId, sensorData));
            }
        }
        return ResponseEntity.ok(sensorValuesMap);

    }

}
