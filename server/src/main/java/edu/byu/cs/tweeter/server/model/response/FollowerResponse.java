package edu.byu.cs.tweeter.server.model.response;

import java.util.List;

import edu.byu.cs.tweeter.server.model.domain.User;


public class FollowerResponse extends PagedResponse {

    public List<User> followers; // TODO: this may need to change

    public FollowerResponse(String message) {
        super(false, message, false);
    }

    public FollowerResponse(List<User> followees, boolean hasMorePages) { // TODO: this may need to change
        super(true, hasMorePages);
        this.followers = followees; // TODO: this may need to change
    }

    public FollowerResponse() {}


    public List<User> getFollowees() {
        return followers;
    } // TODO: this may need to change
}
