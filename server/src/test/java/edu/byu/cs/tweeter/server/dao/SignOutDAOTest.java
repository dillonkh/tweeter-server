package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.model.request.LoginRequest;
import edu.byu.cs.tweeter.server.model.request.SignUpRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.testing.ServerFacade;


class SignOutDAOTest {

    ServerFacade facade = new ServerFacade();

//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testSignOutValid() {

        User user = new User("Test", "User", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
//        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = facade.signUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest("pass",user.getAlias());
//        loginPresenter = new LoginPresenter();
        loginResponse = facade.login(loginRequest);

        Assertions.assertEquals(loginResponse.isAuthentcated(), true);
        Assertions.assertEquals(loginResponse.getUserSignedIn(), new User("Dummy", "Data", "@DummyData", ""));

        UserRequest userRequest = new UserRequest(user,user.getAlias());
//        FeedPresenter presenter = new FeedPresenter();
        UserResponse response = facade.getUser(userRequest);

        Assertions.assertEquals(response.getUser(), user);
    }

    @Test
    void testSignOutNotCorrectUser() {

        User user = new User("Test", "User", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
//        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = facade.signUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest("pass",user.getAlias());
//        loginPresenter = new LoginPresenter();
        loginResponse = facade.login(loginRequest);

        Assertions.assertEquals(loginResponse.isAuthentcated(), true);
        Assertions.assertEquals(loginResponse.getUserSignedIn(), new User("Dummy", "Data", "@DummyData", ""));

        UserRequest userRequest = new UserRequest(user,user.getAlias());
//        FeedPresenter presenter = new FeedPresenter();
        UserResponse response = facade.getUser(userRequest);

        Assertions.assertEquals(response.getUser(), user);
    }

}
