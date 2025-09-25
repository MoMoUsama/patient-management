package com.pm.billingservice.grpc;

import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static  final Logger logger = LoggerFactory.getLogger(BillingGrpcService.class.getName());
    @Override
    public void createBillingAccount(billing.BillingRequest request,
                                     StreamObserver<billing.BillingResponse> responseObserver)
    {
        logger.info("createBillingAccount request recieved {}", request.toString());

         //  business logic - e.g. saving to DB .. etc

        // construct the response
        billing.BillingResponse billingResponse = billing.BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

        // send the response on the streamObserver obj
        responseObserver.onNext(billingResponse); //send response(s)
        responseObserver.onCompleted();  // end stream
    }
}
