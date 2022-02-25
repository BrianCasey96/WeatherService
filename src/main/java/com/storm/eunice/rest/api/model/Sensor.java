package com.storm.eunice.rest.api.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sensor {

    @Id
    private String id;

    @Embedded
    private SensorMetadata sensorMetadata;

    @ElementCollection
    private List<SensorData> sensorDataList;

    protected Sensor() {
    }

    public Sensor(final String id) {
        this.id = id;
        this.sensorDataList = new ArrayList<>();
    }

    public Sensor(final String id, final SensorMetadata sensorMetadata) {
        this.id = id;
        this.sensorMetadata = sensorMetadata;
        this.sensorDataList = new ArrayList<>();
    }

    public String getId () {
        return id;
    }

    public SensorMetadata getSensorMetadata() {
        return sensorMetadata;
    }

    public List<SensorData> getSensorData() {
        return this.sensorDataList;
    }

    public void addSensorData(SensorData data){
        this.sensorDataList.add(data);
    }


}
