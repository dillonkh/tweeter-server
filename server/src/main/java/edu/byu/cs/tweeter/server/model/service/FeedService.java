package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.ServerFacade;
import edu.byu.cs.tweeter.server.dao.request.FeedRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;

public class FeedService {

    private static FeedService instance;

    private final ServerFacade serverFacade;

    public static FeedService getInstance() {
        if(instance == null) {
            instance = new FeedService();
        }

        return instance;
    }

    private FeedService() {
        serverFacade = new ServerFacade();
    }

    public FeedResponse getFeed(FeedRequest request) {
        FeedResponse r = serverFacade.getFeed(request);
        return r;
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
