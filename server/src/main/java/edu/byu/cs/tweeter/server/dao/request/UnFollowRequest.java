package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class UnFollowRequest {

    public User userToFollow;
    public User userLoggedIn;


    public UnFollowRequest(User userToFollow, User userLoggedIn) {
        this.userToFollow = userToFollow;
        this.userLoggedIn = userLoggedIn;
    }

    public UnFollowRequest() {
    }

    public User getUserToFollow() {
        return userToFollow;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }
}
