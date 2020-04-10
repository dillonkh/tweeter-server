package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Iterator;

import edu.byu.cs.tweeter.server.dao.request.FollowRequest;
import edu.byu.cs.tweeter.server.dao.request.FollowingRequest;
import edu.byu.cs.tweeter.server.dao.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.domain.User;

public class FollowingDAO {

    public FollowResponse followUser(FollowRequest request) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);

        try {
            // phase 1
            putYourselfInFollowers(request, db.getTable("follower_to_user"));

            // phase 2
            putUserToFollowInYourFollowees(request, db.getTable("followee_to_user"));

            return new FollowResponse(true);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);

        try {
            // phase 1
            removeUserLoggedInFromFollowers(request, db.getTable("follower_to_user"));

            // phase 2
            removeUserToFollowFromFollowees(request, db.getTable("followee_to_user"));

            return new UnFollowResponse(true);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request) { // people i follow

        ArrayList<User> users = new ArrayList<>();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("follower_to_user");


        try {

            QuerySpec spec = new QuerySpec()
                    .withHashKey("follower_alias", request.getFollower().alias);


            ItemCollection<QueryOutcome> items = table.query(spec);

            Iterator<Item> iterator = items.iterator();
            Item item = null;
            Gson gson = new Gson();
            while (iterator.hasNext()) {
                item = iterator.next();
                String handle = item.get("user_alias").toString();
                String firstName = item.get("user_firstName").toString();
                String lastName = item.get("user_lastName").toString();
                String imageUrl = item.get("user_imageUrl").toString();

                User u = new User(firstName,lastName,handle,imageUrl);

                users.add(u);
            }


            return new FollowingResponse(users, false);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PutItemOutcome putYourselfInFollowers(FollowRequest request, Table table) {
        Item item = new Item();
        item.withPrimaryKey("follower_alias", request.userLoggedIn.alias);
        item.withString("user_alias", request.userToFollow.alias);
        item.withString("user_firstName", request.userToFollow.firstName);
        item.withString("user_lastName", request.userToFollow.lastName);
        item.withString("user_imageUrl", request.userToFollow.imageUrl);

        PutItemOutcome outcome = table.putItem(item);
        return outcome;
    }

    private PutItemOutcome putUserToFollowInYourFollowees(FollowRequest request, Table table) {
        Item item = new Item();
        item.withPrimaryKey("followee_alias", request.userToFollow.alias);
        item.withString("user_alias", request.userLoggedIn.alias);
        item.withString("user_firstName", request.userLoggedIn.firstName);
        item.withString("user_lastName", request.userLoggedIn.lastName);
        item.withString("user_imageUrl", request.userLoggedIn.imageUrl);

        PutItemOutcome outcome = table.putItem(item);
        return outcome;
    }

    private DeleteItemOutcome removeUserLoggedInFromFollowers(UnFollowRequest request, Table table) {
        PrimaryKey key = new PrimaryKey(
                "follower_alias",
                request.getUserLoggedIn().alias,
                "user_alias",
                request.userToFollow.alias
        );
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(key);

        DeleteItemOutcome outcome = table.deleteItem(spec);
        return outcome;
    }

    private DeleteItemOutcome removeUserToFollowFromFollowees(UnFollowRequest request, Table table) {
        PrimaryKey key = new PrimaryKey(
                "followee_alias",
                request.getUserToFollow().alias,
                "user_alias",
                request.userLoggedIn.alias
        );
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(key);

        DeleteItemOutcome outcome = table.deleteItem(spec);
        return outcome;
    }

}
