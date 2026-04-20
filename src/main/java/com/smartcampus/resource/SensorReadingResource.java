package com.smartcampus.resource;

import com.smartcampus.model.*;
import com.smartcampus.service.DataStore;
import com.smartcampus.exception.SensorUnavailableException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

public class SensorReadingResource {

    private String id;

    public SensorReadingResource(String id) {
        this.id = id;
    }

    @GET
    public List<SensorReading> get() {
        return DataStore.readings.getOrDefault(id, new ArrayList<>());
    }

    @POST
    public Response add(SensorReading r) {
        Sensor s = DataStore.sensors.get(id);

        if (s.getStatus().equals("MAINTENANCE")) {
            throw new SensorUnavailableException("Sensor unavailable");
        }

        DataStore.readings.putIfAbsent(id, new ArrayList<>());
        DataStore.readings.get(id).add(r);

        s.setCurrentValue(r.getValue());

        return Response.status(201).build();
    }
}