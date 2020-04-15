package edu.byu.cs.tweeter.net;


import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.UploadRequest;
import edu.byu.cs.tweeter.net.request.CurrentUserRequest;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.IsFollowingRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.IsFollowingResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.net.response.UnFollowResponse;
import edu.byu.cs.tweeter.net.response.UploadResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class ServerFacade {

//    private static Map<User, List<User>> followeesByUser;
//    private static Map<User, List<User>> followersByUser;
//    private static Set<User> usersInDB;
//    private static User userSignedIn;

    private final String BASE_URL = "https://99n8wodqn9.execute-api.us-west-2.amazonaws.com/dev";

    public UserResponse getUser(UserRequest request) {

        String urlPath = "/getuser";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, UserResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new UserResponse(null);
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request) { // people i follow

        String urlPath = "/getfollowees";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new FollowingResponse(null, false);
        }
    }

    public UserResponse setCurrentUser(UserRequest request) {
        String urlPath = "/setcurrentuser";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, UserResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserResponse getCurrentUser(CurrentUserRequest request) {
        String urlPath = "/getcurrentuser";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doGet(urlPath,null, UserResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserResponse getUserShown (CurrentUserRequest request) {
        String urlPath = "/getusershown";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, UserResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FollowerResponse getFollowers(FollowerRequest request) {

        String urlPath = "/getfollowers";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, FollowerResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FeedResponse getFeed(FeedRequest request) {

        String urlPath = "/getfeed";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StoryResponse getStory(StoryRequest request) {

        String urlPath = "/getstory";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, StoryResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public TweetResponse addTweet(TweetRequest request) {

        String urlPath = "/addtweet";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, TweetResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UploadResponse uploadImage(UploadRequest request) {

        String urlPath = "/uploadimage";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, UploadResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {
//
//        int followeesIndex = 0;
//
//        if(lastFollowee != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowees.size(); i++) {
//                if(lastFollowee.equals(allFollowees.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followeesIndex = i + 1;
//                }
//            }
//        }
//
//        return followeesIndex;
//    }

//    private int getTweetsStartingIndex(Tweet lastTweet, List<Tweet> allTweets) {
//
//        int tweetIndex = 0;
//
//        if(lastTweet != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allTweets.size(); i++) {
//                if(lastTweet.equals(allTweets.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    tweetIndex = i + 1;
//                }
//            }
//        }
//
//        return tweetIndex;
//    }

//    /**
//     * Generates the followee data.
//     */
//    private Map<User, List<User>> initializeFollowees(User user) { // people i follow
//
//        followeesByUser = new HashMap<>();
//
//        List<User> randomUsers = getUserGenerator().generateUsers(3);
//
//        followeesByUser.put(user,randomUsers);
//
//        if (usersInDB == null) {
//            usersInDB = new HashSet<>();
//        }
//        usersInDB.add(user);
//
//        if (followersByUser == null) {
//            followersByUser = initializeFollowers(user);
//        }
//
//        List<User> followeesOfUser = followeesByUser.get(user);
//        for (User u : followeesOfUser) {
//            List<User> temp = followersByUser.get(u);
//            if (temp == null) {
//                temp = new ArrayList<>();
//            }
//            temp.add(user);
//            followersByUser.put(u, temp);
//            usersInDB.add(u);
//        }
//
//
//        return followeesByUser;
//    }

//    private Map<User, List<User>> initializeFollowers(User user) { // people following me
//
//        followersByUser = new HashMap<>();
//
//        List<User> randomUsers = getUserGenerator().generateUsers(3);
//
//        followersByUser.put(user,randomUsers);
//
//        if (usersInDB == null) {
//            usersInDB = new HashSet<>();
//        }
//        usersInDB.add(user);
//
//        if (followeesByUser == null) {
//            followeesByUser = initializeFollowees(user);
//        }
//
//        List<User> followersOfUser = followersByUser.get(user); // people i follow
//        for (User u : followersOfUser) {
//            List<User> temp = followeesByUser.get(u);
//            if (temp == null) {
//                temp = new ArrayList<>();
//            }
//            temp.add(user);
//            followeesByUser.put(u, temp);
//            usersInDB.add(u);
//        }
//
//        return followersByUser;
//    }

    public FollowResponse followUser(FollowRequest request) {

        String urlPath = "/followuser";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, FollowResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {

        String urlPath = "/unfollowuser";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, UnFollowResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IsFollowingResponse isFollowing(IsFollowingRequest request) {

        String urlPath = "/isfollowing";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, IsFollowingResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResponse login(LoginRequest request) {
        String urlPath = "/login";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResponse signUp(SignUpRequest request) {
        String urlPath = "/signup";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResponse signOut(LoginRequest request) {
        String urlPath = "/signout";

        ClientCommunicator clientCommunicator = new ClientCommunicator(BASE_URL);
        try {
            return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void clearAll() {
//        if (followeesByUser != null) {
//            followeesByUser.clear();
//            followeesByUser = null;
//        }
//        if (followersByUser != null) {
//            followersByUser.clear();
//            followersByUser = null;
//        }
//        if (usersInDB != null) {
//            usersInDB.clear();
//            usersInDB = null;
//        }
//
//    }

    /**
     * Generates the tweet data.
     */


//    /**
//     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
//     * written as a separate method to allow mocking of the generator.
//     *
//     * @return the generator.
//     */
//
//    UserGenerator getUserGenerator() {
//        return UserGenerator.getInstance();
//    }

}
