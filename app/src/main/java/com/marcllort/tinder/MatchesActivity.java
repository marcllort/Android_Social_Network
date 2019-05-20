package com.marcllort.tinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.marcllort.tinder.API.ConnectionsCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.MyProfile;

import java.util.ArrayList;

public class MatchesActivity extends Activity implements ConnectionsCallBack {


    private ImageButton profileBtn;
    private ImageButton chatButton;
    private ImageButton mainBtn;
    private ListView matchList;

    ArrayList<String> listItems = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        RestAPIManager.getInstance().getConnections(this);
        topBarSetup();
        listSetup();

    }


    public void addItems(String user) {
        listItems.add(user);
        adapter.notifyDataSetChanged();
    }

    private void topBarSetup() {

        profileBtn = findViewById(R.id.btn_profile);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent profileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(profileIntent);
            }
        });

        mainBtn = findViewById(R.id.btn_main);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatButton = findViewById(R.id.btn_matches);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void listSetup() {
        /*for (int i = 0; i < 5; i++) {                                                                       // Aqui farem el get de els users amb qui tenim connection
            listItems.add("Marc");
            listItems.add("Paula");
            listItems.add("Javo");
            listItems.add("Alex");
            listItems.add("Marcel");
        }*/


    }

    @Override
    public void onGetConnections(final ArrayList<MyProfile> users) {
        for (MyProfile i : users) {
            System.out.println(i.getDisplayName());
            listItems.add(i.getDisplayName());
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);


        matchList = findViewById(R.id.matchList);
        matchList.setAdapter(adapter);


        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                chatIntent.putExtra("login", listItems.get(position));                                                                  // passar el nom de usuari
                chatIntent.putExtra("userid", users.get(position).getId());                                                                              // cal posar el id del user
                startActivity(chatIntent);
            }
        });

    }

    @Override
    public void onFailure(Throwable t) {
        new AlertDialog.Builder(this)
                .setTitle("Users not found")
                .setMessage(t.getMessage())


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
