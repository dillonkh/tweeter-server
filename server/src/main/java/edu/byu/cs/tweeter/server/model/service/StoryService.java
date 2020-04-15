package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.testing.ServerFacade;
import edu.byu.cs.tweeter.server.dao.TweetsDAO;
import edu.byu.cs.tweeter.server.model.request.StoryRequest;
import edu.byu.cs.tweeter.server.model.request.TweetRequest;
import edu.byu.cs.tweeter.server.model.response.StoryResponse;
import edu.byu.cs.tweeter.server.model.response.TweetResponse;

public class StoryService {

    private static StoryService instance;
    private final ServerFacade serverFacade;

    public static StoryService getInstance() {
        if(instance == null) {
            instance = new StoryService();
        }

        return instance;
    }

    private StoryService() {
        serverFacade = new ServerFacade();
    }

    public StoryResponse getTweets(StoryRequest request) {
        StoryResponse r = new TweetsDAO().getStory(request);
        return r;
    }

    public TweetResponse addTweet(TweetRequest request) throws RuntimeException {

        if (AuthenticationService.getInstance().isAuthenticated(request.getTweet().getUser())) {
            return new TweetsDAO().addTweet(request);
        }
        else {
            return new TweetResponse(false);
        }
    }

}
