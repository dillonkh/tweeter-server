package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
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
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.IsFollowingResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
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
import edu.byu.cs.tweeter.view.asyncTasks.SignOutTask;
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
        IsFollowingTask.IsFollowingObserver,
        SignOutTask.SignOutObserver,
        GetCurrentUserTask.GetCurrentUserObserver
{

    private MainPresenter presenter;
    private ImageView userImageView;
    private StoryFragment storyFragment;

    private boolean isFollowing;
    Button followButton;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview);

        presenter = new MainPresenter(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
//
        Toolbar toolbar = findViewById(R.id.userviewToolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        ImageView optionDots = findViewById(R.id.optionDots);
        FloatingActionButton fab = findViewById(R.id.fab);
        final CardView optionsCard = findViewById(R.id.settingsCard);
        final CardView tweetCard = findViewById(R.id.makeTweetCard);
        TextView optionsCardCancel = findViewById(R.id.optionsCardCancel);
        TextView tweetCardCancel = findViewById(R.id.tweetCardCancel);
        Button signOutButton = findViewById(R.id.signOutButton);
        Button sendTweetButton = findViewById(R.id.sendTweetButton);
        followButton = findViewById(R.id.followButton);
        final EditText searchBox = findViewById(R.id.searchText);
        ImageView searchUserIcon = findViewById(R.id.searchUserIcon);
        followButton.setEnabled(false);
        followButton.setBackgroundColor(getColor(R.color.greyText));
        isFollowing();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
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


        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)findViewById(R.id.tweetMessage);
                String message = text.getText().toString();
                User user = SessionManager.getInstance().getUserLoggedIn();
                Tweet tweet = new Tweet(user.getAlias(), user.getFirstName(), user.getLastName(), message, "make URL");

                TweetRequest request = new TweetRequest(tweet);
                GetSendTweetTask getSendTweetTask = new GetSendTweetTask(presenter, UserViewActivity.this);
                getSendTweetTask.execute(request);
                tweetCard.setVisibility(View.INVISIBLE);
            }
        });

        searchUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToThisUserView(searchBox.getText().toString());
            }
        });

        userImageView = findViewById(R.id.userImage);

        getCurrentUser(); // sets user when task returns


    }

    private void switchToThisUserView(String searchBoxText) {
        GetUserTask getUserTask = new GetUserTask(presenter, UserViewActivity.this, this);
        getUserTask.execute(new UserRequest(null, searchBoxText));


    }

    private void signOut() {
        SignOutTask signOutTask = new SignOutTask(presenter, this);
        signOutTask.execute(new LoginRequest(null, SessionManager.getInstance().getUserLoggedIn().getAlias()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {


            case android.R.id.home:
                onUpButtonPressed();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onUpButtonPressed() {
        User userShown = SessionManager.getInstance().getUserLoggedIn();
        SessionManager.getInstance().setUserShown(userShown);
    }


    private void getCurrentUser() {
        User currentUser = SessionManager.getInstance().getUserShown();
        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(currentUser.getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(currentUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(currentUser.getAlias());
    }

    private void switchToSignInView () {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void isFollowing () {
        IsFollowingTask isFollowingTask = new IsFollowingTask(presenter, this);
        isFollowingTask.execute(
                new IsFollowingRequest(
                        SessionManager.getInstance().getUserLoggedIn(),
                        SessionManager.getInstance().getUserShown()
                )
        );
        // continued at isFollowingResponded
    }




    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(SessionManager.getInstance().getUserShown(), drawables[0]);

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
        if (userResponse != null) {
            if (userResponse.getUser() != null) {
                SessionManager.getInstance().setUserShown(userResponse.getUser());
                Intent intent = new Intent(this, UserViewActivity.class);
                this.startActivity(intent);
            }
            else {
                Toast.makeText(this, "Sorry, that user does not exist", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void isFollowingResponded(IsFollowingResponse response) {

        if (response != null) {
            isFollowing = response.isSuccess();
            followButton.setEnabled(true);

            if (isFollowing) {
                followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                followButton.setText("Unfollow");
            }
            else {
                followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                followButton.setText("Follow");
            }

            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(view.getContext(), "implementation needed", Toast.LENGTH_SHORT).show();
                    if (isFollowing) { // unfollow them

                        GetUnFollowTask getUnFollowTask = new GetUnFollowTask(presenter, UserViewActivity.this);
                        UnFollowRequest request = new UnFollowRequest(
                                SessionManager.getInstance().getUserShown(),
                                SessionManager.getInstance().getUserLoggedIn()
                        );
                        getUnFollowTask.execute(request);

                        followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        followButton.setText("Follow");
                        isFollowing = false;
                    }
                    else { // follow them

                        GetFollowTask getFollowTask = new GetFollowTask(presenter, UserViewActivity.this);
                        FollowRequest request = new FollowRequest(
                                SessionManager.getInstance().getUserShown(),
                                SessionManager.getInstance().getUserLoggedIn()
                        );
                        getFollowTask.execute(request);

                        followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        followButton.setText("Unfollow");
                        isFollowing = true;
                    }

                }
            });

        }

    }

    @Override
    public void currentUserGot(UserResponse userResponse) {
//        currentUserLoggedIn = userResponse.getUser();
    }

    @Override
    public void signOutResponded(LoginResponse r) {
        switchToSignInView();
    }
}