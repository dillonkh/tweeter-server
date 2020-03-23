package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class UserService {

    private static UserService instance;

    private User currentUser;
    private User userShown;

    private final ServerFacade serverFacade;

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    private UserService() {
//        // TODO: Remove when the actual login functionality exists.
//        currentUser = new User("Test", "User",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        setCurrentUser(currentUser);
        serverFacade = new ServerFacade();
    }

    public UserResponse getCurrentUser() {
//        CurrentUserRequest request = new CurrentUserRequest();
//        UserResponse response = serverFacade.getCurrentUser(request);
        if (currentUser == null) {
            return null;
        }
        return new UserResponse(currentUser);
    }

    public UserResponse setCurrentUser(UserRequest userRequest) {
//        UserResponse response = serverFacade.setCurrentUser(userRequest);
            currentUser = userRequest.getUser();

        return new UserResponse(currentUser);
    }

    public UserResponse getUserShown(CurrentUserRequest userRequest) {
//        UserResponse response = serverFacade.getUserShown(userRequest);
//        return response;
        return new UserResponse(userShown);
    }

    public UserResponse setUserShown(UserRequest userRequest) {
        userShown = userRequest.getUser();

        return new UserResponse(userShown);
    }
}
