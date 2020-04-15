package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.model.domain.Tweet;
import edu.byu.cs.tweeter.server.model.request.FeedRequest;
import edu.byu.cs.tweeter.server.model.request.StoryRequest;
import edu.byu.cs.tweeter.server.model.request.TweetRequest;
import edu.byu.cs.tweeter.server.model.request.UpdateFeedsRequest;
import edu.byu.cs.tweeter.server.model.response.FeedResponse;
import edu.byu.cs.tweeter.server.model.response.StoryResponse;
import edu.byu.cs.tweeter.server.model.response.TweetResponse;

public class FeedDAO {

    public FeedResponse getFeed(FeedRequest request) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("feed");


        try {

            QuerySpec spec;
            if (request.getLastTweet() != null) {
                spec = new QuerySpec()
                        .withHashKey("user_alias", request.getUser().alias)
                        .withRangeKeyCondition(new RangeKeyCondition("timestamp").gt(request.getLastTweet().getTimeStamp()))
                        .withMaxPageSize(request.limit);
            }
            else {
                spec = new QuerySpec()
                        .withHashKey("user_alias", request.getUser().alias)
                        .withMaxPageSize(request.limit);
            }

            ItemCollection<QueryOutcome> items = table.query(spec);

            Iterator<Item> iterator = items.iterator();
            ArrayList<Tweet> tweets = new ArrayList<>();
            Item item = null;
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item != null) {
                    String user = item.get("author_alias").toString();
                    String firstName = item.get("author_first_name").toString();
                    String lastName = item.get("author_last_name").toString();
                    String message = item.get("message").toString();
                    String timeStamp = item.get("timestamp").toString();

                    Tweet t = new Tweet(user,firstName,lastName,message,"url",timeStamp);

                    tweets.add(t);
                }
            }

            if (tweets.size() > 0) {
                return new FeedResponse(tweets, true);
            }
            else {
                return new FeedResponse(tweets, false);
            }




        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFeeds(UpdateFeedsRequest updateFeedsRequest) {
        // batch write with the list I have in the request

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems("feed");
        System.out.println("start updating feeds");
        // Add each user into the TableWriteItems object
        for (String alias : updateFeedsRequest.getUserAliasList()) {
            System.out.println("ADDING TWEET FOR: "+alias);
            Item item = new Item()
                    .withPrimaryKey("user_alias", alias)
                    .withString("timestamp", updateFeedsRequest.getTweet().getTimeStamp())
                    .withString("author_alias", updateFeedsRequest.getTweet().getUser())
                    .withString("author_first_name", updateFeedsRequest.getTweet().getFirstName())
                    .withString("author_last_name", updateFeedsRequest.getTweet().getLastName())
                    .withString("message", updateFeedsRequest.getTweet().getMessage());
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("feed");
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }

        System.out.println("start updating feeds");


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



}
