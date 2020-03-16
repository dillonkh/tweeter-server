package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.server.dao.request.CurrentUserRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.model.service.FeedService;
import edu.byu.cs.tweeter.server.model.service.LoginService;


/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetCurrentUserHandler implements RequestHandler<CurrentUserRequest, UserResponse> {

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
    public UserResponse handleRequest(CurrentUserRequest request, Context context) {
        LoginService service = LoginService.getInstance();
        return service.getCurrentUser(request);
    }


}
