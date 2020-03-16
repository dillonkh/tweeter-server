package edu.byu.cs.tweeter.server.model.service;

import edu.byu.cs.tweeter.server.dao.ServerFacade;
import edu.byu.cs.tweeter.server.dao.request.FollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;

public class FollowingService {

    private static FollowingService instance;

    private final ServerFacade serverFacade;

    public static FollowingService getInstance() {
        if(instance == null) {
            instance = new FollowingService();
        }

        return instance;
    }

    private FollowingService() {
        serverFacade = new ServerFacade();
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        return serverFacade.getFollowees(request);
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
