package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import com.smartcampus.config.AppConfig;

import java.net.URI;

public class Main {
    public static void main(String[] args) {
        String BASE_URI = "http://localhost:8080/api/v1/";

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                new AppConfig()
        );

        System.out.println("Server running at " + BASE_URI);
    }
}