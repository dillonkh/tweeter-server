package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.model.request.FollowRequest;
import edu.byu.cs.tweeter.server.model.request.FollowingRequest;
import edu.byu.cs.tweeter.server.model.request.IsFollowingRequest;
import edu.byu.cs.tweeter.server.model.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.model.response.FollowResponse;
import edu.byu.cs.tweeter.server.model.response.FollowingResponse;
import edu.byu.cs.tweeter.server.model.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.model.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.model.service.FollowService;
import edu.byu.cs.tweeter.server.model.service.FollowingService;

class FollowingDAOTest {


    @Test
    void testFollowUser() {
        User userToFollow = new User("Test1", "User", "@testUser1", "url");
        User userLoggedIn = new User("Dillon","Harris", "@dillonkh", "url");
        FollowRequest request = new FollowRequest(userLoggedIn,userToFollow);

        FollowResponse response = new FollowingDAO().followUser(request);

        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    void testUnFollowUser() {
        User userToUnFollow = new User("Test1", "User1", "@testUser1", "url");
        User userLoggedIn = new User("Dillon","Harris", "@dillonkh", "url");
        UnFollowRequest request = new UnFollowRequest(userLoggedIn, userToUnFollow);

        UnFollowResponse response = new FollowingDAO().unfollowUser(request);

        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    void testGetFollowees() {
        User userToGetFolloweesFrom = new User("Dillon", "Harris", "@dillonkh", "url");
        FollowingRequest request = new FollowingRequest(userToGetFolloweesFrom,10,null);
        FollowingResponse response = new FollowingDAO().getFollowees(request);

        Assertions.assertTrue(response.success);

    }

    @Test
    void testIsFollowing() {
        User userLoggedIn = new User("Dillon", "Harris", "@dillonkh", "url");
        User userShown = new User("Test2", "User", "@testUser2", "url");
        IsFollowingRequest request = new IsFollowingRequest(userLoggedIn, userShown);
        IsFollowingResponse response = FollowService.getInstance().isFollowing(request);

        Assertions.assertTrue(!response.success);

    }

    @Test
    void testPostSQSMessage() {

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        String messageBody = "test trigger is gone";

        String queueUrl = "https://sqs.us-west-2.amazonaws.com/500231213860/post_tweets_to_feeds_queue";



        SendMessageRequest send_msg_request = new SendMessageRequest()

                .withQueueUrl(queueUrl)

                .withMessageBody(messageBody)

                .withDelaySeconds(5);



        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);



        String msgId = send_msg_result.getMessageId();

        System.out.println("Message ID: " + msgId);

        Assertions.assertTrue(true);
    }
}
