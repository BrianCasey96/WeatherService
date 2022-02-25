package com.storm.eunice.rest.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storm.eunice.rest.api.formatter.DateFormatter;
import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherControllerTest {

    private static final String SENSOR_ID = "123456";

   private WeatherController weatherController;

    @Mock
    private WeatherService mockWeatherService;

    private ObjectMapper objectMapper;

    @Mock
    private DateFormatter mockDateFormater;

    private ArgumentCaptor<Sensor> internalCaptor;
    private Sensor sensor;
    private SensorMetadata sensorMetadata;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        internalCaptor = ArgumentCaptor.forClass(Sensor.class);
        sensor = new Sensor(SENSOR_ID);
        sensorMetadata = new SensorMetadata("Ireland", "Galway");

        weatherController = new WeatherController(mockWeatherService, objectMapper, mockDateFormater);
    }

    @Test
    public void verify_registerSensor_ReturnsNewSensorWithId() throws JsonProcessingException {
        when(mockWeatherService.registerSensor(SENSOR_ID, null)).thenReturn(sensor);

        ResponseEntity<?> responseEntity = weatherController.registerSensor(SENSOR_ID, Optional.empty());

        verify(mockWeatherService).registerSensor(SENSOR_ID, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(sensor), responseEntity.getBody());
    }

    @Test
    public void verify_registerSensor_ReturnsNewSensorWithIdAndMetadata() throws JsonProcessingException {
        when(mockWeatherService.registerSensor(SENSOR_ID, sensorMetadata)).thenReturn(sensor);

        ResponseEntity<?> responseEntity = weatherController.registerSensor(SENSOR_ID, Optional.of(sensorMetadata));

        verify(mockWeatherService).registerSensor(SENSOR_ID, sensorMetadata);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(sensor), responseEntity.getBody());
    }
}
