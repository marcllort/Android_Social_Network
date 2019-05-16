package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.User;

import java.util.ArrayList;

public interface UserProfileCallBack {

    void onGetUser(User user);
    void onFailure(Throwable t);
}
