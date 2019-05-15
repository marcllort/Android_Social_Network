package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.Invitation;

import java.util.ArrayList;

public interface InvitationCallBack  {
    void onGetPendingInvites(Invitation[] invitations);
    void onGetAllInvitations(Invitation[] invitations);
    void onFailure(Throwable t);
}
