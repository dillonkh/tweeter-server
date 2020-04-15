package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.GetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.request.FollowRequest;
import edu.byu.cs.tweeter.server.model.request.FollowingRequest;
import edu.byu.cs.tweeter.server.model.request.IsFollowingRequest;
import edu.byu.cs.tweeter.server.model.request.UnFollowRequest;
import edu.byu.cs.tweeter.server.model.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.server.model.response.FollowResponse;
import edu.byu.cs.tweeter.server.model.response.FollowingResponse;
import edu.byu.cs.tweeter.server.model.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.model.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.model.service.HashPasswordService;

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

    public boolean batchAddFollowers (ArrayList<User> peopleFollowingMe) {
        // batch write with the list I have in the request

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems("followee_to_user");

        // Add each user into the TableWriteItems object
        for (User u : peopleFollowingMe) {
//            System.out.println("ADDING TWEET FOR: "+alias);

            Item item = new Item();
            item.withPrimaryKey("followee_alias", "@dillonkh");
            item.withString("user_alias", u.alias);
            item.withString("user_firstName", u.firstName);
            item.withString("user_lastName", HashPasswordService.getInstance().hashPassword("Password1"));
            item.withString("user_imageUrl", u.imageUrl);
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("followee_to_user");
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
        return true;
    }

    private void loopBatchWrite(TableWriteItems items) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = db.batchWriteItem(items);
//        logger.log("Wrote User Batch");

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = db.batchWriteItemUnprocessed(unprocessedItems);
//            logger.log("Wrote more Users");
        }
    }

    public UpdateFeedsRequest getFolloweesMessage(FollowingRequest request, Tweet tweet) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("followee_to_user");


        try {

            QuerySpec spec;
            spec = new QuerySpec()
                    .withHashKey("followee_alias", request.getFollower().alias)
                    .withMaxResultSize(request.limit);

            ItemCollection<QueryOutcome> items = table.query(spec);

            Iterator<Item> iterator = items.iterator();
            ArrayList<String> userAliasList = new ArrayList<>();
            Item item = null;
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item != null) {
                    String user = item.get("user_alias").toString();
                    userAliasList.add(user);
                }
            }
            System.out.println("size of list: "+userAliasList.size());
            if (userAliasList.size() > 0) {
                return new UpdateFeedsRequest(userAliasList, tweet, true, null);
            }
            else {
                return new UpdateFeedsRequest(userAliasList, tweet, false, null);
            }



        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IsFollowingResponse isFollowing(IsFollowingRequest request) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("follower_to_user");

        try {

            User userLoggedIn = request.getUserLoggedIn();
            User userShown = request.getUserShown();

            PrimaryKey key = new PrimaryKey(
                    "follower_alias",
                    userLoggedIn.alias,
                    "user_alias",
                    userShown.alias
            );

            Item item = table.getItem(key);

            if (item != null) {
                return new IsFollowingResponse(true);
            }
            else {
                return new IsFollowingResponse(false);
            }


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
            QuerySpec spec;
            if (request.getLastFollowee() != null) {
                spec = new QuerySpec()
                        .withHashKey("follower_alias", request.getFollower().alias)
                        .withMaxResultSize(request.getLimit())
                        .withRangeKeyCondition(new RangeKeyCondition("user_alias").gt(request.getLastFollowee().alias));

            }
            else {
                spec = new QuerySpec()
                        .withHashKey("follower_alias", request.getFollower().alias)
                        .withMaxResultSize(request.getLimit());
            }


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

            if (users.size() > 0) {
                return new FollowingResponse(users, true);
            }
            else {
                return new FollowingResponse(users, false);
            }




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
