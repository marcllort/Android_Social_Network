package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.MyProfile;

public interface RegisterCallBack {
    void onRegisterSuccess();
    void onFailure(Throwable t);
}
