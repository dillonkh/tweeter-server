package edu.byu.cs.tweeter.server.dao.response;

public class IsFollowingResponse {

    private boolean success;

    public IsFollowingResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
