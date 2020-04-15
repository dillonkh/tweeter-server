package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import edu.byu.cs.tweeter.server.model.domain.User;
import edu.byu.cs.tweeter.server.model.request.LoginRequest;
import edu.byu.cs.tweeter.server.model.request.SignUpRequest;
import edu.byu.cs.tweeter.server.model.request.UserRequest;
import edu.byu.cs.tweeter.server.model.response.IsFollowingResponse;
import edu.byu.cs.tweeter.server.model.response.LoginResponse;
import edu.byu.cs.tweeter.server.model.response.UserResponse;
import edu.byu.cs.tweeter.server.model.service.HashPasswordService;

public class AuthenticatedUsersDAO {

    public String setAuthToken(String userAlias) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("authenticated_users");

        try {

            String expirationTime = getNewExpirationTime();
            String authToken = UUID.randomUUID().toString();

            Item item = new Item()
                    .withPrimaryKey("user_alias", userAlias)
                    .withString("expiration_time", expirationTime)
                    .withString("auth_token", authToken);

            PutItemOutcome outcome = table.putItem(item);
            return authToken;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String getNewExpirationTime() {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(30);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return dtf.format(expirationTime);
    }

    public String getAuthToken(String userAlias) { // if it returns null it mean it has expired
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("authenticated_users");

        try {

            PrimaryKey key = new PrimaryKey(
                    "user_alias",
                    userAlias
            );

            Item item = table.getItem(key);

            if (item != null) {
                String expirationTimeStr = item.get("expiration_time").toString();
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = sdf.parse(expirationTimeStr);

                if (isDateExpired(date)) {
                    return null;
                }
                else {
                    return item.get("auth_token").toString();
                }
            }
            else {
                return null;
            }


        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDateExpired(Date dateOfAuth) {
        Date now = new Date();
        if (dateOfAuth.after(now)) {
            return false;
        }
        else {
            return true;
        }
    }

    public LoginResponse removeRow(String userAlias) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("authenticated_users");

        try {

            PrimaryKey key = new PrimaryKey(
                    "user_alias",
                    userAlias
            );
            DeleteItemSpec spec = new DeleteItemSpec()
                    .withPrimaryKey(key);

            DeleteItemOutcome outcome = table.deleteItem(spec);

            return new LoginResponse(false, null, null);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
