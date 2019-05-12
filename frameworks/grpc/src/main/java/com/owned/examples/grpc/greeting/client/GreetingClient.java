package com.owned.examples.grpc.greeting.client;

import com.proto.greeting.Greeting;
import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC client");

        final ManagedChannel channel = startChannel(50051);

        final GreetingClient greetingClient = new GreetingClient();

//      First example
//      greetingClient.startExample(channel);

//      Second example
//      greetingClient.unaryExample(channel);

//      Third example
//      greetingClient.serverStreamExample(channel);

//      Fourth example
        greetingClient.clientStreamExample(channel);


        channel.shutdown();

    }


//    First example

//    private void startExample(Channel channel) {
//        First example
//        DummyServiceGrpc.DummyServiceBlockingStub dummyClient = dummyClientOf(channel);
//    }

//    static DummyServiceGrpc.DummyServiceBlockingStub dummyClientOf(ManagedChannel channel) {
//        System.out.println("Creating stub");
//        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);
//        System.out.println("Shutting down channel");
//
//        return syncClient;
//    }

//    Second example

//    private void unaryExample(ManagedChannel channel) {
//        created a greet service client (blocking - synchronous)
//        final GreetingServiceGrpc.GreetingServiceBlockingStub greetingClient = GreetingServiceGrpc.newBlockingStub(channel);

//        created a protocol buffer - greeting representation message
//        final Greeting greeting = Greeting.newBuilder().setFirstName("Javi").setLastName("Garcia Cotado").build();
//        final GreetingRequest greetingRequest = GreetingRequest.newBuilder().setGreeting(greeting).build();

//        create a RPC request
//        final GreetingResponse greetingResponse = greetingClient.greet(greetingRequest);

//        prints response
//        System.out.println(greetingResponse.getResult());
//    }

//    Third example

//    private void serverStreamExample(ManagedChannel channel) {
//
//        final GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
//        final Greeting greeting = Greeting.newBuilder().setFirstName("Javi").setLastName("Garcia Cotado").build();
//        final GreetingRequest greetingRequest = GreetingRequest.newBuilder().setGreeting(greeting).build();
//        stub.greetStreamServer(greetingRequest).forEachRemaining(System.out::println);
//    }

//    Fourth example

    private void clientStreamExample(ManagedChannel channel) {
        //Client should be async
        final GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        StreamObserver<GreetingRequest> requestObserver = stub.greetStreamClient(new StreamObserver<GreetingResponse>() {
            @Override
            public void onNext(GreetingResponse greetingResponse) {
//                Get response from the server
                System.out.println("Received a response from the server " + greetingResponse.getResult());
            }

            @Override
            public void onError(Throwable t) {
//                Get an error from the server

            }

            @Override
            public void onCompleted() {
                System.out.println("Server has completed sending us something");
                countDownLatch.countDown();
            }
        });

        IntStream.range(0, 10).forEach(index -> {
            System.out.println("Sending message " + index);
            requestObserver.onNext(GreetingRequest.newBuilder().setGreeting(Greeting.newBuilder().setFirstName(randomAlphabetic(10))).build());
        });

//      Client is done sending data
        requestObserver.onCompleted();

        try {
            countDownLatch.await(3, SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static ManagedChannel startChannel(int port) {
        return ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();
    }
}
