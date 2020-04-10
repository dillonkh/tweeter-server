package edu.byu.cs.tweeter.view.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.main.UserViewActivity;


//public class SetUserShownTask extends AsyncTask<UserRequest, Void, User> {
public class SetUserShownTask{


//    private final SetUserShownTask.SetUserShownObserver observer;
//
//    public interface SetUserShownObserver {
//        void userShownSet(User user);
//    }
//
//    private final StoryPresenter storyPresenter;
//    private final FeedPresenter feedPresenter;
//    private final FollowerPresenter followerPresenter;
//    private final FollowingPresenter followingPresenter;
//    private final MainPresenter mainPresenter;
//
//
//    private final FragmentActivity activity;
//
//
//    public SetUserShownTask(FeedPresenter presenter, FragmentActivity activity, SetUserShownTask.SetUserShownObserver observer) {
//        this.feedPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        storyPresenter = null;
//        followerPresenter = null;
//        followingPresenter = null;
//        mainPresenter = null;
//    }
//
//    public SetUserShownTask(StoryPresenter presenter, FragmentActivity activity, SetUserShownTask.SetUserShownObserver observer) {
//        this.storyPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        feedPresenter = null;
//        followerPresenter = null;
//        followingPresenter = null;
//        mainPresenter = null;
//    }
//
//    public SetUserShownTask(FollowerPresenter presenter, FragmentActivity activity, SetUserShownTask.SetUserShownObserver observer) {
//        this.followerPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        feedPresenter = null;
//        storyPresenter = null;
//        followingPresenter = null;
//        mainPresenter = null;
//    }
//
//    public SetUserShownTask(FollowingPresenter presenter, FragmentActivity activity, SetUserShownTask.SetUserShownObserver observer) {
//        this.followingPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        feedPresenter = null;
//        storyPresenter = null;
//        followerPresenter = null;
//        mainPresenter = null;
//    }
//
//    public SetUserShownTask(MainPresenter presenter, FragmentActivity activity, SetUserShownTask.SetUserShownObserver observer) {
//        this.mainPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        feedPresenter = null;
//        storyPresenter = null;
//        followerPresenter = null;
//        followingPresenter = null;
//    }
//
//    private Presenter getActivePresenter() {
//        if (storyPresenter != null) {
//            return storyPresenter;
//        }
//        else if (feedPresenter != null) {
//            return feedPresenter;
//        }
//        else if (followerPresenter != null) {
//            return followerPresenter;
//        }
//        else if (followingPresenter != null) {
//            return followingPresenter;
//        }
//        else if (mainPresenter != null) {
//            return mainPresenter;
//        }
//        else {
//            return null;
//        }
//    }
//
//
//    @Override
//    protected User doInBackground(UserRequest... userRequests) {
//
//        return getActivePresenter().setShownUser(userRequests[0]);
//    }
//
//    @Override
//    protected void onPostExecute(User user) {
//
//        observer.userShownSet(user);
//
////        if (userResponse != null) {
////            getActivePresenter().setShownUser(userResponse.getUser());
////            User u = getActivePresenter().getUserShown();
////            Intent intent = new Intent(activity, UserViewActivity.class);
////            activity.startActivity(intent);
////        }
////
//////        if(observer != null) {
//////            observer.followersRetrieved(followerResponse);
//////        }
//    }
}
