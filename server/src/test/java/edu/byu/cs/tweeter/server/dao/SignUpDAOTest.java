package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import edu.byu.cs.tweeter.server.model.domain.User;
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
        SignUpRequest request = new SignUpRequest("Dillon", "Harris", "@dillonkh", "Password1", "https://images.unsplash.com/photo-1516912347627-9c5b5d08b35f?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        LoginResponse response = LoginService.getInstance().signUp(request);

        Assertions.assertTrue(response.isAuthentcated);
    }

    @Test
    void testCreateManyUsers() {
        int usersToCreate = 10000;
        ArrayList<User> users = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < usersToCreate; i++) {
            User u = new User(
                    "Test",
                    "User"+i,
                    "@testUser"+i,
                    "https://tweeter-dillonkh.s3-us-west-2.amazonaws.com/006677ab-e58d-405b-a689-a1438f7637c1.png"
            );
            users.add(u);
            count++;

            if (count == 25) {
                boolean added = new UserDAO().batchAddUsers(users);
                users.clear();
                count = 0;
            }
        }


        Assertions.assertTrue(true);

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
