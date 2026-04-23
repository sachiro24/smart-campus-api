package com.smartcampus.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")   // ←←← THIS IS THE IMPORTANT CHANGE
public class AppConfig extends ResourceConfig {

    public AppConfig() {
        // Explicitly register everything (most reliable)
        register(com.smartcampus.resource.RootResource.class);
        register(com.smartcampus.resource.RoomResource.class);
        register(com.smartcampus.resource.SensorResource.class);
        register(com.smartcampus.resource.SensorReadingResource.class);

        register(com.smartcampus.filter.LoggingFilter.class);
        register(com.smartcampus.exception.mapper.RoomNotEmptyMapper.class);
        register(com.smartcampus.exception.mapper.LinkedResourceMapper.class);
        register(com.smartcampus.exception.mapper.SensorUnavailableMapper.class);
        register(com.smartcampus.exception.mapper.GlobalExceptionMapper.class);

        // Better JSON support
        register(org.glassfish.jersey.jackson.JacksonFeature.class);
    }
}