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
    private ImageButton mainBtn;
    private ListView matchList;

    ArrayList<String> listItems = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        topBarSetup();
        for (int i =0; i < 5; i++) {
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
        //matchList.add

        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //Toast.makeText(getBaseContext(), names.get(position), Toast.LENGTH_LONG).show();
                Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                //System.out.println("-------- "+ id + "---------" + names.get(position).getDisplayName());

                chatIntent.putExtra("login", listItems.get(position));
                startActivity(chatIntent);
            }
        });


    }

    public void addItems(View v) {
        listItems.add("Clicked :");
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

    }

}
