package edu.byu.cs.tweeter.server.model.response;


import edu.byu.cs.tweeter.server.model.domain.User;

public class LoginResponse {

    public boolean isAuthentcated;
    public User userSignedIn;
    public String authToken;


    public LoginResponse(boolean isAuthentcated, User userSignedIn, String authToken) {
        this.isAuthentcated = isAuthentcated;
        this.userSignedIn = userSignedIn;
        this.authToken = authToken;
    }

    public LoginResponse() {
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
