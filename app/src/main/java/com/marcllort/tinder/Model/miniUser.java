package com.marcllort.tinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class miniUser { // Fa de sent i received

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("pictureContentType")
    @Expose
    private String pictureContentType;
    @SerializedName("displayName")
    @Expose
    private String displayName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
