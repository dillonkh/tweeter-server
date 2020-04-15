package edu.byu.cs.tweeter.server.model.service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordService {

    private static HashPasswordService instance;

    public static final String SALT = "some_salty_text";

    public static HashPasswordService getInstance() {
        if(instance == null) {
            instance = new HashPasswordService();
        }

        return instance;
    }

    private HashPasswordService() {}

    public String hashPassword (String plainTextPass) {
        String saltedPassword = SALT + plainTextPass;
        String hashedPassword = generateHash(saltedPassword);

        return hashedPassword;
    }

    private String generateHash(String saltedPassword) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(saltedPassword.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int i = 0; i < hashedBytes.length; i++) {
                byte b = hashedBytes[i];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }


}
