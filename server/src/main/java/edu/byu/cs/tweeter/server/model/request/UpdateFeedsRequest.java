package edu.byu.cs.tweeter.server.model.request;


import java.util.List;

import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;

public class UpdateFeedsRequest {

    public List<String> userAliasList;
    public Tweet tweet;
    public boolean hasMore;
    public User lastFol;

    public UpdateFeedsRequest(List<String> userAliasList, Tweet tweet, boolean hasMore, User lastFol) {
        this.userAliasList = userAliasList;
        this.tweet = tweet;
        this.hasMore = hasMore;
        this.lastFol = lastFol;
    }

    public UpdateFeedsRequest() {}

    public List<String> getUserAliasList() {
        return userAliasList;
    }

    public void setUserAliasList(List<String> userAliasList) {
        this.userAliasList = userAliasList;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public User getLastFol() {
        return lastFol;
    }

    public void setLastFol(User lastFol) {
        this.lastFol = lastFol;
    }
}
