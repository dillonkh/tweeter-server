package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
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

    public UserResponse getUser(UserRequest request) {
        UserResponse r = new UserDAO().getUser(request);
        return r;
    }

    public void setCurrentUser(User user) { // this will probably use the auth token in an authorized user table
        this.userShown = user;
    }
}
