package com.marcllort.tinder.API;

public interface RestAPICallBack {
    void onLoginSuccess(UserToken userToken);
    void onRegisterSuccess();
    void onFailure(Throwable t);
}
