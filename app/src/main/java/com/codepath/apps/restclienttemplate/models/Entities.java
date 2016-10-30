package com.codepath.apps.restclienttemplate.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by taq on 30/10/2016.
 */

public class Entities {
    @SerializedName("hashtags")
    private JsonArray hashTag;

    @SerializedName("urls")
    private JsonArray urls;

    @SerializedName("media")
    private JsonArray media;

    public String getPhotoMediaUrl() {
        if (media != null && media.size() > 0) {
            JsonObject mediaObj = (JsonObject) media.get(0);
            if (mediaObj.get("type").getAsString().equals("photo")) {
                return mediaObj.get("media_url").getAsString();
            }
        }
        return "";
    }
}
