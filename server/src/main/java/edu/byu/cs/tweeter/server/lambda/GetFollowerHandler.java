package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.model.request.FollowerRequest;
import edu.byu.cs.tweeter.server.model.response.FollowerResponse;
import edu.byu.cs.tweeter.server.model.service.FollowerService;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetFollowerHandler implements RequestHandler<FollowerRequest, FollowerResponse> {

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
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        FollowerService service = FollowerService.getInstance();
        return service.getFollowers(request);
    }
}
