package edu.byu.cs.tweeter.server.model.service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.byu.cs.tweeter.server.dao.AuthenticatedUsersDAO;

public class AuthenticationService {

    private static AuthenticationService instance;

    public static AuthenticationService getInstance() {
        if(instance == null) {
            instance = new AuthenticationService();
        }

        return instance;
    }

    private AuthenticationService() {}

    public boolean isAuthenticated(String userAlias) {
        if (getAuthToken(userAlias) != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getAuthToken(String userALias) {
        return new AuthenticatedUsersDAO().getAuthToken(userALias);
    }

    public String authorize(String userAlias) {
        return new AuthenticatedUsersDAO().setAuthToken(userAlias);
    }


}
