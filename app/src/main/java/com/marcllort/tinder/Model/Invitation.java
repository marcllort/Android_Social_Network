
package com.marcllort.tinder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invitation {

    @SerializedName("accepted")
    @Expose
    private Boolean accepted;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("received")
    @Expose
    private miniUser received;
    @SerializedName("sent")
    @Expose
    private miniUser sent;

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
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

    public miniUser getReceived() {
        return received;
    }

    public void setReceived(miniUser received) {
        this.received = received;
    }

    public miniUser getSent() {
        return sent;
    }

    public void setSent(miniUser sent) {
        this.sent = sent;
    }

}
