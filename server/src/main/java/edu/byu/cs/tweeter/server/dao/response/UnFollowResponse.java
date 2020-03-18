package edu.byu.cs.tweeter.server.dao.response;

public class UnFollowResponse {

    public boolean success;

    public UnFollowResponse(boolean success) {
        this.success = success;
    }

    public UnFollowResponse() {
    }

    public boolean isSuccess() {
        return success;
    }
}
