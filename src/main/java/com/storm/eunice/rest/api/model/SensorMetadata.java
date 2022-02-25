package com.storm.eunice.rest.api.model;

import javax.persistence.*;

@Embeddable
public class SensorMetadata {

    private String country;
    private String city;

    public SensorMetadata(String country, String city){
        this.country = country;
        this.city = city;
    }

    protected SensorMetadata() {}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
