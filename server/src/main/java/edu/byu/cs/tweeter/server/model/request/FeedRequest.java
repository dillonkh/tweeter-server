package edu.byu.cs.tweeter.server.model.request;


import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;

public class FeedRequest {

    public User user;
    public int limit;
    public Tweet lastTweet;

    public FeedRequest(User user, int limit, Tweet lastTweet) {
        this.user = user;
        this.limit = limit;
        this.lastTweet = lastTweet;
    }

    public FeedRequest() {
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public Tweet getLastTweet() {
        return lastTweet;
    }
}
