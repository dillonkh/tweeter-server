package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.model.request.CurrentUserRequest;
import edu.byu.cs.tweeter.server.model.request.LoginRequest;
import edu.byu.cs.tweeter.server.model.request.SignUpRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

public class LoginService {

    private static LoginService instance;

//    private final ServerFacade serverFacade;
    private final UserDAO userDAO;
    private User currentUser;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    private LoginService() {
        userDAO = new UserDAO();
    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse r = userDAO.login(request);

        if (r != null) {
            if (r.isAuthentcated) {
                String token = AuthenticationService.getInstance().authorize(r.userSignedIn.alias);
                r.authToken = token;
            }
            return r;

        }
        else {
            return new LoginResponse(false,null,null);
        }

    }

    public LoginResponse signUp(SignUpRequest request) {
        LoginResponse r = userDAO.signUp(request);

        if (r != null) {
            if (r.isAuthentcated) {
                String token = AuthenticationService.getInstance().authorize(r.userSignedIn.alias);
                r.authToken = token;
            }
            return r;
        }
        else {
            return new LoginResponse(false, null, null);
        }
    }
}
