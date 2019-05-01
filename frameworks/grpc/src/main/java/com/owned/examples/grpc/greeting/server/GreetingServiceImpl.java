package com.owned.examples.grpc.greeting.server;

import com.proto.greeting.Greeting;
import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc.GreetingServiceImplBase;
import io.grpc.stub.StreamObserver;

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
}
