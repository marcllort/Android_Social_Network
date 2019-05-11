package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;

public interface ProfileCallBack {

    void onGetMyProfile(MyProfile myProfile);
    void onUpdateProfile(MyProfile myProfile);
    void onUpdateMyProfile(MyProfile myProfile);
    void onFailure(Throwable t);
}
