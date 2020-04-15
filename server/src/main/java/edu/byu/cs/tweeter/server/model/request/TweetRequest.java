package edu.byu.cs.tweeter.server.model.request;


import edu.byu.cs.tweeter.server.model.domain.Tweet;

public class TweetRequest {

    public Tweet tweet;

    public TweetRequest(Tweet tweet) {
        this.tweet = tweet;
    }

    public TweetRequest() {
    }

    public Tweet getTweet() {
        return tweet;
    }
}
