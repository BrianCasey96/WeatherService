package com.storm.eunice.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SensorAlreadyExistsException extends ResponseStatusException {
    public SensorAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Sensor already exists");
    }
}
