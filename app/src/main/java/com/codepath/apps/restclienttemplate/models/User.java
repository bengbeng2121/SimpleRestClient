package com.codepath.apps.restclienttemplate.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by taq on 29/10/2016.
 */

public class User {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_image_url")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBiggerImageUrl() {
        return imageUrl.replace("_normal", "_bigger");
    }
}
