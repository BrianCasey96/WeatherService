package com.storm.eunice.rest.api.service;

import com.storm.eunice.rest.api.model.Sensor;
import com.storm.eunice.rest.api.model.SensorMetadata;
import com.storm.eunice.rest.api.repository.CustomisedWeatherSensorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {

    private static final String SENSOR_ID = "123456";
    private static final String COUNTRY = "Ireland";
    private static final String CITY = "Galway";

    @Mock
    private CustomisedWeatherSensorRepository mockCustomisedWeatherSensorRepository;

    private WeatherService weatherService;
    private Sensor sensor;
    private Sensor sensorWithMetadata;
    private SensorMetadata sensorMetadata;
    private ArgumentCaptor<Sensor> internalCaptor;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        internalCaptor = ArgumentCaptor.forClass(Sensor.class);
        sensor = new Sensor(SENSOR_ID);
        sensorMetadata = new SensorMetadata(COUNTRY, CITY);
        sensorWithMetadata = new Sensor(SENSOR_ID, sensorMetadata);

        weatherService = new WeatherService(mockCustomisedWeatherSensorRepository);
    }

    @Test
    public void verify_registerSensorWithoutMetadata_ReturnsSavedSensor() {
        when(mockCustomisedWeatherSensorRepository.existsById(any())).thenReturn(false);
        when(mockCustomisedWeatherSensorRepository.saveSensor(isA(Sensor.class))).thenReturn(sensor);

        Sensor returnedSensor = weatherService.registerSensor(SENSOR_ID, null);

        verify(mockCustomisedWeatherSensorRepository).saveSensor(internalCaptor.capture());
        assertEquals(internalCaptor.getValue().getId(), returnedSensor.getId());
    }

    @Test
    public void verify_registerSensorWithMetadata_ReturnsSavedSensor() {
        when(mockCustomisedWeatherSensorRepository.existsById(any())).thenReturn(false);
        when(mockCustomisedWeatherSensorRepository.saveSensor(isA(Sensor.class))).thenReturn(sensorWithMetadata);


        Sensor returnedSensor = weatherService.registerSensor(SENSOR_ID, sensorMetadata);

        verify(mockCustomisedWeatherSensorRepository).saveSensor(internalCaptor.capture());
        assertEquals(internalCaptor.getValue().getId(), returnedSensor.getId());
        assertEquals(internalCaptor.getValue().getSensorMetadata().getCity(), returnedSensor.getSensorMetadata().getCity());
        assertEquals(internalCaptor.getValue().getSensorMetadata().getCountry(), returnedSensor.getSensorMetadata().getCountry());

    }

    @Test
    public void verify_findSensor_ReturnsOptionalSensor() {
        when(mockCustomisedWeatherSensorRepository.findSensor(SENSOR_ID)).thenReturn(Optional.of(sensor));

        Optional<Sensor> returnedSensor = weatherService.findSensor(SENSOR_ID);

        verify(mockCustomisedWeatherSensorRepository).findSensor(SENSOR_ID);
        assertTrue(returnedSensor.isPresent());
    }

    @Test
    public void verify_findSensor_ReturnsOptionalEmpty() {
        when(mockCustomisedWeatherSensorRepository.findSensor(SENSOR_ID)).thenReturn(Optional.empty());

        Optional<Sensor> returnedSensor = weatherService.findSensor(SENSOR_ID);

        verify(mockCustomisedWeatherSensorRepository).findSensor(SENSOR_ID);
        assertFalse(returnedSensor.isPresent());
    }
}
