package edu.byu.cs.tweeter.server.model.response;

import java.util.List;

import edu.byu.cs.tweeter.server.model.domain.Tweet;

public class FeedResponse extends PagedResponse {

    public List<Tweet> tweets;

    public FeedResponse(String message) {
        super(false, message, false);
    }

    public FeedResponse(List<Tweet> tweets, boolean hasMorePages) {
        super(true, hasMorePages);
        this.tweets = tweets;
    }

    public FeedResponse() {}



    public List<Tweet> getTweets() {
        return tweets;
    }
}
