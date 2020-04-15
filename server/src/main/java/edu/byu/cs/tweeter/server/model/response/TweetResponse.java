package edu.byu.cs.tweeter.server.model.response;

public class TweetResponse {

    public boolean sent;

    public TweetResponse(boolean sent) {
        this.sent = sent;
    }

    public boolean isSent() {
        return sent;
    }
}
