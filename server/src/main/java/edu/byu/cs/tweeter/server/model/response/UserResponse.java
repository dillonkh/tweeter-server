package edu.byu.cs.tweeter.server.model.response;


import edu.byu.cs.tweeter.server.model.domain.User;

public class UserResponse {

    public User user;

    public UserResponse(User user) {
//        super(true, hasMorePages);
        this.user = user;
    }

    public UserResponse() {}

    public User getUser() {
        return user;
    }
}
