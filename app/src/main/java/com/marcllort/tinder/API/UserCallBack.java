package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;

import java.util.ArrayList;

public interface UserCallBack {

    void onGetUsers(ArrayList<MyProfile> users);
    void onFailure(Throwable t);
}
