package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class UserService {

    private static UserService instance;

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

    public User getUserShown() {
        CurrentUserRequest request = new CurrentUserRequest();
        UserResponse response = serverFacade.getUserShown(request);
        return response.getUser();
    }

    public void setCurrentUser(User user) {
        UserRequest request = new UserRequest(user);
        UserResponse response = serverFacade.setCurrentUser(request);
    }
}
