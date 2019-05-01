package com.owned.examples.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello gRPC!");

        final Server server = startServer(50051);

        addShutdownHook(server);

        server.awaitTermination();
    }

    private static Server startServer(int port) throws IOException {
        final Server server = ServerBuilder
                .forPort(port)
                .addService(new GreetingServiceImpl())
                .build();
        server.start();

        return server;
    }

    private static void addShutdownHook(Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Received shutdown request");
            server.shutdown();
            System.out.println("Succesfully stopped the server");
        }));
    }
}
