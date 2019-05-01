package com.owned.examples.grpc.calculator.server;

import com.proto.calculator.CalculatorServiceGrpc.CalculatorServiceImplBase;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
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
}
