package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.User;

public interface AccountCallback {

    void onGetAccount(User account);

    void onFailure(Throwable t);

}
