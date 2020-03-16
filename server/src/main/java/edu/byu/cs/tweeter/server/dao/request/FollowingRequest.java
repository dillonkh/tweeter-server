package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class FollowingRequest {

    public User follower = null;
    public int limit = 0;
    public User lastFollowee = null;

    public FollowingRequest(User follower, int limit, User lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    public FollowingRequest() {
    }

    public User getFollower() {
        return follower;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollowee() {
        return lastFollowee;
    }
}
