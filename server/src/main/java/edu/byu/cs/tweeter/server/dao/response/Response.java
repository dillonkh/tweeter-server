package edu.byu.cs.tweeter.server.dao.response;

class Response {

    public boolean success;
    public String message;

    Response(boolean success) {
        this(success, null);
    }

    Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
