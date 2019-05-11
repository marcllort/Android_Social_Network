package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;

public interface CreateProfileCallBack {

    void onCreateMyProfile(MyProfile myProfile);
    void onSuccess();
    void onFailure(Throwable t);
}
