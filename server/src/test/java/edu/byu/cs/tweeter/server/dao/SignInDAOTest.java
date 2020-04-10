package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.dao.request.LoginRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;


class SignInDAOTest {

    ServerFacade facade = new ServerFacade();

//    @BeforeEach
//    void setup() {
//        ServerFacade facade = new ServerFacade();
//        facade.clearAll();
//    }

    @Test
    void testSignInValid() {

        LoginRequest request = new LoginRequest("Password1", "@dillonkh");
        LoginResponse response = new UserDAO().login(request);

        Assertions.assertTrue(response.isAuthentcated);

    }

    @Test
    void testSignInNoUser() {

//        User user = new User("Test", "User", null);
//
//        LoginRequest loginRequest = new LoginRequest("pass",user.getAlias());
////        LoginPresenter loginPresenter = new LoginPresenter();
//        LoginResponse loginResponse = facade.login(loginRequest);
//
//        Assertions.assertEquals(loginResponse.isAuthentcated(), true);
//        Assertions.assertEquals(loginResponse.getUserSignedIn(), new User("Dummy", "Data", "@DummyData", ""));

    }

}
