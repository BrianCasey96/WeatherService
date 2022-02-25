package com.storm.eunice.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SensorNotFoundException extends ResponseStatusException {
    public SensorNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Sensor not found");
    }
}
