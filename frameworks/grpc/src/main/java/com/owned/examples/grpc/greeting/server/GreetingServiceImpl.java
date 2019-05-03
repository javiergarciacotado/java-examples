package com.owned.examples.grpc.greeting.server;

import com.proto.greeting.Greeting;
import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc.GreetingServiceImplBase;
import io.grpc.stub.StreamObserver;

import java.util.stream.IntStream;

public class GreetingServiceImpl extends GreetingServiceImplBase {

    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {

        //extract info
        final Greeting greeting = request.getGreeting();
        final String firstName = greeting.getFirstName();
        final String lastName = greeting.getLastName();

        //create the response
        final String result = "Hello " + firstName + " " + lastName;
        final GreetingResponse response = GreetingResponse.newBuilder().setResult(result).build();

        //send response to the client
        responseObserver.onNext(response);

        //complete the RPC call
        responseObserver.onCompleted();
    }

    @Override
    public void greetStreamServer(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {

        final String firstName = request.getGreeting().getFirstName();
        final String lastName = request.getGreeting().getLastName();

        IntStream.range(0, 10).forEach(index -> {

            final String result = "Hello " + firstName + " " + lastName + ". This is server response number " + index;
            GreetingResponse response = GreetingResponse.newBuilder().setResult(result).build();

            responseObserver.onNext(response);

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        responseObserver.onCompleted();
    }
}
