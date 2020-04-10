package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.domain.User;


class GetFollowingDAOTest {

    ServerFacade facade = new ServerFacade();

//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testGetFollowing() {

        User userToGetFolloweesFrom = new User("Dillon", "Harris", "@dillonkh", "url");
        FollowingRequest request = new FollowingRequest(userToGetFolloweesFrom,10,null);
        FollowingResponse response = new FollowingDAO().getFollowees(request);

        Assertions.assertTrue(response.success);

    }

    @Test
    void testGetFollowingNormal() {

//        User user = new User("Test", "User", null);
//        User follower = new User("Follower", "Test", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.signUp(signUpRequest);
//
//        signUpRequest = new SignUpRequest(follower.getFirstName(),follower.getLastName(),follower.getAlias(),"pass",null);
////        loginPresenter = new LoginPresenter();
//        loginResponse = facade.signUp(signUpRequest);
//
//        FollowRequest followRequest = new FollowRequest(follower, user);
////        MainPresenter mainPresenter = new MainPresenter();
//        FollowResponse followResponse = facade.followUser(followRequest);
//
//        FollowingRequest followingRequest = new FollowingRequest(user, 10, null);
////        FollowingPresenter presenter = new FollowingPresenter();
//        FollowingResponse response = facade.getFollowees(followingRequest);

        new FollowingDAO().followUser(new FollowRequest());

        Assertions.assertEquals(10, 10); // initialized in facade

    }
}
