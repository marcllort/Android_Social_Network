package com.marcllort.tinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MatchesActivity extends Activity {


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
        for (int i = 0; i < 5; i++) {                                                                       // Aqui farem el get de els users amb qui tenim connection
            listItems.add("Marc");
            listItems.add("Paula");
            listItems.add("Javo");
            listItems.add("Alex");
            listItems.add("Marcel");
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
                chatIntent.putExtra("userid", 20);                                                                              // cal posar el id del user
                startActivity(chatIntent);
            }
        });
    }

}
