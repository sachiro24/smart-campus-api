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
    public Response getAll(@QueryParam("type") String type) {
        Collection<Sensor> result;
        if (type == null || type.trim().isEmpty()) {
            result = DataStore.sensors.values();
        } else {
            result = DataStore.sensors.values().stream()
                    .filter(s -> s.getType() != null && s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return Response.ok(result).build();
    }

    @POST
    public Response create(Sensor s) {
        if (s == null || s.getId() == null || s.getId().trim().isEmpty()) {
            return Response.status(400).entity("{\"error\":\"Sensor ID is required\"}").build();
        }
        if (s.getRoomId() == null || s.getRoomId().trim().isEmpty()) {
            return Response.status(400).entity("{\"error\":\"Room ID is required\"}").build();
        }
        if (!DataStore.rooms.containsKey(s.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room with id " + s.getRoomId() + " does not exist");
        }

        // Default status if not provided
        if (s.getStatus() == null || s.getStatus().trim().isEmpty()) {
            s.setStatus("ACTIVE");
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