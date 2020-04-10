package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.main.UserViewActivity;


//public class GetUserShownTask extends AsyncTask<CurrentUserRequest, Void, User> {
public class GetUserShownTask {

//    private final GetUserShownTask.GetUserShownObserver observer;
//
//    public interface GetUserShownObserver {
//        void userShownGot(User user);
//    }
//
//    private final StoryPresenter storyPresenter;
//    private final FeedPresenter feedPresenter;
//    private final FollowerPresenter followerPresenter;
//    private final FollowingPresenter followingPresenter;
//    private final MainPresenter mainPresenter;
//    private final UserViewActivity userViewActivity;
//
//
//    private final FragmentActivity activity;
//
//    public GetUserShownTask(FeedPresenter presenter, FragmentActivity activity, GetUserShownTask.GetUserShownObserver observer) {
//        this.feedPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        this.storyPresenter = null;
//        this.followerPresenter = null;
//        this.followingPresenter = null;
//        this.mainPresenter = null;
//        this.userViewActivity = null;
//    }
//
//    public GetUserShownTask(StoryPresenter presenter, FragmentActivity activity, GetUserShownTask.GetUserShownObserver observer) {
//        this.storyPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        this.feedPresenter = null;
//        this.followerPresenter = null;
//        this.followingPresenter = null;
//        this.mainPresenter = null;
//        this.userViewActivity = null;
//    }
//
//    public GetUserShownTask(FollowerPresenter presenter, FragmentActivity activity, GetUserShownTask.GetUserShownObserver observer) {
//        this.followerPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        this.feedPresenter = null;
//        this.storyPresenter = null;
//        this.followingPresenter = null;
//        this.mainPresenter = null;
//        this.userViewActivity = null;
//    }
//
//    public GetUserShownTask(FollowingPresenter presenter, FragmentActivity activity, GetUserShownTask.GetUserShownObserver observer) {
//        this.followingPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        this.feedPresenter = null;
//        this.storyPresenter = null;
//        this.followerPresenter = null;
//        this.mainPresenter = null;
//        this.userViewActivity = null;
//    }
//
//    public GetUserShownTask(MainPresenter presenter, FragmentActivity activity, GetUserShownTask.GetUserShownObserver observer) {
//        this.mainPresenter = presenter;
//        this.activity = activity;
//
//        this.observer = observer;
//
//        this.feedPresenter = null;
//        this.storyPresenter = null;
//        this.followerPresenter = null;
//        this.followingPresenter = null;
//        this.userViewActivity = null;
//    }
//
//    public GetUserShownTask(MainPresenter presenter, UserViewActivity activity, GetUserShownTask.GetUserShownObserver observer) {
//        this.mainPresenter = presenter;
//        this.userViewActivity = activity;
//        this.observer = observer;
//
//        this.feedPresenter = null;
//        this.storyPresenter = null;
//        this.followerPresenter = null;
//        this.followingPresenter = null;
//        this.activity = null;
//    }
//
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
//    protected User doInBackground(CurrentUserRequest... userRequests) {
//
//        return getActivePresenter().getUserShown(userRequests[0]);
//    }
//
//    @Override
//    protected void onPostExecute(User user) {
//
//        observer.userShownGot(user);
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
