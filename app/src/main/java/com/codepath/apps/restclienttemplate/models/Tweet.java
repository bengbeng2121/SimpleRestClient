package com.codepath.apps.restclienttemplate.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by taq on 29/10/2016.
 */

public class Tweet {

    @SerializedName("id")
    private long id;

    @SerializedName("text")
    private String text;

    @SerializedName("retweet_count")
    private int retweet;

    @SerializedName("favorite_count")
    private int favorite;

    @SerializedName("user")
    private User user;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getRetweet() {
        return retweet;
    }

    public int getFavorite() {
        return favorite;
    }

    public User getUser() {
        return user;
    }
}
