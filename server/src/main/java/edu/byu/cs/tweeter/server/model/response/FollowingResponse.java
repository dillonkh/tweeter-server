package edu.byu.cs.tweeter.server.model.response;

import java.util.List;

import edu.byu.cs.tweeter.server.model.domain.User;


public class FollowingResponse extends PagedResponse {

    public List<User> followees;

    public FollowingResponse(String message) {
        super(false, message, false);
    }

    public FollowingResponse(List<User> followees, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followees = followees;
    }

    public FollowingResponse(){}

    public List<User> getFollowees() {
        return followees;
    }
}
