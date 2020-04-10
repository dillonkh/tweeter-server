package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

import edu.byu.cs.tweeter.server.dao.request.FollowerRequest;
import edu.byu.cs.tweeter.server.dao.response.FollowerResponse;
import edu.byu.cs.tweeter.server.dao.response.FollowingResponse;
import edu.byu.cs.tweeter.server.model.domain.User;

public class FollowerDAO {

    public FollowerResponse getFollowers(FollowerRequest request) {

        ArrayList<User> users = new ArrayList<>();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("followee_to_user");


        try {

            QuerySpec spec = new QuerySpec()
                    .withHashKey("followee_alias", request.getFollowing().alias);


            ItemCollection<QueryOutcome> items = table.query(spec);

            Iterator<Item> iterator = items.iterator();
            Item item = null;
            while (iterator.hasNext()) {
                item = iterator.next();
                String handle = item.get("user_alias").toString();
                String firstName = item.get("user_firstName").toString();
                String lastName = item.get("user_lastName").toString();
                String imageUrl = item.get("user_imageUrl").toString();

                User u = new User(firstName,lastName,handle,imageUrl);

                users.add(u);
            }


            return new FollowerResponse(users,false);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
