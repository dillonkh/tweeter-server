package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.server.model.request.LoginRequest;
import edu.byu.cs.tweeter.server.model.request.SignUpRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.response.UserResponse;
import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.model.service.HashPasswordService;

public class UserDAO {

    public UserResponse getUser(UserRequest request) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("users");


        try {

            Item item = table.getItem("alias", request.getHandle());
            User u = makeUser(item);
            return new UserResponse(u);
        }
        catch (Exception e) {
            return new UserResponse(null);
        }

    }

    public boolean batchAddUsers (ArrayList<User> listOfPeopleFollowingTest) {
        // batch write with the list I have in the request

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems("users");

        // Add each user into the TableWriteItems object
        for (User u : listOfPeopleFollowingTest) {
//            System.out.println("ADDING TWEET FOR: "+alias);
            Item item = new Item();
            item.withPrimaryKey("alias", u.getAlias());
            item.withString("first_name", u.getFirstName());
            item.withString("last_name", u.getLastName());
            item.withString("password", HashPasswordService.getInstance().hashPassword("Password1"));
            item.withString("image_url", u.getImageUrl());
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("users");
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

    public LoginResponse signUp(SignUpRequest request) {

        if (request.imageURL.equals("")) {
            request.imageURL = "https://orbitermag.com/wp-content/uploads/2017/03/default-user-image-300x300.png";
        }

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("users");



        try {
            Item item = new Item();
            item.withPrimaryKey("alias", request.getHandle());
            item.withString("first_name", request.getFirstName());
            item.withString("last_name", request.getLastName());
            item.withString("password", HashPasswordService.getInstance().hashPassword(request.getPassword()));
            item.withString("image_url", request.getImageURL());

            PutItemOutcome outcome = table.putItem(item);

            return new LoginResponse(true, makeUser(item), "fakeAuth");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public LoginResponse login(LoginRequest request) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("users");



        try {

            Item item = table.getItem("alias", request.getHandle());
            if (item.get("password").toString().equals(HashPasswordService.getInstance().hashPassword(request.getPassword()))) {
                return new LoginResponse(true, makeUser(item), "FakeAuth");
            }
            else {
                throw new RuntimeException();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private User makeUser(Item item) {
        return new User(
                item.get("first_name").toString(),
                item.get("last_name").toString(),
                item.get("alias").toString(),
                item.get("image_url").toString()
        );
    }
}
