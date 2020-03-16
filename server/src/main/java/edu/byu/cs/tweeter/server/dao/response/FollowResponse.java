package edu.byu.cs.tweeter.server.dao.response;

public class FollowResponse {

    private boolean success;

    public FollowResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
