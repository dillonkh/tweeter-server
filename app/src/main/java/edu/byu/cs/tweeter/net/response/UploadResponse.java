package edu.byu.cs.tweeter.net.response;

public class UploadResponse {
    private boolean success;

    public UploadResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
