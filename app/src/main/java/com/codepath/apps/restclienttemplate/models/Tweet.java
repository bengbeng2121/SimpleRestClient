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

    @SerializedName("created_at")
    private String createTime;

    @SerializedName("user")
    private User user;

    @SerializedName("entities")
    private Entities entities;

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

    public String getCreateTime() {
        return createTime;
    }

    public User getUser() {
        return user;
    }

    public Entities getEntities() {
        return entities;
    }
}
