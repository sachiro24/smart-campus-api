package com.smartcampus.resource;

import com.smartcampus.model.*;
import com.smartcampus.service.DataStore;
import com.smartcampus.exception.SensorUnavailableException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.UUID;
import java.util.*;

public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {
        List<SensorReading> readings = DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(readings).build();
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(404)
                    .entity("{\"error\":\"Sensor with id " + sensorId + " not found\"}")
                    .build();
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is in MAINTENANCE mode and cannot accept new readings");
        }

        if (reading.getId() == null || reading.getId().trim().isEmpty()) {
            reading.setId(UUID.randomUUID().toString());
        }
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        DataStore.readings.putIfAbsent(sensorId, new ArrayList<>());
        DataStore.readings.get(sensorId).add(reading);

        // Update current value on parent sensor
        sensor.setCurrentValue(reading.getValue());

        return Response.status(201)
                .entity("{\"message\":\"Reading added successfully\", \"currentValue\":" + reading.getValue() + "}")
                .build();
    }
}