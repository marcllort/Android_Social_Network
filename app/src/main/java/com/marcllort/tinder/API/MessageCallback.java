package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.Message;

public interface MessageCallback {

    void onGetMessages(Message[] messages);
    void onGetMessage(Message message);
    void onSendMessageSucces();
    void onFailure(Throwable t);

}
