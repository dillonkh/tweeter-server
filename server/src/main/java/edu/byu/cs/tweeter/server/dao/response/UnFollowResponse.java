package edu.byu.cs.tweeter.server.dao.response;

public class UnFollowResponse {

    private boolean success;

    public UnFollowResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
