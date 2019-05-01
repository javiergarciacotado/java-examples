package com.owned.examples.grpc.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greeting.Greeting;
import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC client");

        final ManagedChannel channel = startChannel(50051);

//        First example
//        DummyServiceGrpc.DummyServiceBlockingStub dummyClient = dummyClientOf(channel);

//        created a greet service client (blocking - synchronous)
        final GreetingServiceGrpc.GreetingServiceBlockingStub greetingClient = GreetingServiceGrpc.newBlockingStub(channel);

//        created a protocol buffer - greeting representation message
        final Greeting greeting = Greeting.newBuilder().setFirstName("Javi").setLastName("Garcia Cotado").build();
        final GreetingRequest greetingRequest = GreetingRequest.newBuilder().setGreeting(greeting).build();

//        create a RPC request
        final GreetingResponse greetingResponse = greetingClient.greet(greetingRequest);

//        prints response
        System.out.println(greetingResponse.getResult());
        channel.shutdown();

    }

//    First example

//    static DummyServiceGrpc.DummyServiceBlockingStub dummyClientOf(ManagedChannel channel) {
//        System.out.println("Creating stub");
//        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);
//        System.out.println("Shutting down channel");
//
//        return syncClient;
//    }

    private static ManagedChannel startChannel(int port) {
        return ManagedChannelBuilder.forAddress("localhost", port)
                    .usePlaintext()
                    .build();
    }
}
