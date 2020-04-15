package edu.byu.cs.tweeter.view.main.following;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import edu.byu.cs.tweeter.model.SessionManager;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserShownTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.SetUserShownTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.UserViewActivity;

public class FollowingFragment extends Fragment implements
        FollowingPresenter.View
//        SetUserShownTask.SetUserShownObserver
{

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private FollowingPresenter presenter;

//    private SetUserShownTask.SetUserShownObserver setUserShownObserver;

    private FollowingRecyclerViewAdapter followingRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        presenter = new FollowingPresenter(this);

//        setUserShownObserver = this;

        RecyclerView followingRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followingRecyclerView.setLayoutManager(layoutManager);

        followingRecyclerViewAdapter = new FollowingRecyclerViewAdapter();
        followingRecyclerView.setAdapter(followingRecyclerViewAdapter);

        followingRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

//    @Override
//    public void userShownSet(User user) {
//        if (user != null) {
//            Intent intent = new Intent(getActivity(), UserViewActivity.class);
//            startActivity(intent);
//        }
//    }


    private class FollowingHolder extends RecyclerView.ViewHolder implements GetUserTask.GetUserObserver {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;

        FollowingHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        void bindUser(final User user) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());

            userAlias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchToThisUserView(getActivity(), userAlias.getText().toString());
                }
            });
        }

        private void switchToThisUserView(FragmentActivity activity, String userAlias) {
            GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), this);
            getUserTask.execute(new UserRequest(null, userAlias));
            // continued in userShownSet
        }

        @Override
        public void userRetrieved(UserResponse userResponse) {
            if (userResponse != null) {
                if (userResponse.getUser() != null) {
                    SessionManager.getInstance().setUserShown(userResponse.getUser());
                    Intent intent = new Intent(getActivity(), UserViewActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "Sorry, that user does not exist", Toast.LENGTH_LONG);
                }
            }
        }
    }

    private class FollowingRecyclerViewAdapter extends RecyclerView.Adapter<FollowingHolder> implements
            GetFollowingTask.GetFolloweesObserver
//            GetUserShownTask.GetUserShownObserver
    {

        private final List<User> users = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.User lastFollowee;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FollowingRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(startInsertPosition, newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FollowingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowingFragment.this.getContext());
            View view;

            if(isLoading) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowingHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowingHolder followingHolder, int position) {
            if(!isLoading) {
                followingHolder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

//            GetUserShownTask userShownTask = new GetUserShownTask(presenter, getActivity(), this);
//            userShownTask.execute(new CurrentUserRequest());
            // continues on userShownGot
            GetFollowingTask getFollowingTask = new GetFollowingTask(presenter, this);
            FollowingRequest request = new FollowingRequest(SessionManager.getInstance().getUserShown(), PAGE_SIZE, lastFollowee);
            getFollowingTask.execute(request);


        }

        @Override
        public void followeesRetrieved(FollowingResponse followingResponse) {
            if (followingResponse != null) {

                removeLoadingFooter();
                isLoading = false;
                if (followingResponse.getFollowees() != null) {
                    List<User> followees = followingResponse.getFollowees();

                    lastFollowee = (followees.size() > 0) ? followees.get(followees.size() -1) : null;
                    hasMorePages = followingResponse.hasMorePages();

                    followingRecyclerViewAdapter.addItems(followees);
                }
            }
            else {
                loadMoreItems();
            }

        }

        private void addLoadingFooter() {

            addItem(new User("Dummy", "User", ""));
        }

        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }

//        @Override
//        public void userShownGot(User user) {
//            if (user != null) {
//                GetFollowingTask getFollowingTask = new GetFollowingTask(presenter, this);
//                FollowingRequest request = new FollowingRequest(user, PAGE_SIZE, lastFollowee);
//                getFollowingTask.execute(request);
//            }
//
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

            if (!followingRecyclerViewAdapter.isLoading && followingRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followingRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
