package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutRequest;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.server.model.request.TweetRequest;
import edu.byu.cs.tweeter.server.model.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.server.model.response.TweetResponse;
import edu.byu.cs.tweeter.server.model.service.FeedService;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class UpdateFeedsHandler implements RequestHandler<SQSEvent, Void> {


    @Override
    public Void handleRequest(SQSEvent event, Context context) {

        for (SQSEvent.SQSMessage msg : event.getRecords()) {

            // post the feeds in batches. we will have user, author and tweet
            Gson gson = new Gson();
            UpdateFeedsRequest updateFeedsRequest = gson.fromJson(msg.getBody(),UpdateFeedsRequest.class);
            System.out.println("LIST OF USERS: "+updateFeedsRequest.getUserAliasList());
            FeedService.getInstance().updateFeeds(updateFeedsRequest);

        }


        return null;
    }


}
