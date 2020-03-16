package edu.byu.cs.tweeter.server.dao.response;


import edu.byu.cs.tweeter.server.model.domain.User;

public class LoginResponse {

    private boolean isAuthentcated;
    private User userSignedIn;
    private String authToken;


    public LoginResponse(boolean isAuthentcated, User userSignedIn, String authToken) {
        this.isAuthentcated = isAuthentcated;
        this.userSignedIn = userSignedIn;
        this.authToken = authToken;
    }

    public boolean isAuthentcated() {
        return isAuthentcated;
    }

    public User getUserSignedIn() {
        return userSignedIn;
    }

    public String getAuthToken() {
        return authToken;
    }
}