package com.owned.examples.grpc.calculator.server;

import com.proto.calculator.CalculatorServiceGrpc.CalculatorServiceImplBase;
import com.proto.calculator.SquareRootRequest;
import com.proto.calculator.SquareRootResponse;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceImplBase {

    @Override
    public void calc(SumRequest request, StreamObserver<SumResponse> responseObserver) {

        int firstNumber = request.getFirstNumber();
        int secondNumber = request.getSecondNumber();

        int result = firstNumber + secondNumber;
        SumResponse sumResponse = SumResponse.newBuilder().setResult(result).build();

        responseObserver.onNext(sumResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {
        int number = request.getNumber();
        if (number >= 0) {
            responseObserver.onNext(SquareRootResponse.newBuilder().setNumber(Math.sqrt(number)).build());
        } else {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("The number being sent is not positive").augmentDescription("Number: " + number).asRuntimeException());
        }
    }
}
