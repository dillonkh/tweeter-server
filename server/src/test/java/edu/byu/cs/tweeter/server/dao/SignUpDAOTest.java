package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.model.request.SignUpRequest;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.service.LoginService;
import edu.byu.cs.tweeter.server.testing.ServerFacade;


class SignUpDAOTest {

    ServerFacade facade = new ServerFacade();
//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testSignUpValid() {
        SignUpRequest request = new SignUpRequest("Test2", "User", "@testUser2", "Password1", "https://images.unsplash.com/photo-1516912347627-9c5b5d08b35f?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        LoginResponse response = LoginService.getInstance().signUp(request);

        Assertions.assertTrue(response.isAuthentcated);
    }


    @Test
    void testSignUpAlreadySignedUp() {

//        User user = new User("Test", "User", null);
//
//        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.signUp(signUpRequest);
//
//        LoginRequest loginRequest = new LoginRequest("pass",user.getAlias());
////        loginPresenter = new LoginPresenter();
//        loginResponse = facade.login(loginRequest);
//
//        Assertions.assertEquals(loginResponse.isAuthentcated(), true);
//        Assertions.assertEquals(loginResponse.getUserSignedIn(), new User("Dummy", "Data", "@DummyData", ""));
//
//        UserRequest userRequest = new UserRequest(user,user.getAlias());
////        FeedPresenter presenter = new FeedPresenter();
//        UserResponse response = facade.getUser(userRequest);
//
//        Assertions.assertEquals(response.getUser(), user);
    }

}
