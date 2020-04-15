package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.FollowerDAO;
import edu.byu.cs.tweeter.server.testing.ServerFacade;
import edu.byu.cs.tweeter.server.model.request.FollowerRequest;
import edu.byu.cs.tweeter.server.model.response.FollowerResponse;

public class FollowerService {

    private static FollowerService instance;

    private final ServerFacade serverFacade;

    public static FollowerService getInstance() {
        if(instance == null) {
            instance = new FollowerService();
        }

        return instance;
    }

    private FollowerService() {
        serverFacade = new ServerFacade();
    }

    public FollowerResponse getFollowers(FollowerRequest request) {
//        return serverFacade.getFollowers(request);
        return new FollowerDAO().getFollowers(request);
    }

//    public UserResponse getUser(UserRequest request) {
////        UserResponse r = serverFacade.getUser(request);
//        UserResponse r = new UserDAO().getUser(request);
//        return r;
//    }
}
