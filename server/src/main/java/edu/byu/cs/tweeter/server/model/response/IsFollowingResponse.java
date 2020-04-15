package edu.byu.cs.tweeter.server.model.response;

public class IsFollowingResponse {

    public boolean success;

    public IsFollowingResponse(boolean success) {
        this.success = success;
    }

    public IsFollowingResponse() {
    }

    public boolean isSuccess() {
        return success;
    }
}
