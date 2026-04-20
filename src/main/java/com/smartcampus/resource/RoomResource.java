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
    public Collection<Room> getAll() {
        return DataStore.rooms.values();
    }

    @POST
    public Response create(Room room) {
        DataStore.rooms.put(room.getId(), room);
        return Response.status(201).entity(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        Room r = DataStore.rooms.get(id);

        if (r != null && !r.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room has sensors");
        }

        DataStore.rooms.remove(id);
        return Response.ok().build();
    }
}
