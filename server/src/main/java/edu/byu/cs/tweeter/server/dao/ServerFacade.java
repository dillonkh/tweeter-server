package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.tweeter.server.dao.request.FeedRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowerRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.IsFollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.LoginRequest;
import edu.byu.cs.tweeter.server.dao.request.SignUpRequest;
import edu.byu.cs.tweeter.server.dao.request.StoryRequest;
import edu.byu.cs.tweeter.server.dao.request.TweetRequest;
import edu.byu.cs.tweeter.server.dao.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.dao.request.UserRequest;
import edu.byu.cs.tweeter.server.dao.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.response.TweetResponse;
import edu.byu.cs.tweeter.server.dao.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.dao.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;


public class ServerFacade {

    private static Map<User, List<User>> followeesByUser;
    private static Map<User, List<User>> followersByUser;
    private static Set<User> usersInDB;
    private static User userSignedIn;

    public UserResponse getUser(UserRequest request) {

        for (User u : usersInDB) {
            if (request.getHandle().equals(u.getAlias())) {
                return new UserResponse(u);
            }
        }

        return null;
    }

    public FollowingResponse getFollowees(FollowingRequest request) { // people i follow

        return new FollowingResponse(new ArrayList<User>(),false);
//        assert request.getLimit() >= 0;
//        assert request.getFollower() != null;
//
//        if(followeesByUser == null) {
//            followeesByUser = initializeFollowees(request.getFollower());
//        }
//
//        List<User> allFollowees = followeesByUser.get(request.getFollower());
//        List<User> responseFollowees = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFollowees != null) {
//                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);
//
//                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
//                    responseFollowees.add(allFollowees.get(followeesIndex));
//                }
//
//                hasMorePages = followeesIndex < allFollowees.size();
//            }
//        }
//
//        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    public FollowerResponse getFollowers(FollowerRequest request) {

        assert request.getLimit() >= 0;
        assert request.getFollowing() != null;

        if(followersByUser == null) {
            followersByUser = initializeFollowers(request.getFollowing());
        }

        List<User> allFollowees = followersByUser.get(request.getFollowing());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowerResponse(responseFollowees, hasMorePages);
    }

    public FeedResponse getFeed(FeedRequest request) {

        assert request.getLimit() >= 0;
        assert request.getUser() != null;


        if(followeesByUser == null) {
            followeesByUser = initializeFollowees(request.getUser());
        }

        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        List<Tweet> allTweets = new ArrayList<>();
        List<User> usersIFollow = followeesByUser.get(request.getUser());

        // add tweets from people i follow to feed
        if (usersIFollow != null) {
            for (User u : usersIFollow) {
                if (u.getTweets() != null) {
                    allTweets.addAll(0, u.getTweets());
                }
            }
        }


        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allTweets != null) {
                int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);

