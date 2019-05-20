package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;

import java.util.ArrayList;

public interface ConnectionsCallBack {

    void onGetConnections(ArrayList<MyProfile> users);
    void onFailure(Throwable t);
}
