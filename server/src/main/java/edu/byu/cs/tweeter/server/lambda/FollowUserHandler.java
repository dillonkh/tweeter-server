package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.model.service.FeedService;
import edu.byu.cs.tweeter.server.model.service.FollowService;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class FollowUserHandler implements RequestHandler<FollowRequest, FollowResponse> {

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
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowService service = FollowService.getInstance();
        return service.followUser(request);
    }


}