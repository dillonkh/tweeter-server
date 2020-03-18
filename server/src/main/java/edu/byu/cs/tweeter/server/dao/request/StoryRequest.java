package edu.byu.cs.tweeter.server.dao.request;


import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;

public class StoryRequest {

    public User user;
    public int limit;
    public Tweet lastTweet;

    public StoryRequest(User user, int limit, Tweet lastTweet) {
        this.user = user;
        this.limit = limit;
        this.lastTweet = lastTweet;
    }

    public StoryRequest() {
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
