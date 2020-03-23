package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetCurrentUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetSendTweetTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserShownTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.asyncTasks.SetUserShownTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;

public class MainActivity extends AppCompatActivity implements
        SetUserShownTask.SetUserShownObserver,
        GetSendTweetTask.GetTweetObserver,
        LoadImageTask.LoadImageObserver,
        GetCurrentUserTask.GetCurrentUserObserver,
        GetUserTask.GetUserObserver,
        GetUserShownTask.GetUserShownObserver,
        MainPresenter.View
{

    private MainPresenter presenter;
//    private StoryPresenter storyPresenter;
    private User currentUser;
    private ImageView userImageView;
    private boolean following = true; // TODO: this should come from the user itself
    private StoryFragment storyFragment;
//    private View theView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        ImageView optionDots = findViewById(R.id.optionDots);
        FloatingActionButton fab = findViewById(R.id.fab);
        final CardView optionsCard = findViewById(R.id.settingsCard);
        final CardView tweetCard = findViewById(R.id.makeTweetCard);
        TextView optionsCardCancel = findViewById(R.id.optionsCardCancel);
        TextView tweetCardCancel = findViewById(R.id.tweetCardCancel);
        Button signOutButton = findViewById(R.id.signOutButton);
        Button sendTweetButton = findViewById(R.id.sendTweetButton);
        final EditText searchBox = findViewById(R.id.searchText);
        ImageView searchUserIcon = findViewById(R.id.searchUserIcon);
//        final Button followButton = findViewById(R.id.followButton);

        storyFragment = sectionsPagerAdapter.getStoryFragment();
        final FeedFragment feedFragment = sectionsPagerAdapter.getFeedFragment();


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

        getCurrentUser(); // sets user when task returns

        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),
                EditText text = (EditText)findViewById(R.id.tweetMessage);
                String message = text.getText().toString();
                Tweet tweet = new Tweet(currentUser.getAlias(), message, "make URL");

                TweetRequest request = new TweetRequest(tweet);
                GetSendTweetTask getSendTweetTask = new GetSendTweetTask(presenter, MainActivity.this);
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

    }

    private void switchToThisUserView(String searchBoxText) {
//        getUserShown();
        // continued in userShownGot()
        GetUserTask getUserTask = new GetUserTask(presenter,MainActivity.this, this);
        getUserTask.execute(new UserRequest(new User(searchBoxText)));
    }

//    private void getUserShown() {
//        GetUserShownTask getUserShownTask = new GetUserShownTask(presenter, MainActivity.this, this);
//        getUserShownTask.execute(new CurrentUserRequest());
//    }

    private void getCurrentUser() {
        // get the current user
        presenter = new MainPresenter(this);
        GetCurrentUserTask currentUserTask = new GetCurrentUserTask(presenter,MainActivity.this,this);
        currentUserTask.execute(new CurrentUserRequest());
        // continues at currentUserGot
    }

    @Override
    public void currentUserGot(UserResponse userResponse) {
        currentUser = userResponse.getUser();

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(currentUser.getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(currentUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(currentUser.getAlias());
    }


    private void switchToSignInView (View view) {
//        Toast.makeText(view.getContext(),"TODO: switch to sign in page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isFollowing () {
        following = !following;

        return following;
    }

    private void setUserShown(User userToShow) {
        SetUserShownTask setUserShownTask = new SetUserShownTask(presenter,MainActivity.this, this);
        setUserShownTask.execute(new UserRequest(userToShow));
        // continued in userShownSet
    }

    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(currentUser, drawables[0]);

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
            setUserShown(userResponse.getUser());
        }
    }

    @Override
    public void userShownGot(User user) {
//        GetUserTask getUserTask = new GetUserTask(presenter, MainActivity.this, presenter.getUserShown(), searchBox.getText().toString());
//        UserRequest request = new UserRequest(presenter.getUserShown(), searchBoxText);
//        getUserTask.execute(request);
    }

    @Override
    public void userShownSet(User user) {
        Intent intent = new Intent(this, UserViewActivity.class);
        this.startActivity(intent);
    }

//    class MyCustomSpannable extends ClickableSpan
//    {
////        String handle;
//        public MyCustomSpannable() {
////            this.handle = handle;
//        }
//        @Override
//        public void updateDrawState(TextPaint ds) {
//            // Customize your Text Look if required
//            ds.setColor(Color.YELLOW);
//            ds.setFakeBoldText(true);
//            ds.setStrikeThruText(true);
//            ds.setTypeface(Typeface.SERIF);
//            ds.setUnderlineText(true);
//            ds.setShadowLayer(10, 1, 1, Color.WHITE);
//            ds.setTextSize(15);
//        }
//        @Override
//        public void onClick(View widget) {
//
//        }
////        public String getUrl() {
////            return handle;
////        }
//    }
}