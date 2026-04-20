package com.smartcampus.exception.mapper;

import com.smartcampus.exception.RoomNotEmptyException;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        return Response.status(Response.Status.CONFLICT)
                .entity("{\"error\":\"" + ex.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
