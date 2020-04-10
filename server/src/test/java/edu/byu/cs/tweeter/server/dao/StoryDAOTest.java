package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import edu.byu.cs.tweeter.server.dao.request.FeedRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.request.StoryRequest;
import edu.byu.cs.tweeter.server.dao.request.TweetRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.response.TweetResponse;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;


class StoryDAOTest {

    ServerFacade facade = new ServerFacade();

    @Test
    void testAddTweet() {

        Tweet tweet = new Tweet("@dillonkh", "this is the first tweet","url");
        TweetResponse response = new TweetsDAO().addTweet(new TweetRequest(tweet));

        Assertions.assertTrue(response.sent);
    }

    @Test
    void testGetStory() {
        User user = new UserDAO().getUser(new UserRequest(null, "@dillonkh")).getUser();
        StoryResponse response = new TweetsDAO().getStory(new StoryRequest(user, 10, null));

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void testGetFeed() {
        User user = new UserDAO().getUser(new UserRequest(null, "@dillonkh")).getUser();

        FeedResponse response = new TweetsDAO().getFeed(new FeedRequest(user, 10, null));

        Assertions.assertTrue(response.isSuccess());
    }


//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

//    @Test
//    void testGetEmptyStoryInitial() {
//
//        User user = new User("Test", "User", null);
//        StoryRequest request = new StoryRequest(user, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
//        StoryResponse response = facade.getStory(request);
//
//        Assertions.assertEquals(response.getTweets().size(), 0); // initialized by facade
//    }
//
//    @Test
//    void testGetStoryNormal() {
//
//        User user = new User("Test", "User", null);
//        User following = new User("Following", "Test", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.signUp(signUpRequest);
//
//        signUpRequest = new SignUpRequest(following.getFirstName(), following.getLastName(), following.getAlias(), "pass", null);
//        facade.signUp(signUpRequest);
//
//        FollowRequest followRequest = new FollowRequest(following, user);
////        MainPresenter mainPresenter = new MainPresenter();
//        FollowResponse followResponse = facade.followUser(followRequest);
//
//        int i = 0;
//        while(i < 9) {
//            TweetRequest tweetRequest = new TweetRequest(new Tweet(following.getAlias(),"fake tweet: "+i,null));
//            TweetResponse tweetResponse = facade.addTweet(tweetRequest);
//
//            i++;
//        }
//
//        StoryRequest request = new StoryRequest(user, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
//        StoryResponse response = facade.getStory(request);
//
//        Assertions.assertEquals(response.getTweets().size(), 0);
//        Assertions.assertEquals(response.hasMorePages(), false);
//    }
//
//    @Test
//    void testGetStoryCorrectOrder() {
//
//        User user = new User("Test", "User", null);
//        User following = new User("Following", "Test", null);
//
//        FollowRequest followRequest = new FollowRequest(following, user);
////        MainPresenter mainPresenter = new MainPresenter();
//        FollowResponse followResponse = facade.followUser(followRequest);
//
//        int i = 0;
//        while(i < 9) {
//            TweetRequest tweetRequest = new TweetRequest(new Tweet(following.getAlias(),"fake tweet: "+i,null));
//            TweetResponse tweetResponse = facade.addTweet(tweetRequest);
//
//            i++;
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        StoryRequest request = new StoryRequest(user, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
//        StoryResponse response = facade.getStory(request);
//
//        i = 9;
//        for (Tweet t : response.getTweets()) {
//            if (t.getUser().equals(following)) {
//                Assertions.assertEquals("fake tweet: "+i, t.getMessage());
//                i--;
//            }
//        }
//    }

}
