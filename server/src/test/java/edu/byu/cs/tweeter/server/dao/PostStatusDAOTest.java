package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.testing.ServerFacade;


class PostStatusDAOTest {

    ServerFacade facade = new ServerFacade();

//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testPostStatusValid() {

//        User user = new User("Test", "User", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.signUp(signUpRequest);
//
//        TweetRequest tweetRequest = new TweetRequest(new Tweet(user.getAlias(),"fake tweet",null));
////        MainPresenter mainPresenter = new MainPresenter();
//        TweetResponse tweetResponse = facade.addTweet(tweetRequest);
//
//        Assertions.assertEquals(tweetResponse.isSent(), true);
//
//        StoryRequest storyRequest = new StoryRequest(user, 10, null);
////        StoryPresenter storyPresenter = new StoryPresenter();
//        StoryResponse storyResponse = facade.getStory(storyRequest);
//
//        Assertions.assertEquals(storyResponse.getTweets().size(), 0);

    }

    @Test
    void testPostStatusUserNotSignedIn() {

//        User user = new User("Test", "User", null);
//        User bad = new User("Bad", "Test", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.signUp(signUpRequest);
//
//        TweetRequest tweetRequest = new TweetRequest(new Tweet(bad.getAlias(),"fake tweet",null));
////        MainPresenter mainPresenter = new MainPresenter();
//        TweetResponse tweetResponse = facade.addTweet(tweetRequest);
//
//        Assertions.assertEquals(tweetResponse.isSent(), true);
//
//        StoryRequest storyRequest = new StoryRequest(bad, 10, null);
////        StoryPresenter storyPresenter = new StoryPresenter();
//        StoryResponse storyResponse = facade.getStory(storyRequest);
//
//        Assertions.assertEquals(storyResponse.getMessage(), null);

    }

}
