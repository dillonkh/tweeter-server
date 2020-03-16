package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.Tweet;

public class TweetRequest {

    private Tweet tweet;

    public TweetRequest(Tweet tweet) {
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }
}
