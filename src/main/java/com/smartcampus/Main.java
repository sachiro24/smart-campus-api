package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import com.smartcampus.config.AppConfig;
import java.io.IOException;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    public static void main(String[] args) throws IOException {
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI), new AppConfig(), true);

        System.out.println("=========================================");
        System.out.println("✅ Smart Campus API STARTED SUCCESSFULLY");
        System.out.println("→ Base URL : " + BASE_URI);
        System.out.println("→ Test it  : http://localhost:8080/api/v1/");
        System.out.println("Press ENTER to stop...");
        System.out.println("=========================================");

        System.in.read();
        server.shutdownNow();
    }
}