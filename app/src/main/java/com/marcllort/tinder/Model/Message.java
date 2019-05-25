
package com.marcllort.tinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("chatroom")
    @Expose
    private Chatroom chatroom;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("pictureContentType")
    @Expose
    private String pictureContentType;
    @SerializedName("sender")
    @Expose
    private MyProfile sender;
    @SerializedName("recipient")
    @Expose
    private MyProfile reciver;
    @SerializedName("url")
    @Expose
    private String url;


    public Message(String messagee, MyProfile senderr, MyProfile reciverr, int idd) {
        id = idd+1;
        message = messagee;
        sender = senderr;
        createdDate = "2019-05-21T18:56:00Z";
        url = "";
        picture = "";
        pictureContentType = null;
        reciver = reciverr;

    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public MyProfile getSender() {
        return sender;
    }

    public MyProfile getReciver() {
        return reciver;
    }

    public void setSender(MyProfile sender) {
        this.sender = sender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
