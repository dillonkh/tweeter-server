package edu.byu.cs.tweeter.server.dao.response;

public class TweetResponse {

    private boolean sent;

    public TweetResponse(boolean sent) {
        this.sent = sent;
    }

    public boolean isSent() {
        return sent;
    }
}
