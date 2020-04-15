package edu.byu.cs.tweeter.server.model.request;

public class SignUpRequest {

    public String firstName;
    public String lastName;
    public String handle;
    public String password;
    public String imageURL;

    public SignUpRequest(String firstName, String lastName, String handle, String password, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.password = password;
        this.imageURL = imageURL;
    }

    public SignUpRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHandle() {
        return handle;
    }

    public String getPassword() {
        return password;
    }

    public String getImageURL() {
        return imageURL;
    }
}
