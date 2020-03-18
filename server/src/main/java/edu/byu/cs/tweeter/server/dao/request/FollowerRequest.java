package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.User;

public class FollowerRequest {

    public User follower;
    public int limit;
    public User lastFollowee;

    public FollowerRequest(User follower, int limit, User lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    public FollowerRequest() {
    }

    public User getFollowing() {
        return follower;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollowee() {
        return lastFollowee;
    }
}
