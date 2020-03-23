package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.TweetRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.TweetResponse;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.service.FollowService;
import edu.byu.cs.tweeter.server.model.service.StoryService;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class AddTweetHandler implements RequestHandler<TweetRequest, TweetResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public TweetResponse handleRequest(TweetRequest request, Context context) {
        StoryService service = StoryService.getInstance();
        TweetResponse response = service.addTweet(request);

        return response;
    }


}
