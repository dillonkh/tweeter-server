package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.UserResponse;

class AuthenticatedUsersDAOTest {


    @Test
    void testSetAuthToken() {
        String token = new AuthenticatedUsersDAO().setAuthToken("@hp");

        Assertions.assertNotNull(token);

    }

    @Test
    void testGetAuthToken() {
        String token = new AuthenticatedUsersDAO().getAuthToken("@hp");

        Assertions.assertNotNull(token);

    }
}
