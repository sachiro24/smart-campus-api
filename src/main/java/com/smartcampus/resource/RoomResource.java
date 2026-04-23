package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.service.DataStore;
import com.smartcampus.exception.RoomNotEmptyException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAll() {
        return Response.ok(DataStore.rooms.values()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);
        if (room == null) {
            return Response.status(404)
                    .entity("{\"error\":\"Room with id " + id + " not found\"}")
                    .build();
        }
        return Response.ok(room).build();
    }

    @POST
    public Response create(Room room) {
        if (room == null || room.getId() == null || room.getId().trim().isEmpty()) {
            return Response.status(400)
                    .entity("{\"error\":\"Room ID is required\"}")
                    .build();
        }
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            return Response.status(400)
                    .entity("{\"error\":\"Room name is required\"}")
                    .build();
        }
        if (room.getCapacity() <= 0) {
            return Response.status(400)
                    .entity("{\"error\":\"Capacity must be greater than 0\"}")
                    .build();
        }

        DataStore.rooms.put(room.getId(), room);
        return Response.status(201).entity(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        Room r = DataStore.rooms.get(id);
        if (r == null) {
            return Response.status(404)
                    .entity("{\"error\":\"Room with id " + id + " not found\"}")
                    .build();
        }

        if (!r.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room cannot be deleted because it still has sensors assigned");
        }

        DataStore.rooms.remove(id);
        return Response.status(200)
                .entity("{\"message\":\"Room deleted successfully\"}")
                .build();
    }
}