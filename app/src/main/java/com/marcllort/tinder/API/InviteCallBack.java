package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.Invitation;

public interface InviteCallBack {
    void onGetInvitation(Invitation invitation);
    void onFailure(Throwable t);
}
