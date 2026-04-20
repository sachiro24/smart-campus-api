package com.smartcampus.exception.mapper;

import com.smartcampus.exception.SensorUnavailableException;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException ex) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity("{\"error\":\"" + ex.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}