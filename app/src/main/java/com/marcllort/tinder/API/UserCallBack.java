package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.User;

public interface UserCallBack {

    void onGetUsers(User user);
    void onFailure(Throwable t);
}
