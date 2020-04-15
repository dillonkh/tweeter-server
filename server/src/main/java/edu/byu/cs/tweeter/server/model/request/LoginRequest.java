package edu.byu.cs.tweeter.server.model.request;

public class LoginRequest {

    public String password;
    public String handle;

    public LoginRequest(String password, String handle) {
        this.password = password;
        this.handle = handle;
    }

    public LoginRequest() {
    }

    public String getPassword() {
        return password;
    }

    public String getHandle() {
        return handle;
    }
}
