package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowerRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

class GetFollowersDAOTest {

    ServerFacade facade = new ServerFacade();

//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testGetEmptyFollowersInitial() {

        User user = new User("Test", "User", null);
        FollowerRequest followerRequest = new FollowerRequest(user, 10, null);
//        FollowerPresenter presenter = new FollowerPresenter();
        FollowerResponse response = facade.getFollowers(followerRequest);

        Assertions.assertEquals(response.getFollowees().size(), 10); // initialized in facade
    }

    @Test
    void testGetFollowersNormal() {

        User user = new User("Test", "User", null);
        User follower = new User("Follower", "Test", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
//        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = facade.signUp(signUpRequest);

        signUpRequest = new SignUpRequest(follower.getFirstName(),follower.getLastName(),follower.getAlias(),"pass",null);
//        loginPresenter = new LoginPresenter();
        loginResponse = facade.signUp(signUpRequest);

        FollowRequest followRequest = new FollowRequest(user, follower);
//        MainPresenter mainPresenter = new MainPresenter();
        FollowResponse followResponse = facade.followUser(followRequest);

        FollowerRequest followerRequest = new FollowerRequest(user, 10, null);
//        FollowerPresenter presenter = new FollowerPresenter();
        FollowerResponse response = facade.getFollowers(followerRequest);

        Assertions.assertEquals(response.getFollowees().size(), 10); // initialized in facade
    }
}
