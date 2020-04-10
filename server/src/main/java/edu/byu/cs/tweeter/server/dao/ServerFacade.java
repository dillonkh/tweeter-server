package edu.byu.cs.tweeter.server.dao;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.byu.cs.tweeter.server.dao.request.CurrentUserRequest;
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


    public UserResponse getUser(UserRequest request) {

        return new UserResponse(request.user);
    }

    public FollowingResponse getFollowees(FollowingRequest request) { // people i follow

        List<User> followees = new ArrayList<>();

        followees = initializeFollowees();
        List<User> responseList = new ArrayList<>();

        for (int i = 0; i < request.getLimit(); i++) {
            responseList.add(followees.get(i));
        }

        return new FollowingResponse(responseList,true);
    }

    public FollowerResponse getFollowers(FollowerRequest request) {

        List<User> followers = new ArrayList<>();

        followers = initializeFollowees();
        List<User> responseList = new ArrayList<>();

        for (int i = 0; i < request.getLimit(); i++) {
            responseList.add(followers.get(i));
        }

        return new FollowerResponse(responseList,true);
    }

    public FeedResponse getFeed(FeedRequest request) {

        try {
            List<User> randomUsers = generateUsers(10);


            List<Tweet> allTweets = new ArrayList<>();

            for (User u : randomUsers) {
//                allTweets.addAll(u.getTweets());
            }


            Collections.sort(allTweets, Collections.<Tweet>reverseOrder());

            List<Tweet> responseTweets = new ArrayList<>();
            for (int i = 0; i < request.getLimit(); i++) {
                responseTweets.add(allTweets.get(i));
            }


            Collections.sort(responseTweets, Collections.<Tweet>reverseOrder());
            if (responseTweets == null) {
                return new FeedResponse("there was an error");
            }
            return new FeedResponse(responseTweets,true);
        }
        catch (Exception ex) {
            return new FeedResponse("There was an exception");
        }


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
//        allTweets = u.getTweets();

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

    private ArrayList<Tweet> myTweets;

    public TweetResponse addTweet(TweetRequest request) {

        return new TweetResponse(true);
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

    public UserResponse getCurrentUser(CurrentUserRequest request) {

        return new UserResponse(new User("Dillon", "Harris", "@dillonkh",""));
    }

    public UserResponse setCurrentUser(UserRequest request) {

//        currentUser = request.getUser();

        return new UserResponse(request.user);
    }

    /**
     * Generates the followee data.
     */
    private List<User> initializeFollowees() { // people i follow
        List<User> randomUsers = generateUsers(20);

        return randomUsers;
    }

    private List<User> generateUsers(int count) {

        String MALE_NAMES_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json";
        String FEMALE_NAMES_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/fnames.json";
        String SURNAMES_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/snames.json";

        String [] maleNames;
        String [] femaleNames;
        String [] surnames;

        String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";


        try {
            maleNames = loadNamesFromJSon(MALE_NAMES_URL);
            femaleNames = loadNamesFromJSon(FEMALE_NAMES_URL);
            surnames = loadNamesFromJSon(SURNAMES_URL);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        List<User> users = new ArrayList<>(count);

        Random random = new Random();

        while(users.size() < count) {
            // Randomly determine if the user will be male or female and generate a gender
            // specific first name
            String firstName;
            String imageULR;
            if(random.nextInt(2) == 0) {
                firstName = maleNames[random.nextInt(maleNames.length)];
                imageULR = MALE_IMAGE_URL;
            } else {
                firstName = femaleNames[random.nextInt(femaleNames.length)];
                imageULR = FEMALE_IMAGE_URL;
            }

            String lastName = surnames[random.nextInt(surnames.length)];
//            User user = new User(firstName, lastName, imageULR);

//            user.makeTweets(new TweetGenerator().generateTweets(2, user));

//            users.add(user);
        }

        return users;
    }

    private static String [] loadNamesFromJSon(String urlString) throws IOException {

        HttpURLConnection connection = null;

        Names names;

        try {
            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                names = (new Gson()).fromJson(br, Names.class);

            } else {
                throw new IOException("Unable to read from url. Response code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        return names == null ? null : names.getNames();
    }

    class Names {

        @SuppressWarnings("unused")
        private String [] data;

        private String [] getNames() {
            return data;
        }
    }

    private Map<User, List<User>> initializeFollowers(User user) { // people following me

        Map<User, List<User>> followeesByUser = new HashMap<>();

        List<User> randomUsers = new UserGenerator().generateUsers(10);

        followeesByUser.put(user,randomUsers);

        return followeesByUser;
    }

    public FollowResponse followUser(FollowRequest request) {
//        1. add usertofollow to followees under userlogged in
//        2. add userloggedin to followers under usertofollow.

//        Map<User, List<User>> followeesByUser = initializeFollowees(request.getUserLoggedIn());
//
//        Map<User, List<User>> followersByUser = initializeFollowers(request.getUserLoggedIn());
//
//        // STEP 1 /////
//        List<User> temp = followeesByUser.get(request.getUserLoggedIn());
//        if (temp == null) {
//            temp = new ArrayList<>();
//        }
//        temp.add(request.getUserToFollow());
//        followeesByUser.put(request.getUserLoggedIn(), temp);
//
//        // STEP 2 /////
//        temp = followersByUser.get(request.getUserToFollow());
//        if (temp == null) {
//            temp = new ArrayList<>();
//        }
//        temp.add(request.getUserLoggedIn());
//        followersByUser.put(request.getUserToFollow(), temp);
//
//        return new FollowResponse(true);

        return new FollowResponse(true);
    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {
//        1. add usertofollow to followees under userlogged in
//        2. add userloggedin to followers under usertofollow.

//        Map<User, List<User>> followeesByUser = initializeFollowees(request.userLoggedIn);
//        Map<User, List<User>> followersByUser = initializeFollowers(request.userLoggedIn);
//
//        // STEP 1 /////
//        List<User> temp = followeesByUser.get(request.getUserLoggedIn());
//        temp.remove(request.getUserToFollow());
////        temp.add(request.getUserToFollow());
//        if (temp == null) {
//            temp = new ArrayList<>();
//        }
//        followeesByUser.put(request.getUserLoggedIn(), temp);
//
//        // STEP 2 /////
//        temp = followersByUser.get(request.getUserToFollow());
//        if (temp == null) {
//            temp = new ArrayList<>();
//        }
//        temp.remove(request.getUserLoggedIn());
////        temp.add(request.getUserLoggedIn());
//        followersByUser.put(request.getUserToFollow(), temp);
//
//        return new UnFollowResponse(true);

        return new UnFollowResponse(true);
    }

//    public boolean isFollowing(User userLoggedIn, User userShown) {
    public IsFollowingResponse isFollowing(IsFollowingRequest request) {
//        Map<User, List<User>> followeesByUser = initializeFollowees(request.userLoggedIn);
//
//        for (User u : followeesByUser.get(request.getUserLoggedIn())) {
//            if (request.getUserShown().equals(u)) {
//                return new IsFollowingResponse(true);
//            }
//        }
        return new IsFollowingResponse(false);

    }

    public LoginResponse login(LoginRequest request) {
        return new LoginResponse(true, new User("Dummy", "Data", "@dummy", ""), "fakeToken");
    }

    public LoginResponse signUp(SignUpRequest request) {
        User newUser = new User(request.getFirstName(),request.getLastName(),request.getHandle(),request.getImageURL());

        return new LoginResponse(true, newUser, "FakeAuth");
    }

    // signout


}
