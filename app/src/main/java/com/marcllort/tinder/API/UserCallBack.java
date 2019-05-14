package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;

public interface UserCallBack {

    void onGetUsers();
    void onSuccess();
    void onFailure(Throwable t);
}
