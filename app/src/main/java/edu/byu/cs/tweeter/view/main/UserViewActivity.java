package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.SessionManager;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.UserService;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.IsFollowingRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.IsFollowingResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetCurrentUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetSendTweetTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUnFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserShownTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.IsFollowingTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.asyncTasks.SetUserShownTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;


public class UserViewActivity extends AppCompatActivity implements
        LoadImageTask.LoadImageObserver,
        MainPresenter.View,
        GetFollowTask.GetFollowObserver,
        GetUnFollowTask.GetUnFollowObserver,
        GetSendTweetTask.GetTweetObserver,
        GetUserTask.GetUserObserver,
//        GetUserShownTask.GetUserShownObserver,
        IsFollowingTask.IsFollowingObserver,
        GetCurrentUserTask.GetCurrentUserObserver
{

    private MainPresenter presenter;
    private User currentUserLoggedIn;
    private User userShown;
    private ImageView userImageView;
    private boolean following = true; // TODO: this should come from the user itself
    private StoryFragment storyFragment;

    private boolean isFollowing;
    Button followButton;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.userviewToolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        presenter = new MainPresenter(this);

//        getUserShown();

        ImageView optionDots = findViewById(R.id.optionDots);
        FloatingActionButton fab = findViewById(R.id.fab);
        final CardView optionsCard = findViewById(R.id.settingsCard);
        final CardView tweetCard = findViewById(R.id.makeTweetCard);
        TextView optionsCardCancel = findViewById(R.id.optionsCardCancel);
        TextView tweetCardCancel = findViewById(R.id.tweetCardCancel);
        Button signOutButton = findViewById(R.id.signOutButton);
        Button sendTweetButton = findViewById(R.id.sendTweetButton);
        followButton = findViewById(R.id.followButton);
//
        storyFragment = sectionsPagerAdapter.getStoryFragment();
        final FeedFragment feedFragment = sectionsPagerAdapter.getFeedFragment();

//
//
        followButton.setEnabled(false);
        isFollowing();
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFollowing) { // unfollow them

                    GetUnFollowTask getUnFollowTask = new GetUnFollowTask(presenter, UserViewActivity.this);
                    UnFollowRequest request = new UnFollowRequest(userShown, currentUserLoggedIn);
                    getUnFollowTask.execute(request);

                    followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    followButton.setText("Follow");
                    isFollowing = false;
                }
                else { // follow them

                    GetFollowTask getFollowTask = new GetFollowTask(presenter, UserViewActivity.this);
                    FollowRequest request = new FollowRequest(userShown, currentUserLoggedIn);
                    getFollowTask.execute(request);

                    followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    followButton.setText("Following");
                    isFollowing = true;
                }

            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSignInView(view);
            }
        });

        optionsCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsCard.setVisibility(View.INVISIBLE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)findViewById(R.id.tweetMessage);
                text.setText("");
                tweetCard.setVisibility(View.VISIBLE);
            }
        });

        tweetCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweetCard.setVisibility(View.INVISIBLE);
            }
        });

        optionDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsCard.setVisibility(View.VISIBLE);
            }
        });

        userImageView = findViewById(R.id.userImage);


//        presenter.setShownUser(user);

        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"TODO: implement send tweet", Toast.LENGTH_SHORT).show();
//                EditText text = (EditText)findViewById(R.id.tweetMessage);
//                String message = text.getText().toString();
////                UserResponse currentUserResponse = UserService.getInstance().getCurrentUser();
////                Tweet tweet = new Tweet(currentUserResponse.getUser().getAlias(),message, "fakeURL");
//
//                TweetRequest request = new TweetRequest(tweet);
//                GetSendTweetTask getSendTweetTask = new GetSendTweetTask(presenter, UserViewActivity.this);
//                getSendTweetTask.execute(request);
                tweetCard.setVisibility(View.INVISIBLE);
            }
        });


        // Asynchronously load the user's image
    }

//    private void getUserShown() {
//        if (userShown == null) {
//            GetUserShownTask getUserShownTask = new GetUserShownTask(presenter, UserViewActivity.this, this);
//            getUserShownTask.execute(new CurrentUserRequest());
//            // continued at userShownGot
//        }
//        else {
//            userShownGot(userShown);
//        }
//    }

    private void getCurrentUser() {
        if (currentUserLoggedIn == null) {
//            GetCurrentUserTask currentUserTask =  new GetCurrentUserTask(presenter, UserViewActivity.this, this);
//            currentUserTask.execute(new CurrentUserRequest());
//            // continued at currentUserGot
        }
        else {
//            currentUserGot(new UserResponse(currentUserLoggedIn));
        }
    }

    private void switchToSignInView (View view) {
//        Toast.makeText(view.getContext(),"TODO: switch to sign in page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void isFollowing () {
        IsFollowingTask isFollowingTask = new IsFollowingTask(presenter, this);
        isFollowingTask.execute(new IsFollowingRequest());
        // continued at isFollowingResponded
    }




    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(userShown, drawables[0]);

        if(drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    @Override
    public void tweetResponded(TweetResponse tweetResponse) {
        if (tweetResponse.isSent()) {
            storyFragment.listChanged();
        }
    }

    @Override
    public void userRetrieved(UserResponse userResponse) {

    }

//    @Override
//    public void userShownGot(User user) {
//
//        // Asynchronously load the user's image
//        LoadImageTask loadImageTask = new LoadImageTask(this);
//        loadImageTask.execute(user.getImageUrl());
//
//        TextView userName = findViewById(R.id.userName);
//        userName.setText(user.getName());
//
//        TextView userAlias = findViewById(R.id.userAlias);
//        userAlias.setText(user.getAlias());
//    }

    @Override
    public void isFollowingResponded(IsFollowingResponse response) {

        isFollowing = response.isSuccess();
        followButton.setEnabled(true);

        if (isFollowing) {
            followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            followButton.setText("Following");
        }
        else {
            followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            followButton.setText("Follow");
        }
    }

    @Override
    public void currentUserGot(UserResponse userResponse) {
        currentUserLoggedIn = userResponse.getUser();
    }
}