package edu.byu.cs.tweeter.server.sqs;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;



public class QueueProcessor implements RequestHandler<SQSEvent, Void> {



    @Override

    public Void handleRequest(SQSEvent event, Context context) {

        for (SQSEvent.SQSMessage msg : event.getRecords()) {

            // Add code to print message body to the log
            System.out.println(msg);
        }

        return null;

    }

}
