package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.request.LoginRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.lambda.SignUpHandler;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.model.service.LoginService;


class SignUpDAOTest {

    ServerFacade facade = new ServerFacade();
//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testSignUpValid() {
        SignUpRequest request = new SignUpRequest("Dillon", "Harris", "@dillonkh", "Password1", "https://orbitermag.com/wp-content/uploads/2017/03/default-user-image-300x300.png");
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
