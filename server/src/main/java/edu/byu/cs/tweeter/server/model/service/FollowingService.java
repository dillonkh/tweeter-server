package edu.byu.cs.tweeter.server.model.service;

import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.testing.ServerFacade;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.request.FollowingRequest;
import edu.byu.cs.tweeter.server.model.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.server.model.response.FollowingResponse;

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
        return new FollowingDAO().getFollowees(request);
    }
    public UpdateFeedsRequest getFolloweesMessage(FollowingRequest request, Tweet tweet) {
        return new FollowingDAO().getFolloweesMessage(request, tweet);
    }
}
