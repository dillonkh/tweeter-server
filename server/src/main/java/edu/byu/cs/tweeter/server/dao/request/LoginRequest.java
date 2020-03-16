package edu.byu.cs.tweeter.server.dao.request;

public class LoginRequest {

    private final String password;
    private final String handle;

    public LoginRequest(String password, String handle) {
        this.password = password;
        this.handle = handle;
    }

    public String getPassword() {
        return password;
    }

    public String getHandle() {
        return handle;
    }
}
