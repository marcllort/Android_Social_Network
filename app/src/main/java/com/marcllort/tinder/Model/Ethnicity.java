
package com.marcllort.tinder.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ethnicity {

    @SerializedName("ethnicity")
    @Expose
    private String ethnicity;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
