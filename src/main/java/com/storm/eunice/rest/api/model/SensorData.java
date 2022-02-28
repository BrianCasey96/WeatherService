package com.storm.eunice.rest.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class SensorData {

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime date;
    protected Double temperature;
    protected Double humidity;

    protected SensorData(){}

    public SensorData(final Double temperature, final Double humidity){
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    private void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    private void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }


}
