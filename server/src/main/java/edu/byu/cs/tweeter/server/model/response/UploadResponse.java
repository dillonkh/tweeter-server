package edu.byu.cs.tweeter.server.model.response;

public class UploadResponse {

    public boolean success;

    public UploadResponse(boolean success) {
        this.success = success;
    }

    public UploadResponse() {
    }

    public boolean isSuccess() {
        return success;
    }
}
