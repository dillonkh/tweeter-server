package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.server.lambda.UploadImageHandler;
import edu.byu.cs.tweeter.server.model.request.UploadRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.response.UploadResponse;
import edu.byu.cs.tweeter.server.model.response.UserResponse;

class UserDAOTest {


    @Test
    void testGetUser() {
        UserRequest r = new UserRequest(null, "@dillonkh");

        UserResponse resp = new UserDAO().getUser(r);

        Assertions.assertTrue(resp.getUser() != null);

    }

}
