package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.User;

import java.util.ArrayList;

public interface UserCallBack {

    void onGetUsers(ArrayList<User> users);
    void onFailure(Throwable t);
}
