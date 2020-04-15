package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.AuthenticatedUsersDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.model.request.LoginRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

public class UserService {

    private static UserService instance;

    private User userShown;

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    private UserService() {}

//    public UserResponse getUserShown() {
//        return new UserResponse(userShown);
//    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = new UserDAO().getUser(request);
        return r;
    }

    public LoginResponse signOut(LoginRequest request) {
        LoginResponse r = new AuthenticatedUsersDAO().removeRow(request.getHandle());
        return r;
    }

}
