package com.storm.eunice.rest.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Embeddable
public class SensorData {

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    protected Date date;
    protected Double temperature;
    protected Double humidity;

    protected SensorData(){}

    public SensorData(Date date, Double temperature, Double humidity){
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public SensorData(Double temperature, Double humidity){
        this.date = new Date();
        this.temperature = temperature;
        this.humidity = humidity;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    private void setDate() {
        this.date = new Date();
    }

    private void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    private void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }


}
