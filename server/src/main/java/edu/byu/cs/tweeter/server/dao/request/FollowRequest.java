package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class FollowRequest {

    public User userToFollow;
    public User userLoggedIn;


    public FollowRequest(User userToFollow, User userLoggedIn) {
        this.userToFollow = userToFollow;
        this.userLoggedIn = userLoggedIn;
    }

    public FollowRequest() {
    }

    public User getUserToFollow() {
        return userToFollow;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }
}
