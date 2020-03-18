package edu.byu.cs.tweeter.server.dao.response;

public class FollowResponse {

    public boolean success;

    public FollowResponse(boolean success) {
        this.success = success;
    }

    public FollowResponse() {
    }

    public boolean isSuccess() {
        return success;
    }
}
