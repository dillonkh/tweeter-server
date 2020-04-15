package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.model.request.FollowingRequest;
import edu.byu.cs.tweeter.server.model.request.TweetRequest;
import edu.byu.cs.tweeter.server.model.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.server.model.response.FollowingResponse;
import edu.byu.cs.tweeter.server.model.response.TweetResponse;
import edu.byu.cs.tweeter.server.model.service.FollowingService;
import edu.byu.cs.tweeter.server.model.service.StoryService;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetUsersImFollowingToUpdateFeedsHandler implements RequestHandler<SQSEvent, Void> {


    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        final int LIMIT = 200;
//        final int MESSAGE_BATCH_LIMIT = 25;
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        final String QUEUE_URL = "https://sqs.us-west-2.amazonaws.com/500231213860/update_feeds_queue";


        // get all the followers (all 10,000)
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            System.out.println("MESSAGE: "+msg.toString());
            Gson gson = new Gson();
            TweetRequest r = gson.fromJson(msg.getBody(), TweetRequest.class);
            System.out.println(r.getTweet().getMessage());
            Tweet tweet = r.getTweet();
            User newUser = new User(tweet.getFirstName(),tweet.getLastName(),tweet.getUser(),"url");

            boolean hasMoreUsers = true;
            while (hasMoreUsers) {
                // get all the users im following
                UpdateFeedsRequest updateFeedsRequest = FollowingService.getInstance().getFolloweesMessage(new FollowingRequest(newUser, LIMIT, null), r.getTweet());

                String message = gson.toJson(updateFeedsRequest);

                SendMessageRequest send_msg_request = new SendMessageRequest()

                        .withQueueUrl(QUEUE_URL)

                        .withMessageBody(message)

                        .withDelaySeconds(5);



                SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

//                System.out.println(send_msg_result.toString());

                hasMoreUsers = updateFeedsRequest.hasMore;
            }



        }


        return null;


    }


}
