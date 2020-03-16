package edu.byu.cs.tweeter.server.dao.response;


import java.util.List;

import edu.byu.cs.tweeter.server.model.domain.Tweet;

public class StoryResponse extends PagedResponse {

    private List<Tweet> tweets;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Tweet> tweets, boolean hasMorePages) {
        super(true, hasMorePages);
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
