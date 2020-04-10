package edu.byu.cs.tweeter.model;

import edu.byu.cs.tweeter.model.domain.User;

public class SessionManager {

    private static SessionManager instance;

    private String UserLoggedInAuthToken;
    private User userShown;
    private User userLoggedIn;

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    public String getUserLoggedInAuthToken() {
        return UserLoggedInAuthToken;
    }

    public void setUserLoggedInAuthToken(String userLoggedInAuthToken) {
        UserLoggedInAuthToken = userLoggedInAuthToken;
    }

    public User getUserShown() {
        return userShown;
    }

    public void setUserShown(User userShown) {
        this.userShown = userShown;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }
}
