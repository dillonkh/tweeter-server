package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class UserRequest {

    private final User user;
    private final String handle;

    public UserRequest(User user, String handle) {
        this.user = user;
        this.handle = handle;
    }

    public User getUser() {
        return user;
    }

    public String getHandle() {
        return handle;
    }
}