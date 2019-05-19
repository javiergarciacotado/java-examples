package com.owned.examples.grpc.calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.CalculatorServiceGrpc.CalculatorServiceImplBase;
import com.proto.calculator.SquareRootRequest;
import com.proto.calculator.SquareRootResponse;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class CalculatorClient extends CalculatorServiceImplBase {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

//        unary(channel);

        error(channel);
    }

    private static void unary(ManagedChannel channel) {
        CalculatorServiceGrpc.CalculatorServiceBlockingStub calculatorServiceStub = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest sumRequest = SumRequest.newBuilder().setFirstNumber(1).setSecondNumber(2).build();
        SumResponse sumResponse = calculatorServiceStub.calc(sumRequest);

        System.out.println(sumResponse.getResult());

        channel.shutdown();
    }

    private static void error(ManagedChannel channel) {

        CalculatorServiceGrpc.CalculatorServiceBlockingStub calculatorServiceBlockingStub = CalculatorServiceGrpc.newBlockingStub(channel);
        try {
            SquareRootResponse squareRootResponse = calculatorServiceBlockingStub.squareRoot(SquareRootRequest.newBuilder().setNumber(-1).build());
        } catch (StatusRuntimeException statusRuntimeException) {
            System.out.println("Got an exception for square root!");
            statusRuntimeException.printStackTrace();
        }
    }
}
