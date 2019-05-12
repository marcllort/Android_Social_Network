package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;

public interface ProfileCallBack {

    void onGetProfile(MyProfile myProfile);
    void onUpdateProfile(MyProfile myProfile);
    void onFailure(Throwable t);
}
