package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.testing.ServerFacade;
import edu.byu.cs.tweeter.server.dao.TweetsDAO;
import edu.byu.cs.tweeter.server.model.request.FeedRequest;
import edu.byu.cs.tweeter.server.model.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.server.model.response.FeedResponse;

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
        FeedResponse r = new TweetsDAO().getFeed(request);
        return r;
    }

    public void updateFeeds(UpdateFeedsRequest updateFeedsRequest) {
        new TweetsDAO().updateFeeds(updateFeedsRequest);
    }
}
