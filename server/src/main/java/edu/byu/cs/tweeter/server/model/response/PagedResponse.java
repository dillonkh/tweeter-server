package edu.byu.cs.tweeter.server.model.response;

public class PagedResponse extends Response {

    public boolean hasMorePages;

    PagedResponse(boolean success, boolean hasMorePages) {
        super(success);
        this.hasMorePages = hasMorePages;
    }

    PagedResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message);
        this.hasMorePages = hasMorePages;
    }

    PagedResponse() {}



    public boolean hasMorePages() {
        return hasMorePages;
    }
}
