package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class LoginService {

    private static LoginService instance;

    private final ServerFacade serverFacade;


    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    private LoginService() {
        // TODO: Remove when the actual login functionality exists.
//        currentUser = new User("Test", "User",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        setCurrentUser(currentUser);
        serverFacade = new ServerFacade();
    }

//    public UserResponse getCurrentUser() {
//        CurrentUserRequest request = new CurrentUserRequest();
//        UserResponse response = serverFacade.getCurrentUser(request);
//        return response;
//    }

//    public void setCurrentUser(User currentUser) {
//
//        UserRequest request = new UserRequest(currentUser);
//        UserResponse response = serverFacade.setCurrentUser(request);
//    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse r = serverFacade.login(request);
        // some other stuff
//        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
//            UserService.getInstance().setCurrentUser(new UserRequest(r.getUserSignedIn()));
//            UserService.getInstance().setUserShown(new UserRequest(r.getUserSignedIn()));
//        }

        return r;
    }

    public LoginResponse signUp(SignUpRequest request) {
        LoginResponse r = serverFacade.signUp(request);
        // some other stuff
//        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
//            UserService.getInstance().setCurrentUser(new UserRequest(r.getUserSignedIn()));
//            UserService.getInstance().setUserShown(new UserRequest(r.getUserSignedIn()));
//        }

        return r;
    }
}
