package com.storm.eunice.rest.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storm.eunice.rest.api.exception.SensorAlreadyExistsException;
import com.storm.eunice.rest.api.exception.SensorNotFoundException;
import com.storm.eunice.rest.api.formatter.DateFormatter;
import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.model.SensorData;
import com.storm.eunice.rest.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;
    private final DateFormatter dateFormatter;

    public WeatherController(final WeatherService weatherService, final ObjectMapper objectMapper,
                             final DateFormatter dateFormatter) {
        this.weatherService = weatherService;
        this.objectMapper = objectMapper;
        this.dateFormatter = dateFormatter;
    }

    @PostMapping("/registerSensor/{id}")
    public ResponseEntity<?> registerSensor(@PathVariable final String id,
                                            @RequestBody final Optional<SensorMetadata> sensorMetadata) throws JsonProcessingException {

        if(weatherService.existsById(id)){
            throw new SensorAlreadyExistsException();
        }

        Sensor sensor = weatherService.registerSensor(id, sensorMetadata.orElse(null));
        return ResponseEntity.ok(objectMapper.writeValueAsString(sensor));
    }

    @GetMapping("/getSensor/{sensorId}")
    public ResponseEntity<?> getSensor(@PathVariable final String sensorId) throws JsonProcessingException {
        Optional<Sensor> sensor = weatherService.findSensor(sensorId);

        if(!sensor.isPresent()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(objectMapper.writeValueAsString(sensor.get()));
    }

    @PostMapping("/addSensorData/{id}")
    public ResponseEntity<?> addSensorData(@PathVariable final String id,
                                           @RequestBody final SensorData data) throws JsonProcessingException {
        Optional<Sensor> sensor = weatherService.findSensor(id);

        if(!sensor.isPresent()){
            throw new SensorNotFoundException();
        }


        //TODO: fix up date being passed in, and if null, generate automatically
        Sensor returnedSensor =  weatherService.addSensorData(sensor.get(), data);
        return ResponseEntity.ok(objectMapper.writeValueAsString(returnedSensor));
    }

    //*Not Fully implemented
    @GetMapping("/getSensors")
    public ResponseEntity<?> getSensors(@RequestParam List<String> ids,
                                        @RequestParam Optional<String> from,
                                        @RequestParam Optional<String> to) {

        Map<String, SensorData> averageValues;

        log.info("Sensors Id's to process are {}", ids);

        if (ids.get(0).equals("all")) {
            log.info("Need to get all Id's");
        }

        Date fromDate;
        Date toDate;

        if (from.isPresent() && to.isPresent()) {
            fromDate = dateFormatter.stringToDate(from.get());
            toDate = dateFormatter.stringToDate(to.get());

            if (fromDate == null || toDate == null) {
                return ResponseEntity.badRequest().build();
            }

            log.info("From Date: {}. To Date: {}", fromDate, toDate);

            for (String sensorId : ids) {
                Optional<Sensor> sensor = weatherService.findSensor(sensorId);
                if (sensor.isPresent()) {
                    //TODO: get list of sensorData for between the dates, add up temp and humidity and get average value
                    //add average values for this sensor to a map where key is the sensorId and average SensorData is the value
                    //return that map at the end to the user
                }
            }
        }
        return ResponseEntity.ok().build();
    }

}
