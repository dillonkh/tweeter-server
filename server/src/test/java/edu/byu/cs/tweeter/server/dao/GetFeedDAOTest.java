package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.model.request.FeedRequest;
import edu.byu.cs.tweeter.server.model.response.FeedResponse;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.testing.ServerFacade;

class GetFeedDAOTest {

    ServerFacade facade = new ServerFacade();

    @Test
    void testGetFeed() {

        User user = new User("Test", "User1", "@testUser100", "url");
        FeedResponse response = new TweetsDAO().getFeed(new FeedRequest(user, 10, null));

        Assertions.assertTrue(response.isSuccess());

        new FeedDAO().getFeed(null);
        new FeedDAO().updateFeeds(null);

    }

//    @Test
//    void testGetEmptyFeedNoFollowees() {
//
//        User user = new User("Test", "User", null);
//        User follower = new User("Follows", "Test", null);
//
////        FollowRequest followRequest = new FollowRequest(user, follower);
////        MainPresenter mainPresenter = new MainPresenter();
////        FollowResponse followResponse = mainPresenter.follow(followRequest);
//
//        int i = 0;
//        while(i < 9) {
//            TweetRequest tweetRequest = new TweetRequest(new Tweet(follower.getAlias(),"fake tweet: "+i,null));
////            TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);
//            facade.addTweet(tweetRequest);
//
//            i++;
//        }
//
//        FeedRequest request = new FeedRequest(user, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
////        FeedResponse response = presenter.getTweets(request);
//
//        FeedResponse response = facade.getFeed(request);
//
//        Assertions.assertEquals(response.getTweets().size(), 10);
//
//    }
//
//    @Test
//    void testGetFeedNormal() {
//
//        User user = new User("Test", "User", null);
//        User following = new User("Following", "Test", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
////        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);
//
//        facade.signUp(signUpRequest);
//
//        signUpRequest = new SignUpRequest(following.getFirstName(), following.getLastName(), following.getAlias(), "pass", null);
//        facade.signUp(signUpRequest);
//
//        FollowRequest followRequest = new FollowRequest(following, user);
////        MainPresenter mainPresenter = new MainPresenter();new
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
//        FeedRequest request = new FeedRequest(user, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
//        FeedResponse response = facade.getFeed(request);
//
//        Assertions.assertEquals(response.getTweets().size(), 10);
//        Assertions.assertEquals(response.hasMorePages(), true);
//    }
//
//    @Test
//    void testGetFeedCorrectOrder() {
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
//        FeedRequest request = new FeedRequest(user, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
//        FeedResponse response = facade.getFeed(request);
//
//        i = 9;
//        for (Tweet t : response.getTweets()) {
//            if (t.getUser().equals(following)) {
//                Assertions.assertEquals("fake tweet: "+i, t.getMessage());
//                i--;
//            }
//        }
//    }
//
//    @Test
//    void testGetFollowersFeed() {
//
//        User user = new User("Test", "User", null);
//        User follower = new User("Follower", "Test", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.signUp(signUpRequest);
//
//        signUpRequest = new SignUpRequest(follower.getFirstName(), follower.getLastName(), follower.getAlias(), "pass", null);
//        facade.signUp(signUpRequest);
//
//        FollowRequest followRequest = new FollowRequest(user, follower);
////        MainPresenter mainPresenter = new MainPresenter();
//        FollowResponse followResponse = facade.followUser(followRequest);
//
//        int i = 0;
//        while(i < 9) {
//            TweetRequest tweetRequest = new TweetRequest(new Tweet(user.getAlias(),"fake tweet: "+i,null));
//            TweetResponse tweetResponse = facade.addTweet(tweetRequest);
//
//            i++;
//        }
//
//        FeedRequest request = new FeedRequest(follower, 10, null);
////        FeedPresenter presenter = new FeedPresenter();
//        FeedResponse response = facade.getFeed(request);
//
//        Assertions.assertEquals(response.getTweets().size(), 10);
//        Assertions.assertEquals(response.hasMorePages(), true);
//
//    }
}
