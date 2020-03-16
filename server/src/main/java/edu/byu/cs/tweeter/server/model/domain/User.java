package edu.byu.cs.tweeter.server.model.domain;


//import com.sun.istack.internal.NotNull;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Comparable<User> {

    public String firstName = null;
    public String lastName = null;
    public String alias = null;
    public String imageUrl = null;
    public List<Tweet> tweets = null;


    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL, null);
    }

    public User(String firstName, String lastName, String alias, String imageURL) {
        this(firstName, lastName, alias, imageURL, null);
    }

    public User(String firstName, String lastName, String imageURL, List<Tweet> tweets) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL, tweets);
    }

    public User() {
    }

    public User(String firstName, String lastName, String alias, String imageURL, List<Tweet> tweets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUrl = imageURL;
        this.tweets = tweets;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void makeTweets(List<Tweet> newTweets) {
        tweets = newTweets;
    }

    public void addTweet(Tweet tweet) {
        if (tweets == null) {
            tweets = new ArrayList<>();
        }
        tweets.add(tweet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", alias='" + alias + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }
}
