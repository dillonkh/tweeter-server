package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.IsFollowingRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.IsFollowingResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;

public class IsFollowingTask extends AsyncTask<IsFollowingRequest, Void, IsFollowingResponse> {

    private final MainPresenter presenter;
    private final IsFollowingObserver observer;

    public interface IsFollowingObserver {
        void isFollowingResponded(IsFollowingResponse response);
    }

    public IsFollowingTask(MainPresenter presenter, IsFollowingObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected IsFollowingResponse doInBackground(IsFollowingRequest... isFollowingRequests) {
        IsFollowingResponse response = presenter.isFollowing(isFollowingRequests[0]);

        return response;
    }

    @Override
    protected void onPostExecute(IsFollowingResponse isFollowingResponse) {

        observer.isFollowingResponded(isFollowingResponse);

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
