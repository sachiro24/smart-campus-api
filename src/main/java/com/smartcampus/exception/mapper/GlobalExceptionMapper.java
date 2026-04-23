package com.smartcampus.exception.mapper;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

import java.util.logging.*;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable ex) {
        // Log the real error on server console
        logger.log(Level.SEVERE, "Unhandled exception occurred", ex);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Something went wrong\", \"message\":\"" + ex.getMessage() + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}