package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class IsFollowingRequest {

    public User userLoggedIn;
    public User userShown;

    public IsFollowingRequest(User userLoggedIn, User userShown) {
        this.userLoggedIn = userLoggedIn;
        this.userShown = userShown;
    }

    public IsFollowingRequest() {
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public User getUserShown() {
        return userShown;
    }
}
