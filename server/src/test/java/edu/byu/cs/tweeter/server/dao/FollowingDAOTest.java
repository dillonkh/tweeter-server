package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

class FollowingDAOTest {


    @Test
    void testFollowUser() {
        User userToFollow = new User("Test1", "User", "@testUser1", "url");
        User userLoggedIn = new User("Dillon","Harris", "@dillonkh", "url");
        FollowRequest request = new FollowRequest(userToFollow, userLoggedIn);

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
    void testPostSQSMessage() {

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        String messageBody = "test trigger is gone";

        String queueUrl = "https://sqs.us-west-2.amazonaws.com/500231213860/test_queue";



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
