package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.Message;

import java.util.ArrayList;

public interface MessageCallback {

    void onGetMessages(ArrayList<Message> messages);
    void onSendMessage();
    void onFailure(Throwable t);

}
