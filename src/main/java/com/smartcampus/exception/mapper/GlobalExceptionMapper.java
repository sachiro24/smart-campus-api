package com.smartcampus.exception.mapper;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Something went wrong\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}