package com.marcllort.tinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends Activity {
    private ImageButton profileBtn;
    private ImageButton mainBtn;
    private ListView matchList;
    private EditText filter ;
    ArrayList<String> names=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        matchList = findViewById(R.id.matchList);
        filter = findViewById(R.id.searchBar);
        topBarSetup();

        matchList = findViewById(R.id.matchList);
        names = new ArrayList<>();
        names.add("Alex");
        names.add("Almansa");
        names.add("Marc");
        names.add("Paula");
        names.add("Javo");

        adapter = new ArrayAdapter(this,R.layout.activity_searchlayout,names);
        matchList.setAdapter(adapter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (SearchActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //matchList.add


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
