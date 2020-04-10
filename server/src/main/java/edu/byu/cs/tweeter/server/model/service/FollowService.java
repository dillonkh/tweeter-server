package edu.byu.cs.tweeter.server.model.service;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.ServerFacade;
import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.IsFollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

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
//        FollowResponse response = serverFacade.followUser(request);
        FollowResponse response = followingDAO.followUser(request);
        return response;

    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {
//        UnFollowResponse response = serverFacade.unfollowUser(request);
        UnFollowResponse response = followingDAO.unfollowUser(request);
        return response;

    }

//    public boolean isFollowing(User userLoggedIn, User userShown) {
//        return serverFacade.isFollowing(userLoggedIn,userShown);
//    }
    public IsFollowingResponse isFollowing(IsFollowingRequest request) {
        return serverFacade.isFollowing(request);
    }

}
