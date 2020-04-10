package edu.byu.cs.tweeter.server.model.service;


import edu.byu.cs.tweeter.server.dao.ServerFacade;
import edu.byu.cs.tweeter.server.dao.TweetsDAO;
import edu.byu.cs.tweeter.server.dao.request.StoryRequest;
import edu.byu.cs.tweeter.server.dao.request.TweetRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.response.TweetResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;

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
//        StoryResponse r = serverFacade.getStory(request);
        StoryResponse r = new TweetsDAO().getStory(request);
        return r;
    }

    public TweetResponse addTweet(TweetRequest request) throws RuntimeException {
//        return serverFacade.addTweet(request);
        return new TweetsDAO().addTweet(request);
    }

//    public UserResponse getUser(UserRequest request) {
//        UserResponse r = serverFacade.getUser(request);
//        return r;
//    }
}
