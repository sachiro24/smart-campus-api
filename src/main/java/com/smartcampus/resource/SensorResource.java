package com.smartcampus.resource;

import com.smartcampus.model.*;
import com.smartcampus.service.DataStore;
import com.smartcampus.exception.LinkedResourceNotFoundException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;
import java.util.stream.*;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Collection<Sensor> getAll(@QueryParam("type") String type) {
        if (type == null) return DataStore.sensors.values();

        return DataStore.sensors.values().stream()
                .filter(s -> s.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @POST
    public Response create(Sensor s) {
        if (!DataStore.rooms.containsKey(s.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room not found");
        }

        DataStore.sensors.put(s.getId(), s);
        DataStore.rooms.get(s.getRoomId()).getSensorIds().add(s.getId());

        return Response.status(201).entity(s).build();
    }

    @Path("/{id}/readings")
    public SensorReadingResource readings(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
}