package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class IsFollowingRequest {

    private final User userLoggedIn;
    private final User userShown;

    public IsFollowingRequest(User userLoggedIn, User userShown) {
        this.userLoggedIn = userLoggedIn;
        this.userShown = userShown;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public User getUserShown() {
        return userShown;
    }
}
