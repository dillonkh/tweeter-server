package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

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
