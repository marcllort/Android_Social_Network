package com.marcllort.tinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcllort.tinder.API.MessageCallback;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.Message;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity implements MessageCallback {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private EditText mChatBox;
    private Button mSendButton;
    private Toolbar mToolbar;
    private String user;
    private int userid, myid = 3;
    private int nextidmessage=500;
    private User myUser, usersend;
    private MyProfile profile, destprofile;
    private List<Message> messageList;
    private ImageView profileImage;
    private ImageButton photopicker;
    private boolean init = true;
    private Timer t ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("login");
            userid = extras.getInt("userid");
            myUser = (User) extras.getSerializable("myuser");
            profile = (MyProfile) extras.getSerializable("myprofile");
            destprofile = (MyProfile) extras.getSerializable("destprofile");

            myid = myUser.getId();
        }


        RestAPIManager.getInstance().getMessages(this);

        recyclerSetup();
        topBarSetup();
        chatBoxSetup();
        //initLocal();

        t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {

                RestAPIManager.getInstance().getMessages(ChatActivity.this);
            }
        }, 3000, 3000);


        if (messageList.size() != 0) {
            mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
        }
    }


    private void recyclerSetup() {
        messageList = new ArrayList<>();


        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setNestedScrollingEnabled(false);
        mMessageRecycler.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);

        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mMessageRecycler.setLayoutManager(linearLayoutManager);


        mMessageAdapter = new MessageListAdapter(this, getChatData(), userid);
        mMessageRecycler.setAdapter(mMessageAdapter);

    }

    private void chatBoxSetup() {

        mChatBox = (EditText) findViewById(R.id.edittext_chatbox);
        mSendButton = (Button) findViewById(R.id.button_chatbox_send);
        photopicker = (ImageButton) findViewById(R.id.ib_photo_picker);

        mChatBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Send text button
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        photopicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void topBarSetup() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView title = findViewById(R.id.text_toolbar_title);
        title.setText(user);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profileImage = findViewById(R.id.image_profile);
        //profileImage.setImageBitmap();                                                                        //Posem la imatge de perfil-----------------------------------------------------------------
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                profileIntent.putExtra("login", userid);
                startActivity(profileIntent);
            }
        });


    }                                                                                           // cal modificar coses aqui

    private void initLocal() {                                                                                               //shaura de borrar
        for (int i = 0; i < 27; i++) {
            // messageList.add(new Message("Rebo" + i, new User(user, "sd", "ES")));
            // messageList.add(new Message("Envio" + i, null));
        }
    }

    private void getChatMessages() {


        RestAPIManager.getInstance().getMessages(this);

        mMessageAdapter.notifyDataSetChanged();

    }                                                                                        // cal modificar coses aqui

    private void sendMessage() {

        String messageText = mChatBox.getText().toString();

        if (!messageText.isEmpty()) {
            RestAPIManager.getInstance().sendMessage(new Message(messageText, profile, destprofile, (nextidmessage+500)), this);
            System.out.println("ssss "+(nextidmessage+300));
            messageList.add(new Message(messageText, profile, destprofile, nextidmessage));
            mMessageAdapter.notifyDataSetChanged();
        }

        mChatBox.setText(null);
        if(messageList.size()> 10) {
            mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
        }
    }

    private List<Message> getChatData() {
        return messageList;
    }


    @Override
    public void onGetMessages(ArrayList<Message> messages) {
        //if (init) {
        messageList.clear();

            for (Message m : messages) {
                //if ((m.getSender().getId() == userid && m.getReciver().getId() == myid) || (m.getSender().getId() == myid && m.getReciver().getId() == userid)) {                  //userid y myid no se reciben bien hay q conseguirlas bien
                if ((m.getSender().getDisplayName().equals(profile.getDisplayName()) && m.getReciver().getDisplayName().equals(destprofile.getDisplayName())) || (m.getReciver().getDisplayName().equals(profile.getDisplayName()) && m.getSender().getDisplayName().equals(destprofile.getDisplayName()))) {                  //userid y myid no se reciben bien hay q conseguirlas bien
                    messageList.add(m);
                    System.out.println("Sender: " + m.getSender().getDisplayName() + "     Reciver: " + m.getReciver().getDisplayName());
                }


                System.out.println("Sender: " + m.getSender().getDisplayName() + "     Reciver: " + m.getReciver().getDisplayName());
            }
            init = false;
        //} else {
           // messageList.add(messages.get(messages.size() - 1));
        //}
        nextidmessage = messages.size();
        if (messageList.size()!=0) {
            mMessageRecycler.smoothScrollToPosition(messageList.size() - 1);
        }
        mMessageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSendMessage() {
        System.out.println("ENVIAT-----------");
        getChatMessages();
    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println(t.getLocalizedMessage());
        System.out.println(t.getMessage());
        System.out.println("NO ENVIAT");
    }

    @Override
    public void onStop () {
        super.onStop();
        t.cancel();
    }

}