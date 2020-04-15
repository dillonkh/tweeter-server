package edu.byu.cs.tweeter.server.model.response;


import java.util.List;

import edu.byu.cs.tweeter.server.model.domain.Tweet;

public class StoryResponse extends PagedResponse {

    public List<Tweet> tweets;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Tweet> tweets, boolean hasMorePages) {
        super(true, hasMorePages);
        this.tweets = tweets;
    }

    public StoryResponse(){}

    public List<Tweet> getTweets() {
        return tweets;
    }
}
