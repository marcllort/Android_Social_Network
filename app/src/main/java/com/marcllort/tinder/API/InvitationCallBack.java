package com.marcllort.tinder.API;

import com.marcllort.tinder.Model.Invitation;

import java.util.ArrayList;
import java.util.List;

public interface InvitationCallBack  {
    void onGetPendingInvites(ArrayList<Invitation> invitations);
    void onGetAllInvitations(List<Invitation> invitations);
    void onChangeState();
    void onUpdateInvitation(Invitation invitation);
    void onFailure(Throwable t);
}
