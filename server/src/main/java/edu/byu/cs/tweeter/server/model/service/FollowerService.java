package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.ServerFacade;
import edu.byu.cs.tweeter.server.dao.request.FollowerRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;

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
        return serverFacade.getFollowers(request);
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
