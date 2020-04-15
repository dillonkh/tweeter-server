package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;


public class GetUserTask extends AsyncTask<UserRequest, Void, UserResponse> {

    private final GetUserTask.GetUserObserver observer;

    public interface GetUserObserver {
        void userRetrieved(UserResponse userResponse);
    }

    private final StoryPresenter storyPresenter;
    private final FeedPresenter feedPresenter;
    private final FollowerPresenter followerPresenter;
    private final FollowingPresenter followingPresenter;
    private final MainPresenter mainPresenter;


    private final FragmentActivity activity;

    public GetUserTask(FeedPresenter presenter, FragmentActivity activity, GetUserTask.GetUserObserver observer) {
        this.feedPresenter = presenter;
        this.activity = activity;

        this.observer = observer;

        storyPresenter = null;
        followerPresenter = null;
        followingPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(StoryPresenter presenter, FragmentActivity activity, GetUserTask.GetUserObserver observer) {
        this.storyPresenter = presenter;
        this.activity = activity;

        this.observer = observer;

        feedPresenter = null;
        followerPresenter = null;
        followingPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(FollowerPresenter presenter, FragmentActivity activity, GetUserTask.GetUserObserver observer) {
        this.followerPresenter = presenter;
        this.activity = activity;

        this.observer = observer;

        feedPresenter = null;
        storyPresenter = null;
        followingPresenter = null;
        mainPresenter = null;
    }
//
    public GetUserTask(FollowingPresenter presenter, FragmentActivity activity, GetUserTask.GetUserObserver observer) {
        this.followingPresenter = presenter;
        this.activity = activity;

        this.observer = observer;

        feedPresenter = null;
        storyPresenter = null;
        followerPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(MainPresenter presenter, FragmentActivity activity, GetUserTask.GetUserObserver observer) {
        this.mainPresenter = presenter;
        this.activity = activity;

        this.observer = observer;

        feedPresenter = null;
        storyPresenter = null;
        followerPresenter = null;
        followingPresenter = null;
    }

    private Presenter getActivePresenter() {
        if (storyPresenter != null) {
            return storyPresenter;
        }
        else if (feedPresenter != null) {
            return feedPresenter;
        }
        else if (followerPresenter != null) {
            return followerPresenter;
        }
        else if (followingPresenter != null) {
            return followingPresenter;
        }
        else if (mainPresenter != null) {
            return mainPresenter;
        }
        else {
            return null;
        }
    }


    @Override
    protected UserResponse doInBackground(UserRequest... userRequests) {
        UserResponse response;
        response = getActivePresenter().getUser(userRequests[0]);

        return response;
    }

    @Override
    protected void onPostExecute(UserResponse userResponse) {

        observer.userRetrieved(userResponse);

//        if (userResponse != null) {
//            getActivePresenter().setShownUser(new UserRequest(userResponse.getUser()));
//            User u = getActivePresenter().getUserShown();
//            Intent intent = new Intent(activity, UserViewActivity.class);
//            activity.startActivity(intent);
//        }

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
