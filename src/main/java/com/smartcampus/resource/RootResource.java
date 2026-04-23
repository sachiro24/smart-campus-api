package com.smartcampus.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {

    @GET
    public Response getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Smart Campus Sensor & Room Management API");
        info.put("version", "1.0");
        info.put("apiVersion", "v1");
        info.put("description", "RESTful API for managing rooms and sensors in a smart campus");
        info.put("contact", "Your Name - sachiro.20232147@iit.ac.lk");

        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        links.put("documentation", "See README.md");

        info.put("links", links);

        return Response.ok(info).build();
    }
}