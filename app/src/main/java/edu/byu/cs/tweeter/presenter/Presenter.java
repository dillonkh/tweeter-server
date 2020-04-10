package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.model.services.UserService;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.UserResponse;

public abstract class Presenter {

//    private User shownUser;

    public UserResponse getCurrentUser(CurrentUserRequest request) {
        return UserService.getInstance().getCurrentUser(request);
    }

//    public User getUserShown(CurrentUserRequest request) {
//        return UserService.getInstance().getUserShown(request).getUser();
//    }
//
//    public User setShownUser(UserRequest userRequest) {
//        return UserService.getInstance().setUserShown(userRequest).getUser();
//    }

    public abstract UserResponse getUser(UserRequest request);
}
