package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.ServerFacade;
import edu.byu.cs.tweeter.server.dao.request.CurrentUserRequest;
import edu.byu.cs.tweeter.server.dao.request.LoginRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

public class LoginService {

    private static LoginService instance;

    private final ServerFacade serverFacade;

    private User currentUser;

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

    public UserResponse getCurrentUser(CurrentUserRequest request) {
        return serverFacade.getCurrentUser(request);
    }

    public UserResponse setCurrentUser(UserRequest currentUserRequest) {
        return serverFacade.setCurrentUser(currentUserRequest);
    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse r = serverFacade.login(request);
        // some other stuff
        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
            UserRequest userRequest = new UserRequest(r.getUserSignedIn(),null);
            setCurrentUser(userRequest);
        }

        return r;
    }

    public LoginResponse signUp(SignUpRequest request) {
        LoginResponse r = serverFacade.signUp(request);
        // some other stuff
        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
            UserRequest userRequest = new UserRequest(r.getUserSignedIn(),null);
            setCurrentUser(userRequest);
        }

        return r;
    }
}
