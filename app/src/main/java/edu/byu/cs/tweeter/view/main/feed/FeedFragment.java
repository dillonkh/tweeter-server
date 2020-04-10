package edu.byu.cs.tweeter.view.main.feed;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserShownTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.SetUserShownTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.UserViewActivity;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;

public class FeedFragment extends Fragment implements
        FeedPresenter.View
//        SetUserShownTask.SetUserShownObserver
{

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private FeedPresenter presenter;

//    private SetUserShownTask.SetUserShownObserver setUserShownObserver;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    private User userShown;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        presenter = new FeedPresenter(this);

//        setUserShownObserver = this;

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }


    @Override
    public void listChanged() {
//        feedRecyclerViewAdapter.notifyThereAreMoreItems();
//        feedRecyclerViewAdapter.notifyDataSetChanged();
        feedRecyclerViewAdapter.loadMoreItems();
    }

//    @Override
//    public void userShownSet(User user) {
//        if (user != null) {
//            Intent intent = new Intent(getActivity(), UserViewActivity.class);
//            startActivity(intent);
//        }
//    }


    private class FeedHolder extends RecyclerView.ViewHolder implements GetUserTask.GetUserObserver {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userFirstName;
        private final TextView userLastName;
        private final TextView userTweet;
        private final TextView timeStamp;

        FeedHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.tweetUserImage);
            userAlias = itemView.findViewById(R.id.tweetUserHandle);
            userFirstName = itemView.findViewById(R.id.tweetUserFirstName);
            userLastName = itemView.findViewById(R.id.tweetUserLastName);
            userTweet = itemView.findViewById(R.id.tweetUserTweet);
            timeStamp = itemView.findViewById(R.id.timeStamp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchToThisUserView(getActivity(), userAlias.getText().toString());
                }
            });

        }

        private void switchToThisUserView(FragmentActivity activity, String userAlias) {
//            SetUserShownTask setUserShownTask = new SetUserShownTask(presenter, activity, setUserShownObserver);
//            setUserShownTask.execute(new UserRequest(new User(userAlias)));
            // continued in userShownSet
        }

        void bindTweet(Tweet tweet) {
            getUserToBindTweets(tweet);

            SpannableString ss = parseMessage(tweet.getMessage());
            if (ss != null) {
                userTweet.setText(ss);
            }
            else {
                userTweet.setText(tweet.getMessage());
            }
            userTweet.setMovementMethod(LinkMovementMethod.getInstance());
            userTweet.setHighlightColor(Color.TRANSPARENT);
            timeStamp.setText(tweet.getTimeStamp());

            userAlias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchToThisUserView(getActivity(), userAlias.getText().toString());
                }
            });
        }

        private void getUserToBindTweets(Tweet tweet) {

            GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), this);
            getUserTask.execute(new UserRequest(new User(tweet.getUser())));
            // continued in userRetrieved

        }

        @Override
        public void userRetrieved(UserResponse userResponse) {
            if (userResponse.getUser() != null) {
                userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(userResponse.getUser()));
                userAlias.setText(userResponse.getUser().getAlias());
                userFirstName.setText(userResponse.getUser().getFirstName());
                userLastName.setText(userResponse.getUser().getLastName());
            }
        }
    }

    private SpannableString parseMessage(String message) {
        String parsed = new String();
        SpannableString ss = new SpannableString(message);

        int start = -1;
        int end = -1;
        for (int i = 0; i < message.length(); i++) {
            if (start < 0) {
                if (message.charAt(i) == '@') {
                    start = i;
                }
            }
            else {
                if (message.charAt(i) == ' ' || i == message.length()) {
                    end = i;
                    break;
                }
            }
        }
        if (start == -1) {
            return null;
        }
        else if (end == -1) {
            end = message.length() - 1;
        }
        while (true) {
            if ( (message.charAt(end) >= 48 && message.charAt(end) <= 57)
                    || (message.charAt(end) >= 65 && message.charAt(end) <= 90)
                    || (message.charAt(end) >= 97 && message.charAt(end) <= 122) ) {

                break;
            }
            else {
                end = end - 1;
            }
        }

        ClickableSpan clickableSpan = new FeedFragment.MyClickableSpan(message.substring(start, end + 1));

        ss.setSpan(clickableSpan, start, end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }

    class MyClickableSpan extends ClickableSpan {

        String message;

        public MyClickableSpan(String message) {
            this.message = message;
        }

        @Override
        public void onClick(View textView) {
//                startActivity(new Intent(MyActivity.this, NextActivity.class));

//            Toast.makeText(textView.getContext(),message,Toast.LENGTH_SHORT).show();
//            GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), presenter.getUserShown(), message);
//            UserRequest request = new UserRequest(presenter.getUserShown(), message);
//            getUserTask.execute(request);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedHolder> implements
            GetFeedTask.GetFeedObserver
//            GetUserShownTask.GetUserShownObserver
    {

        private final List<Tweet> tweets = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Tweet lastTweet;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Tweet> newTweets) {
            int startInsertPosition = tweets.size();
            tweets.addAll(tweets.size(), newTweets);
            this.notifyItemRangeInserted(startInsertPosition, tweets.size());
        }

        void addItem(Tweet tweet) {
            int startInsertPosition = tweets.size();
            tweets.add(tweets.size(), tweet);
            this.notifyItemInserted(startInsertPosition);
        }

        void removeItem(Tweet tweet) {
            int position = tweets.indexOf(tweet);
            tweets.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(isLoading) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.tweet, parent, false);
            }

            return new FeedHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedHolder feedHolder, int position) {
            if(!isLoading) {
                feedHolder.bindTweet(tweets.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return tweets.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == tweets.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            if (userShown == null) {
//                GetUserShownTask userShownTask = new GetUserShownTask(presenter, getActivity(), this);
//                userShownTask.execute(new CurrentUserRequest());
                // continues on userShownGot
            }
            else {
//                userShownGot(userShown);
            }
        }

        @Override
        public void tweetsRetrieved(FeedResponse feedResponse) {
            List<Tweet> tweets = feedResponse.getTweets();

            if (tweets != null) {
                if (tweets.size() > 0) {
                    lastTweet = tweets.get(tweets.size() - 1);
                    hasMorePages = feedResponse.hasMorePages();
                    feedRecyclerViewAdapter.addItems(tweets);

                }
            }

            isLoading = false;
//            removeLoadingFooter();
        }

        void notifyThereAreMoreItems() {
//            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
//            FeedRequest request = new FeedRequest(presenter.getUserShown(), PAGE_SIZE, lastTweet);
//            getFeedTask.execute(request);
        }

        private void addLoadingFooter() {
//            addItem(
//                    new Tweet(
//                            "@fake",
//                            "This is placeholder text for tweet",
//                            null
//                    )
//            );
        }

        private void removeLoadingFooter() {
            removeItem(tweets.get(tweets.size() - 1));
        }

//        @Override
//        public void userShownGot(User user) {
//
//            if (user != null) {
//                GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
//                FeedRequest request = new FeedRequest(user, PAGE_SIZE, lastTweet);
//                getFeedTask.execute(request);
//            }
//        }
    }

    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
