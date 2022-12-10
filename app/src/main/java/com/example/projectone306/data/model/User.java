package com.example.projectone306.data.model;

import com.example.projectone306.items.Item;

import java.util.ArrayList;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    public String username, emailAddress;
    private Boolean loggedIn = false;
    public ArrayList<Item> wishList, cart;

    public User () {
        // empty constructor
    }

    public User(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
        wishList = new ArrayList<>();
        cart = new ArrayList<>();
    }

    public Boolean isLoggedIn() { return loggedIn; }

    public void setLoggedIn(Boolean isLoggedIn) { loggedIn = isLoggedIn; }
}