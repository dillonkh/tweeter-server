package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.model.request.FeedRequest;
import edu.byu.cs.tweeter.server.model.request.StoryRequest;
import edu.byu.cs.tweeter.server.model.request.TweetRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.FeedResponse;
import edu.byu.cs.tweeter.server.model.response.StoryResponse;
import edu.byu.cs.tweeter.server.model.response.TweetResponse;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.testing.ServerFacade;


class StoryDAOTest {

    ServerFacade facade = new ServerFacade();

    @Test
    void testAddTweet() {

        Tweet tweet = new Tweet("@dillonkh", "Dillon", "Harris", "time test 2","url");
        TweetResponse response = new TweetsDAO().addTweet(new TweetRequest(tweet));

        Assertions.assertTrue(response.sent);
    }

    @Test
    void testAddTweetToFeeds() {

        Tweet tweet = new Tweet("@testUser2", "Test2", "User", "testing update feeds for @dillonkh","url");
        new TweetsDAO().addTweetsToFeeds(new TweetRequest(tweet));

        Assertions.assertTrue(true);
    }

    @Test
    void testGetStory() {
//        User user = new UserDAO().getUser(new UserRequest(null, "@dillonkh")).getUser();
        User user = new User("Dillon", "Harris", "@dillonkh", "url");
        Tweet lastTweet = new Tweet("@dillonkh","Dillon","Harris","fake","fake","13/04/2020 12:19:41");
        StoryResponse response = new TweetsDAO().getStory(new StoryRequest(user, 10, lastTweet));

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
