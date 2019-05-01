package com.owned.examples.grpc.calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws InterruptedException, IOException {
        final Server server = startServer(50052);

        addShutdownHook(server);

        System.out.println("Calculator server awaiting for termination");
        server.awaitTermination();
    }


    private static Server startServer(int port) throws IOException {
        return ServerBuilder.forPort(port)
                .addService(new CalculatorServiceImpl())
                .build()
                .start();
    }

    private static void addShutdownHook(Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }

}
