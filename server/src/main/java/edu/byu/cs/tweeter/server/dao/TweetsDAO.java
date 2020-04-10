package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

import edu.byu.cs.tweeter.server.dao.request.FeedRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.StoryRequest;
import edu.byu.cs.tweeter.server.dao.request.TweetRequest;
import edu.byu.cs.tweeter.server.dao.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.response.TweetResponse;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;

public class TweetsDAO {

    public FeedResponse getFeed(FeedRequest request) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("tweets");


        try {

            // get all users you are following
            ArrayList<User> usersImFollowing = (ArrayList<User>) new FollowingDAO().getFollowees(
                    new FollowingRequest(
                            request.getUser(),
                            10,
                            null
                    )
            ).getFollowees();

            // get 10 tweets (sorted by timestamp) by each user
            for (int i = 0; i < usersImFollowing.size(); i++) {
                QuerySpec spec = new QuerySpec()
                        .withHashKey("user_alias", usersImFollowing.get(i).alias);


                ItemCollection<QueryOutcome> items = table.query(spec);

                Iterator<Item> iterator = items.iterator();
                Item item = null;

                while (iterator.hasNext()) {
                    item = iterator.next();
                    if (item != null) {
                        String user = item.get("user_alias").toString();
                        String firstName = item.get("first_name").toString();
                        String lastName = item.get("last_name").toString();
                        String message = item.get("message").toString();
                        String url = item.get("url").toString();
                        String timeStamp = item.get("timestamp").toString();

                        Tweet t = new Tweet(user,firstName,lastName,message,url,timeStamp);

                        tweets.add(t);
                    }
                }
            }

            // return all those tweets
            return new FeedResponse(tweets, false);



        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StoryResponse getStory(StoryRequest request) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("tweets");


        try {

            QuerySpec spec = new QuerySpec()
                    .withHashKey("user_alias", request.getUser().alias);


            ItemCollection<QueryOutcome> items = table.query(spec);

            Iterator<Item> iterator = items.iterator();
            ArrayList<Tweet> tweets = new ArrayList<>();
            Item item = null;
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item != null) {
                    String user = item.get("user_alias").toString();
                    String firstName = item.get("first_name").toString();
                    String lastName = item.get("last_name").toString();
                    String message = item.get("message").toString();
                    String url = item.get("url").toString();
                    String timeStamp = item.get("timestamp").toString();

                    Tweet t = new Tweet(user,firstName,lastName,message,url,timeStamp);

                    tweets.add(t);
                }
            }


            return new StoryResponse(tweets,false);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TweetResponse addTweet(TweetRequest request) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("tweets");



        try {
            Item item = new Item();
            item.withPrimaryKey("user_alias", request.getTweet().user);
            item.withString("first_name", request.getTweet().firstName);
            item.withString("last_name", request.getTweet().lastName);
            item.withString("message", request.getTweet().message);
            item.withString("url", request.getTweet().url);
            item.withString("timestamp", request.getTweet().timeStamp);

            PutItemOutcome outcome = table.putItem(item);

            return new TweetResponse(true);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
