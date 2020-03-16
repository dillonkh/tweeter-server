package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

public class UserService {

    private static UserService instance;

    private User userShown;

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
    }

    public UserResponse getUserShown() {
        return new UserResponse(userShown);
    }

    public void setCurrentUser(User user) {
        this.userShown = user;
    }
}
