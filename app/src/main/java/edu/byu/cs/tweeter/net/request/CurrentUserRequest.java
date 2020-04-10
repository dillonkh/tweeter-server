package edu.byu.cs.tweeter.net.request;


public class CurrentUserRequest {

    private final String authToken;

    public CurrentUserRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

}
