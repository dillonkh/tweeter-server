package edu.byu.cs.tweeter.view.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.main.UserViewActivity;


public class GetCurrentUserTask extends AsyncTask<CurrentUserRequest, Void, UserResponse> {

    private final GetCurrentUserTask.GetCurrentUserObserver observer;

    public interface GetCurrentUserObserver {
        void currentUserGot(UserResponse userResponse);
    }

    private final StoryPresenter storyPresenter;
    private final FeedPresenter feedPresenter;
    private final FollowerPresenter followerPresenter;
    private final FollowingPresenter followingPresenter;
    private final MainPresenter mainPresenter;


    private final FragmentActivity activity;
    private final User user;
    private final String userHandle;

//    public GetCurrentUserTask(FeedPresenter presenter, FragmentActivity activity, User user, String userHandle, GetCurrentUserTask.GetCurrentUserObserver observer) {
//        this.feedPresenter = presenter;
//        this.activity = activity;
//        this.user = user;
//        this.userHandle = userHandle;
//
//        this.observer = observer;
//
//        storyPresenter = null;
//        followerPresenter = null;
//        followingPresenter = null;
//        mainPresenter = null;
//    }
//
//    public GetCurrentUserTask(StoryPresenter presenter, FragmentActivity activity, User user, String userHandle, GetCurrentUserTask.GetCurrentUserObserver observer) {
//        this.storyPresenter = presenter;
//        this.activity = activity;
//        this.user = user;
//        this.userHandle = userHandle;
//
//        this.observer = observer;
//
//
//        feedPresenter = null;
//        followerPresenter = null;
//        followingPresenter = null;
//        mainPresenter = null;
//    }
//
//    public GetCurrentUserTask(FollowerPresenter presenter, FragmentActivity activity, User user, String userHandle, GetCurrentUserTask.GetCurrentUserObserver observer) {
//        this.followerPresenter = presenter;
//        this.activity = activity;
//        this.user = user;
//        this.userHandle = userHandle;
//
//        this.observer = observer;
//
//
//        feedPresenter = null;
//        storyPresenter = null;
//        followingPresenter = null;
//        mainPresenter = null;
//    }
//
//    public GetCurrentUserTask(FollowingPresenter presenter, FragmentActivity activity, User user, String userHandle, GetCurrentUserTask.GetCurrentUserObserver observer) {
//        this.followingPresenter = presenter;
//        this.activity = activity;
//        this.user = user;
//        this.userHandle = userHandle;
//
//        this.observer = observer;
//
//
//        feedPresenter = null;
//        storyPresenter = null;
//        followerPresenter = null;
//        mainPresenter = null;
//    }

    public GetCurrentUserTask(MainPresenter presenter, FragmentActivity activity, GetCurrentUserTask.GetCurrentUserObserver observer) {
        this.mainPresenter = presenter;
        this.activity = activity;

        this.observer = observer;


        user = null;
        userHandle = null;
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
    protected UserResponse doInBackground(CurrentUserRequest... currentUserRequests) {
        UserResponse response;
        response = getActivePresenter().getCurrentUser(currentUserRequests[0]);

        return response;
    }

    @Override
    protected void onPostExecute(UserResponse userResponse) {

        observer.currentUserGot(userResponse);

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
