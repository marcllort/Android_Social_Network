package com.marcllort.tinder.API;

public interface LoginCallBack {

    void onLoginSuccess(UserToken userToken);
    void onFailure(Throwable t);
}
