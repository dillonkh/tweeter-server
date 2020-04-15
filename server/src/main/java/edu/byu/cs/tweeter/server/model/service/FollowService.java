package edu.byu.cs.tweeter.server.model.service;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.testing.ServerFacade;
import edu.byu.cs.tweeter.server.model.request.FollowRequest;
import edu.byu.cs.tweeter.server.model.request.IsFollowingRequest;
import edu.byu.cs.tweeter.server.model.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.model.response.FollowResponse;
import edu.byu.cs.tweeter.server.model.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.model.response.UnFollowResponse;

public class FollowService {

    private static FollowService instance;

    private final ServerFacade serverFacade;

    private final FollowingDAO followingDAO;

    public static FollowService getInstance() {
        if(instance == null) {
            instance = new FollowService();
        }

        return instance;
    }

    private FollowService() {

        serverFacade = new ServerFacade();
        followingDAO = new FollowingDAO();
    }


    public FollowResponse followUser(FollowRequest request) {
        if (AuthenticationService.getInstance().isAuthenticated(request.getUserLoggedIn().alias)) {
            FollowResponse response = followingDAO.followUser(request);
            return response;
        }
        else {
            return null;
        }

    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {
        if (AuthenticationService.getInstance().isAuthenticated(request.getUserLoggedIn().alias)) {
            UnFollowResponse response = followingDAO.unfollowUser(request);
            return response;
        }
        else {
            return null;
        }


    }

    public IsFollowingResponse isFollowing(IsFollowingRequest request) {
        if (AuthenticationService.getInstance().isAuthenticated(request.getUserLoggedIn().alias)) {
            IsFollowingResponse response = followingDAO.isFollowing(request);
            return response;
        }
        else {
            return null;
        }
    }

}