                for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < request.getLimit(); tweetsIndex++, limitCounter++) {
                    responseTweets.add(allTweets.get(tweetsIndex));
                }

                hasMorePages = tweetsIndex < allTweets.size();
            }
        }

        Collections.sort(responseTweets, Collections.<Tweet>reverseOrder());
        return new FeedResponse(responseTweets,hasMorePages);
    }

    public StoryResponse getStory(StoryRequest request) {

        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        List<Tweet> allTweets = new ArrayList<>();
        UserResponse r = getUser(new UserRequest(request.getUser(), request.getUser().getAlias()));
        if (r == null) {
            return new StoryResponse("Error");
        }
        User u = r.getUser();
        if (u == null) {
            return new StoryResponse("Error");
        }
        allTweets = u.getTweets();

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allTweets != null) {
                int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);

                for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < request.getLimit(); tweetsIndex++, limitCounter++) {
                    responseTweets.add(allTweets.get(tweetsIndex));
                }

                hasMorePages = tweetsIndex < allTweets.size();
            }
        }

        Collections.sort(responseTweets, Collections.<Tweet>reverseOrder());
        return new StoryResponse(responseTweets,hasMorePages);

    }

    public TweetResponse addTweet(TweetRequest request) {

        if (usersInDB == null) {
            usersInDB = new HashSet<>();
        }

        for (User u : usersInDB) {
            if (u.equals(request.getTweet().getUser())) {
                u.addTweet(request.getTweet());
                return new TweetResponse(true);
            }
        }

        return new TweetResponse(false);
    }

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    private int getTweetsStartingIndex(Tweet lastTweet, List<Tweet> allTweets) {

        int tweetIndex = 0;

        if(lastTweet != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allTweets.size(); i++) {
                if(lastTweet.equals(allTweets.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    tweetIndex = i + 1;
                }
            }
        }

        return tweetIndex;
    }

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees(User user) { // people i follow

        followeesByUser = new HashMap<>();

        List<User> randomUsers = getUserGenerator().generateUsers(3);

        followeesByUser.put(user,randomUsers);

        if (usersInDB == null) {
            usersInDB = new HashSet<>();
        }
        usersInDB.add(user);

        if (followersByUser == null) {
            followersByUser = initializeFollowers(user);
        }

        List<User> followeesOfUser = followeesByUser.get(user);
        for (User u : followeesOfUser) {
            List<User> temp = followersByUser.get(u);
            if (temp == null) {
                temp = new ArrayList<>();
            }
            temp.add(user);
            followersByUser.put(u, temp);
            usersInDB.add(u);
        }


        return followeesByUser;
    }

    private Map<User, List<User>> initializeFollowers(User user) { // people following me

        followersByUser = new HashMap<>();

        List<User> randomUsers = getUserGenerator().generateUsers(3);

        followersByUser.put(user,randomUsers);

        if (usersInDB == null) {
            usersInDB = new HashSet<>();
        }
        usersInDB.add(user);

        if (followeesByUser == null) {
            followeesByUser = initializeFollowees(user);
        }

        List<User> followersOfUser = followersByUser.get(user); // people i follow
        for (User u : followersOfUser) {
            List<User> temp = followeesByUser.get(u);
            if (temp == null) {
                temp = new ArrayList<>();
            }
            temp.add(user);
            followeesByUser.put(u, temp);
            usersInDB.add(u);
        }

        return followersByUser;
    }

    public FollowResponse followUser(FollowRequest request) {
//        1. add usertofollow to followees under userlogged in
//        2. add userloggedin to followers under usertofollow.

        if (followeesByUser == null) {
            initializeFollowees(request.getUserLoggedIn());
        }
        if (followersByUser == null) {
            initializeFollowers(request.getUserLoggedIn());
        }
        // STEP 1 /////
        List<User> temp = followeesByUser.get(request.getUserLoggedIn());
        if (temp == null) {
            temp = new ArrayList<>();
        }
        temp.add(request.getUserToFollow());
        followeesByUser.put(request.getUserLoggedIn(), temp);

        // STEP 2 /////
        temp = followersByUser.get(request.getUserToFollow());
        if (temp == null) {
            temp = new ArrayList<>();
        }
        temp.add(request.getUserLoggedIn());
        followersByUser.put(request.getUserToFollow(), temp);

        return new FollowResponse(true);
    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {
//        1. add usertofollow to followees under userlogged in
//        2. add userloggedin to followers under usertofollow.


        // STEP 1 /////
        List<User> temp = followeesByUser.get(request.getUserLoggedIn());
        temp.remove(request.getUserToFollow());
//        temp.add(request.getUserToFollow());
        if (temp == null) {
            temp = new ArrayList<>();
        }
        followeesByUser.put(request.getUserLoggedIn(), temp);

        // STEP 2 /////
        temp = followersByUser.get(request.getUserToFollow());
        if (temp == null) {
            temp = new ArrayList<>();
        }
        temp.remove(request.getUserLoggedIn());
//        temp.add(request.getUserLoggedIn());
        followersByUser.put(request.getUserToFollow(), temp);

        return new UnFollowResponse(true);
    }

//    public boolean isFollowing(User userLoggedIn, User userShown) {
    public IsFollowingResponse isFollowing(IsFollowingRequest request) {

        for (User u : followeesByUser.get(request.getUserLoggedIn())) {
            if (request.getUserShown().equals(u)) {
                return new IsFollowingResponse(true);
            }
        }
        return new IsFollowingResponse(false);
    }

    public LoginResponse login(LoginRequest request) {
        if (usersInDB == null) {
            return new LoginResponse(false, null, "");
        }
        for (User u : usersInDB) {
            if (u.getAlias().equals(request.getHandle())) {
                userSignedIn = u;
                return new LoginResponse(true, u, "FakeAuth");
            }
        }

        return new LoginResponse(false, null, "");
    }

    public LoginResponse signUp(SignUpRequest request) {
        if (usersInDB == null) {
            usersInDB = new HashSet<>();
        }
        for (User u : usersInDB) {
            if (u.getAlias().equals(request.getHandle())) {
                return new LoginResponse(false, null, "");
            }
        }

        User newUser = new User(request.getFirstName(),request.getLastName(),request.getHandle(),request.getImageURL());
        userSignedIn = newUser;
        usersInDB.add(newUser);

        return new LoginResponse(true, newUser, "FakeAuth");
    }

    public void clearAll() {
        if (followeesByUser != null) {
            followeesByUser.clear();
            followeesByUser = null;
        }
        if (followersByUser != null) {
            followersByUser.clear();
            followersByUser = null;
        }
        if (usersInDB != null) {
            usersInDB.clear();
            usersInDB = null;
        }

    }

    /**
     * Generates the tweet data.
     */


    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */

    UserGenerator getUserGenerator() {
        return UserGenerator.getInstance();
    }

}
