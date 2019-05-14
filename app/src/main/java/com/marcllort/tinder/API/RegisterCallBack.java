package com.marcllort.tinder.API;

public interface RegisterCallBack {
    void onRegisterSuccess();
    void onFailure(Throwable t);
}
