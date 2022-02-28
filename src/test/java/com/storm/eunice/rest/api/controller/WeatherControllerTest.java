package com.storm.eunice.rest.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storm.eunice.rest.api.exception.SensorAlreadyExistsException;
import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WeatherControllerTest {

    private static final String SENSOR_ID = "123456";

   private WeatherController weatherController;

    @Mock
    private WeatherService mockWeatherService;

    private Sensor sensor;
    private Sensor sensorWithMetadata;
    private SensorMetadata sensorMetadata;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sensor = new Sensor(SENSOR_ID);
        sensorMetadata = new SensorMetadata("Ireland", "Galway");
        sensorWithMetadata = new Sensor(SENSOR_ID, sensorMetadata);

        weatherController = new WeatherController(mockWeatherService);
    }

    @Test
    public void verify_registerSensor_ReturnsNewSensorWithId() {
        when(mockWeatherService.existsById(SENSOR_ID)).thenReturn(false);
        when(mockWeatherService.registerSensor(SENSOR_ID, null)).thenReturn(sensor);

        ResponseEntity<?> responseEntity = weatherController.registerSensor(SENSOR_ID, Optional.empty());

        verify(mockWeatherService).registerSensor(SENSOR_ID, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void verify_registerSensor_ReturnsNewSensorWithIdAndMetadata() {
        when(mockWeatherService.existsById(SENSOR_ID)).thenReturn(false);
        when(mockWeatherService.registerSensor(SENSOR_ID, sensorMetadata)).thenReturn(sensorWithMetadata);

        ResponseEntity<?> responseEntity = weatherController.registerSensor(SENSOR_ID, Optional.of(sensorMetadata));

        verify(mockWeatherService).registerSensor(SENSOR_ID, sensorMetadata);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test()
    public void verify_RegisteredSensor_ReturnsAlreadyExistsException(){
        when(mockWeatherService.existsById(SENSOR_ID)).thenReturn(true);

        Assertions.assertThrows(SensorAlreadyExistsException.class, () -> {
            ResponseEntity<?> responseEntity = weatherController.registerSensor(SENSOR_ID, Optional.empty());
            verifyNoMoreInteractions(mockWeatherService);
            assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        });
    }
}
