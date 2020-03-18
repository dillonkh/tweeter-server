package edu.byu.cs.tweeter.net.response;

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
